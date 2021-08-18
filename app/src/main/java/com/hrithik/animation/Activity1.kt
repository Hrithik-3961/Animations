package com.hrithik.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.hrithik.animation.databinding.Activity1Binding

class Activity1 : AppCompatActivity() {

    private lateinit var binding: Activity1Binding
    private lateinit var objectAnimator1: ObjectAnimator
    private lateinit var objectAnimator2: ObjectAnimator
    private val animatorSet = AnimatorSet()
    private lateinit var bounceAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            objectAnimator1 = ObjectAnimator.ofFloat(foreground1, "translationX", -210f)
            objectAnimator2 = ObjectAnimator.ofFloat(foreground2, "translationX", 210f)

            bounceAnim = AnimationUtils.loadAnimation(this@Activity1, R.anim.bounce)
            bounceAnim.repeatCount = 5

            animatorSet.apply {
                playTogether(objectAnimator1, objectAnimator2)
                childAnimations.forEach {
                    val t = it as ObjectAnimator
                    t.duration = 1200
                }
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {}

                    override fun onAnimationEnd(p0: Animator?) {
                        Handler(Looper.getMainLooper()).postDelayed({ animatorSet.start() }, 1800)
                    }

                    override fun onAnimationCancel(p0: Animator?) {}

                    override fun onAnimationRepeat(p0: Animator?) {}
                })
            }

            bounceAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn.startAnimation(bounceAnim)
                    }, 2000)
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }
            })

        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        animatorSet.start()
        binding.btn.startAnimation(bounceAnim)
    }
}