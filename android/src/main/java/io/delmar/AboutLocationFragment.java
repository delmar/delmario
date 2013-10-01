package io.delmar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class AboutLocationFragment extends Fragment {
    private static final String TAG = "AboutLocationFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group,
                             Bundle saved) {
        return inflater.inflate(R.layout.about_location, group, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebView webView = (WebView) getActivity().findViewById(
                R.id.locationcontent);

        webView.loadUrl("file:///android_asset/about_location.html");
    }
}
