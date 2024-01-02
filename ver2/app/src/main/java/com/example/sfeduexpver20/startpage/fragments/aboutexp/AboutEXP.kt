package com.example.sfeduexpver20.startpage.fragments.aboutexp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sfeduexpver20.R
import com.example.sfeduexpver20.databinding.FragmentAboutEXPBinding
import com.example.sfeduexpver20.databinding.FragmentStartBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class AboutEXP : Fragment() {

    private lateinit var binding: FragmentAboutEXPBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutEXPBinding.inflate(inflater,container,false)
        val animation = AboutExpAnimation(
            requireContext(),
            R.animator.front_animator,
            R.animator.back_animator,
            R.anim.expand_animation,
            R.anim.collapse_animation
        )
        val scale:Float = requireContext().resources.displayMetrics.density
        val card1 = AboutexpCard(binding.cardFront1,binding.cardBack1,scale)
        val card2 = AboutexpCard(binding.cardFront2,binding.cardBack2,scale)

        binding.cardFront1.setOnClickListener{
            animation.setTargetandStart(card1)
        }
        binding.cardFront2.setOnClickListener{
            animation.setTargetandStart(card2)
        }
        binding.textItogi.setOnClickListener {
            showBottomSheet()
        }
        return binding.root
    }

    private fun showBottomSheet(){
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_aboutexp,null)
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()
    }

}