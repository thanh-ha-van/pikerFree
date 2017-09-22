package ha.thanh.pikerfree.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.models.categoryItem.Item;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> item;
    private ArrayList<Item> originalItem;

    public CategoryAdapter(Context context, ArrayList<Item> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (item.get(position).isSection()) {

            convertView = inflater.inflate(R.layout.item_session, parent, false);
            CustomTextView tvSectionTitle = (CustomTextView) convertView.findViewById(R.id.tvSectionTitle);
            tvSectionTitle.setText(item.get(position).getTitle());
        } else {
            // if item
            convertView = inflater.inflate(R.layout.item_entry, parent, false);
            CustomTextView tvItemTitle = (CustomTextView) convertView.findViewById(R.id.tvItemTitle);
            tvItemTitle.setText(item.get(position).getTitle());
        }
        return convertView;
    }


    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                item = (ArrayList<Item>) results.values;
                notifyDataSetChanged();
            }

            @SuppressWarnings("null")
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Item> filteredArrayList = new ArrayList<>();


                if (originalItem == null || originalItem.size() == 0) {
                    originalItem = new ArrayList<>(item);
                }

                if (constraint == null && constraint.length() == 0) {
                    results.count = originalItem.size();
                    results.values = originalItem;
                } else {
                    constraint = constraint.toString().toLowerCase(Locale.ENGLISH);
                    for (int i = 0; i < originalItem.size(); i++) {
                        String title = originalItem.get(i).getTitle().toLowerCase(Locale.ENGLISH);
                        if (title.startsWith(constraint.toString())) {
                            filteredArrayList.add(originalItem.get(i));
                        }
                    }
                    results.count = filteredArrayList.size();
                    results.values = filteredArrayList;
                }

                return results;
            }
        };

        return filter;
    }
}
