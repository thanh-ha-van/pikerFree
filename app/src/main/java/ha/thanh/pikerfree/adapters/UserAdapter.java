package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.models.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList;
    private ItemClickListener mClickListener;
    private Context context;

    public UserAdapter(Context context, List<User> list, ItemClickListener listener) {
        this.context = context;
        this.userList = list;
        mClickListener = listener;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requesting_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            holder.tvName.setText(userList.get(position).getName());
            if (userList.get(position).getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                holder.tvName.setText(userList.get(position).getName() + context.getResources().getString(R.string.you));
            if (userList.get(position).isOnline())
                holder.opStatus.setImageResource(R.drawable.shape_circle_green);
            else holder.opStatus.setImageResource(R.drawable.shape_circle_gray);
            getUserImageLink(userList.get(position).getAvatarLink(), holder.imgItemImage);
        } catch (Exception e) {

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
                            Glide.with(context)
                                    .load(uri)
                                    .apply(new RequestOptions()
                                            .error(R.drawable.ic_user)
                                            .centerCrop()
                                            .dontAnimate()
                                            .override(200, 200)
                                            .dontTransform())
                                    .into(imageView);
                        } catch (IllegalArgumentException e) {
                            e.getMessage();
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface ItemClickListener {

        void onChooseUser(int position);

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView imgItemImage;
        CustomTextView tvName;
        ImageView opStatus;

        MyViewHolder(View view) {
            super(view);
            imgItemImage = view.findViewById(R.id.owner_pic);
            tvName = view.findViewById(R.id.tv_owner_name);
            opStatus = view.findViewById(R.id.op_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onChooseUser(getAdapterPosition());

        }
    }
}

