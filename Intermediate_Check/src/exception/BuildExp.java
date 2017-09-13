package exception;

public class BuildExp extends RuntimeException {

    public BuildExp(){
        super();
    }

    public BuildExp(String message){
        super(message);
    }
}
