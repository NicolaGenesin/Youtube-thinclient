package com.z1911.thinyoutube;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.z1911.thinyoutube.Fragments.YoutubeListFragment;
import com.z1911.thinyoutube.Network.Const;
import com.z1911.thinyoutube.Network.IYoutubeService;

import retrofit.RestAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends FragmentActivity {

    private static IYoutubeService mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createApiClient();
        loadYoutubeListFragment();
        loadYoutubePlayerFragment("lMC8XYX5Ot0");
        initializeFont();
    }

    private void loadYoutubePlayerFragment(final String videoId){
        YouTubePlayerSupportFragment fragment = new YouTubePlayerSupportFragment();
        fragment.initialize(Const.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.playerContainer, fragment);
        fragmentTransaction.commit();
    }

    private void initializeFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/minimal_one.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // This method creates an ArrayList that has three Song objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.


    private void loadYoutubeListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        YoutubeListFragment fragment = new YoutubeListFragment();
        fragmentTransaction.add(R.id.listContainer, fragment);
        fragmentTransaction.commit();
    }

    private void createApiClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Const.Youtube)
                .build();

        mApi = restAdapter.create(IYoutubeService.class);
    }

    public static IYoutubeService getApiService() {
        return mApi;
    }
}
