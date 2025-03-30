package com.example.recepieapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recepieapp.databinding.RvPopularItemBinding

class PopularAdaptor(var datalist: ArrayList<Recipe>, var context: Context) :
    RecyclerView.Adapter<PopularAdaptor.ViewHolder>() {

    inner class ViewHolder(var binding: RvPopularItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvPopularItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = datalist[position]

        // Load image using Glide
        Glide.with(context)
            .load(item.img)
            .into(holder.binding.popularImg)

        // Set text fields
        holder.binding.popularText.text = item.tittle

        // Handle ingredients safely
        val timeList = item.ing.split("\n").filter { it.isNotBlank() }
        holder.binding.popularTime.text = if (timeList.isNotEmpty()) timeList[0] else "N/A"

        holder.itemView.setOnClickListener{
            var intent = Intent(context,RecipeActivity::class.java)
            intent.putExtra("img",datalist.get(position).img)
            intent.putExtra("tittle",datalist.get(position).tittle)
            intent.putExtra("des",datalist.get(position).des)
            intent.putExtra("ing",datalist.get(position).ing)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}
