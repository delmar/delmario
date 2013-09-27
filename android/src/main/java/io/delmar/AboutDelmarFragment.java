package io.delmar;

import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import io.delmar.util.DataLoader;

public class AboutDelmarFragment extends Fragment {
    private static final String TAG = "AboutDelmarFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group,
                             Bundle saved) {
        return inflater.inflate(R.layout.about_content, group, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*
        WebView creditsWebView = (WebView) getActivity().findViewById(
                R.id.about_thirdsparty_credits);
        try {
            creditsWebView.loadData(DataLoader.loadData(getActivity()
                    .getBaseContext(), "credits_thirdparty"), "text/html",
                    "UTF-8");
        } catch (IOException ioe) {
            Log.e(TAG, "Error reading changelog file!", ioe);
        }
*/
    }
}
