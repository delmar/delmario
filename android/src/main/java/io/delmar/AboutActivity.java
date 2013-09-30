package io.delmar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class AboutActivity extends ActionBarActivity implements ActionBar.TabListener{
    private static final String BUNDLE_KEY_TABINDEX = "tabindex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Needs to be called before setting the content view
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.about_navigation);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab1 = getSupportActionBar().newTab();
        tab1.setText(R.string.about_delmar_title);
        tab1.setTabListener(this);

        ActionBar.Tab tab2 = getSupportActionBar().newTab();
        tab2.setText(R.string.about_location_title);
        tab2.setTabListener(this);

        getSupportActionBar().addTab(tab1);
        getSupportActionBar().addTab(tab2);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(BUNDLE_KEY_TABINDEX, getSupportActionBar()
                .getSelectedTab().getPosition());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getSupportActionBar().setSelectedNavigationItem(
                savedInstanceState.getInt(BUNDLE_KEY_TABINDEX));
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction transaction) {
        Log.i("Tab Reselected", tab.getText().toString());
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction transaction) {
        if (0 == tab.getPosition()) {
            AboutDelmarFragment fragment = new AboutDelmarFragment();
            transaction.replace(android.R.id.content, fragment);
        } else {
            AboutLocationFragment fragment = new AboutLocationFragment();
            transaction.replace(android.R.id.content, fragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction transaction) {
        Log.i("Tab Unselected", tab.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onOpenGoogleplusClick(View view) {
        openBrowser(this.getString(R.string.googleplus_url));
    }

    public void onOpenGithubClick(View view) {
        openBrowser(this.getString(R.string.github_url));
    }

    public void onOpenFeedbackClick(View view) {
        openBrowser(this.getString(R.string.github_issues_url));
    }

    public void onOpenFacebookClick(View view) {
        openBrowser(this.getString(R.string.facebook_url));
    }

    public void onOpenTwitterClick(View view) {
        openBrowser(this.getString(R.string.twitter_url));
    }

    private void openBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
    }

}
