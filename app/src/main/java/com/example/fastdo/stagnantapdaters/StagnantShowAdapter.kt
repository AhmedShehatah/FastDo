package com.example.fastdo.stagnantapdaters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fastdo.R
import com.example.fastdo.databinding.StagnantItemsModelBinding
import com.example.fastdo.firebase.FireStore
import com.example.fastdo.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StagnantShowAdapter(var context: Context, var items: ArrayList<StagnantShowModel>) :
    RecyclerView.Adapter<StagnantShowAdapter.ViewHolder>() {

    class ViewHolder(binding: StagnantItemsModelBinding) : RecyclerView.ViewHolder(binding.root) {
        var stagnantName = binding.tvName
        var stagnantType = binding.tvType
        var stagnantPrice = binding.tvPrice
        var stagnantAmount = binding.tvAmount
        var tbModel = binding.tbModel
        var ivControl = binding.ivControl
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StagnantItemsModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]
        holder.stagnantName.text = model.stagnantName
        holder.stagnantType.text = model.stagnantType
        holder.stagnantAmount.text = model.stagnantAmount.toString()
        holder.stagnantPrice.text = model.unitPrice.toString()
        holder.ivControl.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Delete")
            dialog.setMessage("هل تريد حذف الراكد")
            dialog.setPositiveButton("yes") { _, _ ->
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, items.size)
                deleteStagnantFromDatabase(holder)
            }
            dialog.setNegativeButton("No") { _, _ ->

            }
            dialog.show()
        }
        if (position % 2 != 0) {
            holder.tbModel.background = ContextCompat.getDrawable(context, R.color.show_list)
            holder.ivControl.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_delete_white
                )
            )
            setColor(position, holder)

        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    private fun setColor(position: Int, holder: ViewHolder) {
        val list = arrayListOf<TextView>()
        list.add(0, holder.stagnantName)
        list.add(1, holder.stagnantType)
        list.add(2, holder.stagnantPrice)
        list.add(3, holder.stagnantAmount)

        for (text in list) {
            list[list.indexOf(text)].setTextColor(Color.WHITE)

        }
    }

    private fun deleteStagnantFromDatabase( holder: ViewHolder) {
        val searchDB = FirebaseDatabase.getInstance().getReference(Constants.MARKET)
            .child(Constants.DETAILS)
        searchDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {

                        if (data.child("id").value == FireStore().getCurrentUserID()
                            && data.child("stagnantName").value
                            == holder.stagnantName.text.toString()){
                            data.ref.removeValue()
                        }
                    }


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val showDB = FirebaseDatabase.getInstance().getReference(Constants.MYSTAGNANTS)
            .child(Constants.DETAILS).child(FireStore().getCurrentUserID())
        showDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (data in snapshot.children) {

                        if (data.child("id").value == FireStore().getCurrentUserID()
                            && data.child("stagnantName").value
                            == holder.stagnantName.text.toString()){
                            data.ref.removeValue()
                        }
                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}