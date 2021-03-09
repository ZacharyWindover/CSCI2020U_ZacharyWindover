package SpamDetection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class ExecutionCallable {

    public static void execute() throws IOException {

        // Ham Folder Training
        File trainHamFolder = new File("./assignment1_data/data/train/ham"); 			    // Declare first ham test folder
        File trainHamFolder2 = new File("./assignment1_data/data/train/ham2"); 			// Declare second ham test folder

        WordTracker trainHamFreq = new WordTracker(trainHamFolder, trainHamFolder2); 	            // create a new WordTracker instance for ham
        trainHamFreq.scanFiles(); 														            // create ham word map
        trainHamFreq.calculatePrWX(); 													            // calculate probability of words in ham
        Map<String, Float> PrWH = trainHamFreq.getPrWX(); 								            // get PrWH



        // Spam Folder Training
        File trainSpamFolder = new File("./assignment1_data/data/train/spam"); 			// Declare spam test folder

        WordTracker trainSpamFreq = new WordTracker(trainSpamFolder); 					            // create a new WordTracker instance for spam
        trainSpamFreq.scanFiles(); 														            // create new spam word map
        trainSpamFreq.calculatePrWX(); 													            // calculate probability of words in spam
        Map<String, Float> PrWS = trainSpamFreq.getPrWX(); 								            // get PrWS

        trainHamFreq.calculatePrSW(PrWS, PrWH); 										            // get PrSW

        Map<String, Float> PrSW = trainHamFreq.getPrSW();								            // create new Map for PrSW and get value from WordTracker to use in main class

        trainHamFreq.outputPrWX(new File("PrWH.txt"));									    // output PrWH to a file
        trainSpamFreq.outputPrWX(new File("PrWS.txt"));									// output PrWS to a file

        trainHamFreq.outputPrSW(new File("PrSW.txt"));									    // output PrSW to a file

        File testHamFolder = new File("./assignment1_data/data/test/ham");				    // declare test ham folder as a file
        SpamFilter hamFilter = new SpamFilter(testHamFolder, "Ham", PrSW);				    // create new instance of SpamFilter with test ham folder
        hamFilter.calculatePrSF();														            // calculate PrSF for the test ham folder
        hamFilter.outputSpamProbabilities(new File("hamSpamProbabilities.txt"));		    // output PrSF to a file

        File testSpamFolder = new File("./assignment1_data/data/test/spam");			    // declare test spam folder as a file
        SpamFilter spamFilter = new SpamFilter(testSpamFolder, "Spam", PrSW);			    // create new instance of SpamFilter with test spam folder
        spamFilter.calculatePrSF();														            // calculate PrSF for the test spam folder
        spamFilter.outputSpamProbabilities(new File("spamSpamProbabilities.txt"));		    // output PrSF to a file

    }

}
