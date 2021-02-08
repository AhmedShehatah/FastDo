package com.example.fastdo.stagnantapdaters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdo.R
import com.example.fastdo.firebase.FireStore
import com.example.fastdo.models.Users
import com.example.fastdo.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
 class SearchStagnantAdapter(
    var context: Context,
    var itemsList: ArrayList<SearchStagnantModel>
) :
    RecyclerView.Adapter<SearchStagnantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var tvType: TextView = itemView.findViewById(R.id.tvType)
        var tvDiscount: TextView = itemView.findViewById(R.id.tvDiscount)
        var tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        var tvExpiryDate: TextView = itemView.findViewById(R.id.tvExpiryDate)
        var btnBuy: Button = itemView.findViewById(R.id.btnBuy)
        var tvUnitType: TextView = itemView.findViewById(R.id.tvUnitType)
        var tvPriceType: TextView = itemView.findViewById(R.id.tvPriceType)
        var tvUnitPrice: TextView = itemView.findViewById(R.id.tvUnitPrice)
        var tvContactInfo: TextView = itemView.findViewById(R.id.tvContactInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_stagnant_itemsm_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val model = itemsList[position]
        holder.tvName.text = model.stagnantName
        holder.tvType.text = model.stagnantType
        holder.tvAmount.text = model.stagnantAmount.toString()
        holder.tvDiscount.text = model.discount.toString()
        holder.tvUnitType.text = model.unitType
        holder.tvPriceType.text = model.priceType
        holder.tvUnitPrice.text = model.unitPrice.toString()
        holder.tvContactInfo.text = model.contactInfo
        holder.tvExpiryDate.text = model.stagnantExpiryDate
        val items = ArrayList<SearchStagnantModel>()
        val userInfoItems = ArrayList<Users>()
        var clintContactInfo = ""

        val mRef = FirebaseDatabase.getInstance().getReference(Constants.MARKET)
            .child(Constants.DETAILS)
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val model: SearchStagnantModel =
                        data.getValue(SearchStagnantModel::class.java)!!
                    items.add(model)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        val userInfo = FirebaseDatabase.getInstance().getReference(Constants.USERS)
            .child(Constants.DETAILS).child(FireStore().getCurrentUserID())
        userInfo.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                val model = snapshot.getValue(Users::class.java)!!

//                userInfoItems.add(model)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        for (user in userInfoItems) {
            clintContactInfo = "${user.phoneNumber}" + "\n" + "${user.exactAddress}"
        }


        holder.btnBuy.setOnClickListener {
            if (FireStore().getCurrentUserID() != items[position].id) {
                val searchStagnantInfo = SearchStagnantModel(
                    FireStore().getCurrentUserID(),
                    holder.tvName.text.toString(),
                    holder.tvType.text.toString(),
                    holder.tvAmount.text.toString().toInt(),
                    holder.tvUnitType.text.toString(),
                    holder.tvUnitPrice.text.toString().toDouble(),
                    holder.tvPriceType.text.toString(),
                    holder.tvExpiryDate.text.toString(),
                    holder.tvDiscount.text.toString().toInt(),
                    holder.tvContactInfo.text.toString()

                )
                val sentForUs = SearchStagnantModel(
                    FireStore().getCurrentUserID(),
                    holder.tvName.text.toString(),
                    holder.tvType.text.toString(),
                    holder.tvAmount.text.toString().toInt(),
                    holder.tvUnitType.text.toString(),
                    holder.tvUnitPrice.text.toString().toDouble(),
                    holder.tvPriceType.text.toString(),
                    holder.tvExpiryDate.text.toString(),
                    holder.tvDiscount.text.toString().toInt(),
                    clintContactInfo

                )
                Toast.makeText(
                    context,
                    "Done!",
                    Toast.LENGTH_SHORT
                )
                    .show()
                FireStore().requestedStagnantStore(searchStagnantInfo, context)
                FireStore().stagnantSentForUsStore(
                    sentForUs,
                    items[position].id, context
                )
            } else {
                Toast.makeText(context, "لا يمكنك شراء منتجاتكم", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}