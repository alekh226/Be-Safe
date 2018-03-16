package project226.a000webhostapp.com.feelsecure;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by WINDOWS on 3/14/2018.
 */

public class searchCrimeAsync extends AsyncTask {

    private ProgressDialog progressDialog;
    private Context context;
    private int flag =0;
    GetCrimeDetails getCrimeDetails =new GetCrimeDetails();
    public searchCrimeAsync(Context context1,ProgressDialog p){
        context=context1;
        progressDialog =p;
    }
   /* @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        // super.onPreExecute();
    }*/

    @Override
    protected void onPostExecute(Object o) {
        //  super.onPostExecute(o);
        progressDialog.dismiss();
        if(flag==2){
            new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Slow Internet or Place Not found")
                    .setPositiveButton("retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        CrimeFragment crimeFragment =CrimeFragment.newInstance(getCrimeDetails.crimeLabel,getCrimeDetails.crimeCount);
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.popBackStackImmediate();
        SearchBar searchBar1 =new SearchBar();
        FragmentManager fm =((FragmentActivity)context).getSupportFragmentManager();
        fm.popBackStackImmediate();
        fm.popBackStackImmediate();
        FragmentTransaction ft =fm.beginTransaction();
        ft.addToBackStack("First");
        ft.add(R.id.map,searchBar1);
        ft.commit();
        FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("Second");
        fragmentTransaction.add(R.id.map,crimeFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String krime =(String) objects[0];

        /*if(krime.equals("Ernakulam"))
            krime ="Ernakulam Rural";*/
        int sec=0;

        getCrimeDetails.searchCrime(context,krime);
        getCrimeDetails.searchCrime(context,krime+" Commr.");
        while (getCrimeDetails.crimeLabel.size()<1){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sec+=1;
            if (sec==75){
                flag=2;
                break;
            }
        }
        return null;
    }
}
