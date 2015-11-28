package ucf.eel4915l.mini_mixermobile;

// Class that holds the Authentication Token so that it can be serialized and sent to the server in JSON format.
public class AuthToken {

    final String token;

    AuthToken(String token) {

        this.token = token;
    }
}
