package SpamDetection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class DataSource {

    private static int numFiles = 0;
    private static int numTruePositives = 0;
    private static int numTrueNegatives = 0;
    private static int numFalsePositives = 0;

    public static float getAccuracy() {
        float accuracy = 0.00000f;
        accuracy = (float) (numTruePositives + numTrueNegatives) / numFiles;
        return  accuracy;
    }

    public static float getPrecision() {
        float precision = 0.00000f;
        precision =  (float) numTruePositives / (numFalsePositives + numTruePositives);
        return  precision;
    }

    public static void printStuff() {
        System.out.println("numFiles: " + numFiles);
        System.out.println("numTruePositives: " + numTruePositives);
        System.out.println("numTrueNegatives: " + numTrueNegatives);
        System.out.println("numFalsePositives: " + numFalsePositives);
    }

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
                    String[] stringsHam = new String[4];

                    stringsHam = line.split(" ", 4);

                    String fileName = stringsHam[0];
                    String actualClass = stringsHam[1];
                    String spamProbability = stringsHam[2];
                    String classGuess = stringsHam[3];

                    if (actualClass.equals("Spam") && classGuess.equals("Spam")) { numTruePositives++; }
                    if (actualClass.equals("Ham") && classGuess.equals("Ham")) { numTrueNegatives++; }
                    if (actualClass.equals("Ham") && classGuess.equals("Spam")) { numFalsePositives++; }
                    numFiles++;

                    reports.add(new SpamReport(fileName, actualClass, spamProbability, classGuess));

                }

                Scanner spamScanner = new Scanner(PrSF2);

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
