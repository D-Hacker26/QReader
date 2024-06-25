package com.example.ascan.ui_design

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.ascan.Main.MainActivity
import com.example.ascan.R

class SplashActivity : AppCompatActivity() {
    companion object{
        private const val CAMERA_PERMISSION_CODE=876
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            CheckAppPermission()
        },3000)

    }
    private fun CheckAppPermission()
    {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED)
        {
//            if we get the permission then go to the main activity
            MoveToMainActivity()
        }
        else
        {
//            If  we have not the permission granted then we have to request the App Permission
            RequestAppPermission()
        }
    }
    private fun RequestAppPermission()
    {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== CAMERA_PERMISSION_CODE)
        {
//            here we have only one element because permission is only for camera
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
//                if the permission is granted
                MoveToMainActivity()
            }
            else if(isUserPermanentlyDeniedPermission())
            {
//                if user is permanently denied then user have dialog which go to App Setting
                    MoveToAppSettingDailog()
            }
            else
            {
                RequestAppPermission()
            }
        }
    }

    private fun isUserPermanentlyDeniedPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA).not()
        } else {
            return  false
        }
    }
    private fun MoveToAppSettingDailog()
    {
        AlertDialog.Builder(this).setTitle("Allow Me Permission")
            .setMessage("We need Camera permission.Go to app setting and then manage permissions")
            .setPositiveButton("Allow"){_,_->
                MoveToAppSettings()
            }
            .setNegativeButton("Cancel"){ _, _ ->
                Toast.makeText(this,"we need permission for functioning of app",Toast.LENGTH_SHORT).show()
                finish()
            }.show()
    }
    private fun MoveToAppSettings()
    {
         var intent=Intent(ACTION_APPLICATION_DETAILS_SETTINGS,Uri.fromParts("package",packageName,null))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun MoveToMainActivity()
    {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
//    app should have to always check the permission for  app
    override fun onRestart() {
        super.onRestart()
//    after restart the app it have to check the permission
    CheckAppPermission()
    }
}