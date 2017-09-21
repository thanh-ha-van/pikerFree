package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.ImagePost;

public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.MyViewHolder> {
    private List<ImagePost> imagePosts;
    private ItemClickListener mClickListener;
    private int currentPosition;
    private Context context;

    public ImagePickerAdapter(Context context, List<ImagePost> dataSet, ItemClickListener listener) {
        this.context = context;
        this.imagePosts = dataSet;
        mClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_item_image)
        ImageView imgItemImage;
        @BindView(R.id.text_uploading)
        TextView tvUploading;
        @BindView(R.id.delete_button)
        ImageView deleteButton;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            currentPosition = getAdapterPosition();
            if (mClickListener != null && currentPosition == getItemCount() - 1) {
                mClickListener.onAddImagesToAdapter();
            }
//            else if (view.getId() != deleteButton.getId()) {
//                if (deleteButton.getVisibility() == View.GONE)
//                    deleteButton.setVisibility(View.VISIBLE);
//                else deleteButton.setVisibility(View.GONE);
//            }
//            if (deleteButton.getVisibility() == View.VISIBLE && view.getId() == deleteButton.getId()) {
//                mClickListener.onItemDeleted(currentPosition);
//            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_images, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        File imgFile = new File(imagePosts.get(position).getPathLocal());
        if (imgFile.exists()) {
            Glide.with(context)
                    .load(imgFile)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.file)
                            .centerCrop()
                            .dontAnimate()
                            .override(120, 160)
                            .dontTransform())
                    .into(holder.imgItemImage);
        }
        if (imagePosts.get(position).isUploadDone()) {
            holder.tvUploading.setTrans;
            holder.tvUploading.setVisibility(View.GONE);
        } else holder.tvUploading.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return imagePosts.size();
    }

    public interface ItemClickListener {

        void onAddImagesToAdapter();

        void onItemDeleted(int position);
    }

}