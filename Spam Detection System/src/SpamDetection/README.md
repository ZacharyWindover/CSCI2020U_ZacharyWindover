a. Project Information:
  This software was developed to take test spam and non-spam (ham) emails as input.
  
  It then analyzes that data to calculate the probability of that word being in a spam file 
  based on how many times it appears in both spam and non-spam files
  
  A string of letters is determined to be a word if the string contains only letters.
  Of course this could be refined to include words containing periods (at the end of a sentence) or       commas, or even check if it is a word based on how many characters there are before a vowel in a word,   but this definition was the easiest to use.
  
  The formula to determine how many times it appears in spam files is 
  Pr|W|S| = # of spam files containing word / number of spam files
  Pr|W|H| = # of ham files containing word / number of ham files is also used to determine how often the    word appears in non-spam files
  
  The two statistics are collected for all words that appear in the training files and put into two maps    (PrWS and PrWH). 
  These two maps are then used to determine the probability that a file is spam based on one word         (PrSW). 
  The formula for PrSW is Pr|S|W| = Pr|W|S| / Pr|W|S|+Pr|W|H|
  This statistic is also stored in a map for each string
  
  This PrSW map is then taken, and input test spam and non-spam emails are given and it determines the      probability that the email is spam based on the probability of all of those words. 
  The probability a file is spam based on the probabilities of all of the words is PrSF.
  Pr|S|F| = 1 / 1+e^[eta] where eta = the sum of all [(ln(1-Pr|S|W|)) - (ln(Pr|S|W|))]
  
  *All formulas stated up to this point were provided by the University of Ontario Institute of                Technology.*
  However, when using this last formula to determine Pr|S|F| I found the results to be unsatisfactory. I found the values to round to 0.0 or 1.0 too quickly given the large        values of eta for each file. I originally tried fixing this by scaling down the number by finding the remainder of dividing it by 11.5. 
  However this provided bad results as the remainder didn't give enough value as to the scale of the original eta.
  So I went on desmos and plotted the original function (1 / 1+e^[eta]) and I found that the value      rounded to 1 at approximately -7.61. 
  I also knew at that point that the value the numbers rounded to 1 at had to be scaled by the amount   of valid words in the file, so after a little trial and error I was able    to come up with t = n /     7.61, where n is the number of valid words in the file. 
  I was also able to find this because when I plugged t = n / 7.61 in for just x in the base            function, I noticed it changed the value that rounded to 1.0. 
  So I realized that the fraction would be able to scale determining when the value would round to      1.0.
  I originally didn't know to use -7.61, I just noticed that that was the number that y rounded to 1.0 for 
  I made a table, multiplying -7.61 by n, where n is any positive whole number (1, 2, 3, 4, 5, 6), and that is when I noticed that it is a linear growth. 
  So I was able to make the connection and created the t function. I orignally didn't know how it connect to the PrSF function though, so I played around with word counts when I   realized that the word count / -7.61 would give the value where y rounded to 1.0. 
  So then for example if the file had 250 words, 250 / 7.61 =~ 32.85, and so doing eta / 32.85 would give a correct scale, where if it equaled 1, 1 / 1+e^1 would be 100%           probability. 
  This formula did not change the accuracy or precision of the function, and that isn't what I was looking to do, I was happy with 79.5% accuracy, I was annoyed that almost all   values were 0.0 or 1.0, so it never gave a correct percent probability that the file was spam, but this formula did. 
  I determined that the best ways to make the accuracy better were to give more training cases to recognize more words and give it a better idea of how common it was in spam vs    non-spam emails, and to make a better filter so that it recognized more words as words, and would know if a string was more likely to be spam 
  
  The spam probabilities for all of the files are not stored in a map, but an array, for ease
  The actual class, guessedClass, and file names are also stored in arrays
  After all of the probabilities are calculated, the four arrays are then stores in a txt file. 
  
  To display the information, a SpamReport class is used to store the file name, class, probability, and guessedClass. 
  The reports are read line by line from the file, and stored in an observable list. 
  One thing to note about the overall appearance of the window, the text was meant to be yellow, as someone I live with works at aviva,
  as yellow text on a blue background is what is used on one of their development tools, and that is the kind of appearence I wanted it to have.
  However, even though I set the background color as white, and originally set the text color as yellow, it didn't feel like it, and I didn't have enough time to fix it for the    deadline, so this is what I had to settle with. 
  
  In regards to how the classes interact with each other:
  ExecutionCallable is the sort of "main" class that calls all of the methods to train the system and calculate the email spam probabilities.
  The WordTracker class is used to train the system, it collects the training folders and calculates PrSW.
  The SpamFilter class is used to calculate the probability that an email is spam (PrSF). 
  Once PrSW is calculated, it is saved in ExecutionCallable, and passed to SpamFilter along with the test cases to determine PrSF.
  Once PrSF is calculated, all of the results ("reports") are saved to a file. 
  
  The SpamReport class is a class meant to save each part of the spam report (file name, actual class, probability, guessed class).
  The data source uses a scanner to go through the file and create new instances of SpamReport for each report (each line).
  This data is stored in an ObservableList, which is used to display the information in the spreadsheet (TableView).
  
  While all of the information is being retrieved in the DataSource class, the accuracy and precision values are also calculated at the same time by comparing the actualClass      values and the guessedClass values, which are then passsed to the controller to be assigned to the labels. I think the controller class and Main class are fairly self            explanatory so I will not go into detail with them, as they are the same as almost any standard JavaFX program. 
  In the controller class the values of the table defined in the FXML file are assigned to the columns, and the accuracy and precision are retrieved and assigned to the labels.
  
  The testFile class is not used as it was easy to make everything go automatically just by calling the ExecutionCallable class. 
  
  Unfortunately I was not able to improve the interface or accuracy more, and I was not able to make the email sample data choosable with DirectoryChooser due to time              contraints.
  
  I also wanted to make detected spam files contain red text or the cells be highlighted in red, while cells detected as ham be highlighted in green, however due to time           contraints that did not happen.
  
  One thing to note is that all maps are saved for easy reference, and an output is printed saying where it was saved, which is in the project folder.
  Along with that, the statistics to calculate accuracy and precision (numFiles, numTruePositives, numTrueNegatives, numFalsePositives) along with the accuracy and precision,      are given as output. This was originally just for testing but I like it because it shows how many files it got right, how many got wrong, and how many there were in total,      just some interesting little outputs.
  
  
  b. The parts I attempted to improve were the probability percent of each file being spam, and the appearance of the UI. Unfortunately the UI didn't turn out as hoped, but the    model was successfully improved using the method previously stated above.
  
  c. Running the program is easy through an IDE, just press start and it automatically does everything (would start with choosing a sample directory if I had time to implement     it).
  It is not currently a JAR file, so it cannot be run as a standalone application, although it could be. 
  
  d. other than the importable built in libraries in java (java.io, java.text, javafx.scene, javafx.collections, java.util, javafx.fxml, javafx.stage, javafx.                     javafx.application, and java.math) I don't recall using anything else. 
  
  
 image 1: console output from application
 ![image](https://user-images.githubusercontent.com/71024596/110417267-443e8e00-8063-11eb-90d3-65cd799a5deb.png)

image 2: spreadsheet output from application
![image](https://user-images.githubusercontent.com/71024596/110417363-764ff000-8063-11eb-8a9d-1695ffbdb1af.png)

image 3: layout of class structure for recreation by TA, Professor, or other
![image](https://user-images.githubusercontent.com/71024596/110417411-95e71880-8063-11eb-8930-c74dd8cbf0cb.png)

image 4: project folder for file outputs and train and test data storage
![image](https://user-images.githubusercontent.com/71024596/110417504-b9aa5e80-8063-11eb-838f-a1489eeb6422.png)


please let me know if the images don't work for you, they appear for me, but I just want to make sure. 
