package simpsonsviewer.xfinity.com.simpsonscharacterviewer.Model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import simpsonsviewer.xfinity.com.simpsonscharacterviewer.R;

/**
 * Created by m14x on 04/29/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<ObjectDescriptor> content = new ArrayList<>();
    private Activity activity;

    public CustomAdapter(Context context, List<ObjectDescriptor> content,Activity activity){
        this.context = context;
        this.content = content;
        this.activity = activity;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view,context,activity);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        ObjectDescriptor od = content.get(position);
        holder.setItem(od);

    }

    @Override
    public int getItemCount() {
        return content.size();
    }
}
