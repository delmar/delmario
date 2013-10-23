package io.delmar;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines a contract between the LocationContract content provider and its clients. A contract defines the
 * information that a client needs to access the provider as one or more data tables. A contract
 * is a public, non-extendable (final) class that contains constants defining column names and
 * URIs. A well-written client depends only on the constants in the contract.
 */
public final class LocationContract {
    public static final String AUTHORITY = "io.delmar.provider.location";

    // This class cannot be instantiated
    private LocationContract() {
    }

    /**
     * locations table contract
     */
    public static final class Locations implements BaseColumns {

        // This class cannot be instantiated
        private Locations() {}

        /**
         * The table name offered by this provider
         */
        public static final String TABLE_NAME = "locations";

        /*
         * URI definitions
         */

        /**
         * The scheme part for this provider's URI
         */
        private static final String SCHEME = "content://";

        /**
         * Path parts for the URIs
         */

        /**
         * Path part for the locations URI
         */
        private static final String PATH_LOCATIONS = "/locations";

        /**
         * Path part for the locations URI
         */
        private static final String FILTER_LOCATIONS = "/locations/filter";

        /**
         * Path part for the Location ID URI
         */
        private static final String PATH_LOCATION_ID = "/locations/";

        /**
         * 0-relative position of a location ID segment in the path part of a location ID URI
         */
        public static final int LOCATION_ID_PATH_POSITION = 1;

        public static final int LOCATION_TITLE_FILTER_POSITION = 2;

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_LOCATIONS);

        /**
         * The content:// style URL for this table
         */
        public static final Uri CONTENT_FILTER_URI =  Uri.parse(SCHEME + AUTHORITY + FILTER_LOCATIONS);

        /**
         * The content URI base for a single location. Callers must
         * append a numeric location id to this Uri to retrieve a location
         */
        public static final Uri CONTENT_ID_URI_BASE
                = Uri.parse(SCHEME + AUTHORITY + PATH_LOCATION_ID);

        /**
         * The content URI match pattern for a single location, specified by its ID. Use this to match
         * incoming URIs or to construct an Intent.
         */
        public static final Uri CONTENT_ID_URI_PATTERN
                = Uri.parse(SCHEME + AUTHORITY + PATH_LOCATION_ID + "/#");

        public static final Uri CONTENT_FILTER_URI_PATTERN
                = Uri.parse(SCHEME + AUTHORITY + FILTER_LOCATIONS + "/*");

        /*
         * MIME type definitions
         */

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of locations.
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.delmar.location";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single
         * location.
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.delmar.location";

        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "modified DESC";

        /*
         * Column definitions
         */

        /**
         * Column name for the title of the location
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_TITLE = "title";

        /**
         * Column name of the location content
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_NOTE = "note";

        /**
         * Column name for the creation timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_CREATE_DATE = "created";

        /**
         * Column name for the modification timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_MODIFICATION_DATE = "modified";
    }
}
