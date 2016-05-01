package simpsonsviewer.xfinity.com.simpsonscharacterviewer.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simpsonsviewer.xfinity.com.simpsonscharacterviewer.R;

/**
 * Created by m14x on 04/30/2016.
 */
public class ViewDialog {

    private List<String> label;
    private List<String> value;
    private NetworkImageView imageView;
    private TextView characterName;


    public void showDialog(Activity activity, HashMap<String,String> detail, String url, ImageLoader image,String name){

        label = new ArrayList<>(detail.keySet());
        value = new ArrayList<>(detail.values());

        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.details_dialog);

        imageView = (NetworkImageView) dialog.findViewById(R.id.characterImage);
        characterName = (TextView) dialog.findViewById(R.id.characterNameTextView);
        characterName.setText(name);

        if(url.length()!=0) {
            imageView.setImageUrl(url, image);
        }else{
            imageView.setDefaultImageResId(R.drawable.unknown);
        }

        LinearLayout layout = (LinearLayout)dialog.findViewById(R.id.relativeLayout);
        TextView textView1 = new TextView(activity);
        textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView1.setText(label.get(0)+": "+value.get(0));
        textView1.setPadding(20, 10, 10, 10);// in pixels (left, top, right, bottom)
        layout.addView(textView1);

        for(int i = 1 ; i<label.size(); i++){
            TextView info = new TextView(activity);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            ll.setMargins(20, 10, 10, 10);
            info.setLayoutParams(ll);
            info.setText(label.get(i)+": "+value.get(i));
            layout.addView(info);

        }

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
