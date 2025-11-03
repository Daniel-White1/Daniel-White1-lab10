public class InvalidStopwordException extends Exception{
    public InvalidStopwordException(String string){
        super("Couldn't find stopword: " + string);
    }
}
