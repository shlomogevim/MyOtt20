package com.example.myott20

import android.animation.AnimatorInflater
import android.content.res.Resources
import android.graphics.drawable.AnimatedVectorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.myott20.R.drawable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    var counter=0
    private var animationMode = true
    lateinit var otts: ArrayList<Ott>
    private var videoBG: VideoView? = null
    var mMediaPlayer: MediaPlayer? = null
    var mCurrentVideoPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoBackgroundPreperation()
       // mainLayout.setBackgroundResource(R.drawable.paper)

      /*  button.setOnClickListener {
            if (imageView.visibility==View.VISIBLE) imageView.visibility=View.GONE
                    else imageView.visibility=View.VISIBLE
        }*/

         mainLayout.setOnClickListener {
        CoroutineScope(Dispatchers.Main).launch {
            //delay(1000)
            createOtt()
            drawAllOtts()
            lastApizode()
        }
          }
    }

    private fun videoBackgroundPreperation(){
        videoBG = findViewById(R.id.videoView) as VideoView
        // Build your video Uri
        val uri = Uri.parse(
            ("android.resource://" // First start with this,
                    + packageName // then retrieve your package name,
                    ) + "/" // add a slash,
                    + R.raw.wave
        ) // and then finally add your video resource. Make sure it is stored
        // in the raw folder.
// Set the new Uri to our VideoView
        videoBG!!.setVideoURI(uri)
        // Start the VideoView
        videoBG!!.start()
        // Set an OnPreparedListener for our VideoView. For more information about VideoViews,
// check out the Android Docs: https://developer.android.com/reference/android/widget/VideoView.html
        videoBG!!.setOnPreparedListener { mediaPlayer ->
            mMediaPlayer = mediaPlayer
            // We want our video to play over and over so we set looping to true.
            mMediaPlayer!!.isLooping = true
            // We then seek to the current posistion if it has been set and play the video.
            if (mCurrentVideoPosition != 0) {
                mMediaPlayer!!.seekTo(mCurrentVideoPosition)
                mMediaPlayer!!.start()
            }
        }
    }

    /*override fun onPause() {
        super.onPause()
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer!!.currentPosition
        videoBG!!.pause()
    }*/

    override fun onResume() {
        super.onResume()
        // Restart the video when resuming the Activity
        videoBG!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer!!.release()
        mMediaPlayer = null
    }

    private suspend fun lastApizode() {
        delay(2000)
        for (index in 0 until otts.size) {

            val anim = AnimatorInflater.loadAnimator(this, R.animator.set3)
            anim?.apply {
                setTarget(otts[index].iv)
                start()
            }
        }
    }

    private suspend fun drawAllOtts() {
        withContext(Dispatchers.Main) {
            for (i in 0 until otts.size) {
                drawOneOtt(otts[i])
            }
        }
    }

    private suspend fun drawOneOtt(ott: Ott) {
        val image = ott.iv

        if (animationMode) {
            if (counter > 0) delay(100)
            counter++
            mainLayout.addView(image)
            setParameters(ott)
            val avd = image.drawable as AnimatedVectorDrawable
            avd.start()
        } else {
            mainLayout.addView(image)
            setParameters(ott)
        }
        if (ott.index==29){
            val anim = AnimatorInflater.loadAnimator(this, R.animator.rotate)
            anim?.apply {
                setTarget(otts[counter].iv)
                start()
            }

        }
    }

    private fun setParameters(ott: Ott) {
        with(ott) {
            if (width > 0) {
                iv.layoutParams.height = height.toPx()
                iv.layoutParams.width = width.toPx()
            }
            val imageView = ott.iv
            imageView.id = View.generateViewId()
            val set = ConstraintSet()
            set.clone(mainLayout)

           /* set.connect(
                imageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID,
                ConstraintSet.TOP, ott.mT.toPx()
            )*/
           /* set.connect(
                imageView.id, ConstraintSet.START, ConstraintSet.PARENT_ID,
                ConstraintSet.START, ott.mL.toPx()
            )*/

            set.connect(
                imageView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM, ott.mBottom.toPx()
            )

            set.connect(
                imageView.id, ConstraintSet.END, ConstraintSet.PARENT_ID,
                ConstraintSet.END, ott.mRight.toPx()
            )

            set.applyTo(mainLayout)
        }
    }

    private fun createOtt() {
        otts = ArrayList()
        val int0 = -70
        val int1 = -10
        val int2 = -10
        val int3 = 0
        val scale1 = 70
        val scale2 = 50
        val bottom0 = 450
        val buttom1 =370
        val buttom2 = 290
        val buttom3 = 220
        val buttom4 = 150
                            /* width,height,mLeft,mTop,mRight,mBelow*/
        otts = arrayListOf(
            Ott(mV("ל"), 0,  scale1,scale1, 150,  bottom0),
            Ott(mV("ק"), 1,  scale1,scale1, 190,  bottom0-15),
            Ott(mV("ו"), 2,  scale1,scale1, 213,  bottom0),

            Ott(mV("ה"), 3,  scale1,scale1, 270,  bottom0-1),
            Ott(mV("ח"), 4,  72,72, 307,  bottom0-5),
            Ott(mV("ו"), 5,  scale1,scale1,332,  bottom0+2),
            Ott(mV("ף"), 6,  scale1,scale1, 350,  bottom0+5),

            Ott(mV("ל"), 7,  scale1,scale1, 150+int0,  buttom1),
            Ott(mV("ר"), 8,  scale1,scale1, 185+int0,  buttom1-2),
            Ott(mV("ח"), 9,  scale1,scale1, 215+int0,  buttom1-2),
            Ott(mV("ש"), 10,  scale1,scale1, 250+int0,  buttom1),

            Ott(mV("ה"), 11,  scale1,scale1, 315+int0,  buttom1),
            Ott(mV("ג"), 12,  scale1,scale1, 350+int0,  buttom1),
            Ott(mV("ל"), 13,  scale1,scale1, 382+int0,  buttom1),
            Ott(mV("י"), 14,  scale1,scale1, 405+int0,  buttom1),
            Ott(mV("ם"), 15,  scale1,scale1, 435+int0,  buttom1),

            Ott(mV("ו"), 16,  scale1,scale1, 87+int1,  buttom2),
            Ott(mV("ל"), 17,  scale1,scale1, 110+int1,  buttom2),
            Ott(mV("ק"), 18,  scale1,scale1, 150+int1,  buttom2-13),
            Ott(mV("צ"), 19,  scale1,scale1, 185+int1,  buttom2),
            Ott(mV("ף"), 20,  scale1,scale1, 220+int1,  buttom2+3),
            Ott(mV("ה"), 21,  scale1,scale1, 285+int1,  buttom2),
            Ott(mV("ל"), 22,  scale1,scale1, 320+int1,  buttom2),
            Ott(mV("ב"), 23,  scale1,scale1, 361+int1,  buttom2),
            Ott(mV("ן"), 24,  scale1,scale1, 382+int1,  buttom2-15),

            Ott(mV("א"), 25,  scale2,scale2, 85,  buttom3),
            Ott(mV("י"), 26,  scale2,scale2, 100,  buttom3),
            Ott(mV("ן"), 27,  scale2,scale2, 105,  buttom3-10),
            Ott(mV("א"), 28,  scale2,scale2, 152+int2,  buttom3),
            Ott(mV("ג"), 29,  scale2,scale2, 175+int2,  buttom3),
            Ott(mV("י"), 30,  scale2,scale2, 187+int2,  buttom3+10),
            Ott(mV("נ"), 31,  scale2,scale2, 208+int2,  buttom3-10),
            Ott(mV("ד"), 32,  scale2+5,scale2+5, 220+int2,  buttom3),
            Ott(mV("ה"), 33,  scale2,scale2, 250+int2,  buttom3),
            Ott(mV("פ"), 34,  scale2,scale2, 295+int2,  buttom3),
            Ott(mV("ו"), 35,  scale2,scale2, 315+int2,  buttom3),
            Ott(mV("ל"), 36,  scale2,scale2, 330+int2,  buttom3),
            Ott(mV("י"), 37,  scale2,scale2, 350+int2,  buttom3),
            Ott(mV("ט"), 38,  scale2,scale2, 370+int2,  buttom3),
            Ott(mV("י"), 39,  scale2,scale2, 390+int2,  buttom3),
            Ott(mV("ת"), 40,  scale2,scale2, 410+int2,  buttom3),

            Ott(mV("ה"), 41,  scale2,scale2, 90+int3,  buttom4),
            Ott(mV("ם"), 42,  scale2-8,scale2, 125+int3,  buttom4),

            Ott(mV("פ"), 43,  scale2,scale2, 172+int3,  buttom4),
            Ott(mV("ש"), 44,  scale2,scale2, 197+int3,  buttom4),
            Ott(mV("ו"), 45,  scale2,scale2, 215+int3,  buttom4),
            Ott(mV("ט"), 46,  scale2+3,scale2+3, 233+int3,  buttom4),

            Ott(mV("ק"), 47,  scale2+5,scale2+5, 285+int3,  buttom4-15),
            Ott(mV("י"), 48,  scale2,scale2, 305+int3,  buttom4),
            Ott(mV("י"), 49,  scale2,scale2, 315+int3,  buttom4),
            Ott(mV("מ"), 50,  scale2,scale2, 330+int3,  buttom4-3),
            Ott(mV("י"), 51,  scale2,scale2, 350+int3,  buttom4),
            Ott(mV("ם"), 52,  scale2,scale2, 373+int3,  buttom4)
        )

    }

    private fun mV(letter: String): ImageView {
        val imageView = ImageView(this)
        val address = Helper(this).getAnimation1(letter)
        if (animationMode) {
            val address = Helper(this).getAnimation2(letter)
            imageView.setImageResource(address)
        } else {
            val address = Helper(this).getAnimation1(letter)
            imageView.setImageResource(address)
        }
        return imageView
    }

    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

}
