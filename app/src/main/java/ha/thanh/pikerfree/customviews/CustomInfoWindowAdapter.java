package ha.thanh.pikerfree.customviews;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.utils.Utils;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    private Post post;

    public CustomInfoWindowAdapter(Activity context, Post post) {
        this.context = context;
        this.post = post;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = context.getLayoutInflater().inflate(R.layout.item_custom_info_window, null);

        CustomTextView tvTitle = (CustomTextView) view.findViewById(R.id.tv_title);
        CustomTextView tvTime = (CustomTextView) view.findViewById(R.id.tv_day);
        CustomTextView tvStatus = (CustomTextView) view.findViewById(R.id.tv_status);

        tvTitle.setText(marker.getTitle());
        tvTime.setText(Utils.getTimeString(post.getTimePosted()));
        tvStatus.setText(getStatus());
        return view;
    }

    private String getStatus() {
        if (post.getStatus() == 1)
            return "Opening";
        return "Closed";
    }
}
