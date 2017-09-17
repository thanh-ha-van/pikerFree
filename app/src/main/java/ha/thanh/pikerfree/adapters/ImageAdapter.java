package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.otherHandle.ImageHolder;

public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {
    private ArrayList<Integer> postImage;
//    private ArrayList<Uri> dataSet;
//    private ItemClickListener mClickListener;
//    private int currentPosition;

//    public ImageAdapter(Context context, ArrayList<Uri> dataSet) {
//        this.dataSet = dataSet;
//    }

    public ImageAdapter(ArrayList<Integer> postImage) {
        this.postImage = postImage;
    }

//    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        @BindView(R.id.img_item_image)
//        ImageView imgItemImage;
//
//        MyViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
//
////        @Override
////        public void onClick(View view) {
////
////            currentPosition = getAdapterPosition();
////            if (mClickListener != null) {
////                if (!dataSet.get(currentPosition).equals(Uri.EMPTY))
////                mClickListener.onItemClick(currentPosition);
////            }
////        }
//    }

//    public void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }


//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_images, parent, false);
//        return new MyViewHolder(itemView);
//    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_images, parent, false);
        ImageHolder viewHolder = new ImageHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {

        Integer post = postImage.get(position);
        holder.setImage(post);
    }

//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        Post post = postImage.get(position);
//        holder.imgItemImage.setImageResource(R.drawable.ic_image);
////        Uri uri = dataSet.get(position);
////        if (uri.equals(Uri.EMPTY)) {
////            holder.imgItemImage.setImageResource(R.drawable.ic_image);
////        }
////        holder.imgItemImage.setImageURI(uri);
//
//    }

    @Override
    public int getItemCount() {
        return postImage.size();
    }

//    public interface ItemClickListener {
//
//        void onItemClick(int position);
//    }

}