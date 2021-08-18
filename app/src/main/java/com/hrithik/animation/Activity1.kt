package com.hrithik.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hrithik.animation.databinding.Activity1Binding

class Activity1 : AppCompatActivity() {

    private lateinit var binding: Activity1Binding
    private lateinit var objectAnimator1: ObjectAnimator
    private lateinit var objectAnimator2: ObjectAnimator
    private val animatorSet = AnimatorSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        objectAnimator1 = ObjectAnimator.ofFloat(binding.foreground1, "translationX", -210f)
        objectAnimator2 = ObjectAnimator.ofFloat(binding.foreground2, "translationX", 210f)

        animatorSet.apply {
            playTogether(objectAnimator1, objectAnimator2)
            childAnimations.forEach {
                val t = it as ObjectAnimator
                t.duration = 1200
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    Handler(Looper.getMainLooper()).postDelayed( { animatorSet.start() }, 1200)
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }
            })
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)


        animatorSet.start()
    }
}