package project226.a000webhostapp.com.feelsecure;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rishabh on 3/16/2018.
 */

public class getContactDetailsRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://alekhkumar226.000webhostapp.com/getcontact.php";
    private Map<String, String> params;

    public getContactDetailsRequest(LatLng latLng, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("latitude", ""+latLng.latitude);
        params.put("longitude",""+latLng.longitude);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
