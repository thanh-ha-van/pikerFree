package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import ha.thanh.pikerfree.utils.Utils;

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
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_status)
        TextView tvStatus;

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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Post post = dataSet.get(position);
        String linkFirstImages = Utils.getLinkImages(post).get(0);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.file);
        requestOptions.error(R.drawable.file);

        Glide.with(this.mConText)
                .setDefaultRequestOptions(requestOptions)
                .load(linkFirstImages)
                .into(holder.imgPostImage);

        holder.tvTitle.setText(post.getTitle());
        holder.tvDay.setText(Utils.getTimeString(post.getTimePosted()));
        holder.tvStatus.setText(getTextFromStatus(post.getStatus()));
        holder.tvStatus.setBackground(getDrawFromStatus(post.getStatus()));

    }

    private String getTextFromStatus(int status) {
        switch (status) {
            case 1:
                return mConText.getResources().getString(R.string.status_open);
            case 2:
                return mConText.getResources().getString(R.string.status_close);
            default:
                return "";
        }
    }

    private Drawable getDrawFromStatus(int status) {
        switch (status) {
            case 1:
                return mConText.getResources().getDrawable(R.drawable.plate_fill_green);
            case 2:
                return mConText.getResources().getDrawable(R.drawable.plate_fill_orange);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}


