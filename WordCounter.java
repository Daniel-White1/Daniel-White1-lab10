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
    
    public static int processText (StringBuffer text, String stopword) throws TooSmallTexts, InvalidStopwordException{
        //Counter to track how many words are in it
        int wordCounter = 0;
        //So I found a neat way to trigger this .
        //It sets the variable foundStopword or not depending on if stopword was inputted into the method
        boolean foundStopword = (stopword == null);

        //Regex is the pattern of words that we need to search for to count a single word
        //While stopRegex is the search word we are looking for to stop the code
        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
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
            throw new InvalidStopwordException();
        }

        if (wordCounter < 5) {
            throw new TooSmallTexts();
        }
        return wordCounter;
    }
    //This method processes a text file into a string buffer and returns it
    //If the path to a file can not be found than repeat asking for the file
    //If the file is empty than return a emptyfileexception error before continuing with the code (which will then cause a 
    // too small error next)
    public static StringBuffer processFile(String path) throws EmptyFileException{
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
        //Variables to track in main
        Scanner scanObject = new Scanner(System.in);
        WordCounter wordCounter = new WordCounter();

        StringBuffer text = new StringBuffer();
        String stopWord = null;
        int wordCount = -1;
        int optionChoice = -1;

        //So we are going to reorganize this into a while loop
        while (true) {
            //This block is about getting one or two to finish this off
            System.out.println("Enter 1 to process a file\nEnter 2 to process text");
            try {
                optionChoice = Integer.parseInt(scanObject.nextLine());
                if (optionChoice == 1 || optionChoice == 2) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid option. Please enter one or two");
            }
        }

        //Sets up the option choices for 1
        //Basically just asks for the file and if it is empty it sets the text to be empty
        if (optionChoice == 1) {
            System.out.println("Please enter the path to the file.");
            String filePath = scanObject.nextLine();
            try{
                text = wordCounter.processFile(filePath);
            } catch (EmptyFileException e) {
                System.out.println("File is empty setting text to empty");
                text = new StringBuffer("");
            }
        }

        //Sets up the option 2 choice
        //Basically just asks for the text to be inputeded in with a terminal string thingy
        if (optionChoice == 2) {
            System.out.println("Please enter the text you wish to process.");
            text = new StringBuffer(scanObject.nextLine());
        }

        //Now we set it up to take in a input for stopword
        System.out.println("Input in the stopword (leave blank for no stopword)");
        stopWord = scanObject.nextLine();
        if (stopWord.isBlank() == true) {
            stopWord = null;
        }
        //Now we try to process the text and deal with errors and stuff
        try {
            wordCount = wordCounter.processText(text, stopWord);
            System.out.println("Number of words in the text: " + wordCount);
        } catch (InvalidStopwordException e) {
            //So we need to re-enter in the stopword if we got that error
            System.out.println("Enter a new stopword please:");
            stopWord = scanObject.nextLine();
            try{
                wordCount = wordCounter.processText(text, stopWord);
                System.out.println("Yippie second try worked: " + wordCount);
            } catch (InvalidStopwordException secondE){
                System.out.println("Second try failed");
            } catch(TooSmallTexts secondE){
                System.out.println("Text is too small");
            }
        } catch (TooSmallTexts e){
            System.out.println("Text is too small");
        }

        System.out.println("Program hopefully hasent broken");
    }
}
