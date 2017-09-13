USE BDNA

DECLARE @TASK_ID int
-------------------------------------------
-------------------------------------------
--Type your Task ID
--if NULL it will automatically selects the last TaskID
-------------------------------------------
-------------------------------------------
SET @TASK_ID=null

--select * from bms_task
-------------------------------------------

DECLARE @TABLENAME varchar(50)
SET @TABLENAME=NULL
IF @TASK_ID IS NULL
	SELECT TOP 1 @TABLENAME=NAME_PREFIX FROM BMS_TASK WHERE TASK_TYPE='NORMALIZE' ORDER BY TASK_ID DESC
ELSE 
	SELECT @TABLENAME=NAME_PREFIX FROM BMS_TASK WHERE TASK_ID=@TASK_ID
	
--SELECT @TABLENAME
IF @TABLENAME IS NOT NULL
BEGIN
	DECLARE @str varchar(8000)

	CREATE TABLE #TMP_ALLDEDUP1(HOSTID int, PROD_RID int,EDITIONS_RID integer,VERSIONS_RID integer,
                                  VERGROUPS_RID integer,VER_ORDER integer,EDI_ORDER integer,HIDDEN integer,
								  ver_coexists integer,edi_coexists integer)
	SET @str='INSERT INTO #TMP_ALLDEDUP1 
	                       SELECT P.HOSTID, P.PROD_RID,P.EDITIONS_RID,P.VERSIONS_RID,P.VERGROUPS_RID,
                                  V.VER_ORDER,E.EDI_ORDER,HIDDEN,VER_COEXISTS,EDI_COEXISTS
							from ' + @TABLENAME + '_P p
						    left join v_versions v on p.versions_rid = v.VERSION_ID
						    left join v_editions e on p.editions_rid = e.EDITION_ID'
	
	EXEC(@str)

	--SELECT * FROM #TMP_ALLDEDUP1
			
	-------------------
	--BEGIN Version Deduping Issue
	-------------------
	CREATE TABLE #TMP_VERSION2(HOSTID int, PROD_RID int)
	
	SET @str='INSERT INTO #TMP_VERSION2 '
	SET @str=@str+'select hostid,prod_rid from (
	SELECT DISTINCT P.HOSTID, P.PROD_RID 
	                       ,case when p.versions_rid = t1.versions_rid and p.editions_rid = t1.editions_rid then ''true''         --distinct 
                                 when p.vergroups_rid = t1.vergroups_rid and t1.VER_ORDER >= p.VER_ORDER    then ''true''        --get max version
	                             when p.vergroups_rid != t1.vergroups_rid and (t1.VER_ORDER >= p.VER_ORDER or (t1.editions_rid = p.editions_rid and p.editions_rid = -1)) then ''true''  --get max version
	                             when p.vergroups_rid != t1.vergroups_rid and (t1.VER_ORDER >= p.VER_ORDER or t1.editions_rid != p.editions_rid)  then ''true''     --get max version
	                             -- when p.ver_coexists = 0 and p.edi_coexists =1 and t1.VER_ORDER > p.VER_ORDER   then ''true''
	                        end deduping '
	SET @str=@str+'FROM #TMP_ALLDEDUP1 P '
	SET @str=@str+'INNER JOIN #TMP_ALLDEDUP1 T1 ON  T1.HOSTID=P.HOSTID '
	SET @str=@str+'AND T1.PROD_RID=P.PROD_RID '
	SET @str=@str+'WHERE P.HIDDEN>0  AND T1.HIDDEN = 0 AND p.VERSIONS_RID<>-1) t where isnull(deduping,''#'') != ''true'' '

	EXEC(@str)
	
	--SELECT * FROM #TMP_VERSION2
	
	CREATE TABLE #TMP_VERSION3(HOSTID int, PROD_RID int)
	
	SET @str='INSERT INTO #TMP_VERSION3 '
	SET @str=@str+'SELECT DISTINCT P2.HOSTID, P2.PROD_RID '
	SET @str=@str+'FROM '+@TABLENAME+'_P P2 '
	SET @str=@str+'INNER JOIN #TMP_VERSION2 T2 ON  T2.HOSTID=P2.HOSTID '
	SET @str=@str+'AND T2.PROD_RID=P2.PROD_RID '
	SET @str=@str+'WHERE P2.HIDDEN=0 AND VERSIONS_RID=-1 '

	EXEC(@str)
	
	--SELECT * FROM #TMP_VERSION3
	
	SET @str='SELECT PRI_MARKETVERSION.PRI, P3.HOSTID, P3.HIDDEN, P3.* FROM '+@TABLENAME+'_P P3 '
	SET @str=@str+'INNER JOIN #TMP_VERSION3 T3 ON T3.HOSTID=P3.HOSTID '
	SET @str=@str+'AND T3.PROD_RID=P3.PROD_RID '
	SET @str=@str+'LEFT JOIN PRI_MARKETVERSION ON  PRI_MARKETVERSION.VERSIONS_RID=P3.VERSIONS_RID '
	SET @str=@str+'AND PRI_MARKETVERSION.PROD_RID=P3.PROD_RID '
	SET @str=@str+'ORDER BY P3.HOSTID, P3.PROD_RID '

	EXEC(@str)

	-------------------
	--END Version Deduping Issue
	-------------------
	DROP TABLE #TMP_VERSION3
	DROP TABLE #TMP_VERSION2

	-------------------
	--BEGIN Edition Deduping Issue
	-------------------

	CREATE TABLE #TMP_EDITION2(HOSTID int, PROD_RID int)
	
	SET @str='INSERT INTO #TMP_EDITION2 '
	SET @str=@str+'select hostid,prod_rid from (
	SELECT DISTINCT P.HOSTID, P.PROD_RID 
	                        --,p.editions_rid,p.versions_rid,p.vergroups_rid,p.ver_order,p.edi_order
						   ,case when p.versions_rid = t1.versions_rid and p.editions_rid = t1.editions_rid then ''true''         --distinct 
								 when p.vergroups_rid = t1.vergroups_rid and t1.edi_order >= p.edi_order    then ''true''         --get max version
								 when p.vergroups_rid != t1.vergroups_rid and (t1.edi_order >= p.edi_order or (t1.editions_rid = p.editions_rid )) then ''true''
	                             when p.vergroups_rid != t1.vergroups_rid and (t1.edi_order >= p.edi_order or t1.editions_rid != p.editions_rid)  then ''true'' 
	                             -- when p.ver_coexists = 0 and p.edi_coexists =1 and t1.edi_order > p.edi_order   then ''true''
	                        end deduping '
	SET @str=@str+'FROM #TMP_ALLDEDUP1 P '
	SET @str=@str+'INNER JOIN #TMP_ALLDEDUP1 T1 ON  T1.HOSTID=P.HOSTID '
	SET @str=@str+'AND T1.PROD_RID=P.PROD_RID '
	SET @str=@str+'WHERE P.HIDDEN>0 AND T1.HIDDEN = 0 AND p.EDITIONS_RID<>-1) t where isnull(deduping,''#'') != ''true'' '
	
	EXEC(@str)
	
	--SELECT * FROM #TMP_EDITION2 
	
	CREATE TABLE #TMP_EDITION3(HOSTID int, PROD_RID int)
	
	SET @str='INSERT INTO #TMP_EDITION3 '
	SET @str=@str+'SELECT DISTINCT P2.HOSTID, P2.PROD_RID '
	SET @str=@str+'FROM '+@TABLENAME+'_P P2 '
	SET @str=@str+'INNER JOIN #TMP_EDITION2 T2 ON  T2.HOSTID=P2.HOSTID '
	SET @str=@str+'AND T2.PROD_RID=P2.PROD_RID '
	SET @str=@str+'WHERE P2.HIDDEN=0 AND EDITIONS_RID=-1 '

	EXEC(@str)
	
	--SELECT * FROM #TMP_EDITION3

	SET @str='SELECT PRI_MARKETVERSION.PRI, P3.HOSTID, P3.HIDDEN, P3.* FROM '+@TABLENAME+'_P P3 '
	SET @str=@str+'INNER JOIN #TMP_EDITION3 T3 ON T3.HOSTID=P3.HOSTID '
	SET @str=@str+'AND T3.PROD_RID=P3.PROD_RID '
	SET @str=@str+'LEFT JOIN PRI_MARKETVERSION ON PRI_MARKETVERSION.VERSIONS_RID=P3.VERSIONS_RID '
	SET @str=@str+'AND PRI_MARKETVERSION.PROD_RID=P3.PROD_RID '
	SET @str=@str+'ORDER BY P3.HOSTID, P3.PROD_RID '

	EXEC(@str)

	-------------------
	--END Edition Deduping Issue
	-------------------
	DROP TABLE #TMP_EDITION3
	DROP TABLE #TMP_EDITION2
	
	-------------------
	--BEGIN Deduping Issue - All hidden
	-------------------
	
	CREATE TABLE #TMP_ALLHIDDEN2(HOSTID int, PROD_RID int)
	
	SET @str='INSERT INTO #TMP_ALLHIDDEN2 '
	SET @str=@str+'SELECT DISTINCT P.HOSTID, P.PROD_RID '
	SET @str=@str+'FROM '+@TABLENAME+'_P P '
	SET @str=@str+'INNER JOIN #TMP_ALLDEDUP1 T1 ON  T1.HOSTID=P.HOSTID '
	SET @str=@str+'AND T1.PROD_RID=P.PROD_RID '
	SET @str=@str+'WHERE P.HIDDEN=0 '

	EXEC(@str)

	--SELECT * FROM #TMP_ALLHIDDEN2
	
	SET @str='SELECT PRI_MARKETVERSION.PRI, P2.HOSTID, P2.HIDDEN, P2.* '
	SET @str=@str+'FROM '+@TABLENAME+'_P P2 '
	SET @str=@str+'INNER JOIN (select hostid,prod_rid from #TMP_ALLDEDUP1 group by hostid,prod_rid having count(*) > 1  union select hostid,prod_rid from #TMP_ALLDEDUP1 where prod_rid in (8746125,1141947,23089633) group by hostid,prod_rid) T1 ON  T1.HOSTID=P2.HOSTID '
	SET @str=@str+'AND T1.PROD_RID=P2.PROD_RID '
	SET @str=@str+'LEFT JOIN #TMP_ALLHIDDEN2	T2  ON  T2.HOSTID=P2.HOSTID '
	--SET @str=@str+'AND T2.PROD_RID=P2.PROD_RID '
	SET @str=@str+'AND (CASE T2.PROD_RID WHEN 1142671 THEN 8746125 WHEN 9 THEN 1141947 WHEN 6344595 THEN 1141947 WHEN 15709758 THEN 1141947 WHEN 1142579 THEN 23089633 ELSE T2.PROD_RID END) = (CASE P2.PROD_RID WHEN 1142671 THEN 8746125 WHEN 9 THEN 1141947 WHEN 6344595 THEN 1141947 WHEN 15709758 THEN 1141947 WHEN 1142579 THEN 23089633 ELSE P2.PROD_RID END) '
	SET @str=@str+'LEFT JOIN PRI_MARKETVERSION on PRI_MARKETVERSION.VERSIONS_RID=P2.VERSIONS_RID '
	SET @str=@str+'WHERE T2.HOSTID IS NULL AND T2.PROD_RID IS NULL '
	SET @str=@str+'AND (P2.PROD_RID<>23089633 AND P2.ORIGINATE_FROM =2) '
	SET @str=@str+'ORDER BY P2.HOSTID, P2.PROD_RID '

	EXEC(@str)

	-------------------
	--END Deduping Issue - All hidden
	-------------------
	DROP TABLE #TMP_ALLHIDDEN2
	
	-------------------
	DROP TABLE #TMP_ALLDEDUP1
	
	PRINT('------------------------------------------------------------------------')
	PRINT('------------------------------------------------------------------------')
	PRINT('If any data are suspisious , please provide the results of the query below')
	PRINT('SELECT HOSTID, HIDDEN, PROD_RID, *  FROM '+@TABLENAME+'_P ORDER BY 1,3')
	PRINT('------------------------------------------------------------------------')
	PRINT('------------------------------------------------------------------------')
	
END