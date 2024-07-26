package com.example.openinapptest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LinksAdapter(private var linksList: List<Link>) : RecyclerView.Adapter<LinksAdapter.LinkViewHolder>() {

    inner class LinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val clicksTextView: TextView = view.findViewById(R.id.clicksTextView)
        val linkTextView: TextView = view.findViewById(R.id.linkTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return LinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val link = linksList[position]
        // Load image with a library like Glide or Picasso
        Glide.with(holder.imageView.context).load(link.originalImage).into(holder.imageView)
        if(link.title.length>20){
            holder.titleTextView.text = link.title.substring(0,16)+".."
        }
        holder.dateTextView.text = link.createdAt.substring(0,10)
        holder.clicksTextView.text = link.totalClicks.toString()
        holder.linkTextView.text = link.webLink
    }

    override fun getItemCount(): Int {
        return linksList.size
    }

    fun updateLinks(newLinks: List<Link>) {
        linksList = newLinks
        notifyDataSetChanged()
    }
}
