package com.example.recepieapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recepieapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdaptor: PopularAdaptor
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpRecyclerView()

        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.salad.setOnClickListener{
            var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)

        }

        binding.mainDish.setOnClickListener {
            var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent)
        }

        binding.drinks.setOnClickListener{
            var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)

        }

        binding.desserts.setOnClickListener{
            var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Desserts")
            myIntent.putExtra("CATEGORY","Desserts")
            startActivity(myIntent)

        }

        binding.more.setOnClickListener {
            var dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }

    }


    private fun setUpRecyclerView() {
        dataList = ArrayList()
        var recycle : View = findViewById(R.id.rv_popular)
        binding.rvPopular.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val db = Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationFrom()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()

        val recipes = daoObject.getAll()

        for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
            rvAdaptor=PopularAdaptor(dataList,this)
            binding.rvPopular.adapter=rvAdaptor
        }
    }
}