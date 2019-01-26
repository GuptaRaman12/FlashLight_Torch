package com.racker.myapplication



import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.Parameters
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity :AppCompatActivity() {

    private var btnSwitch: ImageButton ?= null
    private var camera: Camera? = null
    private var isFlashOn: Boolean = false
    private var hasFlash: Boolean = false
    var params: Parameters? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // flash switch button
        btnSwitch = findViewById<View>(R.id.btnSwitch) as ImageButton


        // First check if device is supporting flashlight or not
        hasFlash = applicationContext.packageManager
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            val alert = AlertDialog.Builder(this@MainActivity)
                    .create()
            alert.setTitle("Error")
            alert.setMessage("Sorry, your device doesn't support flash light!")
            alert.setButton("OK") { dialog, which ->
                // closing the application
                finish()
            }
            alert.show()
            return
        }

        // get the camera
        getCamera()

        // displaying button image
        toggleButtonImage()


        // Switch button click event to toggle flash on/off
        btnSwitch!!.setOnClickListener {
            if (isFlashOn) {
                // turn off flash
                turnOffFlash()
            } else {
                // turn on flash
                turnOnFlash()
            }
        }
    }


    // Get the camera

    private fun getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open()
                params = camera!!.parameters
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }

        }
    }


    // Turning On flash
    private fun turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return
            }


            params = camera!!.parameters
            params!!.flashMode = Parameters.FLASH_MODE_TORCH
            camera!!.parameters = params
            camera!!.startPreview()
            isFlashOn = true

            // changing button/switch image
            toggleButtonImage()
        }

    }


    // Turning Off flash
    private fun turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return
            }


            params = camera!!.parameters
            params!!.flashMode = Parameters.FLASH_MODE_OFF
            camera!!.parameters = params
            camera!!.stopPreview()
            isFlashOn = false

            // changing button/switch image
            toggleButtonImage()
        }
    }


    /*
     * Toggle switch button images
     * changing image states to on / off
     * */
    private fun toggleButtonImage() {
        if (isFlashOn) {
            btnSwitch?.setImageResource(R.drawable.on)
        } else {
            btnSwitch?.setImageResource(R.drawable.off)
        }
    }




}





