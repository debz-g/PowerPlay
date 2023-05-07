package dev.redfox.powerplay.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.redfox.powerplay.R
import dev.redfox.powerplay.models.BeerModel


class BeerAdapter(
    var productList: MutableList<BeerModel>
) : RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {

    var onItemClick: ((BeerModel) -> Unit)? = null
    var onItemLongClick : ((BeerModel) -> Unit)? = null

    class BeerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val beerImg: ImageView = itemView.findViewById(R.id.beer_image)
        val beerName: TextView = itemView.findViewById(R.id.beer_name)
        val beerTag: TextView = itemView.findViewById(R.id.beer_tagline)
        val btnShare: ImageButton = itemView.findViewById(R.id.btn_share)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.data_item_layout, parent, false)
        return BeerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = productList[position]

        holder.beerName.text = beer.name
        holder.beerTag.text = beer.tagline

        if(beer.image_url == null || beer.image_url!!.isEmpty()) {
            Picasso.get().load(R.drawable.placeholder).placeholder(R.drawable.placeholder)
                .into(holder.beerImg)
        } else {
            Picasso.get().load(beer.image_url).placeholder(R.drawable.placeholder)
                .into(holder.beerImg)
        }


        holder.btnShare.setOnClickListener() {
            onItemClick?.invoke(beer)


        }

        holder.itemView.setOnClickListener {
            onItemLongClick?.invoke(beer)
            true
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }


    fun setfilteredList(productList: ArrayList<BeerModel>){
        this.productList = productList
        notifyDataSetChanged()
    }
}