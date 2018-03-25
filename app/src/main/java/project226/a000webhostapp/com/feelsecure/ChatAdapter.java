package project226.a000webhostapp.com.feelsecure;

/**
 * Created by WINDOWS on 3/24/2018.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<String> values;
    private List<String> keys;
    private List<String> users;
    private Context context;
    private GoogleMap mMap;
    private LatLng otherslocation;
    private LatLng currentLocation;
    private MainActivity mainActivity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView removeButton;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.user_name_for_text);
            txtFooter = (TextView) v.findViewById(R.id.messageText);
            //removeButton=(ImageView)v.findViewById(R.id.deleteButton);
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(Context context1,List<String> myDataset,List<String> keySet,List<String> user,LatLng latLng) {
        keys =keySet;
        context =context1;
        values = myDataset;
        users=user;
        currentLocation =latLng;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.chat_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = users.get(position);
        holder.txtHeader.setText(name);
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MapsActivity2.class);
                Bundle bundle =new Bundle();
                bundle.putString("otherKey",keys.get(position));
                bundle.putDouble("currentLat",currentLocation.latitude);
                bundle.putDouble("currentLong",currentLocation.longitude);
                // bundle.putDouble("otherLat",otherslocation.latitude);
                //bundle.putDouble("otherLong",otherslocation.longitude);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                // getOtherUserLocation(keys.get(position));
                Log.d("MyAdapter",keys.toString());
            }
        });
        /*holder.removeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHandler myDBHandler = new MyDBHandler(context,null,null,1);
                myDBHandler.removeUser(keys.get(position));
                remove(position);
            }
        });*/
        holder.txtFooter.setText("Key: " +values.get(position));
    }

    // Replace the contents of a view (invoked by the layout manager)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }



}