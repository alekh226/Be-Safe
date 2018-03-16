package project226.a000webhostapp.com.feelsecure;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by WINDOWS on 3/15/2018.
 */

public class getLocationFromAddressAsync extends AsyncTask {

    private ProgressDialog progressDialog;
    private Context context;
    private String krime;
    GetCrimeDetails getCrimeDetails;
    public getLocationFromAddressAsync(Context context1){
        context=context1;
        getCrimeDetails =new GetCrimeDetails();

    }
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        // super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        //  super.onPostExecute(o);
        AsyncTask task =new searchCrimeAsync(context,progressDialog);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,(String) o);
        //progressDialog.dismiss();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        krime =getCrimeDetails.getLocationFromAddress(context,(String) objects[0]);
        return krime;
    }
}
