package ha.thanh.pikerfree.otherHandle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.Post;

/**
 * Created by MyPC on 17/09/2017.
 */

public class ImageHolder extends RecyclerView.ViewHolder {

    private ImageView imgView;

    private Integer post;

    public ImageHolder(View itemView) {
        super(itemView);
        this.imgView = (ImageView) itemView.findViewById(R.id.img_item_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Toast.makeText(context, "image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setImage(Integer post) {
        this.post = post;
        this.imgView.setImageResource(R.drawable.demo);
    }
}
