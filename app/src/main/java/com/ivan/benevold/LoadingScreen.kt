package com.ivan.benevold

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.loading_screen.*


class LoadingScreen : AppCompatActivity(), Animation.AnimationListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen)

        loadingScreenLayout.background = ContextCompat.getDrawable(
            this,
            R.drawable.backround_gradient
        )

        startAnimations();
    }

    private fun startAnimations() {
        val fade = AnimationUtils.loadAnimation(this, R.anim.fadein)
        fade.setAnimationListener(this)
        findViewById<View>(R.id.logo).startAnimation(fade)
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        // When all animations have finished - start the next activity
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }
}