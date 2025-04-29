package exception;

public class NotValidElementKind extends RuntimeException{

    public NotValidElementKind(String... message){
        super(String.join("", message));
    }

    public NotValidElementKind(String message){
        super(message);
    }
}
