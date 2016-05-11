package edu.csumb.chin3816.flightreservationsystemotterairways;

/**
 * Created by yarelychino on 5/10/16.
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private int isAdmin;

    public User(){

    }

    public User(int userId, String userName, String password, int isAdmin){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", isAdmin= " + isAdmin +"]";
    }
}
