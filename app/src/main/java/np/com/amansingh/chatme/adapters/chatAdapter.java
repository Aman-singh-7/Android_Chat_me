package np.com.amansingh.chatme.adapters;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.model.message;

public class chatAdapter extends FirebaseRecyclerAdapter<message,chatAdapter.chatAdapterViewHolder> {

    private  String phoneNumber;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public chatAdapter(@NonNull FirebaseRecyclerOptions<message> options,String phoneNumber) {
        super(options);
        this.phoneNumber=phoneNumber;
    }


    @NonNull
    @Override
    public chatAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_messages_list_item,parent,false);
        return new chatAdapterViewHolder(itemView);
    }


    @Override
    protected void onBindViewHolder(@NonNull chatAdapterViewHolder holder, int position, @NonNull message model) {
        message msg=model;
        if(msg.getSentBy().equals(phoneNumber))
        {
            holder.messageBackground.setBackgroundResource(R.drawable.message_sent_background);
           holder.layout.setGravity(Gravity.RIGHT);


        }else
        {
            holder.messageBackground.setBackgroundResource(R.drawable.message_received_background);
            holder.layout.setGravity(Gravity.LEFT);
        }
        holder.textMessage.setText(msg.getTextMessage());
        Log.v("message:",msg.getTextMessage());
        if(msg.getPhotoUrl()==null||msg.getPhotoUrl().equals(""))
            holder.imageMessage.setVisibility(View.GONE);
    }

    class chatAdapterViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageMessage;
        private TextView  textMessage;
       private  TextView  messageTime;
       private LinearLayoutCompat layout;
       private LinearLayoutCompat messageBackground;


        public chatAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
           imageMessage=itemView.findViewById(R.id.message_image);
           textMessage=itemView.findViewById(R.id.message_text);
           messageTime=itemView.findViewById(R.id.message_time);
           layout=itemView.findViewById(R.id.message_box);
           messageBackground=itemView.findViewById(R.id.message_background);


        }
    }
}
