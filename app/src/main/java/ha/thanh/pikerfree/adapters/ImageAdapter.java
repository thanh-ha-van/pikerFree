package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private String[] filePaths;
    private ItemClickListener mClickListener;
    private int currentPosition;

    public ImageAdapter(Context context, String[] dataSet) {
        this.filePaths = dataSet;
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
                mClickListener.onAddImagesToAdapter();
            }
            else {
                deleteButton.setVisibility(View.VISIBLE);
            }
            if (deleteButton.getVisibility() == View.VISIBLE && view.getId() == deleteButton.getId()) {
                mClickListener.onItemDeleted(currentPosition);
            }
            else deleteButton.setVisibility(View.GONE);
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_images, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        File imgFile = new File(filePaths[position]);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgItemImage.setImageBitmap(myBitmap);
        }
        startUploadImages(filePaths[position]);

    }
    public void startUploadImages (String filepath) {

    }

    @Override
    public int getItemCount() {
        return filePaths.length;
    }

    public interface ItemClickListener {
        void onAddImagesToAdapter();
        void onItemDeleted(int position);
    }

}