# Cordova App Icon Changer for Android

###### Powered by  
![OutSystems][oslogo-image]

[oslogo-image]:https://www.outsystems.com/-/media/themes/outsystems/website/site-theme/imgs/logo.svg

## Installation

You must fork or "git clone" the repository, make the changes described below and import the plugin using one of the following commands:
```
$ cordova plugin add your-forked-repository-url.git
```
or 
```
$ cordova plugin add cordova-plugin-app-icon-changer
```

## Preparing your app for icon switching

We have bundled 3 differents icons, as an example, in the  in the `plugin.xml` and in the `res/appIconChanger` that must be changed in order to make your icons work.

Replace the icons with the ones you'd like your users to be able to switch to, using all relevant resolutions as usual.

#### Notes
>Google Play Store does not allow switching to arbitrary icons, so they must be bundled within your app before releasing it to the store.

> Android has specific requirements for icon names.  They can only contain letters, numbers or underscores. 

You must add your icon references to your plugin.xml file.  
Place your icons files in a folder relative to the root of the app.  As you can see in the example below, we placed them in the plugin directory: 'appIconChanger' inside the res folder.  You will need to create a resource-file entry in the plugin.xml for each icon you would like to change to.  

```xml
<source-file src="res/appIconChanger/redapple.png" target-dir="app/src/main/res/drawable-port-xxhdpi"/>
```

We need to somehow reference what the name of the default icon is.  This will be the first icon used on initial app load, and it will also be set as a fallback in case an icon the user selects doesn't exist.

```xml
<config-file target="res/values/strings.xml" parent="/*">
	<string name="default_icon_id">redapple</string>
</config-file>
```

Here's where the different icons are referenced.  We need to create an Activity-Alias for each icon (Look inside the `<config-file target="AndroidManifest.xml" parent="application">` section.  Looking at the first activity-alias below, you should note the following:
1. All activity-alias entries need to be set to enabled="false" except the one for the default entry;
2. The name needs to be set using the following syntax: .<Main Activity Name>__<icon_name> (there is a period at the beginning of the name, followed by the Main Activity Name you have set, there's then two underscores followed by the icon filename with no extension);
3. The target activity needs to be set to the same name as the Main Activity (including the period prefix);
4. The icon needs to be set to the proper filename and storage location that you have set in the resource-file inclusion above.  The default location is the drawable folder.
	
The third activity-alias below is the default entry.  This will be the icon that loads when the app is first installed and it's also used as a fallback icon in case something happens when we try to change to a different icon. This is the only activity-alias that should be marked as enabled.


```xml
<config-file target="AndroidManifest.xml" parent="application">
        <activity-alias 
          android:enabled="false" 
          android:name=".MainActivity__greenapple" 
          android:icon="@drawable/greenapple" 
          android:label="@string/app_name" 
          android:targetActivity=".MainActivity" 
          android:configChanges="orientation|screenSize" 
          android:noHistory="true">
          <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
        </activity-alias>
         <activity-alias
          android:enabled="false"
          android:configChanges="orientation|screenSize"
          android:icon="@drawable/blueapple"
          android:label="@string/app_name" 
          android:name=".MainActivity__blueapple"
          android:noHistory="true" 
          android:targetActivity=".MainActivity">
          <intent-filter>
              <action android:name="android.intent.action.MAIN" />
              <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
        </activity-alias>

        <!-- the default activity -->
        <activity-alias 
          android:enabled="true" 
          android:name=".MainActivity__redapple" 
          android:icon="@drawable/redapple" 
          android:label="@string/app_name" 
          android:targetActivity=".MainActivity" 
          android:configChanges="orientation|screenSize" 
          android:noHistory="true">
          <intent-filter>
            <action android:name="android.intent.action.MAIN"/>
            <category android:name="android.intent.category.LAUNCHER"/>
          </intent-filter>
        </activity-alias>
    </config-file>   
```

##### Here is a full example of all the changes you need to add to your plugin.xml file for Android icon changes:

```xml
<platform name="android">
    <config-file target="config.xml" parent="/*">
        <feature name="AppIconChanger">
            <param name="android-package" value="com.outsystems.iconchanger.AppIconChanger" onload="true" />
        </feature>
    </config-file>

    <!-- Hook for fixing AndroidManifest.xml -->
    <hook type="after_plugin_install" src="hooks/ManifestFix.js" />

    <!-- Java files -->
    <source-file src="src/android/AppIconChanger.java" target-dir="src/com/outsystems/iconchanger" />
    <source-file src="src/android/AppIconNameChanger.java" target-dir="src/com/outsystems/iconchanger" />

    <!-- Icons -->
    <source-file src="res/appIconChanger/redapple.png" target-dir="app/src/main/res/drawable-port-xxhdpi"/>
    <source-file src="res/appIconChanger/blueapple.png" target-dir="app/src/main/res/drawable-port-xxhdpi"/>
    <source-file src="res/appIconChanger/greenapple.png" target-dir="app/src/main/res/drawable-port-xxhdpi"/>

    <!-- Setting the Default Icon -->
    <config-file target="res/values/strings.xml" parent="/*">
      <string name="default_icon_id">redapple</string>
    </config-file>

    <!-- Add the activity aliases -->
    <config-file target="AndroidManifest.xml" parent="application">
        <activity-alias 
          android:enabled="false" 
          android:name=".MainActivity__greenapple" 
          android:icon="@drawable/greenapple" 
          android:label="@string/app_name" 
          android:targetActivity=".MainActivity" 
          android:configChanges="orientation|screenSize" 
          android:noHistory="true">
          <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
        </activity-alias>
         <activity-alias
          android:enabled="false" 
          android:configChanges="orientation|screenSize"
          android:icon="@drawable/blueapple"
          android:label="@string/app_name" 
          android:name=".MainActivity__blueapple"
          android:noHistory="true" 
          android:targetActivity=".MainActivity">
          <intent-filter>
              <action android:name="android.intent.action.MAIN" />
              <category android:name="android.intent.category.LAUNCHER" />
          </intent-filter>
        </activity-alias>

        <!-- the default activity -->
        <activity-alias 
          android:enabled="true" 
          android:name=".MainActivity__redapple" 
          android:icon="@drawable/redapple" 
          android:label="@string/app_name" 
          android:targetActivity=".MainActivity" 
          android:configChanges="orientation|screenSize" 
          android:noHistory="true">
          <intent-filter>
            <action android:name="android.intent.action.MAIN"/>
            <category android:name="android.intent.category.LAUNCHER"/>
          </intent-filter>
        </activity-alias>
    </config-file> 

    <resource-file src="src/android/res/layout/appiconchanger.xml" target="res/layout/appiconchanger.xml" />
  </platform>
```

## Calling the Method

> Make sure to wait for `deviceready` before calling the function below:

### `changeIcon`

Call the `changeIcon` function passing the `iconName`.

```js
AppIconChanger.changeIcon(
    {
      iconName: "myBeautifulIcon"
    },
    function() { console.log("OK")},
    function(err) { console.log(err) }
);
```

## Limitations and Side effects

##### Android 9 API 28 or below:
- It may take almost 10 seconds for the icon change take effect;
- Pinned icons (shortcuts) on the home screen won´t be changed;
- The app icon on the "App Switcher" won't be changed.

##### Android 10 API 29:
- The app will close after the icon change, so you should call the changeIcon method when the user goes back to the home screen, or show an alert saying that your app needs to exit to complete the icon change.
https://source.android.com/setup/start/android-10-release#limitations_to_hiding_app_icons

