package it.linksmt.teechecker

/**
 * Type of the keychain HW check.
 */
enum class Result {
    /**
     * The device does not suport Android 6.0 API (ok, just to check that the app is ok with older devices ...)
     */
    ANDROID_VERSION_OLDER_THAN_6_0,
    /**
     * This is probably a configuration error by our part when creating the test key pair.
     */
    INTERNAL_INVALID_KEY_SPEC,
    /**
     * Any other error (we are noobs!)
     */
    INTERNAL_UNKNOWN_ERROR,
    /**
     * Check was performed and the additional properties should be checked for more details about the support of the Tee.
     */
    OK;
}

/**
 * Result and details of the check that has been performed.
 */
data class KeychainHardwareSupportoResult @JvmOverloads constructor ( val result : Result, val isInsideSecureHardware : Boolean? = false, val userAuthenticationRequirementEnforcedBySecureHardware : Boolean? = false, val exception : Exception? = null ) {
    // See https://proandroiddev.com/creating-multiple-constructors-for-data-classes-in-kotlin-32ad27e58cac for the kotlin syntax

    constructor (result : Result, exception : Exception ) : this( result, false, false, exception ) {}

    fun isSuccessful() : Boolean {
        return Result.OK == result
    }

    fun hasErrorMessage() : Boolean {
        return exception != null
    }

    fun getErrorMessage() : String {
        return exception?.message ?: ""
    }
}