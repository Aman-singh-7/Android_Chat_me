package np.com.amansingh.chatme.ui.main.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class customLinearLayoutManager extends LinearLayoutManager {

    public customLinearLayoutManager(Context context) {
        super(context);
    }

    public customLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public customLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try{
        super.onLayoutChildren(recycler, state);
    }catch (IndexOutOfBoundsException e)
        {
            Log.v("customLLL error",e.getMessage());
        }
    }
}
