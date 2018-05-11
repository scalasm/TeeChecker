package it.linksmt.teechecker.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import it.linksmt.teechecker.R
import it.linksmt.teechecker.model.HardwareCheckItem
import android.R.attr.data



class HardwareCheckItemListAdapter(val context: Context, var items: List<HardwareCheckItem>, val itemClick : (HardwareCheckItem, View) -> Unit ) : RecyclerView.Adapter<HardwareCheckItemListAdapter.HardwareCheckItemHolder>() {
    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: HardwareCheckItemHolder, position: Int) {
        val item = items[position]
        holder.bind( item, context )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HardwareCheckItemHolder {
        val view = LayoutInflater.from( context ).inflate( R.layout.hardware_check_item, parent, false )
        return HardwareCheckItemHolder( view )
    }

    inner class HardwareCheckItemHolder(itemView: View?) :  RecyclerView.ViewHolder(itemView)  {
        var imageCheck : ImageView? = itemView?.findViewById( R.id.imageCheck )
        var textDescription : TextView? = itemView?.findViewById( R.id.textDescription )

        fun bind( item : HardwareCheckItem, context : Context ) {
            textDescription?.text = context.getText( item.descriptionResourceId )

            val resourcedId = if (item.status) R.drawable.ic_icon_check else R.drawable.ic_icon_uncheck
            imageCheck?.setImageResource( resourcedId )

            itemView.setOnClickListener {
                itemClick( item, itemView )
            }
        }
    }

    fun updateData( data: List<HardwareCheckItem> ) {
        this.items = ArrayList( data )
        notifyDataSetChanged()
    }
}
