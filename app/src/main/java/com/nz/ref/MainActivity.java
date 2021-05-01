package com.nz.ref;

import android.annotation.TargetApi;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.hhhaiai.reflib.HiddenApiBypass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        RelativeLayout layout = new RelativeLayout(this);
        TextView textView = new TextView(this);
        textView.setText(testByPass() ? "success" : "fail");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        layout.addView(textView, params);
        setContentView(layout);
    }

    @TargetApi(Build.VERSION_CODES.P)
    private boolean canAccessHiddenApi() {
        try {
//            /**
//             * @hide
//             */
//            public @HiddenApiEnforcementPolicy int getHiddenApiEnforcementPolicy() {
            ApplicationInfo.class.getMethod("getHiddenApiEnforcementPolicy");
        } catch (Exception e) {
            loge(e);
            return false;
        }
        try {
            //  @UnsupportedAppUsage(maxTargetSdk = Build.VERSION_CODES.P, trackingBug = 115609023)
            //    private boolean isPackageUnavailable(PackageManager pm) {
            ApplicationInfo.class.getMethod("isPackageUnavailable", PackageManager.class);
        } catch (NoSuchMethodException e) {
            loge(e);
            return false;
        }
        try {
            //    /**
            //     * Check whether the application is encryption aware.
            //     *
            //     * @see #isDirectBootAware()
            //     * @see #isPartiallyDirectBootAware()
            //     *
            //     * @hide
            //     */
            //    @SystemApi
            //    public boolean isEncryptionAware() {
            ApplicationInfo.class.getMethod("isEncryptionAware");
        } catch (NoSuchMethodException e) {
            loge(e);
            return false;
        }
        try {
            //   /** @hide */
            //    @TestApi
            ApplicationInfo.class.getMethod("isSystemApp");
        } catch (NoSuchMethodException e) {
            loge(e);
            return false;
        }
        return true;
    }

    private void loge(Exception e) {
        Log.e("sanbo", Log.getStackTraceString(e));
    }

        private boolean testByPass() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return true;
//            return HiddenApiBypass.setHiddenApiExemptions("Landroid/") && canAccessHiddenApi();
            return HiddenApiBypass.setHiddenApiExemptions("L") && canAccessHiddenApi();
        }
//    private boolean testByPass() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return true;
//        return areUok();
//    }

//    @RequiresApi(api = Build.VERSION_CODES.P)
//    private boolean areUok() {
////        boolean setHiddenApiExemptions = HiddenApiBypass.setHiddenApiExemptions("Landroid/");
////        logi("setHiddenApiExemptions : " + setHiddenApiExemptions);
//        Method getHiddenApiEnforcementPolicy_hide = NzReflect.getMethod(ApplicationInfo.class, "getHiddenApiEnforcementPolicy");
//
//        if (getHiddenApiEnforcementPolicy_hide == null) {
//            logi("getHiddenApiEnforcementPolicy_hide is null!");
//            return false;
//        }
//        Method isPackageUnavailable_UnsupportedAppUsage = NzReflect.getMethod(ApplicationInfo.class, "isPackageUnavailable", PackageManager.class);
//
//        if (isPackageUnavailable_UnsupportedAppUsage == null) {
//            logi("isPackageUnavailable_UnsupportedAppUsage is null!");
//            return false;
//        }
//        Method isEncryptionAware_SystemApi = NzReflect.getMethod(ApplicationInfo.class, "isEncryptionAware");
//        if (isEncryptionAware_SystemApi == null) {
//            logi("isEncryptionAware_SystemApi is null!");
//            return false;
//        }
//        // /** @hide */
//        //    @TestApi
//        Method isSystemApp_TestApi = NzReflect.getMethod(ApplicationInfo.class, "isSystemApp");
//        if (isSystemApp_TestApi == null) {
//            logi("isSystemApp_TestApi is null!");
//            return false;
//        }
//        return true;
//    }
//
//
//    private void logi(String info) {
//        Log.i("sanbo", info);
//    }
}