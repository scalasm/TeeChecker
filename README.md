#Trusted Execution Environment (TEE) Checker App

A simple app that checks whether or not the ANdroid device supports Hardware or software Keychain implementation,
according to [Android specifications]() 

#Motivations

Android defines a [Trusted Execution Environment (TEE)](https://source.android.com/security/trusty/).


We want to check if there is Hardware support for the Keychain since, according to [Android 6.0 Compatibility Definition](https://source.android.com/compatibility/6.0/android-6.0-cdd#7_3_10_fingerprint):

```
7.3.10. Fingerprint Sensor
Device implementations with a secure lock screen SHOULD include a fingerprint sensor. If a device implementation includes a fingerprint sensor and has a corresponding API for third-party developers, it:

...
* MUST have a hardware-backed keystore implementation, and perform the fingerprint matching in a Trusted Execution Environment (TEE) or on a chip with a secure channel to the TEE.
* MUST have all identifiable fingerprint data encrypted and cryptographically authenticated such that they cannot be acquired, read or altered outside of the Trusted Execution Environment (TEE) as documented in the implementation guidelines on the Android Open Source Project site [Resources, 96].

...

```

But it seems that we may have software implementations of the TEE, and this is what we want to check: 
is our keystore backed by hardware or not?

Sidenote: the whole thing is not quite clear to me so this app is also a playground for experimenting and (maybe)
it will be useful to others too.

#Implementation and build details

App is developed in Kotlin 1.2 by using Android Studio 3.1.2. Nothing fancy, just a basic Kotlin app: don't 
look at it for any good or state of the art practices.

Just download/clone the repository (currently only with a master branch) and import the project within the Android studio IDE.


