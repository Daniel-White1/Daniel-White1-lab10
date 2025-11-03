import java.io.IOException;

public class EmptyFileException extends IOException{
    public EmptyFileException (String fileName){
        super(fileName + " was empty");
    }
}