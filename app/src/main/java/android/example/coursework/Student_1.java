package android.example.coursework;

public class Student_1 {
    private String nUsername;
    private int nFlag;

    public Student_1(String username, int flag) {
        nUsername = username;
        nFlag = flag;
    }

    public String getTitle() {
        return nUsername;
    }
    public int getFlag() {
        return nFlag;
    }

}
