package np.com.amansingh.chatme.ui.main.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import np.com.amansingh.chatme.model.chatRoom;
import np.com.amansingh.chatme.model.message;
import np.com.amansingh.chatme.R;

public class rvChatAdapter extends  FirebaseRecyclerAdapter<chatRoom,rvChatAdapter.viewHolder> {
private  FirebaseDatabase database;

private  final itemClickListener listener;


    public  rvChatAdapter(FirebaseRecyclerOptions options, FirebaseDatabase database, itemClickListener listener)
    {
        super(options);
        this.database=database;
        this.listener= listener;


    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_chat_items,parent,false);
       return  new  viewHolder(itemView,listener);
    }



    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull chatRoom model) {
        try{
            holder.name.setText(model.getName());
            if(model.getPhotoUrl()!=null)
                Glide.with(holder.itemView.getContext()).load(model.getPhotoUrl()).into(holder.profilePic);
            else {
                holder.profilePic.setImageResource(R.drawable.ic_profile_foreground);
                holder.profilePic.setBackgroundResource(R.drawable.ic_profile_background);
            }
            database.getReference().child("ChatRooms").child(model.getChatRoomID()).child("RecentMessages").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isComplete()&&task.isSuccessful())
                    {
                        try {
                            message m = task.getResult().getValue(message.class);
                            holder.recentChat.setText(m.getTextMessage());
                        }catch (Exception e)
                        {
                            Log.e("ADAPTER MESSAGE ERROR ->",e.getMessage());
                        }
                    }
                }
            });

        }catch (Exception e)
        {
            Log.e("Error",e.getMessage());
        }

    }



    class viewHolder extends RecyclerView.ViewHolder
    {
        private  ImageView profilePic;
        private  TextView name;
        private TextView recentChat;
        private  TextView chatTime;
        private  TextView chatDate;

        public viewHolder(@NonNull View itemView,itemClickListener listener) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.rv_chat_profile_photo);
            name=itemView.findViewById(R.id.rv_chat_friend_names);
            recentChat=itemView.findViewById(R.id.rv_chat_recents);
            chatTime=itemView.findViewById(R.id.rv_chat_time);
            chatDate=itemView.findViewById(R.id.rv_chat_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAbsoluteAdapterPosition();
                    chatRoom c=getItem(pos);
                    if(listener!=null&&pos!=RecyclerView.NO_POSITION)
                    {
                        listener.onClickItem(c);
                    }

                }
            });

        }
    }

    public interface itemClickListener
    {
        public  void onClickItem(chatRoom c);
    }


}
