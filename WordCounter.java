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

    int processText(StringBuffer text, String stopword) throws TooSmallTextException, InvalidStopWordException{
        //Counter to track how many words are in it
        int wordCounter = 0;
        //So I found a neat way to trigger this .
        //It sets the variable foundStopword or not depending on if stopword was inputted into the method
        boolean foundStopword = (stopword == null);

        //Regex is the pattern of words that we need to search for to count a single word
        //While stopRegex is the search word we are looking for to stop the code
        Pattern regex = Pattern.compile("[a-zA-z_0-9']+");
        Matcher regexMatcher = regex.matcher(text);   
        
        //While the matcher is true (aka) there is a word then it adds one to the  wordcouter 
        while (regexMatcher.find() == true) {
            String currentWord = regexMatcher.group();

            wordCounter++;
            //If the stopword exists and we find it using .equalsIgnoreCase
            //It then breaks out of the while loop
            if (stopword != null && currentWord.equalsIgnoreCase(stopword)) {
                foundStopword = true;
                break;
            }
        }

        if (foundStopword == false && stopword != null) {
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
        Scanner scanner = new Scanner(System.in);
        Path filePath = null;
        String fileContent = null;

        //While loop because we need to keep checking to see if we have the correct path or not
        //However if we dont have the correct path then we need to re-enter the path until it can be opened
        // Once we do we can read the file to see if we have a empty file or not
        while(true) {
            try {
                filePath = Path.of(path);
                fileContent = Files.readString(filePath);
                break;
            } catch (IOException | InvalidPathException e) {
                System.out.println("Could not open the file please enter in the file path");
                path = scanner.nextLine();
            }
        }

        //If the file is blank (different then null so thats why I used the .isBlank method) then throw the emptyfileexception
        if (fileContent.isBlank()) {
            throw new EmptyFileException();
        }
        return new StringBuffer(fileContent);
    }

    //This  miin method asks a user for 1 or 2
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
