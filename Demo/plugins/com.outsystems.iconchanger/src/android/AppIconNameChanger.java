package com.outsystems.iconchanger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;

import java.util.List;


public class AppIconNameChanger {

    private Activity activity;
    List<String> disableNames;
    String activeName;
    String packageName;

    public AppIconNameChanger(Builder builder) {

        this.disableNames = builder.disableNames;
        this.activity = builder.activity;
        this.activeName = builder.activeName;
        this.packageName = builder.packageName;

    }

    public static class Builder {

        private Activity activity;
        List<String> disableNames;
        String activeName;
        String packageName;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder disableNames(List<String> disableNamesl) {
            this.disableNames = disableNamesl;
            return this;
        }

        public Builder activeName(String activeName) {
            this.activeName = activeName;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public AppIconNameChanger build() {
            return new AppIconNameChanger(this);
        }

    }

    public void setNow() {
        // disable old icon
        for (int i = 0; i < disableNames.size(); i++) {
            try {
                // run getComponentEnabledSetting to make sure activity-alias exists
                activity.getPackageManager().getComponentEnabledSetting(new ComponentName(packageName, disableNames.get(i)));

                activity.getPackageManager().setComponentEnabledSetting(
                        new ComponentName(packageName, disableNames.get(i)),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // enable new icon
        try {
            if (activity.getPackageManager().getComponentEnabledSetting(new ComponentName(packageName, activeName)) != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                activity.getPackageManager().setComponentEnabledSetting(
                        new ComponentName(packageName, activeName),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            }

        } catch (Exception unused) {
            Log.e("ERROR", "Could not find icon, will try to set default");

            Resources activityRes = activity.getResources();
            int default_icon_idResId = activityRes.getIdentifier("default_icon_id", "string", activity.getPackageName());
            String defaultIconID = activityRes.getString(default_icon_idResId);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(packageName);
            stringBuilder.append("." + activity.getClass().getSimpleName() + "__" + defaultIconID);
            activeName = stringBuilder.toString();

            try {
                activity.getPackageManager().setComponentEnabledSetting(
                        new ComponentName(packageName, activeName),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            } catch (Exception e2) {
                e2.getStackTrace();
            }
        }
    }
}