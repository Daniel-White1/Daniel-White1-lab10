import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    //This is the method to count the number of words in a text file
    //It reads the entire file if stopword is null otherwise it stops **on** that stopword
    //method returns the word count if it is greater than than or equal to five otherwise it returns toosmalltext error
    int processText(StringBuffer text){
        int wordCounter = 0;

        //Regex is the pattern of words that we need to search for to count a single word
        //While stopRegex is the search word we are looking for to stop the code
        Pattern regex = Pattern.compile("[a-zA-z_0-9]+");
        Matcher regexMatcher = regex.matcher(text);   
        
        //While the matcher is true (aka) there is a word then it adds one to the  
        while (regexMatcher.find() == true) {
            wordCounter++;
        }

        //Once the matcher reaches the end of the file it checks to see if it is too small before returning the count.
        if (wordCounter < 5) {
            throw TooSmallTextException;
        }
        return wordCounter;
    }
    int processText(StringBuffer text, String stopword){
        //Counter to track how many words are in it
        int wordCounter = 0;
        boolean foundStopword = false;

        //Regex is the pattern of words that we need to search for to count a single word
        //While stopRegex is the search word we are looking for to stop the code
        Pattern regex = Pattern.compile("[a-zA-z_0-9]+");
        Matcher regexMatcher = regex.matcher(text);   
        
        //While the matcher is true (aka) there is a word then it adds one to the  
        while (regexMatcher.find() == true) {
            wordCounter++;
            //If the word found equals the stop word then break out of the loop
            if (regexMatcher.group() == stopword) {
                foundStopword = true;
                break;
            }
        }

        if (stop) {
            
        }
    }

    //This method processes a text file into a string buffer and returns it
    //If the path to a file can not be found than repeat asking for the file
    //If the file is empty than return a emptyfileexception error before continuing with the code (which will then cause a 
    // too small error next)
    StringBuffer processFile(String path){

    }

    //This main method asks a user for 1 or 2
    //If it is 1 than it asks for a string to the path for a file
    //If it is 2 than it directly asks for the text to reathough
    //If it isnt one or two it asks for them to input it again

    //Then you input the text in the first command line argument and the stopword in the second command line argument
    //If the stopword isnt found in the text file then propt the user to ask again for the stopline 
    public static void main(String[] args) {
        
    }
}
