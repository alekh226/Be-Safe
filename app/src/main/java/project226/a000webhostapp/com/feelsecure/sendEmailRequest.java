package project226.a000webhostapp.com.feelsecure;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rishabh on 3/16/2018.
 */

public class sendEmailRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://alekhkumar226.000webhostapp.com/sendemail.php";
    private Map<String, String> params;

    public sendEmailRequest(String email,String latPS,String langPS,
                            String latH,String langH,
                            String latC,String langC,Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", ""+email);
        params.put("latPS",""+latPS);
        params.put("langPS",""+langPS);
        params.put("latH",""+latH);
        params.put("langH",""+langH);
        params.put("latC",""+latC);
        params.put("langC",""+langC);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
