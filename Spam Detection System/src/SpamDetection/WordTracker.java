package SpamDetection;

/*
 *
 * new instance of class is created where path of folder is given
 * constructor then initializes all of the needed variables based on the given folder
 * Class explanation under development
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class WordTracker {

    // declare all variables
    private static File folder;										// store test folder for collecting data

    private static File folder1;									// store folder 1 of 2 for collecting data
    private static File folder2;									// store folder 2 of 2 for collecting data

    private static File[] files;									// store files of folder in an array

    private static File[] files1;									// store files of folder1 in an array
    private static File[] files2;									// store files of folder2 in an array

    private static int size;										// store the number of files in the folder

    private static Map<String, Integer> wordTracker; 				// tracks how many files have the word
    private static Map<String, Boolean> booleanTracker; 			// tracks if that word has already been counted in a single file
    private static Map<String, Float> PrWX; 						// tracks probability of each word (PrWS / PrWH)
    private static Map<String, Float> PrWXX; 						// records the opposite of the PrWX already here.
    private static Map<String, Float> PrSW; 						// records overall probability of each word being spam




    // Constructors
    WordTracker() {													// Default constructor
        wordTracker = new TreeMap<>();
        booleanTracker = new TreeMap<>();
        PrWX = new TreeMap<>();
        PrWXX = new TreeMap<>();
        PrSW = new TreeMap<>();
    }

    WordTracker(File folder) {										// Constructor for one folder
        wordTracker = new TreeMap<>();
        booleanTracker = new TreeMap<>();
        PrWX = new TreeMap<>();
        PrWXX = new TreeMap<>();
        PrSW = new TreeMap<>();

        this.folder = folder;
        this.files = folder.listFiles();

    }


    WordTracker(File folder1, File folder2) {						// Constructor for two folders and merges the files of both into one array so they can all be handled at once
        wordTracker = new TreeMap<>();
        booleanTracker = new TreeMap<>();
        PrWX = new TreeMap<>();
        PrWXX = new TreeMap<>();
        PrSW = new TreeMap<>();

        this.folder1 = folder1;
        this.folder2 = folder2;

        this.files1 = folder1.listFiles();
        this.files2 = folder2.listFiles();

        this.size = this.files1.length + this.files2.length;                                                      // getting size of folder

        this.files = new File[size];                                                                              // assign files array

        System.arraycopy(this.files1, 0, this.files, 0, this.files1.length);                        // copy files1 into files
        System.arraycopy(this.files2, 0, this.files, this.files1.length, this.files2.length);               // copy files2 into files

    }




    // set and get methods for folder
    public void setFolder(File folder) { this.folder = folder; }
    public File getFolder() { return this.folder; }

    // set and get methods for folder1
    public void setFolder1(File folder1) { this.folder1 = folder1; }
    public File getFolder1() { return this.folder1; }

    // set and get methods for folder2
    public void setFolder2(File folder2) { this.folder2 = folder2; }
    public File getFolder2() { return this.folder2; }

    // set and get methods for files
    public void setFiles(File[] files) { this.files = files; }
    public File[] getFiles() { return this.files; }

    // set and get methods for files1
    public void setFiles1(File[] files1) { this.files1 = files1; }
    public File[] getFiles1() { return this.files1; }

    // set and get methods for files2
    public void setFiles2(File[] files2) { this.files2 = files2; }
    public File[] getFiles2() { return this.files2; }

    // set and get methods for size
    public void setSize(int size) { this.size = size; }
    public int getSize() { return this.size; }

    // set and get methods for wordTracker
    public void setWordTracker(Map<String, Integer> wordTracker) { this.wordTracker = wordTracker; }
    public Map<String, Integer> getWordTracker() { return this.wordTracker; }

    // set and get methods for booleanTracker
    public void setBooleanTracker(Map<String, Boolean> booleanTracker) { this.booleanTracker = booleanTracker; }
    public Map<String, Boolean> getBooleanTracker() { return this.booleanTracker; }

    // set and get methods for PrWX
    public void setPrWX(Map<String, Float> PrWX) { this.PrWX = PrWX; }
    public Map<String, Float> getPrWX() { return this.PrWX; }

    // set and get methods for PrSW
    public void setPrSW(Map<String, Float> PrSW) {this.PrSW = PrSW; }
    public Map<String, Float> getPrSW() { return this.PrSW; }




    // checks if word is valid
    public static boolean isValidWord(String token) {
        String allLetters = "^[a-zA-Z]+$";

        return token.matches(allLetters);							// returns true if word is composed of only letters
    }

    // adds word to dictionary
    public static void addToken(String token) {
        wordTracker.put(token, 1);
        booleanTracker.put(token, true);
    }

    // collects words to form dictionary
    public static void scanFiles() throws FileNotFoundException {

        for (File file : files) {									// for each file in the folder

            Scanner fileScanner = new Scanner(file);				// create new scanner to go through the file
            String token = "";

            while(fileScanner.hasNext()) {							// while the scanner has something to scan
                token = fileScanner.next();							// get the word from the file

                if (isValidWord(token)) {							// if it is a valid word, check if the word counter map already has that word

                    if (!wordTracker.containsKey(token)) {			// if the map doesn't have the word, add it to the dictionary
                        addToken(token);
                    }

                    else {											// if the dictionary does have the word, check if it has already been scanned for that file
                        // (only counting how many files have the word, not how many times it is found)

                        if (booleanTracker.get(token) == false) {	// if it hasn't already been scanned in that file, increment the occurence in the dictionary
                            int previous = wordTracker.get(token);
                            wordTracker.put(token, previous+1);
                        }

                    }

                }

            }


            for (Map.Entry<String, Boolean> entry: booleanTracker.entrySet()) {
                String key = entry.getKey();						// reset the boolean map after every file, the map is used to check if a certain file has already had a word scanned,
                booleanTracker.put(key, false);						// so it needs to be reset to make sure the word is counted in future files
            }

        }
    }



    // Calculates PrWH / PrWS
    public void calculatePrWX() {

        for (Map.Entry<String, Integer> entry: wordTracker.entrySet()) {

            float probability = 0.00000f; 							// set initial value of probability

            String key = entry.getKey();							// get current set key
            int value = entry.getValue();							// get current set value

            probability = (float)((float)value / (float)size);		// calculate the probability based on the number of files the word was found in (value)
            // divided by the total number of files in the sample folder (size)

            this.PrWX.put(key, probability);						// insert the word probability key into the dictionary
        }

    }

    // Calculates PrSW based on input PrWH and PrWS
    public void calculatePrSW(Map<String, Float> PrWS, Map<String, Float> PrWH) {

        Map <String, Float> PrSW = new TreeMap<>();					// creating new empty PrSW map to be used

        for (Map.Entry<String, Float> entry : PrWS.entrySet()) {	// for each entry in PrWS

            String word = entry.getKey();							// variable to store key
            float WS = entry.getValue();							// variable to store value
            float WH = 0.00000f;										// variable, stores initial PrWH value,
            // assigned as 0.0000f because if the PrWS word isn't in PrWH,
            // then that would be the right number to put, to get the probability of that word only being in spam emails

            if (PrWH.containsKey(word)) {							// If PrWH contains the word in the PrWS dictionary
                WH = PrWH.get(word);								// get the value from PrWH
            }

            float SW = WS / (WS + WH);								// calculate probability for that word

            PrSW.put(word, SW);										// add word and probability to local PrSW

        }

        for (Map.Entry<String, Float> entry : PrWH.entrySet()) {	// checking for words that are in PrWH but not PrWS

            String word = entry.getKey();							// variable to store key

            if (!PrSW.containsKey(word)) {							// if the word isn't already in PrSW (
                // it is missing from the dictionary because it wasn't in PrWS so it was skipped)
                float SW = 0.00000f;								// if it wasn't in spam then its percent of being spam is zero
                PrSW.put(word, SW);									// adding word to dictionary
            }

        }

        this.PrSW = PrSW;											// finally set the class PrSW to be this PrSW

    }

    // save PrWX (PrWS or PrWH) to a file
    public static void outputPrWX(File output) throws IOException {
        System.out.println("Saving word counts to file: " + output.getAbsolutePath());

        if (!output.exists()) {										// if the file doesn't already exist
            output.createNewFile();									// create a new file with that name

            if (output.canWrite()) {								// if the file can be written to
                PrintWriter fileOutput = new PrintWriter(output);	// create a new PrintWrite to write to the file

                Set<String> keys = PrWX.keySet();					// get the set of keys from the dictionary map
                Iterator<String> keyIterator = keys.iterator();		// create an iterator to iterate through the map keys

                while(keyIterator.hasNext()) {
                    String key = keyIterator.next();				// get the key
                    float count = PrSW.get(key);					// get the value

                    fileOutput.println(key + " " + count);			// write the key : value pair to the file

                }

                fileOutput.close();									// close the file write

            }
        }

        else {
            System.out.println("Error: the output file already exists: " + output.getAbsolutePath());
        }

    }

    // save PrSW to a file
    public static void outputPrSW(File output) throws IOException {
        System.out.println("Saving word counts to file: " + output.getAbsolutePath());


        if (!output.exists()) {
            output.createNewFile();

            if (output.canWrite()) {
                PrintWriter fileOutput = new PrintWriter(output);

                Set<String> keys = PrSW.keySet();
                Iterator<String> keyIterator = keys.iterator();

                while(keyIterator.hasNext()) {
                    String key = keyIterator.next();
                    float count = PrSW.get(key);

                    fileOutput.println(key + " " + count);

                }

                fileOutput.close();

            }
        }

        else {
            System.out.println("Error: the output file already exists: " + output.getAbsolutePath());
        }

    }


}