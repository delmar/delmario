package io.delmar;

import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Demonstration of the use of a CursorLoader to load and display contacts
 * data in a fragment.
 */
public class LoaderCursorSupport extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
            CursorLoaderListFragment list = new CursorLoaderListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    public static class CursorLoaderListFragment extends ListFragment
            implements LoaderManager.LoaderCallbacks<Cursor> {

        // This is the Adapter being used to display the list's data.
        SimpleCursorAdapter mAdapter;

        // If non-null, this is the current filter the user has provided.
        String mCurFilter;
        int mCurCheckPosition = 0;
        boolean mDualPane;
        // Intent for starting the IntentService that populates location ContentProvider.
        // private Intent mServiceIntent;

        @Override public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Give some text to display if there is no data.  In a real
            // application this would come from a resource.
            setEmptyText("No Locations Found.");

            // We have a menu item to show in action bar.
            setHasOptionsMenu(true);

            // Create an empty adapter we will use to display the loaded data.
            mAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, null,
                    new String[] { LocationContract.Locations.COLUMN_NAME_TITLE },
                    new int[] { android.R.id.text1}, 0);
            setListAdapter(mAdapter);

            // Start out with a progress indicator.
            setListShown(false);

            // Prepare the loader.  Either re-connect with an existing one,
            // or start a new one.
            getLoaderManager().initLoader(0, null, this);
        }

        @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Place an action bar item for searching.
            MenuItem item = menu.add("Search");
            item.setIcon(R.drawable.ic_action_search);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            View searchView = SearchViewCompat.newSearchView(getActivity());
            if (searchView != null) {
                SearchViewCompat.setOnQueryTextListener(searchView,
                        new OnQueryTextListenerCompat() {
                            @Override
                            public boolean onQueryTextChange(String newText) {
                                // Called when the action bar search text has changed.  Update
                                // the search filter, and restart the loader to do a new query
                                // with this filter.
                                String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
                                // Don't do anything if the filter hasn't actually changed.
                                // Prevents restarting the loader when restoring state.
                                if (mCurFilter == null && newFilter == null) {
                                    return true;
                                }
                                if (mCurFilter != null && mCurFilter.equals(newFilter)) {
                                    return true;
                                }
                                mCurFilter = newFilter;
                                getLoaderManager().restartLoader(0, null, CursorLoaderListFragment.this);
                                return true;
                            }
                        });
                item.setActionView(searchView);
            }
        }

        @Override public void onListItemClick(ListView l, View v, int position, long id) {
            // Insert desired behavior here.
            Log.i("FragmentComplexList", "Item clicked: " + id);
            // showDetails(position);
        }

/*
        void showDetails(int index) {
            mCurCheckPosition = index;
            if (mDualPane) {
                getListView().setItemChecked(index, true);
                LocationDetailsFragment details = (LocationDetailsFragment)
                        getFragmentManager().findFragmentById(R.id.location_details);
                if (details == null || details.getShownIndex() != index) {
                    details = LocationDetailsFragment.newInstance(index);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.location_details, details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            }
        }
*/
        // These are the Contacts rows that we will retrieve.
        static final String[] LOCATIONS_SUMMARY_PROJECTION = new String[] {
                LocationContract.Locations._ID,
                LocationContract.Locations.COLUMN_NAME_TITLE,
        };

        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // This is called when a new Loader needs to be created.  This
            // sample only has one Loader, so we don't care about the ID.
            // First, pick the base URI to use depending on whether we are
            // currently filtering.
            Uri baseUri;
            if (mCurFilter != null) {
                baseUri = Uri.withAppendedPath(LocationContract.Locations.CONTENT_FILTER_URI, Uri.encode(mCurFilter));

            } else {
                baseUri = LocationContract.Locations.CONTENT_URI;
            }

            String select = "((" +
                    LocationContract.Locations.COLUMN_NAME_TITLE + " NOTNULL) AND (" +
                    LocationContract.Locations.COLUMN_NAME_TITLE + " != '' ))";

            return new CursorLoader(getActivity(), baseUri,
                    LOCATIONS_SUMMARY_PROJECTION, select, null,
                    LocationContract.Locations.COLUMN_NAME_TITLE + " COLLATE LOCALIZED ASC");
        }

        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            // Swap the new cursor in.  (The framework will take care of closing the
            // old cursor once we return.)
            mAdapter.swapCursor(data);

            // The list should now be shown.
            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
        }

        public void onLoaderReset(Loader<Cursor> loader) {
            // This is called when the last Cursor provided to onLoadFinished()
            // above is about to be closed.  We need to make sure we are no
            // longer using it.
            mAdapter.swapCursor(null);
        }
    }

    public static class LocationDetailsActivity extends FragmentActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // setTheme(SampleList.THEME);
            super.onCreate(savedInstanceState);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                finish();
                return;
            }

            if (savedInstanceState == null) {
                LocationDetailsFragment details = new LocationDetailsFragment();
                details.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(
                        android.R.id.content, details).commit();
            }
        }
    }

    public static class LocationDetailsFragment extends Fragment {
        public static LocationDetailsFragment newInstance(int index) {
            LocationDetailsFragment f = new LocationDetailsFragment();
            Bundle args = new Bundle();
            args.putInt("index", index);
            f.setArguments(args);
            return f;
        }

        public int getShownIndex()  {
            return getArguments().getInt("index", 0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (container == null) {
                return null;
            }

            // todo: create the map view
            ScrollView scroller = new ScrollView(getActivity());
            TextView text = new TextView(getActivity());
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    4, getActivity().getResources().getDisplayMetrics());
            text.setPadding(padding, padding, padding, padding);
            scroller.addView(text);
            text.setText("should be a map view");
            return scroller;
        }
    }
}
