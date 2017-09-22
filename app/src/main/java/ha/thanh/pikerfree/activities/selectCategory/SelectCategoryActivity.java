package ha.thanh.pikerfree.activities.selectCategory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.CategoryAdapter;
import ha.thanh.pikerfree.customviews.CustomEditText;
import ha.thanh.pikerfree.models.categoryItem.EntryItem;
import ha.thanh.pikerfree.models.categoryItem.Item;
import ha.thanh.pikerfree.models.categoryItem.SectionItem;

public class SelectCategoryActivity extends AppCompatActivity {

    @BindView(R.id.lv_session)
    public ListView listView;
    @BindView(R.id.edtSearch)
    public CustomEditText edtSearch;
    ArrayList<Item> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        getData();

        initView();
    }

    public void initView() {
        ButterKnife.bind(this);
        listView.requestFocus();
        final CategoryAdapter adapter = new CategoryAdapter(this, categoryList);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void getData() {

        categoryList = new ArrayList<>();
        String[] section = getResources().getStringArray(R.array.Section);
        String[] child1 = getResources().getStringArray(R.array.Accessories);
        String[] child2 = getResources().getStringArray(R.array.BabyStuffs);
        String[] child3 = getResources().getStringArray(R.array.Cloths);
        String[] child4 = getResources().getStringArray(R.array.Electronics);
        String[] child5 = getResources().getStringArray(R.array.Groceries);
        String[] child6 = getResources().getStringArray(R.array.HomeLiving);
        String[] child7 = getResources().getStringArray(R.array.Pets);
        String[] child8 = getResources().getStringArray(R.array.Others);
        String[][] all = {child1, child2, child3, child4, child5, child6, child7, child8};

        for (int i = 0; i < section.length; i++) {
            categoryList.add(new SectionItem(section[i]));
            for (int k = 0; k < all[i].length; k++) {
                categoryList.add(new EntryItem(all[i][k]));
            }
        }
    }
}

