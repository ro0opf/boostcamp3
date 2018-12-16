package com.example.jaehyeok.boostcamp3.adapter

import android.content.Intent
import android.net.Uri
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jaehyeok.boostcamp3.R
import com.example.jaehyeok.boostcamp3.act.WebviewActivity
import com.example.jaehyeok.boostcamp3.item.Homefeed
import com.example.jaehyeok.boostcamp3.item.itemMovie
import kotlinx.android.synthetic.main.item_rcv.view.*
import kotlin.math.round

class RecyclerAdapter(val homefeed: Homefeed): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return homefeed.items.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bindItems(homefeed.items.get(position))
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val TAG = "@#viewHolder#@"
        fun bindItems(data : itemMovie){
            Glide.with(view.context)
                .load(data.image)
                .apply(RequestOptions().override(110, 158))
                .apply(RequestOptions.centerCropTransform())
                .apply(RequestOptions().placeholder(R.drawable.no_image))
                .into(view.iv_poster)
            itemView.tv_title.text = setTextHTML(data.title)
            itemView.tv_pubDate.text = "${data.pubDate}"
            itemView.tv_director.text = "${data.director}"
            itemView.tv_actor.text = "${data.actor}"
            itemView.rating.rating = round(data.userRating.toFloat())/2

            itemView.setOnClickListener({
                val i = Intent(view.context, WebviewActivity::class.java)
                i.putExtra("url", data.link)
                view.getContext().startActivity(i);
            })
        }

        fun setTextHTML(html: String): Spanned
        {
            val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(html)
            }
            return result
        }
    }
}