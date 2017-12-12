package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.Comment;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.utils.Utils;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<Comment> dataSet;
    private CommentClickListener mClickListener;
    private Context mConText;
    private String ownerId;
    private String userId;

    public CommentAdapter(Context context, List<Comment> dataSet, CommentClickListener listener, String OwnerId, String userID) {

        this.dataSet = dataSet;
        this.mConText = context;
        this.mClickListener = listener;
        this.ownerId = OwnerId;
        this.userId = userID;

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.op_pic)
        CircleImageView OpImage;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.img_delete_comment)
        ImageView btnDelete;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) {
                if (view.getId() == R.id.img_delete_comment)
                    mClickListener.onCommentDelete(getAdapterPosition());
                else
                    mClickListener.onCommentClicked(getAdapterPosition());
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final String itemUser = dataSet.get(position).getIdUser();

        FirebaseStorage
                .getInstance()
                .getReference()
                .child("userImages")
                .child(itemUser + ".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(mConText)
                                .load(uri)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.loading)
                                        .centerCrop()
                                        .dontAnimate()
                                        .override(100, 100)
                                        .dontTransform())
                                .into(holder.OpImage);
                    }
                });

        if (ownerId.equalsIgnoreCase(itemUser))
            holder.tvComment.setTextColor(mConText.getResources().getColor(R.color.blue));
        if (userId.equalsIgnoreCase(itemUser)) {

            holder.btnDelete.setVisibility(View.VISIBLE);
        }
        holder.tvComment.setText(dataSet.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface CommentClickListener {
        void onCommentClicked(int position);

        void onCommentDelete(int position);
    }
}