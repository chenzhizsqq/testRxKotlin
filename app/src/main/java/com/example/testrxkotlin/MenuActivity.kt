package com.example.testrxkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.testrxkotlin.databinding.ActivityMenuBinding
import com.example.testrxkotlin.demo1.MainActivity
import com.example.testrxkotlin.demo2.RetrofitRxKotlinActivity

class MenuActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MenuActivity"
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.testRxKotlinTextView.setOnClickListener(this)
        binding.RetrofitRxKotlinActivity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.testRxKotlinTextView -> {
                val intent =
                    Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.RetrofitRxKotlinActivity -> {
                val intent =
                    Intent(this@MenuActivity, RetrofitRxKotlinActivity::class.java)
                startActivity(intent)
            }
        }

    }
}