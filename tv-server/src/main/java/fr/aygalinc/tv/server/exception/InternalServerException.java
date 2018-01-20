package fr.aygalinc.tv.server.exception;

/**
 * Created by aygalinc on 20/01/18.
 */
public class InternalServerException extends Exception{

    public InternalServerException(Throwable cause){
        super(cause);
    }

    public InternalServerException(){
        super();
    }

}
