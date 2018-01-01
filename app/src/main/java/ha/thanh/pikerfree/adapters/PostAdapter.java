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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.utils.Utils;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private List<Post> dataSet;
    private ItemClickListener mClickListener;
    private Context mConText;
    private double lat;
    private double lng;

    public PostAdapter(Context context, List<Post> dataSet, ItemClickListener listener, double lat, double lng) {
        this.dataSet = dataSet;
        this.mConText = context;
        this.mClickListener = listener;
        this.lat = lat;
        this.lng = lng;
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
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.tv_description)
        TextView tvDes;
        @BindView(R.id.tv_owner_name)
        TextView tvOwnerName;
        @BindView(R.id.owner_pic)
        CircleImageView ownerPic;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) {
                mClickListener.onItemClick(getAdapterPosition());
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            Post post = dataSet.get(position);
            holder.tvDistance.setText(Utils.getDistance(lat, lng, post.getLocation().latitude, post.getLocation().longitude));
            holder.tvTitle.setText(post.getTitle());
            holder.tvDay.setText(Utils.getTimeString(post.getTimePosted()));
            holder.tvStatus.setText(getTextFromStatus(post.getStatus()));
            holder.tvDes.setText(post.getDescription());
            if (post.getStatus() == Constants.STATUS_CLOSE) {
                holder.tvStatus.setTextColor(mConText.getResources().getColor(R.color.orange));
            } else holder.tvStatus.setTextColor(mConText.getResources().getColor(R.color.green));

            final DatabaseReference userImagePref;
            userImagePref = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(post.getOwnerId()).child("avatarLink");
            userImagePref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String link = dataSnapshot.getValue(String.class);
                    FirebaseStorage
                            .getInstance()
                            .getReference()
                            .child(link).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(mConText)
                                            .load(uri)
                                            .apply(new RequestOptions()
                                                    .placeholder(R.drawable.ic_user)
                                                    .centerCrop()
                                                    .dontAnimate()
                                                    .override(100, 100)
                                                    .dontTransform())
                                            .into(holder.ownerPic);
                                }
                            });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            final DatabaseReference userPref;
            userPref = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(post.getOwnerId()).child("name");
            userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue(String.class);
                    holder.tvOwnerName.setText(name);
                    userPref.removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            getUserImageLink("userImages/" + post.getOwnerId() + ".jpg", holder.ownerPic);

            FirebaseStorage.getInstance()
                    .getReference()
                    .child("postImages")
                    .child(post.getPostId() + "")
                    .child("image_no_1.jpg")
                    .getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(mConText)
                                    .load(uri)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.background)
                                            .centerCrop()
                                            .dontAnimate()
                                            .override(800, 600)
                                            .dontTransform())
                                    .into(holder.imgPostImage);
                        }
                    });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void getUserImageLink(String link, final ImageView imageView) {
        StorageReference mStorageRef = FirebaseStorage.getInstance()
                .getReference().child(link);
        mStorageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            Glide.with(mConText)
                                    .load(uri)
                                    .apply(new RequestOptions()
                                            .centerCrop()
                                            .dontAnimate()
                                            .override(100, 100)
                                            .dontTransform())
                                    .into(imageView);
                        } catch (IllegalArgumentException e) {
                            e.getMessage();
                        }
                    }
                });
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

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}


