package Lab05;

public class StudentRecord {
    private String studentNumber;
    private float midtermExam;
    private float assignments;
    private float finalExam;
    private float finalMark;
    private char letterGrade;

    public StudentRecord(String studentNumber, float assignments, float midtermExam, float finalExam) {
        this.studentNumber = studentNumber;
        this.assignments = assignments;
        this.midtermExam = midtermExam;
        this.finalExam = finalExam;
    }

    public void setFinalMark(float finalMark) { this.finalMark = finalMark; }

    public void setLetterGrade(char letterGrade) { this.letterGrade = letterGrade; }

    public String getStudentNumber() { return this.studentNumber; }

    public float getMidtermExam() { return this.midtermExam; }

    public float getAssignments() { return this.assignments; }

    public float getFinalExam() { return this.finalExam; }

    public float getFinalMark() {
        float finalMark = 0.0f;

        finalMark = finalMark + (this.assignments * 0.2f);
        finalMark = finalMark + (this.midtermExam * 0.3f);
        finalMark = finalMark + (this.finalExam * 0.5f);

        this.finalMark = finalMark;

        return this.finalMark;
    }

    public char getLetterGrade() {
        if (this.finalMark >= 80) { this.letterGrade = 'A'; }
        else if (this.finalMark >= 70) { this.letterGrade = 'B'; }
        else if (this.finalMark >= 60) { this.letterGrade = 'C'; }
        else if (this.finalMark >= 50) { this.letterGrade = 'D'; }
        else { this.letterGrade = 'F'; }

        return this.letterGrade;

    }

}
