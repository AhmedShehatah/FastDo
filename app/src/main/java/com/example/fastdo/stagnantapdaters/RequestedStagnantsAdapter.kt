package com.example.fastdo.stagnantapdaters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdo.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class RequestedStagnantsAdapter(
    var options: FirebaseRecyclerOptions<SearchStagnantModel?>?, var context: Context
) : FirebaseRecyclerAdapter<SearchStagnantModel?, RequestedStagnantsAdapter.StagnantViewHolder>(
    options!!
) {
    class StagnantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var tvType: TextView = itemView.findViewById(R.id.tvType)
        var tvDiscount: TextView = itemView.findViewById(R.id.tvDiscount)
        var tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        var tvExpiryDate: TextView = itemView.findViewById(R.id.tvExpiryDate)
        var tvContactInfo: TextView = itemView.findViewById(R.id.tvContactInfo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StagnantViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.requseted_stagnants_model, parent, false)
        return StagnantViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: StagnantViewHolder,
        position: Int,
        model: SearchStagnantModel
    ) {
        holder.tvName.text = model.stagnantName
        holder.tvType.text = model.stagnantType
        holder.tvAmount.text = model.stagnantAmount.toString()
        holder.tvDiscount.text = model.discount.toString()
        holder.tvExpiryDate.text = model.stagnantExpiryDate
        holder.tvContactInfo.text = model.contactInfo


    }
}
