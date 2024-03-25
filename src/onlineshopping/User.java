
package onlineshopping;


public class User {
    
    private String userName;
    private String Password;
    
    public User(String userName , String Password){
        this.userName = userName;
        this.Password = Password;
}
     public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
