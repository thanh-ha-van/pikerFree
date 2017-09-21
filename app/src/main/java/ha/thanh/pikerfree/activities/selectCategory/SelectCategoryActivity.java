package ha.thanh.pikerfree.activities.selectCategory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.CategoryAdapter;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SelectCategoryActivity extends AppCompatActivity {

    List<String> listWord;
    private CategoryAdapter mAdapter;
    ExpandableStickyListHeadersListView expandableStickyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        initView();
    }

    public void initView() {

        expandableStickyList = (ExpandableStickyListHeadersListView) findViewById(R.id.list);
        StickyListHeadersAdapter adapter = new CategoryAdapter(this);
        expandableStickyList.setAdapter(adapter);
        expandableStickyList.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                if (expandableStickyList.isHeaderCollapsed(headerId)) {
                    expandableStickyList.expand(headerId);
                } else {
                    expandableStickyList.collapse(headerId);
                }
            }
        });
    }
}

