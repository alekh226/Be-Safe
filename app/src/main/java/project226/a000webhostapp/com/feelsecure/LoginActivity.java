package project226.a000webhostapp.com.feelsecure;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(isServiceOk()){
            init();
        }

    }

    public void NewUserClicked(View v){
        Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
        LoginActivity.this.startActivity(i);

    }

    public void ForgetPasswordClicked(View v){
        Intent i=new Intent(LoginActivity.this,ForgetEmailActivity.class);
        LoginActivity.this.startActivity(i);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }

    private  void init(){
        final EditText etUsername = (EditText) findViewById(R.id.userName);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final Button bLogin = (Button) findViewById(R.id.logInb);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String email=jsonResponse.getString("email");
                            int user_id=jsonResponse.getInt("user_id");


                            if (success) {
                                Intent ie=new Intent(LoginActivity.this,MainActivity.class);
                                LoginActivity.this.startActivity(ie);
                                Model model1=new Model();

                                MyDBHandler dbhandler =new MyDBHandler(LoginActivity.this,null,null,1);
                                model1.setFlag(1);
                                model1.setEmail(email);
                                model1.setUserID(user_id);
                                model1.setUserName(user_name);
                                // dbhandler.addMember(model1);
                               /* AlertDialog.Builder builder = new AlertDialog.Builder(Loading_Page.this);
                                builder.setMessage("Login successful")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();*/
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(user_name, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    public boolean  isServiceOk(){
        Log.d(TAG,"isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(LoginActivity.this);
            if(available== ConnectionResult.SUCCESS){
                Log.d(TAG, "isServicesOK: Google Play Services is working");
                return true;
            }
            else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
                Log.d(TAG, "isServicesOK: an error occured but we can fix it");
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(LoginActivity.this, available, ERROR_DIALOG_REQUEST);
                dialog.show();
            }
            else{
                Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
            }
            return  false;
    }


}
