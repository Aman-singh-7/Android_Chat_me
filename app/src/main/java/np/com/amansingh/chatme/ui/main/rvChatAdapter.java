package np.com.amansingh.chatme.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import np.com.amansingh.chatme.R;

public class rvChatAdapter extends RecyclerView.Adapter<rvChatAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_chat_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.profilePhoto.setImageResource(R.drawable.ic_profile_background);
        holder.friendName.setText("Pashupati prasad");
        holder.recents.setText("Oye ta gayis");
        holder.lastSentDate.setText("4:00");

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhoto;
        TextView friendName;
        TextView recents;
        TextView lastSentTime;
        TextView lastSentDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             profilePhoto =itemView.findViewById(R.id.rv_chat_profile_photo);
             friendName= itemView.findViewById(R.id.rv_chat_friend_names);
             recents=itemView.findViewById(R.id.rv_chat_recents);
             lastSentTime=itemView.findViewById(R.id.rv_chat_time);
             lastSentDate=itemView.findViewById(R.id.rv_chat_date);



        }
    }
}
