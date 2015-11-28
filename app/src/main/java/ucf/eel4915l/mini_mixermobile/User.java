package ucf.eel4915l.mini_mixermobile;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("username")
    String mUserName;

    @SerializedName("password")
    String mPassword;

    public User(String username, String password ) {
        this.mUserName = username;
        this.mPassword = password;
    }
}
