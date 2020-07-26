##  location


## Table of Contents

 * [Introduction](#introduction)
 * [Installation](#installation)
 * [Supported Environments](#supported-environments)
 * [Sample Code](#Sample-Code)
 * [License](#license)
 
 
## Introduction
    HUAWEI Location Kit sample code encapsulates APIs of the HUAWEI Location Kit. It provides many sample programs for your reference or usage.
   

## Installation
    Before using HuaweiLocationKit sample code, check whether the Android Studio environment has been installed. 
    Decompress the HuaweiLocationKit sample code package.
    Download  location.zip.
	Decompress the location.zip.
	Open location with Android studio.
 
    
## Supported Environments
	Android Studio
	Java

	
## Sample Code
    To use the HUAWEI Location Kit service API, you need to download and install the HMS Core service component on your device, and integrate related SDKs into your project.
    The following describes methods in this demo.
    
    SendMessage:    request location update.
	

    1). Assigning App Permissions
    You need to apply for the permissions in the Manifest file.
    Code ��location/app/src/AndroidManifest.xml
    
    2). Creating a Location Service Client.
    Create a FusedLocationProviderClient instance in the OnCreate() method of the activity and use the instance to call location-related APIs.
    Code ��location/app/src/main/java/com/hms/locationki/fusedlocation/RequestLocationUpdatesWithCallbackActivity.java
    
    3). Checking the Device Location Settings.
    you are advised to check whether the device settings meet the location requirements before continuously obtaining location information.
    Code ��location/app/src/main/com/hms/locationki/fusedlocation/RequestLocationUpdatesWithCallbackActivity.kt
    
    4). Continuously Obtaining the Location Information.
    To enable your app to continuously obtain the device location, you can use the requestLocationUpdates() API provided by the HUAWEI Location Kit service. 
    Code ��location/app/src/main/java/com/hms/locationki/fusedlocation/RequestLocationUpdatesWithCallbackActivity.java
    

##  License
    HUAWEI Location kit sample is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

