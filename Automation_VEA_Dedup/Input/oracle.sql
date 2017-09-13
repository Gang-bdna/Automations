SET SERVEROUTPUT ON size unlimited

DECLARE
l_task_id   NUMBER;
l_tablename VARCHAR2(50);
STR       VARCHAR(28000);
--
TABLE_DOES_NOT_EXIST EXCEPTION;
PRAGMA EXCEPTION_INIT(TABLE_DOES_NOT_EXIST, -942);
--
BEGIN

l_task_id:= NULL;
l_tablename:= NULL;

dbms_output.put_line('TASK_ID '||TO_CHAR(l_task_id));
IF l_task_id IS NULL THEN
  BEGIN
    SELECT NAME_PREFIX
     INTO l_tablename
     FROM (SELECT TASK_ID,NAME_PREFIX, row_number() over (order by TASK_ID desc) rn
            FROM bdna.bms_task
            WHERE TASK_TYPE='NORMALIZE'
           ) BMS
     WHERE rn = 1;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      dbms_output.put_line('TASK_ID ' || to_char(l_task_id) ||' does not exist');
      RAISE;
   END;
ELSIF l_task_id IS NOT NULL THEN
   BEGIN
     SELECT DISTINCT NAME_PREFIX
       INTO l_tablename
       FROM bdna.bms_task
      WHERE TASK_ID =l_task_id
       AND TASK_TYPE='NORMALIZE';
   EXCEPTION
    WHEN NO_DATA_FOUND THEN
      dbms_output.put_line('TASK_ID ' || to_char(l_task_id) ||' does not exist');
      RAISE;
    WHEN OTHERS THEN
     dbms_output.put_line('OTHER EXCEPTION');
     raise;
   END;
END IF;

dbms_output.put_line('TABLENAME '||TO_CHAR(l_tablename));

