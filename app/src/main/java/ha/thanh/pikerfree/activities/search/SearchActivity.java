package ha.thanh.pikerfree.activities.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.viewPost.PostActivity;
import ha.thanh.pikerfree.activities.viewProfile.ViewProfileActivity;
import ha.thanh.pikerfree.adapters.PostAdapter;
import ha.thanh.pikerfree.adapters.UserAdapter;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.CustomEditText;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.customviews.WaitingDialog;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.services.GPSTracker;

public class SearchActivity extends AppCompatActivity implements UserAdapter.ItemClickListener, PostAdapter.ItemClickListener {

    @BindView(R.id.search_post)
    CustomTextView tvSearchPost;
    @BindView(R.id.search_user)
    CustomTextView tvSearchUser;
    @BindView(R.id.tv_search_post)
    CustomEditText editTextKey;
    @BindView(R.id.rv_posts)
    RecyclerView rvPost;
    @BindView(R.id.rv_users)
    RecyclerView rvUsers;
    private FirebaseDatabase database;
    private List<Post> postList;
    private List<User> userList;
    private int postCount = -1;
    private String key;
    private WaitingDialog waitingDialog;
    private CustomAlertDialog alertDialog;
    private GPSTracker gpsTracker;
    private UserAdapter userAdapter;
    private PostAdapter postAdapter;
    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        waitingDialog = new WaitingDialog(this);
        waitingDialog.showDialog();
        initData();
        initView();
    }

    private void initData() {
        database = FirebaseDatabase.getInstance();
        postList = new ArrayList<>();
        userList = new ArrayList<>();
        Intent intent = getIntent();
        key = intent.getStringExtra(Constants.POST_SEARCH);
        editTextKey.setText(key);
        type = intent.getIntExtra(Constants.CATEGORY, 1);
        if (type == 1)
            getCurrentPostCount();
        else {
            onSearchUserClicked();
            searchUserByString(key);
        }
        gpsTracker = new GPSTracker(this);
        if (!gpsTracker.canGetLocation()) {
            showGPSRequest();
        }
    }

    void showGPSRequest() {

        CustomAlertDialog alertDialog = new CustomAlertDialog(this);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;

        alertDialog.showAlertDialog(getResources().getString(R.string.gps_off), getResources().getString(R.string.turn_gps));
        alertDialog.setListener(new CustomAlertDialog.AlertListener() {
            @Override
            public void onOkClicked() {
                startActivity(new Intent(action));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

    }

    @OnClick(R.id.btn_search)
    public void searchClicked() {

        if (editTextKey.getText().toString().equalsIgnoreCase(""))
            return;
        postList.clear();
        userList.clear();
        waitingDialog.showDialog();
        if (type == 1) {
            searchByString(editTextKey.getText().toString());
        } else searchUserByString(editTextKey.getText().toString());
    }

    @OnClick(R.id.search_user)
    public void onSearchUserClicked() {
        type = 2;
        tvSearchPost.setClickable(true);
        tvSearchUser.setClickable(false);
        tvSearchUser.setBackground(getDrawable(R.drawable.shape_rectangle_white));
        tvSearchUser.setTextColor(getResources().getColor(R.color.white));
        tvSearchPost.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvSearchPost.setBackground(getDrawable(R.drawable.shape_rectangle_white));
    }

    @OnClick(R.id.search_post)
    public void onSearchPostClicked() {
        type = 1;
        tvSearchPost.setClickable(false);
        tvSearchUser.setClickable(true);
        tvSearchUser.setBackground(getDrawable(R.drawable.shape_rectangle_gray));
        tvSearchPost.setTextColor(getResources().getColor(R.color.white));
        tvSearchUser.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvSearchPost.setBackground(getDrawable(R.drawable.shape_rectangle_green));
    }

    private void searchUserByString(final String key) {

        Query query = database.getReference()
                .child("users")
                .orderByChild("name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        User user = issue.getValue(User.class);
                        if (user.getName().toLowerCase().contains(key))
                            userList.add(user);
                        userAdapter.notifyDataSetChanged();
                        waitingDialog.hideDialog();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setTimeOut();
    }

    private void initView() {

        alertDialog = new CustomAlertDialog(this);

        postAdapter = new PostAdapter(this, postList, this, getUserLat(), getUserLng());
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        rvPost.setLayoutManager(layoutManager);
        rvPost.setAdapter(postAdapter);

        userAdapter = new UserAdapter(this, userList, this);
        LinearLayoutManager layoutManager2 =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager2.setStackFromEnd(true);
        rvUsers.setLayoutManager(layoutManager2);
        rvUsers.setAdapter(userAdapter);

        userAdapter.notifyDataSetChanged();
        postAdapter.notifyDataSetChanged();
    }

    private double getUserLat() {
        return gpsTracker.getLatitude();
    }

    private double getUserLng() {
        return gpsTracker.getLongitude();
    }

    @Override
    public void onChooseUser(int position) {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        intent.putExtra(Constants.USER_ID, userList.get(position).getId());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(Constants.POST_VIEW, postList.get(position).getPostId());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void getCurrentPostCount() {

        DatabaseReference postCountRef;
        postCountRef = database.getReference().child("postCount");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postCount = dataSnapshot.getValue(int.class);
                searchByString(key);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        postCountRef.addListenerForSingleValueEvent(eventListener);
    }

    private void searchByString(final String key) {

        for (int i = postCount; i > 0; i--) {
            searchKeyInId(i, key);
            if (i == 1)
                setTimeOut();
        }
    }

    private void setTimeOut() {
        Handler handler = new Handler();
        if (type == 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (postList.size() == 0)
                        onNoResult();
                }
            }, 6000);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (userList.size() == 0)
                        onNoResult();
                }
            }, 6000);
        }
    }

    private void onNoResult() {
        waitingDialog.hideDialog();
        alertDialog.showAlertDialog(getResources().getString(R.string.no_data), getResources().getString(R.string.no_data_mess));
    }

    private void onHasResult() {
        waitingDialog.hideDialog();
    }

    private void searchKeyInId(final int i, final String key) {

        DatabaseReference postTitlePref;
        postTitlePref = database.getReference().child("posts").child(i + "").child("title");

        postTitlePref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) return;
                String string = dataSnapshot.getValue(String.class);
                if (string.toLowerCase().contains(key.toLowerCase())) {
                    getPostByID(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPostByID(int i) {
        DatabaseReference postRef;
        postRef = database
                .getReference("posts")
                .child(i + "");
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    postList.add(dataSnapshot.getValue(Post.class));
                    postAdapter.notifyDataSetChanged();
                    onHasResult();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
