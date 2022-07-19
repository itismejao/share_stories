# share_stories

A plugin designed to share yor media in stories on Instagram/Facebook and reels on Facebook.

## Getting Started

### Android

Add uses permission in  `android/src/main/AndroidManifest.xml`

```xml
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

Add provider in  `android/src/main/AndroidManifest.xml`

```xml
<provider android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.flutter.social_share"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_provider_paths" />
</provider>
```

Create file in `android/src/main/res/xml/file_provider_paths.xml` and add

```xml
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="external" path="." />
    <external-files-path name="external_files" path="." />
    <external-cache-path name="external_cache" path="." />
    <files-path name="files" path="." />
</paths>
```

On Android 11 or higher it is necessary to enter a `queries` in `android/src/main/AndroidManifest.xml`

For Story Instagram Images:
```xml
<queries>
    <intent>
        <action android:name='com.instagram.share.ADD_TO_STORY' />
        <data android:mimeType='image/*' />
    </intent>
</queries>
```

For Story Instagram Video:
```xml
<queries>
    <intent>
        <action android:name='com.instagram.share.ADD_TO_STORY' />
        <data android:mimeType='video/*' />
    </intent>
</queries>
```

For Story Facebook Images:
```xml
<queries>
    <intent>
        <action android:name='com.facebook.stories.ADD_TO_STORY' />
        <data android:mimeType='image/*' />
    </intent>
</queries>
```

For Story Facebook Videos:
```xml
<queries>
    <intent>
        <action android:name='com.facebook.stories.ADD_TO_STORY' />
        <data android:mimeType='video/*' />
    </intent>
</queries>
```

For Reels Facebook Videos:
```xml
<queries>
    <intent>
        <action android:name='com.facebook.reels.SHARE_TO_REEL' />
        <data android:mimeType='video/*' />
    </intent>
</queries>
```

## Usage

### Share to Instagram Story
```xml
ShareStories.shareToInstagram(
    backgroundAssetUri: file.uri,
    stickerAssetUri: image.uri,
    topColor: Colors.blue,
    bottomColor: Colors.red
);
```

* Facebook requires you to have an app registered in Meta for Developers and use the key *

### Share to Facebook Story
```xml
ShareStories.shareToStoryFacebook(
    backgroundAssetUri: file.uri,
    stickerAssetUri: image.uri,
    topColor: Colors.blue,
    bottomColor: Colors.red
    appID: "xxxxxxxxxxxxxxxx"
);
```

### Share to Facebook Reels
```xml
ShareStories.shareToReelsFacebook(
    backgroundAssetUri: file.uri,
    stickerAssetUri: image.uri,
    topColor: Colors.blue,
    bottomColor: Colors.red
    appID: "xxxxxxxxxxxxxxxx"
);
```