package com.example.recepieapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recepieapp.databinding.ActivityHomeBinding
import com.example.recepieapp.databinding.ActivityMainBinding
import com.example.recepieapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var rvAdaptor: SearchAdapter
    private lateinit var binding: ActivitySearchBinding
    private lateinit var dataList: ArrayList<Recipe>
    private var recipes: List<Recipe?> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding=ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.search.requestFocus()
        val db = Room.databaseBuilder(this@SearchActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationFrom()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()

        recipes = daoObject.getAll()?: listOf()

        binding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString()!= ""){
                    filterData(s.toString())
                }
            }

            private fun filterData(filterText: String) {

                var filterData = ArrayList<Recipe>()
                for(i in recipes.indices)
                {
                    if(recipes[i]!!.tittle.lowercase().contains(filterText.lowercase())){
                        filterData.add(recipes[i]!!)
                    }
                    rvAdaptor.filterList(filterData)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })



        setUpRecyclerView()

        binding.goBackHome.setOnClickListener {
            finish()
        }
    }
    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.rvSearch.layoutManager=
            LinearLayoutManager(this)




        for (i in recipes!!.indices) {
            if(recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }
            rvAdaptor=SearchAdapter(dataList,this)
            binding.rvSearch.adapter=rvAdaptor
        }
    }
}