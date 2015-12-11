package ucf.eel4915l.mini_mixermobile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    public static final String BASE_URL = "http://192.168.8.1:8000";
    final HashMap<String, String> buddies = new HashMap<String, String>();
    public static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to minimixer.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "test:test"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private AuthToken token;

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        // Disable login button until MiniMixer discovery.
        mEmailSignInButton.setEnabled(false);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        //discoverService();

        String minimixers[] = {"Test 1", "Test 2", "Test 3"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, minimixers);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        // Do we have a Mini-Mixer discovered for login?
        if(minimixers.length == 0) {
            mEmailSignInButton.setEnabled(false);
        }
        else {
            mEmailSignInButton.setEnabled(true);
        }

    }

    private void discoverService() {
        WifiP2pManager.DnsSdTxtRecordListener txtListener = new WifiP2pManager.DnsSdTxtRecordListener() {

            @Override
        /* Callback includes:
         * fullDomain: full domain name: e.g "printer._ipp._tcp.local."
         * record: TXT record dta as a map of key/value pairs.
         * device: The device running the advertised service.
         */

            public void onDnsSdTxtRecordAvailable( String fullDomain, Map record, WifiP2pDevice device) {

                Log.d(TAG, "DnsSdTxtRecord available -" + record.toString());
                buddies.put(device.deviceAddress, record.get("beaglebone").toString());
            }
        };

        Log.d(TAG, "Got here.");

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);


        User user = new User(mUsernameView.getText().toString(), mPasswordView.getText().toString());

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(user.mUserName)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user.mUserName)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(user);
            mAuthTask.execute((Void) null);
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final User user;
        private Boolean status = false;
        private Boolean registered = false;

        UserLoginTask(User userObject) {
            user = userObject;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO: authenticate against minimixer service.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final MiniMixerServiceInterface apiService =
                    retrofit.create(MiniMixerServiceInterface.class);
            Call<AuthToken> call = apiService.getAuthToken(user);
            call.enqueue(new Callback<AuthToken>() {
                @Override
                public void onResponse(Response<AuthToken> response, Retrofit retrofit) {
                    int statusCode = response.code();

                    if (statusCode == 200) {
                        token = response.body();
                        Log.d("LoginActivity", "Token " + token.token);
                        status=true;

                    }

                    else {
                        Log.d("LoginActivity", "Attempting to register..");
                        Log.d("LoginActivity", user.mUserName.toString());

                        Call<User> call2 = apiService.createUser(user);
                        call2.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Response<User> response, Retrofit retrofit) {
                                int statusCode = response.code();


                                System.out.println(statusCode);
                                registered = true;
                                status = true;
                                showProgress(false);
                                Toast toast = Toast.makeText(LoginActivity.this, "Account Created!", Toast.LENGTH_SHORT);
                                toast.show();

                            }

                            @Override
                            public void onFailure(Throwable t) {
                                // Log error here since request failed
                            }
                        });
                    }


                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                }
            });

            while(true) {
                if(status == true)
                    return true;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success && !registered) {

                // Go to main menu.
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", user.mUserName);
                intent.putExtra("authtoken", "Token " + token.token);
                startActivity(intent);
            }
            else if(registered) {

            }

            else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

