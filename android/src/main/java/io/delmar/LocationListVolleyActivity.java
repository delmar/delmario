package io.delmar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationListVolleyActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private String TAG = this.getClass().getSimpleName();

    public static final String DELMAR_API_URL = "http://www.delmarcargo.com/api";
    public static final String OFFICE_LIST_URL = DELMAR_API_URL + "/locations/offices/list.json";
    public static final String AGENT_LIST_URL = DELMAR_API_URL + "/locations/agents/list.json";
    public static final String AGENT_AIR_LIST_URL = DELMAR_API_URL + "/locations/agents/air/list.json";
    public static final String AGENT_OCEAN_LIST_URL = DELMAR_API_URL + "/locations/agents/ocean/list.json";

    private ListView mListView;
    private RequestQueue mRequestQueue;
    private ArrayList<Location> locations;
    private LayoutInflater mLayoutInflater;
    private VolleyAdapter mVolleyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.title_section1),
                                getString(R.string.title_section2),
                                getString(R.string.title_section3),
                        }),
                this);

    }

    private void showLocationList(String url) {
        mLayoutInflater = LayoutInflater.from(this);
        locations = new ArrayList<Location>();
        mVolleyAdapter = new VolleyAdapter();
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(mVolleyAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location loc = (Location) mVolleyAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://maps.google.com/maps?q=" + loc.getAddress1() + "+" + loc.getCity()));
                startActivity(intent);
            }
        });
        mRequestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jr = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, "size = " + response.length());
                        parseJSON(response);
                        mVolleyAdapter.notifyDataSetChanged();
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

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location_list_volley, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
/*
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
/*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
*/
        if (position == 0) {
            showLocationList(OFFICE_LIST_URL);
        } else if (position == 1) {
            showLocationList(AGENT_AIR_LIST_URL);
        } else if (position == 2) {
            showLocationList(AGENT_OCEAN_LIST_URL);
        } else {
            Toast.makeText(getApplicationContext(), R.string.not_valid, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_location_list, container, false);
/*
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            Toast.makeText(getActivity(), R.string.no_network_connection, Toast.LENGTH_SHORT).show();
*/

            return rootView;
        }

    }

    private void parseJSON(JSONArray items) {
        try {
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                Location location = new Location();
                location.setName(item.optString("name"));
                location.setAddress1(item.optString("address1"));
                location.setAddress2(item.isNull("address2") ? null : item.optString("address2"));
                location.setCity(item.optString("city"));
                location.setProvState(item.isNull("provState") ? null : item.optString("provState"));
                location.setZipCode(item.isNull("zipCode") ? null : item.optString("zipCode"));
                location.setCountry(item.optString("country"));
                locations.add(location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Location {
        String name;
        String city;
        String address1;
        String address2;
        String country;
        String zipCode;
        String provState;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getProvState() {
            return provState;
        }

        public void setProvState(String provState) {
            this.provState = provState;
        }
    }

    class VolleyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return locations.size();
        }

        @Override
        public Object getItem(int i) {
            return locations.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = mLayoutInflater.inflate(R.layout.fragment_location_list, null);
                viewHolder.tv = (TextView) view.findViewById(R.id.section_label);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            Location location = locations.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append(location.getName());
            sb.append("\n");
            if (location.getAddress1() != null) {
                sb.append(location.getAddress1());
                sb.append("\n");
            }
            if (location.getAddress2() != null) {
                sb.append(location.getAddress2());
                sb.append("\n");
            }
            if (location.getCity() != null) {
                sb.append(location.getCity()).append(", ");
            }
            if (location.getProvState() != null) {
                sb.append(location.getProvState()).append(", ");
            }
            if (location.getZipCode() != null) {
                sb.append(location.getZipCode()).append(", ");
            }
            sb.append(location.getCountry());
            viewHolder.tv.setText(sb.toString());
            return view;
        }

        class ViewHolder {
            TextView tv;
        }

    }

}
