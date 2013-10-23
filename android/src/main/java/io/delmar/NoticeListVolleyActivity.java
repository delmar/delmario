package io.delmar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jinw on 04/10/13.
 */
public class NoticeListVolleyActivity extends BaseActivity {

    public static final String JSON_URL = "http://www.delmarcargo.com/api/notices/list.json";
    private String TAG = this.getClass().getSimpleName();
    private ListView lstView;
    private RequestQueue mRequestQueue;
    private ArrayList<NoticeModel> arrNotices;
    private LayoutInflater lf;
    private VolleyAdapter va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        lf = LayoutInflater.from(this);

        arrNotices = new ArrayList<NoticeModel>();
        va = new VolleyAdapter();

        lstView = (ListView) findViewById(R.id.listView);
        lstView.setAdapter(va);
        mRequestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jr = new JsonArrayRequest(JSON_URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i(TAG, "size = " + response.length());
                    parseJSON(response);
                    va.notifyDataSetChanged();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, error.getMessage());
                }
            }
        );

        mRequestQueue.add(jr);
        mRequestQueue.start();
    }

    private void parseJSON(JSONArray items) {
        try {
            // JSONObject value = json.getJSONObject("value");
            // JSONArray items = value.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                NoticeModel nm = new NoticeModel();
                nm.setTitle(item.optString("title"));
                // nm.setDescription(item.optString("title"));
                nm.setLink(item.optString("id"));
                nm.setPubDate(item.optString("date"));
                arrNotices.add(nm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class NoticeModel {
        private int id;
        private String title;
        private String link;
        private String description;
        private String pubDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        void setTitle(String title) {
            this.title = title;
        }

        void setLink(String link) {
            this.link = link;
        }

        void setDescription(String description) {
            this.description = description;
        }

        void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        String getLink() {
            return link;
        }

        String getDescription() {
            return description;
        }

        String getPubDate() {
            return pubDate;
        }

        String getTitle() {

            return title;
        }
    }


    class VolleyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrNotices.size();
        }

        @Override
        public Object getItem(int i) {
            return arrNotices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = lf.inflate(R.layout.notice_list_item, null);
                vh.tvTitle = (TextView) view.findViewById(R.id.txtTitle);
                // vh.tvDesc = (TextView) view.findViewById(R.id.txtDesc);
                vh.tvDate = (TextView) view.findViewById(R.id.txtDate);
                view.setTag(vh);
            } else {
                vh = (ViewHolder) view.getTag();
            }

            NoticeModel nm = arrNotices.get(i);
            vh.tvTitle.setText(nm.getTitle());
            // vh.tvDesc.setText(nm.getDescription());
            vh.tvDate.setText(nm.getPubDate());
            return view;
        }

        class ViewHolder {
            TextView tvTitle;
            // TextView tvDesc;
            TextView tvDate;
        }

    }
}