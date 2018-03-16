package project226.a000webhostapp.com.feelsecure;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   public EditText enterUserKey;
    public TextView textView;
    public Button addUserButton;

    public static AddUser newInstance(String param1, String param2) {
        AddUser fragment = new AddUser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_add_user, container, false);
        enterUserKey =(EditText) rootView.findViewById(R.id.enterKey);
        addUserButton=(Button)rootView.findViewById(R.id.addNewKey);
        //textView =(TextView)rootView.findViewById(R.id.textView);
        //addNewKeyClicked(rootView);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Key",enterUserKey.getText().toString());
               // GetUsers getUsers = new GetUsers(mParam1);
                //getUsers.addUser(enterUserKey.getText().toString());
              //  Log.d("lolo", "Value is userList: " + getUsers.getUsers());
                final String key =enterUserKey.getText().toString();
                //textView.setText(key);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = myRef = database.getReference(mParam1);;
                //MyDBHandler myDBHandler = new MyDBHandler(getActivity().getApplicationContext(),null,null,1);
                final String[] userName = new String[1];
                myRef.child("accesList").push().setValue(key);
                DatabaseReference myRef2 = database.getReference();
                myRef2.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //String value = dataSnapshot.getValue().toString();
                        userName[0] =String.valueOf((String) dataSnapshot.child("userName").getValue());
                        MyDBHandler myDBHandler = new MyDBHandler(getActivity().getApplicationContext(),null,null,1);
                        myDBHandler.insertUsers(key,userName[0]);
                        Log.d("lolo", "Value is: " + userName[0]);
                        //MyDBHandler myDBHandler = new MyDBHandler(MainActivity.this,null,null,1);
                        Log.d("XXXX",myDBHandler.getUserNameList().toString());
                        Log.d("XXXX",myDBHandler.getKeyList().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("lolo", "Failed to read value.", error.toException());
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        UserList userList=new UserList();
                        AddUser selectUser =new AddUser();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.remove(selectUser);
                        fragmentTransaction.add(R.id.map,userList);
                        fragmentTransaction.commit();
                    }
                }).start();




            }
        });
        return rootView;
    }




    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
