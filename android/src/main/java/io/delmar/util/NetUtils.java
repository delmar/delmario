package io.delmar.util;

import android.content.Context;
import android.content.pm.PackageManager;

import io.delmar.Config;

import static io.delmar.util.LogUtils.makeLogTag;
import static io.delmar.util.LogUtils.LOGD;
import static io.delmar.util.LogUtils.LOGE;

public class NetUtils {
    private static final String TAG = makeLogTag(NetUtils.class);
    private static String mUserAgent = null;

    public static String getUserAgent(Context mContext) {
        if (mUserAgent == null) {
            mUserAgent = Config.APP_NAME;
            try {
                String packageName = mContext.getPackageName();
                String version = mContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
                mUserAgent = mUserAgent + " (" + packageName + "/" + version + ")";
                LOGD(TAG, "User agent set to: " + mUserAgent);
            } catch (PackageManager.NameNotFoundException e) {
                LOGE(TAG, "Unable to find self by package name", e);
            }
        }
        return mUserAgent;
    }
}
