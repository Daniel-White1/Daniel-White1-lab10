import java.io.IOException;

public class EmptyFileException extends IOException{
    public EmptyFileException (String a){
        super(a);
    }
}