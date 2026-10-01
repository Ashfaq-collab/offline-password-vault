package passwordmanager;

import java.io.Serializable;

public class PasswordEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String website;
    private String username;
    private String password;

    public PasswordEntry(int id, String website, String username, String password) {
        this.id = id;
        this.website = website;
        this.username = username;
        this.password = password;
    }

    public void setWebsite(String website){
        this.website=website;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setUsername(String username){
        this.username=username;
    }

    public int getId() {
        return id;
    }

    public String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
