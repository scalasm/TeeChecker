package it.linksmt.teechecker

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyInfo
import android.security.keystore.KeyProperties
import android.util.Log
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.spec.InvalidKeySpecException

class KeyChainHardwareSupportHelper() {

    fun check() : KeychainHardwareSupportoResult {
        lateinit var result : KeychainHardwareSupportoResult

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

                    // Returns true if the key resides inside secure hardware (e.g., Trusted Execution Environment (TEE) or Secure Element (SE)).
                    // Key material of such keys is available in plaintext only inside the secure hardware and is not exposed outside of it.
                    val isInsideSecureHardware = keyInfo.isInsideSecureHardware

                    // Returns true if the requirement that this key can only be used if the user has been authenticated is enforced by secure hardware
                    // (e.g., Trusted Execution Environment (TEE) or Secure Element (SE)).
                    val userAuthenticationRequirementEnforcedBySecureHardware = keyInfo.isUserAuthenticationRequirementEnforcedBySecureHardware

                    result = KeychainHardwareSupportoResult( Result.OK, isInsideSecureHardware, userAuthenticationRequirementEnforcedBySecureHardware )

                } catch (e: InvalidKeySpecException) {
                    result = KeychainHardwareSupportoResult( Result.INTERNAL_INVALID_KEY_SPEC, e )
                }
            } catch (e: Exception) {
                result = KeychainHardwareSupportoResult( Result.INTERNAL_UNKNOWN_ERROR, e )

                Log.e("KK", "Error while checking key")
            }

        } else {
            result = KeychainHardwareSupportoResult( Result.ANDROID_VERSION_OLDER_THAN_6_0 )
        }
        Log.d("TeeChecker", "Checked: $result" )

        return result
    }
}