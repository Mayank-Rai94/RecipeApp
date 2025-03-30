package com.example.recepieapp

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.recepieapp.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecipeBinding
    var imgCrop = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImg)
        binding.title.text = intent.getStringExtra("tittle")
        binding.stepData.text = intent.getStringExtra("des")

        val ing = intent.getStringExtra("ing")?.split("\n")?.filter { it.isNotBlank() }
        binding.time.text = ing?.get(0)

        for(i in 1 until ing!!.size){
            binding.ingData.text = """${binding.ingData.text} 🟢 ${ing[i]} 
            """.trimIndent()
        }

        binding.step.background = null

        binding.ing.setBackgroundResource(R.drawable.btn_ing)

        binding.ing.setTextColor(getColor(R.color.black))
        binding.step.setOnClickListener {
            binding.step.setBackgroundResource(R.drawable.btn_ing)
            binding.step.setTextColor(getColor(R.color.white))
            binding.ing.setTextColor(getColor(R.color.black))
            binding.ing.background = null
            binding.stepScroll.visibility = View.VISIBLE
            binding.ingScroll.visibility = View.GONE

        }

        binding.step.setTextColor(getColor(R.color.black))
        binding.ing.setOnClickListener {
            binding.ing.setBackgroundResource(R.drawable.btn_ing)
            binding.ing.setTextColor(getColor(R.color.white))
            binding.step.setTextColor(getColor(R.color.black))
            binding.step.background = null
            binding.ingScroll.visibility = View.VISIBLE
            binding.stepScroll.visibility = View.GONE
        }

        binding.fullScreen.setOnClickListener {
            if(imgCrop){
                binding.itemImg.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImg)
                binding.shadow.visibility = View.GONE
                binding.fullScreen.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                imgCrop = !imgCrop
            }else{
                binding.itemImg.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImg)
                binding.shadow.visibility = View.GONE
                binding.fullScreen.setColorFilter(null)
                imgCrop = !imgCrop
            }
        }
        
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}