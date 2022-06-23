package com.zensarnewsapp.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zensarnewsapp.R
import com.zensarnewsapp.apiinterface.ItemClickListener
import com.zensarnewsapp.databinding.NewsRowLayoutBinding
import com.zensarnewsapp.model.Article

class NewsAdapter(private val itemClickListener: ItemClickListener): RecyclerView.Adapter<NewsViewHolder>() {

    var newsList = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        //val inflater = LayoutInflater.from(parent.context)
        val binding = NewsRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        if(newsList.get(position).title!=null && newsList.get(position).title!=null){
            holder.binding.tvText.text = newsList.get(position).title
            holder.binding.tvDescription.text = newsList.get(position).description

            if(newsList.get(position).urlToImage!=null){
            Glide.with(holder.itemView.context).load(newsList.get(position).urlToImage)
                .into(holder.binding.img)
            }
            else{
                Glide.with(holder.itemView.context).load(R.mipmap.not_found)
                    .into(holder.binding.img)
            }
        }

        holder.binding.rootLayout.setOnClickListener{
            itemClickListener.onItemClickListener(newsList.get(position).url)
        }
    }

    fun setData(newsList: List<Article>){
        this.newsList = newsList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return 10
    }


}

class NewsViewHolder(val binding: NewsRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

}
