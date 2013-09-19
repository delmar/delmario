package io.delmar;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jinw on 19/09/13.
 */
public class FacebookActivity extends Activity {

    public static final String DELMAR_FACEBOOK_GRAPH = "http://graph.facebook.com/delmarcargo";
    TextView nameView;
    TextView aboutView;
    TextView phoneView;
    TextView websiteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_brief);

        new downloadJsonTask().execute(DELMAR_FACEBOOK_GRAPH);

        TextView nameView = (TextView) findViewById(R.id.name);
        TextView aboutView = (TextView) findViewById(R.id.about);
        TextView phoneView = (TextView) findViewById(R.id.phone);
        TextView websiteView = (TextView) findViewById(R.id.website);

    }

    private class downloadJsonTask extends AsyncTask<String, Void, Page> {
        protected Page doInBackground(String... urls) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(urls[0], Page.class);
        }

        protected void onPostExecute(Page result) {
/*
            Fragment fragment = new FacebookFragment();
            Bundle args = new Bundle();
            args.putInt(FacebookFragment.ARG_FBGRAPH_URL, result.getName());
            fragment.setArguments(args);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

*/
            nameView.setText(result.getName());
            aboutView.setText(result.getAbout());
            phoneView.setText(result.getPhone());
            websiteView.setText(result.getWebsite());
        }
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class FacebookFragment extends Fragment {
        public static final String ARG_FBGRAPH_URL = "fbgraph_url";

        public FacebookFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.facebook_brief, container, false);

            TextView nameView = (TextView) rootView.findViewById(R.id.name);
            // nameView.setText(page.getName());
            return rootView;
        }
    }
}
