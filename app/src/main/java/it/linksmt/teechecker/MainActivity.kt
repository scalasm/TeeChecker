package it.linksmt.teechecker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textIsInsideSecureHardware?.visibility = View.INVISIBLE
        textUserAuthenticationRequirementEnforcedBySecureHardware?.visibility = View.INVISIBLE

        btnPerformCheck.setOnClickListener {
            val result = KeyChainHardwareSupportHelper().check();

            handleResult( result );
        }

    }

    private fun handleResult(result: KeychainHardwareSupportoResult) {
        when (result.result) {
            Result.OK -> {
                textStatus?.text = getString( R.string.status_success )
                textIsInsideSecureHardware?.text = getString( R.string.is_inside_secure_hardware, result.isInsideSecureHardware )
                textUserAuthenticationRequirementEnforcedBySecureHardware?.text = getString( R.string.is_user_authentication_requirement_enforced_by_secure_hardware, result.userAuthenticationRequirementEnforcedBySecureHardware )

                textIsInsideSecureHardware?.visibility = View.VISIBLE
                textUserAuthenticationRequirementEnforcedBySecureHardware?.visibility = View.VISIBLE
            }
            Result.ANDROID_VERSION_OLDER_THAN_6_0 -> {
                textStatus?.text = getString( R.string.device_not_supported )
                textIsInsideSecureHardware?.visibility = View.INVISIBLE
                textUserAuthenticationRequirementEnforcedBySecureHardware?.visibility = View.INVISIBLE
            }
            else -> {
                val errorMessage = if (result.hasErrorMessage()) result.getErrorMessage() else getString( R.string.status_unknown_error )

                textStatus?.text = errorMessage
                textIsInsideSecureHardware?.visibility = View.INVISIBLE
                textUserAuthenticationRequirementEnforcedBySecureHardware?.visibility = View.INVISIBLE
            }
        }
    }
}
