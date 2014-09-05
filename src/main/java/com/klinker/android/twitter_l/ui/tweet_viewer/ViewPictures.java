package com.klinker.android.twitter_l.ui.tweet_viewer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.klinker.android.twitter_l.R;
import com.klinker.android.twitter_l.adapters.PicturesArrayAdapter;
import com.klinker.android.twitter_l.settings.AppSettings;
import com.klinker.android.twitter_l.utils.Utils;

import org.lucasr.smoothie.AsyncListView;
import org.lucasr.smoothie.ItemManager;

import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;
import uk.co.senab.bitmapcache.BitmapLruCache;

/**
 * Created by luke on 5/25/14.
 */
public class ViewPictures extends Activity {

    public AppSettings settings;
    private Context context;

    private long tweetId;

    private LinearLayout spinner;
    private LinearLayout noContent;
    public AsyncListView listView;

    public ArrayList<String> images;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_slide_down);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_slide_down);

        context = this;
        settings = AppSettings.getInstance(this);

        setUpWindow();

        Utils.setUpPopupTheme(this, settings);

        setContentView(R.layout.list_view_activity);

        spinner = (LinearLayout) findViewById(R.id.list_progress);
        noContent = (LinearLayout) findViewById(R.id.no_content);

        listView = (AsyncListView) findViewById(R.id.listView);

        images = getIntent().getStringArrayListExtra("images");

        listView.setAdapter(new PicturesArrayAdapter(this, images, null));

        spinner.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    public void setUpWindow() {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // Params for the window.
        // You can easily set the alpha and the dim behind the window from here
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1.0f;    // lower than one makes it more transparent
        params.dimAmount = .75f;  // set it higher if you want to dim behind the window
        getWindow().setAttributes(params);

        // Gets the display size so that you can set the window to a percent of that
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        // You could also easily used an integer value from the shared preferences to set the percent
        if (height > width) {
            getWindow().setLayout((int) (width * .9), (int) (height * .8));
        } else {
            getWindow().setLayout((int) (width * .7), (int) (height * .8));
        }
    }

    public int toDP(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
    }
}