IF l_tablename IS NOT NULL THEN
 --
 dbms_output.put_line('TABLENAME is not null');
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_ALLDEDUP1' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 --
 STR:='CREATE TABLE TMP_ALLDEDUP1(HOSTID integer, PROD_RID integer,EDITIONS_RID integer,VERSIONS_RID integer,
                                  VERGROUPS_RID integer,VER_ORDER integer,EDI_ORDER integer,HIDDEN integer,
								  ver_coexists integer,edi_coexists integer)';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 STR:='INSERT INTO TMP_ALLDEDUP1  
                            SELECT P.HOSTID, P.PROD_RID,P.EDITIONS_RID,P.VERSIONS_RID,P.VERGROUPS_RID,
                                   V.VER_ORDER,E.EDI_ORDER,HIDDEN,VER_COEXISTS,EDI_COEXISTS
							from '|| l_tablename|| '_P p
						    left join v_versions v on p.versions_rid = v.VERSION_ID
						    left join v_editions e on p.editions_rid = e.EDITION_ID';
							
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 COMMIT;
 --
 -------------------
	--BEGIN Version Deduping Issue
 ------------------
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_VERSION2' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 STR:='CREATE TABLE TMP_VERSION2(HOSTID integer, PROD_RID integer)';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
	
 STR:='INSERT INTO TMP_VERSION2 ';
 STR:=STR || 'select hostid,prod_rid from (
 SELECT DISTINCT P.HOSTID, P.PROD_RID 
                      ,case when p.versions_rid = t1.versions_rid and p.editions_rid = t1.editions_rid then ''true''         --distinct 
                                         when p.vergroups_rid = t1.vergroups_rid and t1.VER_ORDER >= p.VER_ORDER    then ''true''        --get max version
	                                     when p.vergroups_rid != t1.vergroups_rid and (t1.VER_ORDER >= p.VER_ORDER or (t1.editions_rid = p.editions_rid and p.editions_rid = -1)) then ''true''  --get max version
	                                     when p.vergroups_rid != t1.vergroups_rid and (t1.VER_ORDER >= p.VER_ORDER or t1.editions_rid != p.editions_rid)  then ''true''     --get max version
	                                    -- when p.ver_coexists = 0 and p.edi_coexists =1 and t1.VER_ORDER > p.VER_ORDER   then ''true''
	                                end deduping ';
 STR:=STR || 'FROM TMP_ALLDEDUP1 P ';
 STR:=STR || 'INNER JOIN TMP_ALLDEDUP1 T1 ON  T1.HOSTID=P.HOSTID ';
 STR:=STR || 'AND T1.PROD_RID=P.PROD_RID ';
 STR:=STR || 'WHERE P.HIDDEN>0  AND T1.HIDDEN = 0 AND p.VERSIONS_RID<>-1) t where nvl(deduping,''#'') != ''true''';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_VERSION3' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 STR:='CREATE TABLE TMP_VERSION3(HOSTID integer, PROD_RID integer)';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
	
 STR:='INSERT INTO TMP_VERSION3 ';
 STR:=STR || 'SELECT DISTINCT P2.HOSTID, P2.PROD_RID ';
 STR:=STR || 'FROM '|| l_tablename|| '_P P2 ';
 STR:=STR || 'INNER JOIN TMP_VERSION2 T2 ON  T2.HOSTID=P2.HOSTID ';
 STR:=STR || 'AND T2.PROD_RID=P2.PROD_RID ';
 STR:=STR || 'WHERE P2.HIDDEN=0 AND VERSIONS_RID=-1';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 
 STR:='SELECT PRI_MARKETVERSION.PRI, P3.HOSTID, P3.HIDDEN, P3.* FROM '|| l_tablename|| '_P P3 ';
 STR:=STR || 'INNER JOIN TMP_VERSION3 T3 ON T3.HOSTID=P3.HOSTID ';
 STR:=STR || 'AND T3.PROD_RID=P3.PROD_RID ';
 STR:=STR || 'LEFT JOIN PRI_MARKETVERSION ON  PRI_MARKETVERSION.VERSIONS_RID=P3.VERSIONS_RID ';
 STR:=STR || 'AND PRI_MARKETVERSION.PROD_RID=P3.PROD_RID ';
 STR:=STR || 'ORDER BY P3.HOSTID, P3.PROD_RID ';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;

 -------------------
	--END Version Deduping Issue
 ------------------
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_VERSION3' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 --
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_VERSION2' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 --
 -------------------
	--BEGIN Edition Deduping Issue
 ------------------
 
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_EDITION2' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 --
 STR:='CREATE TABLE TMP_EDITION2(HOSTID integer, PROD_RID integer)';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;

 STR:='INSERT INTO TMP_EDITION2 ';
 STR:=STR || 'select hostid,prod_rid from (
 SELECT DISTINCT P.HOSTID, P.PROD_RID 
                      --,p.editions_rid,p.versions_rid,p.vergroups_rid,p.ver_order,p.edi_order
					 ,case when p.versions_rid = t1.versions_rid and p.editions_rid = t1.editions_rid then ''true''         --distinct 
					       when p.vergroups_rid = t1.vergroups_rid and t1.edi_order >= p.edi_order    then ''true''        --get max version
					       when p.vergroups_rid != t1.vergroups_rid and (t1.edi_order >= p.edi_order or (t1.editions_rid = p.editions_rid )) then ''true''
	                       when p.vergroups_rid != t1.vergroups_rid and (t1.edi_order >= p.edi_order or t1.editions_rid != p.editions_rid)  then ''true'' 
	                       -- when p.ver_coexists = 0 and p.edi_coexists =1 and t1.edi_order > p.edi_order   then ''true''
	                     end deduping ';
 STR:=STR || 'FROM TMP_ALLDEDUP1 P ';
 STR:=STR || 'INNER JOIN TMP_ALLDEDUP1 T1 ON  T1.HOSTID=P.HOSTID ';
 STR:=STR || 'AND T1.PROD_RID=P.PROD_RID ';
 STR:=STR || 'WHERE P.HIDDEN>0 AND T1.HIDDEN = 0 and P.EDITIONS_RID<>-1) t where nvl(deduping,''#'') != ''true''';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_EDITION3' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 --
 STR:='CREATE TABLE TMP_EDITION3(HOSTID integer, PROD_RID integer)';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 
 STR:='INSERT INTO TMP_EDITION3 ';
 STR:=STR || 'SELECT DISTINCT P2.HOSTID, P2.PROD_RID ';
 STR:=STR || 'FROM '|| l_tablename|| '_P P2 ';
 STR:=STR || 'INNER JOIN TMP_EDITION2 T2 ON  T2.HOSTID=P2.HOSTID ';
 STR:=STR || 'AND T2.PROD_RID=P2.PROD_RID ';
 STR:=STR || 'WHERE P2.HIDDEN=0 AND EDITIONS_RID=-1';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 
 STR:='SELECT PRI_MARKETVERSION.PRI, P3.HOSTID, P3.HIDDEN, P3.* FROM '|| l_tablename|| '_P P3 ';
 STR:=STR || 'INNER JOIN TMP_EDITION3 T3 ON T3.HOSTID=P3.HOSTID ';
 STR:=STR || 'AND T3.PROD_RID=P3.PROD_RID ';
 STR:=STR || 'LEFT JOIN PRI_MARKETVERSION ON PRI_MARKETVERSION.VERSIONS_RID=P3.VERSIONS_RID ';
 STR:=STR || 'AND PRI_MARKETVERSION.PROD_RID=P3.PROD_RID ';
 STR:=STR || 'ORDER BY P3.HOSTID, P3.PROD_RID ';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
  
 -------------------
	--END Edition Deduping Issue
	-------------------
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_EDITION3' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 --
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_EDITION2' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 --
 -------------------
	--BEGIN Deduping Issue - All hidden
 ------------------
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_ALLHIDDEN2' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 
 STR:='CREATE TABLE TMP_ALLHIDDEN2(HOSTID integer, PROD_RID integer)';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;

 STR:='INSERT INTO TMP_ALLHIDDEN2 ';
 STR:=STR || 'SELECT DISTINCT P.HOSTID, P.PROD_RID ';
 STR:=STR || 'FROM '|| l_tablename|| '_P P ';
 STR:=STR || 'INNER JOIN TMP_ALLDEDUP1 T1 ON  T1.HOSTID=P.HOSTID ';
 STR:=STR || 'AND T1.PROD_RID=P.PROD_RID ';
 STR:=STR || 'WHERE P.HIDDEN=0 ';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 
 --SELECT * FROM TMP_ALLHIDDEN2
	
 STR:='SELECT PRI_MARKETVERSION.PRI, P2.HOSTID, P2.HIDDEN, P2.* ';
 STR:=STR || 'FROM '|| l_tablename|| '_P P2 ';
 STR:=STR || 'INNER JOIN (select hostid,prod_rid from TMP_ALLDEDUP1 group by hostid,prod_rid having count(*) > 1  union select hostid,prod_rid from TMP_ALLDEDUP1 where prod_rid in (8746125,1141947,23089633) group by hostid,prod_rid) T1 ON  T1.HOSTID=P2.HOSTID ';
 STR:=STR || 'AND T1.PROD_RID=P2.PROD_RID ';
 STR:=STR || 'LEFT JOIN TMP_ALLHIDDEN2	T2  ON  T2.HOSTID=P2.HOSTID ';
 --STR:=STR || 'AND T2.PROD_RID=P2.PROD_RID ';
 STR:=STR || 'AND (CASE T2.PROD_RID WHEN 1142671 THEN 8746125 WHEN 9 THEN 1141947 WHEN 6344595 THEN 1141947 WHEN 15709758 THEN 1141947 WHEN 1142579 THEN 23089633 ELSE T2.PROD_RID END) = (CASE P2.PROD_RID WHEN 1142671 THEN 8746125 WHEN 9 THEN 1141947 WHEN 6344595 THEN 1141947 WHEN 15709758 THEN 1141947 WHEN 1142579 THEN 23089633 ELSE P2.PROD_RID END) ';
 STR:=STR || 'LEFT JOIN PRI_MARKETVERSION on PRI_MARKETVERSION.VERSIONS_RID=P2.VERSIONS_RID ';
 STR:=STR || 'WHERE T2.HOSTID IS NULL AND T2.PROD_RID IS NULL ';
 STR:=STR || 'AND (P2.PROD_RID<>23089633 AND P2.ORIGINATE_FROM =2) ';
 STR:=STR || 'ORDER BY P2.HOSTID, P2.PROD_RID ';
 dbms_output.put_line('===================================');
 dbms_output.put_line(STR);
 EXECUTE IMMEDIATE STR;
 
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_ALLHIDDEN2' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 
 BEGIN	
   EXECUTE IMMEDIATE 'DROP TABLE TMP_ALLDEDUP1' ;
 EXCEPTION
 WHEN TABLE_DOES_NOT_EXIST THEN NULL;
 END;
 --
 dbms_output.put_line('------------------------------------------------------------------------');
 dbms_output.put_line('------------------------------------------------------------------------');
 dbms_output.put_line('If any data are suspicious , please provide the results of the query below');
 dbms_output.put_line('SELECT HOSTID, HIDDEN, PROD_RID,p.*  FROM '|| l_tablename|| '_P p ORDER BY 1,3');
 dbms_output.put_line('------------------------------------------------------------------------');
 dbms_output.put_line('------------------------------------------------------------------------');
END IF;

END;
/
show err