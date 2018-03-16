package project226.a000webhostapp.com.feelsecure;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WINDOWS on 3/12/2018.
 */

public class GetDistrictIdRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://alekhkumar226.000webhostapp.com/getdistrictid.php";
    private Map<String, String> params;

    public GetDistrictIdRequest(String place, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("place", place);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
