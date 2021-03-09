package SpamDetection;

public class SpamReport {

    private String fileName;
    private String actualClass;
    private String spamProbability;
    private String classGuess;

    public SpamReport(String fileName, String actualClass, String spamProbability, String classGuess) {
        this.fileName = fileName;
        this.actualClass = actualClass;
        this.spamProbability = spamProbability;
        this.classGuess = classGuess;
    }

    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setActualClass(String actualClass) { this.actualClass = actualClass; }
    public void setSpamProbability(String spamProbability) { this.spamProbability = spamProbability; }
    public void setClassGuess(String classGuess) { this.classGuess = classGuess; }

    public String getFileName() { return this.fileName; }
    public String getActualClass() { return this.actualClass; }
    public String getSpamProbability() { return this.spamProbability; }
    public String getClassGuess() { return this.classGuess; }



}
