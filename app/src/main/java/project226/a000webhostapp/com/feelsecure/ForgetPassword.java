package project226.a000webhostapp.com.feelsecure;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }
    public void ResetClicked(View v){

    }*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        final EditText etotp = (EditText) findViewById(R.id.otp);
        final EditText etPassword1 = (EditText) findViewById(R.id.newPassword);
        final EditText etPassword2 = (EditText) findViewById(R.id.repeatPassword);
        final Button bReset = (Button) findViewById(R.id.reset);

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String otp = etotp.getText().toString();
                final String password = etPassword1.getText().toString();
                final String password2 = etPassword2.getText().toString();
                Intent i = getIntent();
                final String email = i.getStringExtra("email");
                if (password.equals(password2)){
                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Toast.makeText(ForgetPassword.this,"Password reset successful",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ForgetPassword.this, LoginActivity.class);
                                    ForgetPassword.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassword.this);
                                    builder.setMessage("OTP Invalid")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(otp,password,email, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ForgetPassword.this);
                    queue.add(resetPasswordRequest);
                }
                else {
                    Toast.makeText(ForgetPassword.this,"Both password doesn't match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
