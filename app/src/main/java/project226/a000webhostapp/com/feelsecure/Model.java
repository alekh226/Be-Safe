package project226.a000webhostapp.com.feelsecure;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<String> textMessage =new ArrayList<String>();
    private ArrayList<String> keys =new ArrayList<String>();
    private ArrayList<String> user_name =new ArrayList<String>();

    public ArrayList<String> getKeys() {
        return keys;
    }

    public void setKeys(String key) {
        this.keys.add(key);
    }

    public ArrayList<String> getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name1) {
        this.user_name.add(user_name1);
    }

    public ArrayList<String> getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage1) {
        this.textMessage.add(textMessage1);
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
