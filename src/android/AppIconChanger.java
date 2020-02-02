package com.outsystems.iconchanger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AppIconChanger extends CordovaPlugin {
    Activity activity;
    List<String> disableNames = new ArrayList<>();

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        // activity value must be set, for some reason cannot be set in the global variable declaration
        activity = cordova.getActivity();

        if ("changeIcon".equals(action)) {
            String activeName;

            JSONObject argList = args.getJSONObject(0);
            String iconName = argList.getString("iconName");

            // get the list of activities that need to be disabled
            this.getRunningActivity(activity);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(activity.getPackageName());
            stringBuilder.append("." + activity.getClass().getSimpleName() + "__");
            stringBuilder.append(iconName);

            activeName = stringBuilder.toString();

            this.setAppIcon(activeName, disableNames);

            callbackContext.success();
            return true;
        } else {
            callbackContext.error(action + " is not a supported action");
            return false;  // Returning false results in a "MethodNotFound" error.
        }
    }

    public void getRunningActivity(Context context) {
        try {
            String activityName;
            String shortActivityName;

            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);

            for (int i = 0; i < pi.activities.length; i++) {
                activityName = pi.activities[i].name;
                shortActivityName = activityName.replace(activity.getPackageName() + ".", "");

                if (!shortActivityName.equals(activity.getClass().getSimpleName())) {
                    disableNames.add(activityName);
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e("ERROR", "Could not get running activity list");
        }
    }

    public void setAppIcon(String activeName, List<String> disableNames) {
        new AppIconNameChanger.Builder(activity)
            .activeName(activeName) // String
            .disableNames(disableNames) // List<String>
            .packageName(activity.getPackageName())
            .build()
            .setNow();
    }

}