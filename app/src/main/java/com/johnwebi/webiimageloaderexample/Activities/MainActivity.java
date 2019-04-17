package com.johnwebi.webiimageloaderexample.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.johnwebi.webiimageloaderexample.Adapters.PhotoAdapter;
import com.johnwebi.webiimageloaderexample.Api.ApiClient;
import com.johnwebi.webiimageloaderexample.Api.ApiInterface;
import com.johnwebi.webiimageloaderexample.Models.Photo;
import com.johnwebi.webiimageloaderexample.Models.Urls;
import com.johnwebi.webiimageloaderexample.R;
import com.johnwebi.webiimageloaderexample.Service.DetectConnection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String HTTPS_TWITTER_COM_JOHNSWEBI = "https://twitter.com/johnswebi";
    public static final String GITHUB = "Github";
    public static final String HTTPS_GITHUB_COM_JOHNWEBI = "https://github.com/johnwebi";
    public static final String TWITTER = "Twitter";
    private int COLUMN_NUM = 2;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyclerView photoRecyclerView;
    private List<Photo> photoArrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    private PhotoAdapter photoAdapter;
    private Boolean isFabOpen = false;
    private FloatingActionButton plusFab,twitterFab,githubFab;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BindView();
        LoadPhotos();
        checkIfUserConnected();
        RefreshLayout();
        OpenAnimationFab();
        OnTwitterFabClick();
        OnGithubFabClick();
    }

    private void OpenIntent(String webViewTitle, String webViewUrl) {
        Intent myIntent = new Intent(this, WebViewActivity.class);
        myIntent.putExtra("ad_url", webViewTitle);
        myIntent.putExtra("ad_title", webViewUrl);
        startActivity(myIntent);
    }

    private void OnGithubFabClick() {
        githubFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenIntent(HTTPS_GITHUB_COM_JOHNWEBI, GITHUB);
            }
        });

    }

    private void OnTwitterFabClick() {
        twitterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenIntent(HTTPS_TWITTER_COM_JOHNSWEBI, TWITTER);
            }
        });
    }

    private void OpenAnimationFab() {
        plusFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimateFAB();
            }
        });
    }


    private void RefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    private void checkIfUserConnected() {
        if (!DetectConnection.checkInternetConnection(getApplicationContext())) {
            Toast.makeText(this, "Check you internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void BindView() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(COLUMN_NUM, LinearLayout.VERTICAL);
        photoRecyclerView = findViewById(R.id.photos_recyclerview);
        photoRecyclerView.setLayoutManager(staggeredGridLayoutManager );
        photoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        photoRecyclerView.setNestedScrollingEnabled(true);
        swipeRefreshLayout = findViewById(R.id.swipe_refreshLayout);
        plusFab = findViewById(R.id.fab);
        twitterFab = findViewById(R.id.fab1);
        githubFab = findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
    }

    public void LoadPhotos() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Photo>> call;
        call= apiInterface.getPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (!photoArrayList.isEmpty()) {
                        photoArrayList.clear();
                    }

                    photoArrayList = response.body();
                    photoAdapter = new PhotoAdapter(photoArrayList, getApplicationContext());
                    photoRecyclerView.setAdapter(photoAdapter);
                    photoAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this, "No Result!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });
    }

    public void AnimateFAB(){

        if(isFabOpen){

            plusFab.startAnimation(rotate_backward);
            twitterFab.startAnimation(fab_close);
            githubFab.startAnimation(fab_close);
            twitterFab.setClickable(false);
            githubFab.setClickable(false);
            isFabOpen = false;

        } else {

            plusFab.startAnimation(rotate_forward);
            twitterFab.startAnimation(fab_open);
            githubFab.startAnimation(fab_open);
            plusFab.setClickable(true);
            twitterFab.setClickable(true);
            isFabOpen = true;

        }
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                LoadPhotos();
            }
        }, 3000);   //3 seconds

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isFabOpen)
        AnimateFAB();
    }
}
