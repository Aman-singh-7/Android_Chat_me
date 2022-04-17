package np.com.amansingh.chatme.friendList;

import android.content.Context;
import android.content.Loader;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.Objects;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.databinding.ActivityFriendListBinding;
import np.com.amansingh.chatme.model.friends;
import np.com.amansingh.chatme.model.mateModel.Mates;


public class friendListAdapter extends RecyclerView.Adapter<friendListAdapter.viewHolder> {

    private  List<Mates> friendsArrayList=new ArrayList<>();

    public void setList(List<Mates> list)
    {
        friendsArrayList=list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item,parent,false);

        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Mates m=friendsArrayList.get(position);
        holder.name.setText(m.getName());
        holder.status.setText("Hi There! I'm using Chat me");



    }

    @Override
    public int getItemCount() {
        return  friendsArrayList.size();
    }


    class viewHolder extends RecyclerView.ViewHolder
    {
        private  TextView name;
        private  ImageView imageView;
        private  TextView status;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.friend_name);
            status=itemView.findViewById(R.id.friend_status);
            imageView=itemView.findViewById(R.id.friend_image);

        }
    }


}
