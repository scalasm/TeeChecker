package it.linksmt.teechecker.util

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyInfo
import android.security.keystore.KeyProperties
import android.util.Log
import it.linksmt.teechecker.R
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.InvalidKeySpecException

class KeyChainHardwareSupportHelper() {
    private val TAG = "KCHSH"

    fun check() : CheckItemsResult {
        lateinit var result : CheckItemsResult

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val kpg = KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore")
                kpg.initialize(KeyGenParameterSpec.Builder(
                        "someKey",
                        KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY)
                        .setDigests(KeyProperties.DIGEST_SHA256,
                                KeyProperties.DIGEST_SHA512)
                        .build())

                val kp = kpg.generateKeyPair()

                val key = kp.private

                val factory = KeyFactory.getInstance(key.algorithm, "AndroidKeyStore")
                val keyInfo: KeyInfo
                try {
                    keyInfo = factory.getKeySpec(key, KeyInfo::class.java)

                    result = CheckItemsResult( Result.OK )

                    // Returns true if the key resides inside secure hardware (e.g., Trusted Execution Environment (TEE) or Secure Element (SE)).
                    // Key material of such keys is available in plaintext only inside the secure hardware and is not exposed outside of it.
                    val isInsideSecureHardware = keyInfo.isInsideSecureHardware
                    result.addItem( R.string.is_inside_secure_hardware,
                            R.string.is_inside_secure_hardware_hint,
                            isInsideSecureHardware )

                    // Returns true if the requirement that this key can only be used if the user has been authenticated is enforced by secure hardware
                    // (e.g., Trusted Execution Environment (TEE) or Secure Element (SE)).
                    val userAuthenticationRequirementEnforcedBySecureHardware = keyInfo.isUserAuthenticationRequirementEnforcedBySecureHardware
                    result.addItem( R.string.is_user_authentication_requirement_enforced_by_secure_hardware,
                            R.string.is_user_authentication_requirement_enforced_by_secure_hardware_hint,
                            userAuthenticationRequirementEnforcedBySecureHardware )

                } catch (e: InvalidKeySpecException) {
                    result = CheckItemsResult(Result.INTERNAL_INVALID_KEY_SPEC, e)
                }
            } catch (e: Exception) {
                result = CheckItemsResult(Result.INTERNAL_UNKNOWN_ERROR, e)

                Log.e( TAG, "Error while checking key")
            }
        } else {
            result = CheckItemsResult(Result.ANDROID_VERSION_OLDER_THAN_6_0)
        }
        Log.d( TAG, "Checked: $result" )

        return result
    }
}