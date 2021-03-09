package SpamDetection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import static java.lang.Float.isInfinite;
import static java.lang.Float.isNaN;

public class SpamFilter {

    // declare variables
    private static File folder;
    private static File[] files;
    private static int size;                                                                        // stores the amount of emails in the folder for array size

    private static String[] fileNames;                                                              // stores file name of each email
    private static float[] spamProbabilities;                                                       // stores spam probabilities of each email
    private static String actualClass;                                                              // stores the class of the emails (spam / ham)
    private static String[] classGuess;

    private static Map<String, Float> PrSW;


    // constructors
    SpamFilter() {                                                                                  // Default constructor
        this.PrSW = new TreeMap<>();
    }

    SpamFilter(File folder, String actualClass, Map<String, Float> PrSW) {                          // Base constructor
        this.PrSW = new TreeMap<>();

        this.folder = folder;
        this.files = folder.listFiles();
        this.size = files.length;
        this.actualClass = actualClass;
        this.PrSW = PrSW;
        this.fileNames = new String[this.size];
        this.spamProbabilities = new float[this.size];
        this.classGuess = new String[this.size];

    }




    // set and get methods for the folder file
    public void setFolder(File folder) { this.folder = folder; }
    public File getFolder() { return this.folder; }

    // set and get methods for file array
    public void setFiles() { this.files = this.folder.listFiles(); }
    public File[] getFiles() { return this.files; }

    // set and get methods for size of arrays
    public void setSize() { this.size = this.files.length; }
    public int getSize() { return this.size; }

    // set and get methods for the map used to calculate the probability an email is spam
    public void setPrSW(Map<String, Float> PrsW) { this.PrSW = PrSW; }
    public Map<String, Float> getPrSW() { return this.PrSW; }

    // set and get methods for the class an email came from (ham or spam)
    public void setActualClass(String actualClass) { this.actualClass = actualClass; }
    public String getActualClass() { return this.actualClass; }

    // set and get methods for file names
    public static void addFileName(String fileName, int x) { fileNames[x] = fileName; }
    public String[] getFileNames() { return this.fileNames;	}

    // set and get methods for spam probabilities
    public static void addSpamProbability(Float spamProbability, int x) { spamProbabilities[x] = spamProbability; }
    public float[] getSpamProbabilities() { return this.spamProbabilities; }

    // set and get methods for class guesses
    public static void addClassGuess(String guess, int x) { classGuess[x] = guess; }
    public String[] getClassGuess() { return this.classGuess; }




    // checks if a word in an email is valid
    public static boolean isValidWord(String token) {
        String allLetters = "^[a-zA-Z]+$";

        return token.matches(allLetters);                                                           // returns true if word is composed of only letters
    }


    // rounds probability
    public static float roundProbability(float unroundedProbability) {
        DecimalFormat df = new DecimalFormat("0.00000");                                     // set decimal format to five decimal places
        df.setRoundingMode(RoundingMode.CEILING);                                                   // set rounding mode to ceiling
        float roundedProbability = Float.parseFloat(df.format(unroundedProbability));               // round number
        return roundedProbability;                                                                  // return
    }

