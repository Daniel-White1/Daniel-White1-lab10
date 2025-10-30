import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    //This is the method to count the number of words in a text file
    //It reads the entire file if stopword is null otherwise it stops **on** that stopword
    //method returns the word count if it is greater than than or equal to five otherwise it returns toosmalltext error
    int processText(StringBuffer text) throws TooSmallTextException {
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
            throw new TooSmallTextException();
        }
        return wordCounter;
    }
    
    int processText(StringBuffer text, String stopword) throws TooSmallTextException, InvalidStopWordException{
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

        if (foundStopword == false) {
            throw new InvalidStopWordException();
        }

        if (wordCounter < 5) {
            throw new TooSmallTextException();
        }

        return wordCounter;
    }

    //This method processes a text file into a string buffer and returns it
    //If the path to a file can not be found than repeat asking for the file
    //If the file is empty than return a emptyfileexception error before continuing with the code (which will then cause a 
    // too small error next)
    StringBuffer processFile(String path) throws EmptyFileException{
        //Converts the path of the string into a path object.
        Path filePath = Path.of(path);
        String fileContent = null;
        //Try Catch to make sure you can actually read the file
        try {
            fileContent = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileContent == null) {
            throw new EmptyFileException();
        }
        //returns the new StringBuffer of the file content
        return new StringBuffer(fileContent);
    }

    //This main method asks a user for 1 or 2
    //If it is 1 than it asks for a string to the path for a file
    //If it is 2 than it directly asks for the text to reathough
    //If it isnt one or two it asks for them to input it again

    //Then you input the text in the first command line argument and the stopword in the second command line argument
    //If the stopword isnt found in the text file then propt the user to ask again for the stopline 
    public static void main(String[] args) {
        //Scanner to input text into the terminal
        Scanner scanObject = new Scanner(System.in);
        System.out.println("Enter 1 to process a file \nEnter 2 to process text directly");
        String input = scanObject.nextLine();

        while (Integer.parseInt(input) != 1 || Integer.parseInt(input) != 2) {
            System.out.println("Invalid Input: Please enter either a 1 or a two");
            input = scanObject.nextLine();
        }

        //This runs the code looking for a text file
        if (Integer.parseInt(input) == 1){
            System.out.println("Please enter the path to the text file");
            String textPath = scanObject.nextLine();
            System.out.println("Please enter the stop word. Leave empty to continue without a stop word");
            String stopWord = scanObject.nextLine();

            if (stopWord == null) {
                processFile(textPath);
            }
        }

        //This runs the code using a direct string input
        if (Integer.parseInt(input) == 2) {
            
        }
    }
}
