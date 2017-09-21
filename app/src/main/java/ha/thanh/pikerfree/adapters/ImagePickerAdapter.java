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
    private StorageReference mStorageRef;

    public ImagePickerAdapter(Context context, List<ImagePost> dataSet, ItemClickListener listener) {
        this.imagePosts = dataSet;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_item_image)
        ImageView imgItemImage;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
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
                // pick more image when click to final item
                mClickListener.onAddImagesToAdapter();
            } else {
                // show delete option when click on an item.
                deleteButton.setVisibility(View.VISIBLE);
            }
            if (deleteButton.getVisibility() == View.VISIBLE && view.getId() == deleteButton.getId()) {

                // when show option item are show. if user choose to delete. Delete
                mClickListener.onItemDeleted(currentPosition);
            }
            // if user click again out of delete button. hide delete button.
            else deleteButton.setVisibility(View.GONE);
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
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgItemImage.setImageBitmap(myBitmap);
        }
        if (imagePosts.get(position).isUploadDone())
            holder.progressBar.setVisibility(View.GONE);
        else holder.progressBar.setVisibility(View.VISIBLE);
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