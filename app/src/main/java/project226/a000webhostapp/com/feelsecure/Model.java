package project226.a000webhostapp.com.feelsecure;

public class Model {
    private String subName;
    private int countPresent;
    private int countAbsent;
    private int totalPresent;
    private int totalAbsent;
    private int userID;
    private String userName;
    private String email;
    private int flag;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(int totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public int getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(int totalPresent) {
        this.totalPresent = totalPresent;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCountAbsent() {
        return countAbsent;
    }

    public void setCountAbsent(int countAbsent) {
        this.countAbsent = countAbsent;
    }

    public int getCountPresent() {
        return countPresent;
    }

    public void setCountPresent(int countPresent) {
        this.countPresent = countPresent;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
