package ha.thanh.pikerfree.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.dataHelper.SQLiteNotification;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<SQLiteNotification> dataSet;
    private CommentClickListener mClickListener;

    public NotificationAdapter(List<SQLiteNotification> dataSet, CommentClickListener listener) {

        this.dataSet = dataSet;
        this.mClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_body)
        TextView tvBody;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onClicked(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvBody.setText(dataSet.get(position).getMess());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface CommentClickListener {

        void onClicked(int position);

    }
}