    // used to calculate the probability the file is spam
    public static void calculatePrSF() throws FileNotFoundException {

        for (int x = 0; x < files.length; x++) {                                                    // for every file in the folder

            File file = files[x];                                                                   // File is the xth file in the files array

            float eta = 0.00000000f;                                                                // setting beginning value of eta
            float e = (float) Math.E;                                                               // setting value of e
            float PrSF = 0.00000000f;                                                               // setting beginning value of PrSF
            int wordCount = 0;                                                                      // setting beginning value of wordCount

            /*
             * To calculate eta:
             * loop through each word in the file using scanner
             * check if the word is in PrSW
             * If not leave value as 0
             * If it is, use the formula and add it to eta
             */

            Scanner fileScanner = new Scanner(file);                                                // Assign scanner to file
            String token = "";                                                                      // setting beginning value of token

            while(fileScanner.hasNext()) {                                                          // while the scanner has something to scan in the file
                token = fileScanner.next();                                                         // token is the word the scanner scans

                if (isValidWord(token)) {                                                           // if the token is a valid word
                    wordCount++;

                    if (PrSW.containsKey(token)) {                                                  // if PrSW contains the token


                        if (PrSW.get(token) == 1) {                                                 // if the value is 1

                            float value = 0.99999999f;                                              // make value to 0.99999 so it doesn't return an error for log()

                            float partOne = (float)java.lang.Math.log(1 - value);                   // calculate part one of the eta formula

                            float partTwo = (float)java.lang.Math.log(value);                       // calculate part two of the eta formula

                            eta = eta + (partOne - partTwo);                                        // calculate eta

                        }

                        else if (PrSW.get(token) == 0) {                                            // if the value in PrSW is 0

                            float value = 0.00000001f;                                              // make value to 0.00001 so it doesn't return an error for log()

                            float partOne = (float)java.lang.Math.log(1 - value);                   // calculate part one of eta formula

                            float partTwo = (float)java.lang.Math.log(value);                       // calculate part two of eta formula

                            eta = eta + (partOne - partTwo);                                        // calculate eta

                        }


                        else {                                                                      // if the dictionary contains the token and its value isn't 0 or 1

                            float value = PrSW.get(token);                                          // get the token value

                            float partOne = (float)java.lang.Math.log(1 - value);                   // calculate part one of eta formula

                            float partTwo = (float)java.lang.Math.log(value);                       // calculate part two of eta formula

                            eta = eta + ( partOne - partTwo );                                      // calculate eta

                        }

                    }

                    // if the word isn't in the map it is calculated as a 50/50
                    else {

                        float value = 0.50000f;                                                     // assign value as 0.50000 because it could be spam

                        float partOne = ((float)java.lang.Math.log(1 - value));                     // calculate part one of eta formula

                        float partTwo = ((float)java.lang.Math.log(value));                         // calculate part two of eta formula

                        eta = eta + (partOne - partTwo);                                            // calculate eta

                    }

                }

            }

            float en = (float)(java.lang.Math.pow(e, eta));

            PrSF = 1 / (1 + en);

            PrSF = (float)(PrSF * 100.0000f);                                                       // multiply by 100 for percent

            if (isNaN(PrSF)) {                                                                      // if the number is NaN, make it 50% possible to be spam
                PrSF = 50.00000f;                                                                   // (just a fail safe for NaN values)
            }

            PrSF = roundProbability(PrSF);

            String guess = "";

            if (PrSF >= 50) {                                                                       // if the number is 50% or above, it is spam
                guess = "Spam";
            }
            else {                                                                                  // if the number isn't above 50%, it is not spam
                guess = "Ham";
            }

            addFileName(file.getName(), x);                                                         // add file name to array
            addSpamProbability(PrSF, x);                                                            // add probability to array
            addClassGuess(guess, x);                                                                // add guess to array


        }

    }

    // output resulting probabilities to txt file
    public static void outputSpamProbabilities(File output) throws IOException {
        System.out.println("Saving probabilities to file: " + output.getAbsolutePath());

        if (!output.exists()) {                                                                     // if the file doesn't already exist
            output.createNewFile();                                                                 // create new output file

            if (output.canWrite()) {                                                                // if the file can be written to
                PrintWriter fileOutput = new PrintWriter(output);                                   // create new PrintWriter to output things to the file

                for (int x = 0; x < fileNames.length; x++) {
                    fileOutput.println(fileNames[x] + " " + actualClass + " " + spamProbabilities[x] + " " + classGuess[x]);
                }

                fileOutput.close();

            }
        } else {
            System.out.println("Error: the output file already exists: " + output.getAbsolutePath());
        }

    }

}

