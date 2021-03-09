package SpamDetection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class DataSource {

    private static int numFiles = 0;                                                    // used to store number of files tested
    private static int numTruePositives = 0;                                            // used to store number of true positives determined
    private static int numTrueNegatives = 0;                                            // used to store number of true negatives determined
    private static int numFalsePositives = 0;                                           // used to store number of false positives determined

    // calculates and returns the accuracy of the spam filter
    public static float getAccuracy() {
        float accuracy = 0.00000f;
        accuracy = (float) (numTruePositives + numTrueNegatives) / numFiles;
        return  accuracy;
    }

    // calculates and returns precision of the spam filter
    public static float getPrecision() {
        float precision = 0.00000f;
        precision =  (float) numTruePositives / (numFalsePositives + numTruePositives);
        return  precision;
    }

    // used to print base variables to check for correct math, originally just for testing (that's why the bad name)
    public static void printStuff() {
        System.out.println("numFiles: " + numFiles);
        System.out.println("numTruePositives: " + numTruePositives);
        System.out.println("numTrueNegatives: " + numTrueNegatives);
        System.out.println("numFalsePositives: " + numFalsePositives);
    }

    // reads the reports line by line from the file
    public static ObservableList<SpamReport> getAllReports() throws FileNotFoundException {
        ObservableList<SpamReport> reports=
                FXCollections.observableArrayList();

                // read PrSF file
                // use loop to make new SpamReports
                File PrSF = new File("C:/Users/19059/IdeaProjects/Spam Detection System/hamSpamProbabilities.txt");
                File PrSF2 = new File("C:/Users/19059/IdeaProjects/Spam Detection System/spamSpamProbabilities.txt");
                Scanner hamScanner = new Scanner(PrSF);

                while(hamScanner.hasNextLine()) {
                    String line = hamScanner.nextLine();
                    String[] stringsHam = new String[4];                                                    // reads whole line from report into string array

                    stringsHam = line.split(" ", 4);                                             // splits the string array into four pieces for the 4 index array

                    String fileName = stringsHam[0];                                                        // get file name from report
                    String actualClass = stringsHam[1];                                                     // get actual class from report 
                    String spamProbability = stringsHam[2];                                                 // get calculated probability from report
                    String classGuess = stringsHam[3];                                                      // guess estimated class from report

                    if (actualClass.equals("Spam") && classGuess.equals("Spam")) { numTruePositives++; }    // adds to numTruePositives if spam detected correctly
                    if (actualClass.equals("Ham") && classGuess.equals("Ham")) { numTrueNegatives++; }      // adds to numTrueNegatives if ham detected correctly
                    if (actualClass.equals("Ham") && classGuess.equals("Spam")) { numFalsePositives++; }    // adds to numFalsePositives if spam detected incorrectly
                    numFiles++;

                    reports.add(new SpamReport(fileName, actualClass, spamProbability, classGuess));        // adds report to list

                }

                Scanner spamScanner = new Scanner(PrSF2);                                                   // does same thing for Spam PrSF after Ham PrSF
                while(spamScanner.hasNextLine()) {
                    String line = spamScanner.nextLine();
                    String[] stringsSpam = new String[4];

                    stringsSpam = line.split(" ", 4);

                    String fileName = stringsSpam[0];
                    String actualClass = stringsSpam[1];
                    String spamProbability = stringsSpam[2];
                    String classGuess = stringsSpam[3];

                    if (actualClass.equals("Spam") && classGuess.equals("Spam")) { numTruePositives++; }
                    if (actualClass.equals("Ham") && classGuess.equals("Ham")) { numTrueNegatives++; }
                    if (actualClass.equals("Ham") && classGuess.equals("Spam")) { numFalsePositives++; }
                    numFiles++;

                    reports.add(new SpamReport(fileName, actualClass, spamProbability, classGuess));
                }

        return reports;

    }

}

