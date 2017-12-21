package ha.thanh.pikerfree.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.dataHelper.SQLiteNotification;
import ha.thanh.pikerfree.utils.Utils;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<SQLiteNotification> dataSet;
    private CommentClickListener mClickListener;
    private Context context;

    public NotificationAdapter(Context context, List<SQLiteNotification> dataSet, CommentClickListener listener) {
        this.context = context;
        this.dataSet = dataSet;
        this.mClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.view_background)
        LinearLayout linearLayout;
        @BindView(R.id.tv_body)
        CustomTextView tvBody;

        @BindView(R.id.btn_delete)
        ImageView btnDelete;

        @BindView(R.id.tv_time)
        CustomTextView tvTime;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataSet.get(getAdapterPosition()).setRead(1);
                    linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                    mClickListener.onClicked(getAdapterPosition());
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onDeleted(getAdapterPosition());
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
        if (dataSet.get(position).isRead() == 0) {
            holder.tvBody.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvBody.setTypeface(Typeface.DEFAULT_BOLD);
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.light));
        } else {
            holder.tvBody.setTextColor(context.getResources().getColor(R.color.GrayScale));
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        holder.tvTime.setText(Utils.getTimeInHour(dataSet.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface CommentClickListener {

        void onClicked(int position);

        void onDeleted(int position);

    }
}
