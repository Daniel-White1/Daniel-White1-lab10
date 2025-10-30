import java.io.IOException;

public class EmptyFileException extends IOException{
    public EmptyFileException (){
        System.out.println("Empty File Exception: There is nothing in the file");
    }
}