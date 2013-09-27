package io.delmar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.IOException;

import io.delmar.util.DataLoader;

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

        try {
            webView.loadData(DataLoader.loadData(
                    getActivity().getBaseContext(), "location"), "text/html",
                    "UTF-8");
        } catch (IOException ioe) {
            Log.e(TAG, "Error reading location file!", ioe);
        }
    }
}
