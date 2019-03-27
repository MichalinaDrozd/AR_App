package com.example.michalinadrozd.arapp

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.widget.Toast
import com.example.michalinadrozd.arapp.R.id.ar_view
import com.google.ar.core.AugmentedImage
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.UnavailableApkTooOldException
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_augumented_reality.*


class Augumented_reality : AppCompatActivity(), Scene.OnUpdateListener {
    override fun onUpdate(p0: FrameTime?) {
        val frame = ar_view.arFrame
        val updateAugumentedImage = frame!!.getUpdatedTrackables<AugmentedImage>(AugmentedImage::class.java)

        for(augmentedImage in updateAugumentedImage)
        {
            if(augmentedImage.trackingState == TrackingState.TRACKING)
            {
                if(augmentedImage.name.equals("audi_r8"))
                {
                    val node = MyNode (this, R.raw.audi_r8)
                    node.image = augmentedImage
                    ar_view.scene.addChild(node)

                }
            }
        }

    }

    private  var session:com.google.ar.core.Session?=null

    private  var shouldConfigureSession=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_augumented_reality)

        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object:PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                    setSession();


                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@Augumented_reality,"Permission needed to use Camera",Toast.LENGTH_SHORT)
                        .show()
                }


            })
            .check()

        ar_view.scene.addOnUpdateListener(this)
    }



    override fun onResume() {
        super.onResume()
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object:PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                    setSession();


                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@Augumented_reality,"Permission needed to use Camera",Toast.LENGTH_SHORT)
                        .show()
                }


            })
            .check()
    }

    override fun onPause() {

        super.onPause()

        if (session != null) {

            session!!.pause()
            ar_view.pause()

        }
    }

    private fun setSession() {
        if(session == null)
        {

            try {

                session = com.google.ar.core.Session(this)
            }
            catch (e:UnavailableArcoreNotInstalledException)
            {
                e.printStackTrace()
            }
            catch (e:UnavailableApkTooOldException)
            {
                e.printStackTrace()
            }
            catch (e:UnavailableDeviceNotCompatibleException)
            {
                e.printStackTrace()
            }
            catch (e : Exception){
                e.printStackTrace()
            }
            shouldConfigureSession = true
        }

        if (shouldConfigureSession)
        {

            configureSession()
            shouldConfigureSession = false
            ar_view.setupSession(session)
        }
        try {
            session!!.resume()
            ar_view.resume()

        } catch (e: CameraNotAvailableException)
        {
            e.printStackTrace()
            session=null
            return

        }
    }

    private fun configureSession() {

        val configuration = Config(session)
        if(!Databasebuilded(configuration))
            Toast.makeText(this@Augumented_reality,"Database error",Toast.LENGTH_SHORT).show()
        configuration.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE

        session!!.configure(configuration)
    }

    private fun Databasebuilded(configuration: Config): Boolean {

        val augmentedImageDatabase:AugmentedImageDatabase
        val bmp = loadBmpFromDrive()

        if(bmp == null)
            return false

        augmentedImageDatabase = AugmentedImageDatabase(session)

        augmentedImageDatabase.addImage("audi_r8",bmp)

        configuration.augmentedImageDatabase = augmentedImageDatabase
        return true




    }

    private fun loadBmpFromDrive(): Bitmap? {

        val input =assets.open("qrcode.jpeg")
        return BitmapFactory.decodeStream(input)
    }
}
