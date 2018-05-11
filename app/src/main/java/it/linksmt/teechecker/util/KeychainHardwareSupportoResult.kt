package it.linksmt.teechecker.util

import it.linksmt.teechecker.model.HardwareCheckItem

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
data class KeychainHardwareSupportoResult constructor (val result : Result, val exception : Exception? = null ) {
    val items = mutableListOf<HardwareCheckItem>()

    val isSuccessful : Boolean
        get() = Result.OK == result

    val hasErrorMessage : Boolean
        get() = exception != null

    val errorMessage : String
        get() = exception?.message ?: ""

    fun addItem(descriptionResourceId : Int, hintResourceId : Int, status : Boolean ) : KeychainHardwareSupportoResult {
        val item = HardwareCheckItem(descriptionResourceId, hintResourceId, status)
        items.add( item )

        return this
    }
}