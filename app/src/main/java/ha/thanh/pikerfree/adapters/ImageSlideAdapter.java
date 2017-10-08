package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;


public class ImageSlideAdapter extends RecyclerView.Adapter<ImageSlideAdapter.MyViewHolder> {

    private boolean zoomOut = false;
    private List<String> mList;
    protected Context context;
    private OnclickView onclickView;

    public ImageSlideAdapter(Context context, List<String> list, OnclickView onclickView) {
        this.mList = list;
        this.context = context;
        this.onclickView = onclickView;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_item_image)
        ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onclickView.onClick(getAdapterPosition());
//            if (zoomOut) {
//
//                imageView.setLayoutParams(
//                        new LinearLayout.
//                                LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT));
//                imageView.setAdjustViewBounds(true);
//                zoomOut = false;
//            } else {
//                imageView.setLayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT));
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                zoomOut = true;
//            }
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slide, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Glide.with(this.context)
                .load(mList.get(position))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.file)
                        .error(R.drawable.action_button_bg)
                        .centerCrop()
                        .dontAnimate()
                        .override(400, 300)
                        .dontTransform())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnclickView {
        void onClick(int position);
    }

}
