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

import java.io.IOException;

import io.delmar.util.DataLoader;

/**
 * Created by jinw on 19/09/13.
 */
public class FacebookActivity extends BaseActivity {

    public static final String DELMAR_FACEBOOK_GRAPH = "http://graph.facebook.com/delmarcargo";
    private Page page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_brief);
        page = new Page();
    }

    @Override
    public void onStart() {
        super.onStart();

        new getJsonTask().execute(DELMAR_FACEBOOK_GRAPH);

        showFacebook(page);
    }

    private void showFacebook(Page page) {
        TextView textView = (TextView) this.findViewById(R.id.name);
        textView.setText(page.getName());

        textView = (TextView) this.findViewById(R.id.about);
        textView.setText(page.getAbout());

        textView = (TextView) this.findViewById(R.id.phone);
        textView.setText(page.getPhone());

        textView = (TextView) this.findViewById(R.id.website);
        textView.setText(page.getWebsite());
    }

    protected class getJsonTask extends AsyncTask<String, Integer, Page> {
        protected Page doInBackground(String... urls) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            int count = urls.length;
            for (int i = 0; i < count; i++) {
                publishProgress((int) ((i / (float) count) * 100));
                if (isCancelled()) break;
            }
            Page page = restTemplate.getForObject(DELMAR_FACEBOOK_GRAPH, Page.class);
            return page;
        }

        // This is called each time you call publishProgress()
        protected void onProgressUpdate(Integer... progress) {
            // setProgressPercent(progress[0]);
        }

        // This is called when doInBackground() is finished
        protected void onPostExecute(Page result) {
            // showNotification("Downloaded " + result + " bytes");
            showFacebook(result);
        }
    }
}
