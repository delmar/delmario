package io.delmar.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static io.delmar.util.LogUtils.makeLogTag;

public class WiFiUtils {
    // Preference key and values associated with WiFi AP configuration.
    public static final String PREF_WIFI_AP_CONFIG = "pref_wifi_ap_config";
    public static final String WIFI_CONFIG_DONE = "done";
    public static final String WIFI_CONFIG_REQUESTED = "requested";

    private static final String TAG = makeLogTag(WiFiUtils.class);

    public static boolean isWiFiEnabled(final Context context) {
        final WifiManager wifiManager =
                (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    // Stored preferences associated with WiFi AP configuration.
    public static String getWiFiConfigStatus(final Context context) {
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_WIFI_AP_CONFIG, null);
    }

    public static void setWiFiConfigStatus(final Context context, final String status) {
        if (!WIFI_CONFIG_DONE.equals(status) && !WIFI_CONFIG_REQUESTED.equals(status))
            throw new IllegalArgumentException("Invalid WiFi Config status: " + status);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_WIFI_AP_CONFIG, status).commit();
    }

    public static void showWiFiDialog(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_wifi");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new WiFiDialog(isWiFiEnabled(activity)).show(ft, "dialog_wifi");
    }

    public static class WiFiDialog extends DialogFragment {
        private boolean mWiFiEnabled;

        public WiFiDialog() {}

        public WiFiDialog(boolean wifiEnabled) {
            super();
            mWiFiEnabled = wifiEnabled;
        }
    }
}

