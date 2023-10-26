package com.example.sfedu_exponent.Started_page.Fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sfedu_exponent.R

class AboutEXP : Fragment() {
    lateinit var front_anim:AnimatorSet
    lateinit var back_anim:AnimatorSet
    var isFront1 = true
    var isFront2 = true
    var isAnimating = false
    var isPanelOpen = false
    private lateinit var card_front: CardView
    private lateinit var card_back: CardView
    private lateinit var card_front2: CardView
    private lateinit var card_back2: CardView
    private lateinit var overlayLayout: ConstraintLayout
    private lateinit var textHide1: TextView
    private lateinit var textHide2: TextView
    private lateinit var textHide3: TextView
    private lateinit var textHide4: TextView
    private lateinit var textHide5: TextView
    lateinit var expandAnimation: Animation
    lateinit var collapseAnimation: Animation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Передаем значени переменным из XML разметки
        val view = inflater.inflate(R.layout.fragment_about_e_x_p, container, false)
        val scale:Float = requireContext().resources.displayMetrics.density
        UI(view)
        AnimationLoad(scale)
        CardClicker()
        textHide1.setOnClickListener {
            showPanel()
        }
        textHide2.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://sfedu.ru/press-center/news/70676"))
            startActivity(i)
        }
        textHide3.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://sfedu.ru/press-center/news/68728"))
            startActivity(i)
        }
        textHide4.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://sfedu.ru/press-center/news/67255"))
            startActivity(i)
        }
        textHide5.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://sfedu.ru/press-center/news/65675"))
            startActivity(i)
        }
        return view
    }

    private fun UI(view: View) {
        card_front = view.findViewById<CardView>(R.id.card_front1)
        card_back = view.findViewById<CardView>(R.id.card_back1)
        card_front2 = view.findViewById<CardView>(R.id.card_front2)
        card_back2 = view.findViewById<CardView>(R.id.card_back2)
        overlayLayout = view.findViewById<ConstraintLayout>(R.id.ItogiLayout)
        textHide1 = view.findViewById<TextView>(R.id.Text_hide1)
        textHide2 = view.findViewById<TextView>(R.id.Text_hide2)
        textHide3 = view.findViewById<TextView>(R.id.Text_hide3)
        textHide4 = view.findViewById<TextView>(R.id.Text_hide4)
        textHide5 = view.findViewById<TextView>(R.id.Text_hide5)
    }

    private fun showPanel(){
        val layoutParams = overlayLayout.layoutParams
        val textHide1Height = textHide1.height
        if (isPanelOpen) {
            collapseAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    layoutParams.height = textHide1Height
                    overlayLayout.layoutParams = layoutParams
                }
            })
            overlayLayout.startAnimation(collapseAnimation)
            isPanelOpen = false
            card_back.isEnabled=true
            card_back2.isEnabled=true
            card_front.isEnabled=true
            card_front2.isEnabled=true
        } else {
            expandAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    layoutParams.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                    overlayLayout.layoutParams = layoutParams
                }
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {}
            })
            overlayLayout.startAnimation(expandAnimation)
            isPanelOpen = true
            card_back.isEnabled=false
            card_back2.isEnabled=false
            card_front.isEnabled=false
            card_front2.isEnabled=false
        }
    }

    private fun AnimationLoad(scale: Float){
        card_front.cameraDistance=8000*scale
        card_back.cameraDistance=8000*scale
        card_front2.cameraDistance=8000*scale
        card_back2.cameraDistance=8000*scale
        front_anim = AnimatorInflater.loadAnimator(requireContext(), R.animator.front_animator) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(requireContext(), R.animator.back_animator) as AnimatorSet
        expandAnimation = AnimationUtils.loadAnimation(context, R.anim.expand_animation)
        collapseAnimation = AnimationUtils.loadAnimation(context, R.anim.collapse_animation)
        front_anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isAnimating = true
            }
            override fun onAnimationEnd(animation: Animator) {
                isAnimating = false
            }
            override fun onAnimationCancel(animation: Animator) {
                isAnimating = false
            }
            override fun onAnimationRepeat(animation: Animator) {
                // Not used in this case
            }
        })
        back_anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isAnimating = true
            }
            override fun onAnimationEnd(animation: Animator) {
                isAnimating = false
            }
            override fun onAnimationCancel(animation: Animator) {
                isAnimating = false
            }
            override fun onAnimationRepeat(animation: Animator) {
                // Not used in this case
            }
        })
    }

    private fun CardClicker(){
        card_front.setOnClickListener {
            if (!isAnimating) {
                if (isFront1) {
                    front_anim.setTarget(card_front)
                    back_anim.setTarget(card_back)
                    front_anim.start()
                    back_anim.start()
                    isFront1 = false
                } else {
                    front_anim.setTarget(card_back)
                    back_anim.setTarget(card_front)
                    front_anim.start()
                    back_anim.start()
                    isFront1 = true
                }
            }
        }
        card_front2.setOnClickListener {
            if (!isAnimating) {
                if (isFront2) {
                    front_anim.setTarget(card_front2)
                    back_anim.setTarget(card_back2)
                    front_anim.start()
                    back_anim.start()
                    isFront2 = false
                } else {
                    front_anim.setTarget(card_back2)
                    back_anim.setTarget(card_front2)
                    front_anim.start()
                    back_anim.start()
                    isFront2 = true
                }
            }
        }
    }



}