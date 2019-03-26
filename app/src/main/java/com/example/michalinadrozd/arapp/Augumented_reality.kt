package com.example.michalinadrozd.arapp

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class Augumented_reality : AppCompatActivity() {

    private lateinit var session:Session
    private  var shouldConfigureSession=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_augumented_reality)

        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object:PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }


            })
    }
}
