package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private ArrayList<Uri> dataSet;
    private ItemClickListener mClickListener;
    private int currentPosition;

    public ImageAdapter(Context context, ArrayList<Uri> dataSet) {
        this.dataSet = dataSet;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_item_image)
        ImageView imgItemImage;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            currentPosition = getAdapterPosition();
            if (mClickListener != null) {
                if (!dataSet.get(currentPosition).equals(Uri.EMPTY))
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
                .inflate(R.layout.item_images, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Uri uri = dataSet.get(position);
        if (uri.equals(Uri.EMPTY)) {
            holder.imgItemImage.setImageResource(R.drawable.ic_image);
        }
        holder.imgItemImage.setImageURI(uri);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface ItemClickListener {

        void onItemClick(int position);
    }

}