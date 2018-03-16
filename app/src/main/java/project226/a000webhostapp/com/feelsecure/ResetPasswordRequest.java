package project226.a000webhostapp.com.feelsecure;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WINDOWS on 7/16/2017.
 */

public class ResetPasswordRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://alekhkumar226.000webhostapp.com/resetpassword.php";
    private Map<String, String> params;

    public ResetPasswordRequest( String otp,String password,String email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("otp",otp);
        params.put("password",password);
        params.put("email", email);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
