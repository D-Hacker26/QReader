package com.example.ascan.RecentsHistory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.ascan.R
import com.example.ascan.db.DBHelper
import com.example.ascan.db.DatabaseHelper
import com.example.ascan.db.entities.ScanResult
import com.example.ascan.ui_design.ScanResultDialog
import com.example.ascan.ui_design.utils.toDisplay


class ScanResultsListAdapter(var dbHelper: DBHelper,
                             var context: Context,
                             var listofScanResults:MutableList<ScanResult>
                             ):
    RecyclerView.Adapter<ScanResultsListAdapter.ScanResultsListViewHolder>() {
    private  var ScanQRResultDialog=ScanResultDialog(context)
    inner class ScanResultsListViewHolder(var view: View):RecyclerView.ViewHolder(view)
    {
        fun bind(scanResult: ScanResult,position: Int)
        {
            view.findViewById<TextView>(R.id.result).text=scanResult.result
            view.findViewById<TextView>(R.id.tvTime).text=scanResult.calendar.toDisplay()
            SetTofavourite(scanResult.favourite)
            onClicks(scanResult,position)

        }
        private fun onClicks(scanResult: ScanResult,position: Int)
        {
            view.setOnClickListener{
                ScanQRResultDialog.showDialog(scanResult)
            }
            view.setOnLongClickListener {
                showDeleteDialog(scanResult,position)
                return@setOnLongClickListener true
            }
        }
        private fun showDeleteDialog(scanResult: ScanResult,position: Int)
        {
            AlertDialog.Builder(context,R.style.CustomAlertDialog)
                .setTitle("Delete Records")
                .setMessage("Are you really want to delete this Scan Records?")
                .setPositiveButton("Delete"){
                        _, _ ->  //dialog,which
                    deleteThisScanResult(scanResult,position)
                }
                .setNegativeButton("Cancel"){
                        dialog, _ ->
                    dialog.cancel()
                }.show()

        }
        private fun deleteThisScanResult(scanResult: ScanResult,position: Int)
        {
            dbHelper.deleteScanResult(scanResult.id!!)
//            we have to tell that recyclerview that we have removed some data
            listofScanResults.removeAt(position)
            notifyItemRemoved(position)

        }
        private fun SetTofavourite(favourite:Boolean)
        {
            if(favourite)
            {
                view.findViewById<ImageView>(R.id.favouriteIcon).visibility=View.VISIBLE
            }
            else
            {
                view.findViewById<ImageView>(R.id.favouriteIcon).visibility=View.GONE

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanResultsListViewHolder {
        return ScanResultsListViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_single_item_qr_result,parent,false))

    }

    override fun onBindViewHolder(holder: ScanResultsListViewHolder, position: Int) {
        holder.bind(listofScanResults[position],position)

    }

    override fun getItemCount(): Int {
        return listofScanResults.size
    }
}