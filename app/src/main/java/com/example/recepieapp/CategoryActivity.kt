package com.example.recepieapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recepieapp.databinding.ActivityCategoryBinding
import com.example.recepieapp.databinding.ActivityHomeBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var rvAdaptor: CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = intent.getStringExtra("TITLE")
        setUpRecyclerView()
        binding.goBackHome.setOnClickListener {
            finish()
        }

    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        var recycle : View = findViewById(R.id.rvCategory)
        binding.rvCategory.layoutManager=
            LinearLayoutManager(this)
        val db = Room.databaseBuilder(this@CategoryActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationFrom()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()

        val recipes = daoObject.getAll()

        for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)){
                dataList.add(recipes[i]!!)
            }
            rvAdaptor=CategoryAdapter(dataList,this)
            binding.rvCategory.adapter=rvAdaptor
        }
    }
}