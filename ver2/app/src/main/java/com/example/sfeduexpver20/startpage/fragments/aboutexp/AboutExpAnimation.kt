package com.example.sfeduexpver20.startpage.fragments.aboutexp

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView

/**
 * Класс для запуска анимаций переворота карточки
 * @author Rengerstar
 * */

class AboutExpAnimation{

    /**
     * @param Анимации с которыми будет работать класс и контекст
     * */
    constructor(
        context: Context,
        front_anim: Int,
        back_anim: Int,
        expandAnimation: Int,
        collapseAnimation: Int
    ){
        this.context=context
        this.front_anim = AnimatorInflater.loadAnimator(context, front_anim) as AnimatorSet
        this.back_anim = AnimatorInflater.loadAnimator(context, back_anim) as AnimatorSet
        this.expandAnimation = AnimationUtils.loadAnimation(context, expandAnimation)
        this.collapseAnimation = AnimationUtils.loadAnimation(context, collapseAnimation)
        setAnimatorListeners()
    }

    private val context: Context
    private var front_anim: AnimatorSet
    private var back_anim: AnimatorSet
    private var expandAnimation: Animation
    private var collapseAnimation: Animation
    private var isAnimating = false


    /**
     * @param Card - карточка для которой нужно запустить анимацию
     * */
    fun setTargetandStart(Card: AboutexpCard){
        val isFront = Card.takeIsFront()
        if(!isAnimating){
            when(isFront){
                true ->{
                    front_anim.setTarget(Card.takeFrontCard())
                    back_anim.setTarget(Card.takeBackCard())
                }
                false->{
                    front_anim.setTarget(Card.takeBackCard())
                    back_anim.setTarget(Card.takeFrontCard())
                }
            }
            startAnim()
            Card.changeIsFront()
        }
    }

    /**
     * Внутренняя функция в которую вынесен запуск анимации
     * */
    private fun startAnim(){
        front_anim.start()
        back_anim.start()
    }

    /**
     * Установка слушателей на анимации
     * */
    private fun setAnimatorListeners() {
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

}