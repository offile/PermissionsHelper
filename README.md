Easy Activity Result
===================
[![](https://jitpack.io/v/offile/PermissionsHelper.svg)](https://jitpack.io/#offile/PermissionsHelper)
[![Language](https://img.shields.io/badge/compatible-java%20%7C%20kotlin-brightgreen.svg)](https://github.com/offile/PermissionsHelper)

🍎Permission management library for android platform, 
One line of code completes permission request

## Setup

Add it in your root build.gradle at the end of repositories:

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

include the following in your app module build.gradle file:

```gradle
dependencies {
    implementation "com.github.offile:EasyActivityResult:laster-version"
}
```

## Usage

1. Request for runtime permissions.
```kotlin
PermissionsHelper.with(this) // where this is an Activity or Fragment instance
    .runtime()
    .permissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    .onShowRationale{
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Granted permission")
            .setMessage("Next please grant permissions")
            .setPositiveButton("ok"){_,_->
                // proceed request
                it.proceed()
            }.setNegativeButton("cancel"){_,_->
                // cancel request
                it.cancel()
            }.show()
    }.onNeverAskAgain{
        // In this scope, forwardToSettings or cancel must be called to continue
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Permission denied")
            .setMessage("You need to turn on these permissions in the settings.")
            .setPositiveButton("ok"){_,_->
                // Jump to the application details to open the permissions , 
                // and the process will continue when the user returns
                it.forwardToSettings()
            }.setNegativeButton("cancel"){_,_->
                // cancel request
                it.cancel()
            }.show()
    }.request{
        Toast.makeText(this, "this result is ${it.isGranted}", Toast.LENGTH_SHORT).show()
    }
```

## LICENSE
```
Copyright 2021 offile

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
