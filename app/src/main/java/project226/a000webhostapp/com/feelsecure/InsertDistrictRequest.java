package project226.a000webhostapp.com.feelsecure;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WINDOWS on 3/12/2018.
 */

public class InsertDistrictRequest  extends StringRequest {
    private static final String INSERT_DISTRICT_URL = "http://alekhkumar226.000webhostapp.com/insertDistrict.php";
    private Map<String, String> params;

    public InsertDistrictRequest(String districtName, int DistrictId, Response.Listener<String> listener) {
        super(Request.Method.POST,INSERT_DISTRICT_URL, listener, null);
        params = new HashMap<>();
        params.put("district_name", districtName);
        params.put("district_id", ""+DistrictId);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

