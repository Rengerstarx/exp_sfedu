package com.example.sfedu_exponent

import AdapterForHidePanel
import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.evernote.android.job.patched.internal.JobManager.instance
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.fragment_partners.*
import okhttp3.internal.Internal.instance
import okhttp3.internal.cache.DiskLruCache.Snapshot
import java.util.*

class Partners : Fragment(), PartnerAdapter.Listener {

    /**Переменные для работы с Firebase*/
    val adapter=PartnerAdapter(this)
    val storage = FirebaseStorage.getInstance()
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("Partners")

    /**Переменные для всплывающего окна*/
    var ActiveVk = ""
    var ActiveWeb = ""
    var ActiveTG = ""
    var ActiveYouTube = ""
    var MasPhoto: MutableList<String> = arrayListOf<String>() as MutableList<String>
    var MasPhotoURLs: MutableList<String> = arrayListOf<String>() as MutableList<String>

    /**Переменные из разметки XMl*/
    lateinit var overlayLayout: ConstraintLayout
    lateinit var overlayLayoutParams: ConstraintLayout.LayoutParams
    lateinit var textTitle: TextView
    lateinit var textName: TextView
    lateinit var textPhone: TextView
    lateinit var textAddress: TextView
    lateinit var buttunVk: ImageView
    lateinit var buttunTG: ImageView
    lateinit var buttunYT: ImageView
    lateinit var buttunWeb: ImageView

    /**Переменные  анимациями*/
    private lateinit var expandAnimation: Animation
    private lateinit var collapseAnimation: Animation
    /**Массив для потоков*/
    private var threadImage: MutableList<Thread> = mutableListOf()


