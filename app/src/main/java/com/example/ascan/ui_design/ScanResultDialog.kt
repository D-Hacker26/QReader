package com.example.ascan.ui_design

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu.OnDismissListener
import com.example.ascan.R
import com.example.ascan.db.DBHelper
import com.example.ascan.db.DatabaseHelper
import com.example.ascan.db.database.ScanResultDataBase
import com.example.ascan.db.entities.ScanResult
import com.example.ascan.ui_design.utils.toDisplay

class ScanResultDialog(var context: Context) {
    private lateinit var dialog: Dialog
    private var scanResult : ScanResult?=null
    private var onDismissListener:OnDismissListener? =null
    private lateinit var dbHelper:DBHelper
    init {
        initializeDbHelper()
        initializeDialog()
    }
    private fun initializeDbHelper()
    {
        dbHelper=DatabaseHelper(ScanResultDataBase.getAppDatabase(context)!!)
    }
    private fun initializeDialog()
    {
        dialog=Dialog(context)
        dialog.setContentView(R.layout.scan_result_show)
        dialog.setCancelable(false)
        onClicks()

    }
    fun AddOnDismissListener(onDismissListener: OnDismissListener)
    {
        this.onDismissListener=onDismissListener
    }
    fun showDialog(scanResult: ScanResult){
        this.scanResult=scanResult
        dialog.findViewById<TextView>(R.id.scannedDate).text= scanResult.calendar.toDisplay()
//        to display fun is created in utils
        dialog.findViewById<TextView>(R.id.scannedText).text=scanResult.result
        dialog.findViewById<ImageView>(R.id.favouriteIcon).isSelected=scanResult.favourite
        dialog.show()


    }
    private fun onClicks()
    {
        dialog.findViewById<ImageView>(R.id.favouriteIcon).setOnClickListener{
            if(dialog.findViewById<ImageView>(R.id.favouriteIcon).isSelected)
            {
                removeFromFavourite()
            }
            else
            {
                addToFavorite()
            }

        }
        dialog.findViewById<ImageView>(R.id.shareResult).setOnClickListener {
            ShareScannedResults()

        }
        dialog.findViewById<ImageView>(R.id.copyResult).setOnClickListener {
            copyScanResultToClipBoard()
        }
        dialog.findViewById<ImageView>(R.id.cancelDialog).setOnClickListener {
            onDismissListener?.onDismiss()
            dialog.dismiss()
        }
    }
    private fun addToFavorite()
    {
        dialog.findViewById<ImageView>(R.id.favouriteIcon).isSelected=true
        dbHelper.addToFavourite(scanResult?.id!!)

    }
    private fun removeFromFavourite()
    {
        dialog.findViewById<ImageView>(R.id.favouriteIcon).isSelected=false
        dbHelper.removeFromFavourite(scanResult?.id!!)
    }
    private fun copyScanResultToClipBoard()
    {
//        At first take the access of the clipboard from get system service
        val clipboard=context.getSystemService(Context.CLIPBOARD_SERVICE)as ClipboardManager
        val clip=ClipData.newPlainText("ScannerResult",dialog.findViewById<TextView>(R.id.scannedText).text)

        clipboard.text=clip.getItemAt(0).text.toString()
        Toast.makeText(context,"Result is Copied to Clipboard",Toast.LENGTH_SHORT).show()
    }

    private fun ShareScannedResults()
    {
        val textIntent= Intent(Intent.ACTION_SEND)
        textIntent.type="text/Plain"
        textIntent.putExtra(Intent.EXTRA_TEXT,dialog.findViewById<TextView>(R.id.scannedText).text.toString())
        context.startActivity(textIntent)
    }
    interface OnDismissListener
    {
        fun  onDismiss()
    }
}