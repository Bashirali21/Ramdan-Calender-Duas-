package com.example.ramzancalender.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.example.ramzancalender.R
import com.example.ramzancalender.putLatLng
import com.example.ramzancalender.putSect

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GetSectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GetSectFragment : Fragment() {
    var sect = sunni
    lateinit var cardView: CardView
    lateinit var cardView1: CardView
    lateinit var cardHanafi:CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_get_sect, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.findViewById<Button>(R.id.btn_next).setOnClickListener {
            val sharedPreferences =
                requireActivity().getSharedPreferences("main", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putSect(sect)
            viewPager?.currentItem = 1
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardView = view.findViewById(R.id.cardView2)
        cardHanafi=view.findViewById(R.id.cardView3)
        cardView1 = view.findViewById(R.id.cardView4)
        cardView.setOnClickListener {
            sect = sunni
            cardView.setCardBackgroundColor(resources.getColor(R.color.md_theme_primaryContainer))
            cardView1.setCardBackgroundColor(resources.getColor(R.color.md_theme_surface))
            cardHanafi.setCardBackgroundColor(resources.getColor(R.color.md_theme_surface))
        }
        cardHanafi.setOnClickListener {
            sect = hanafi
            cardHanafi.setCardBackgroundColor(resources.getColor(R.color.md_theme_primaryContainer))
            cardView1.setCardBackgroundColor(resources.getColor(R.color.md_theme_surface))
            cardView.setCardBackgroundColor(resources.getColor(R.color.md_theme_surface))
        }
        cardView1.setOnClickListener {
            sect = shia
            cardView1.setCardBackgroundColor(resources.getColor(R.color.md_theme_primaryContainer))
            cardView.setCardBackgroundColor(resources.getColor(R.color.md_theme_surface))
            cardHanafi.setCardBackgroundColor(resources.getColor(R.color.md_theme_surface))

        }
    }

}

const val sunni = "sunni"
const val shia = "shia"
const val hanafi="Hanafi"