    /**Дополнительные переменные*/
    private var ActiveThread = 0
    private lateinit var viewPager: ViewPager2
    private lateinit var adapterPager2: AdapterForHidePanel
    lateinit var indicator: WormDotsIndicator
    lateinit var timer: Timer
    private lateinit var phoneCallPermissionManager: TakePermissions

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_partners, container, false)

        viewPager = view.findViewById(R.id.viewPager)

        /**Присвоение объектов из разметки переменным*/
        view.findViewById<RecyclerView>(R.id.partnerView).layoutManager = GridLayoutManager(context,3)
        view.findViewById<RecyclerView>(R.id.partnerView).adapter=adapter
        overlayLayout = view.findViewById(R.id.LayoutHelp)
        overlayLayoutParams = overlayLayout.layoutParams as LayoutParams
        textTitle = view.findViewById(R.id.textPartner)
        textName = view.findViewById(R.id.NameText)
        textAddress = view.findViewById(R.id.textAddress)
        textPhone = view.findViewById(R.id.textPhone)
        buttunVk = view.findViewById(R.id.vkButton)
        buttunWeb = view.findViewById(R.id.webButton)
        buttunYT = view.findViewById(R.id.youtubeButton)
        buttunTG = view.findViewById(R.id.tgButton)
        viewPager = view.findViewById(R.id.viewPager)
        indicator=view.findViewById(R.id.worm_dots_indicator)
        adapterPager2 = AdapterForHidePanel()
        phoneCallPermissionManager = TakePermissions(requireActivity())
        //imagePokaz = view.findViewById(R.id.imageSmena)

        /**Подклчение анимации*/
        expandAnimation = AnimationUtils.loadAnimation(context, R.anim.expand_animation)
        collapseAnimation = AnimationUtils.loadAnimation(context, R.anim.collapse_animation)
        AnimationInit()

        /**Заполнение RecyclerView*/
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Перебираем каждый объект и выводим значение поля "LogoName"
                for (partnerSnapshot in dataSnapshot.children) {
                    val logoName = partnerSnapshot.child("LogoName").value
                    val _uid = partnerSnapshot.child("KeyNumber").value.toString().toInt()
                    storage.reference.child("Logotips/${logoName}").downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        adapter.PartnerCreate(imageUrl,_uid)
                        println("$logoName $_uid")
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //Обработка ошибки
                println("Failed to read value: ${databaseError.toException()}")
            }
        })
        /**Кликеры*/
        view.findViewById<ImageButton>(R.id.imageArrow).setOnClickListener { //Кликер для свертки
            ShowInfo()
        }
        textPhone.setOnClickListener {
            val phoneNumber = textPhone.text.toString() // Ваш номер телефона
            phoneCallPermissionManager.changePhone(phoneNumber)
            phoneCallPermissionManager.requestPhoneCallPermission()
        }
        view.findViewById<ImageView>(R.id.webButton).setOnClickListener { //Кликер для сайта
            val url = ActiveWeb
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        view.findViewById<ImageView>(R.id.tgButton).setOnClickListener { //Кликер для телеграма
            val url = ActiveTG
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        view.findViewById<ImageView>(R.id.vkButton).setOnClickListener { //Кликер для вк
            val url = ActiveVk
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        view.findViewById<ImageView>(R.id.youtubeButton).setOnClickListener { //Кликер для ютуба
            val url = ActiveYouTube
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        return view
    }

    /**Функция отвечающая за работу всплывающего окна*/
    private fun ShowInfo(uid: Int = 0){
        if (uid==0) { //Закрытие окна
            //Скрываем окно
            overlayLayout.visibility=View.INVISIBLE
            //Запускаем анимацию свертки
            overlayLayout.startAnimation(collapseAnimation)
            //Возвращаем кликабельность общего списка
            timer.cancel()
            partnerView.isEnabled=true
        } else {
            //Проявляем окно
            overlayLayout.visibility=View.VISIBLE
            /**Инициализация всех параметров окна*/
            reference.child(uid.toString()).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    SnapshotInit(dataSnapshot)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Обработка ошибки
                    Log.e("FirebaseError", "Не удалось прочитать значение.", databaseError.toException())
                }
            })
            //Запускаем анимацию развертки
            overlayLayout.startAnimation(expandAnimation)
            //Отключаем кликабельность общего списка
            partnerView.isEnabled=false
        }
    }

    /**Запуск окна при нажатии на RecyclerView*/
    override fun onClick(partner: Partner) {
        ShowInfo(partner._uid)
    }

    override fun onStop() {
        super.onStop()
        adapter.deleter()
    }

    /**Присвоение значений переменным по полученому объекту из БД*/
    private fun SnapshotInit(dataSnapshot: DataSnapshot){
        val valueName = dataSnapshot.child("Name").value
        val valueAddress = dataSnapshot.child("Address").value
        val valuePhone = dataSnapshot.child("PhoneNumber").value
        val ima = dataSnapshot.child("Photo").value
        ButtonInitPanel(dataSnapshot)
        textName.text=valueName.toString()
        textPhone.text=valuePhone.toString()
        textAddress.text=valueAddress.toString()
        MasPhoto = ima.toString().split(" ").toMutableList()
        ImagePager()
    }

    fun ImagePager(){
        adapterPager2.addImage(MasPhoto)
        viewPager.adapter = adapterPager2
        indicator.setViewPager2(viewPager)
        timer = Timer()
        val delay = 3000 // задержка между сменой картинок (5 секунд)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                requireActivity().runOnUiThread {
                    if (viewPager.currentItem < adapterPager2.itemCount - 1) {
                        viewPager.currentItem++
                    } else {
                        viewPager.currentItem = 0
                    }
                }
            }
        }, 0L, delay.toLong())
    }
    fun ButtonInitPanel(dataSnapshot: DataSnapshot){
        ActiveTG = dataSnapshot.child("Tellegram").value.toString()
        if (ActiveTG == "-")
            buttunTG.visibility=View.GONE
        ActiveVk = dataSnapshot.child("VKontakte").value.toString()
        if (ActiveVk == "-")
            buttunVk.visibility=View.GONE
        ActiveWeb = dataSnapshot.child("Web_site").value.toString()
        if (ActiveWeb == "-")
            buttunWeb.visibility=View.GONE
        ActiveYouTube = dataSnapshot.child("YouTube").value.toString()
        if (ActiveYouTube == "-")
            buttunYT.visibility=View.GONE
    }

    /**Инициализация анимации*/
    private fun AnimationInit(){
        val layoutParams = overlayLayout.layoutParams
        //Анимация свертки окна
        collapseAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                layoutParams.height = 0
                overlayLayout.layoutParams = layoutParams
            }
        })
        //Анимация развертки окна
        expandAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                layoutParams.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
                overlayLayout.layoutParams = layoutParams
            }
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {}
        })
    }

    /**Запрос разрешения*/
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        phoneCallPermissionManager.onRequestPermissionsResult(requestCode, grantResults)
    }
}