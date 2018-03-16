package project226.a000webhostapp.com.feelsecure;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ForgetEmailRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://alekhkumar226.000webhostapp.com/emailsubmit.php";
    private Map<String, String> params;

    public ForgetEmailRequest( String email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("email", email);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
