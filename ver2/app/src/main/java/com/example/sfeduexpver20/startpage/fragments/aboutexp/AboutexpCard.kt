package com.example.sfeduexpver20.startpage.fragments.aboutexp

import androidx.cardview.widget.CardView

/**
 * Класс для создания объекта Card (два объекта CardView для формирования анимаций переворота)
 * @author Rengerstar <vip.bekezin@mail.ru>
 * */

class AboutexpCard{

    /**
     * @param frontCard - первый элемент CardView для передней стороны карточки
     * @param backCard - второй элемент CardView для задней стороны карточки
     * @param scale - параметр для установки cameraDistance.
     * */
    constructor(frontCard: CardView, backCard: CardView, scale: Float = 1f){
        this.frontCard=frontCard
        this.backCard=backCard
        this.scale=scale
        changeScale(scale)
    }

    private var frontCard: CardView
    private var backCard: CardView
    private var scale: Float
    var isFront = true //Параметр отвечающий за поворот карточки

    /**
     * @return Возвращает первую карточку
     * */
    fun takeFrontCard(): CardView{
        return frontCard
    }

    /**
     * @return Возвращает вторую карточку
     * */
    fun takeBackCard(): CardView{
        return backCard
    }

    /**
     * @return Возвращает состояние карточки
     * */
    fun takeIsFront(): Boolean{
        return isFront
    }

    /**
     * @return Меняет состояние карточки
     * */
    fun changeIsFront(){
        this.isFront= !isFront
    }

    /**
     * @param scale - параметр для изменения cameraDistance
     * Изменение cameraDistance у карточек
     * */
    fun changeScale(scale: Float){
        this.scale=scale
        frontCard.cameraDistance=8000*scale
        backCard.cameraDistance=8000*scale
    }

}