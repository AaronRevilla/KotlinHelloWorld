package com.aaronrevilla.kotlinhelloworld

import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.graphics.Bitmap
import android.widget.ImageView
import kotlinx.android.synthetic.main.content_main.*
import android.R.attr.data
import android.support.v4.app.NotificationCompat.getExtras




class MainActivity : AppCompatActivity() {

    val CAMERA_PERMISSION_REQUES_CODE = 1 //val means final var
    val CAMERA_RESULT_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            if(hasCameraPermission()) openCamera() else verifyPermissions()
        }

        //verify camera permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) verifyPermissions()

    }

    private fun openCamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivityForResult(intent, CAMERA_RESULT_CODE)
    }

    private fun hasCameraPermission(): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) return ActivityCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED else return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun verifyPermissions(){
        if(ActivityCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) ActivityCompat.requestPermissions( this,  arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CAMERA_RESULT_CODE) showImage(data)

    }

    private fun showImage(data: Intent?) {
        val extras = data?.getExtras()
        imageViewer.setImageBitmap(extras?.get("data") as Bitmap)
    }
}
