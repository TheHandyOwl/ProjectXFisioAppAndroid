# FisioApp Project For Android

## Deploy

### Create Google API Key File  

If you try to start the application, you should see an error because there is no resource containing a valid API key to start Google Maps.

Try to create a new file:
yourAppFolder/app/src/main/res/value/google_maps_api_key.xml

You can see an example of `google_maps_api_key.xml` file here:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
<string name="GOOGLE_MAPS_API_KEY">YourAPIKeyHere</string>
</resources>
```

If you are looking for this file you won't find it. You should create your own file.

### Verify the name / IP of your backend's servers.

`localhost` name doesn't work. For this reason, you must change the name / IP of your servers in the file:
yourAppFolder/repository/src/build.gradle

You can see an example here:
```
apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'

android {
    ext.http_debug_server_name = "\"http://192.168.1.41:3000\""
    ext.http_release_server_name = "\"http://192.168.1.41:3000\""
}
```

If you change the routes to the endpoints, you must also change it in this file.
