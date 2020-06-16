package dev.estebanbarrios.airshare.presentation.grant_access

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_grant_access.*

class GrantAccessActivity : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST_STORAGE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grant_access)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startActivity(Intent(baseContext, MainActivity::class.java))
            finish()
        } else {
            setupView()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
                return
            }
        }
    }

    private fun setupView(){
        continueButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_STORAGE
                )
            }
        }
        quitButton.setOnClickListener {
            finish()
        }
    }
}
