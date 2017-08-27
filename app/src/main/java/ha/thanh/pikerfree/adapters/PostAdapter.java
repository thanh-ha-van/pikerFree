package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.DummyData;
import ha.thanh.pikerfree.models.Post;

/**
 * Created by HaVan on 8/27/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private List<Post> dataSet;
    private ItemClickListener mClickListener;
    private int currentPosition;
    private Context mConText;


    public PostAdapter(Context context, List<Post> dataSet) {
        this.dataSet = dataSet;
        this.mConText = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_post_image)
        ImageView imgPostImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_description)
        TextView tvDescription;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            currentPosition = getAdapterPosition();
            if (mClickListener != null) {
                mClickListener.onItemClick(currentPosition);
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Post post = dataSet.get(position);
        Uri uri = post.getLinkImages().get(0);
        if (uri == null) uri = DummyData.getIns().getUri();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.drawable.temple_images);

        Glide.with(this.mConText)
                .setDefaultRequestOptions(requestOptions)
                .load(uri)
                .into(holder.imgPostImage);

        holder.tvTitle.setText(post.getTitle());
        holder.tvDescription.setText(post.getDescription());

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}


