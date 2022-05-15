package np.com.amansingh.chatme.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.model.mateModel.Mates;


public class friendListAdapter extends RecyclerView.Adapter<friendListAdapter.viewHolder> {

    private  List<Mates> friendsArrayList=new ArrayList<>();
    private final  adapterClickListner clickListner;


    public friendListAdapter(adapterClickListner listner)
    {
        clickListner=listner;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item,parent,false);


        return new viewHolder(itemView,clickListner);
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


    public void setList(List<Mates> list)
    {
        friendsArrayList=list;
        notifyDataSetChanged();

    }
    public  Mates getItem(int position)
    {
        if(friendsArrayList!=null)
        {
            return friendsArrayList.get(position);
        }
        return  null;
    }


    class viewHolder extends RecyclerView.ViewHolder
    {
        private  TextView name;
        private  ImageView imageView;
        private  TextView status;
        private  adapterClickListner listner;
        public viewHolder(@NonNull View itemView,adapterClickListner clickListner) {
            super(itemView);
            name=itemView.findViewById(R.id.friend_name);
            status=itemView.findViewById(R.id.friend_status);
            imageView=itemView.findViewById(R.id.friend_image);
            listner=clickListner;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    if(listner!=null&&pos!=RecyclerView.NO_POSITION) {
                        listner.onlistClick(pos);
                        Log.v("Item clicked","->  "+pos );
                    }else
                    {
                        Log.e("Click Error", "onClick: ");
                    }
                }
            });

        }


    }
    public interface adapterClickListner
    {
        void onlistClick(int position);
    }


}
