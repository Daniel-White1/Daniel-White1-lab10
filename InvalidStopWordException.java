public class InvalidStopWordException extends Exception{
    public InvalidStopWordException(){
        System.out.println("Invalid Stop Word. The stop word is not in the text");
    }
}
