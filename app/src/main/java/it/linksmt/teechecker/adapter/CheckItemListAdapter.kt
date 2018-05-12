package it.linksmt.teechecker.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import it.linksmt.teechecker.R
import it.linksmt.teechecker.util.CheckItem


class CheckItemListAdapter(val context: Context, var items: List<CheckItem>, val itemClick : (CheckItem, View) -> Unit ) : RecyclerView.Adapter<CheckItemListAdapter.CheckItemHolder>() {
    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: CheckItemHolder, position: Int) {
        val item = items[position]
        holder.bind( item, context )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckItemHolder {
        val view = LayoutInflater.from( context ).inflate( R.layout.hardware_check_item, parent, false )
        return CheckItemHolder( view )
    }

    inner class CheckItemHolder(itemView: View?) :  RecyclerView.ViewHolder(itemView)  {
        var imageCheck : ImageView? = itemView?.findViewById( R.id.imageCheck )
        var textDescription : TextView? = itemView?.findViewById( R.id.textDescription )

        fun bind(item : CheckItem, context : Context ) {
            textDescription?.text = context.getText( item.descriptionResourceId )

            val resourcedId = if (item.status) R.drawable.ic_icon_check else R.drawable.ic_icon_uncheck
            imageCheck?.setImageResource( resourcedId )

            itemView.setOnClickListener {
                itemClick( item, itemView )
            }
        }
    }

    fun updateData( data: List<CheckItem> ) {
        this.items = ArrayList( data )
        notifyDataSetChanged()
    }
}
