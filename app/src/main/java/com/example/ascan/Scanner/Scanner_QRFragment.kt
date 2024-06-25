package com.example.ascan.Scanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.ascan.R
import com.example.ascan.db.DBHelper
import com.example.ascan.db.DatabaseHelper
import com.example.ascan.db.database.ScanResultDataBase
import com.example.ascan.ui_design.ScanResultDialog
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Scanner_QRFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Scanner_QRFragment : Fragment(),ZXingScannerView.ResultHandler {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    always we have to do this for new fragments for getting new instance of our fragments
    companion object{
        fun newInstance():Scanner_QRFragment{
            return Scanner_QRFragment()
        }
    }
    private lateinit var mView: View
    private lateinit var scannerView:ZXingScannerView
    private lateinit var scanResultDialog:ScanResultDialog
    private lateinit var dbHelper: DBHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_scanner__q_r, container, false)
//        initialiseQRScanner()
        init()
        initViews()
        onClick()
        return mView.rootView
    }
    private fun init()
    {
        dbHelper= DatabaseHelper(ScanResultDataBase.getAppDatabase(requireContext())!!)

    }
    private fun onClick()
    {
        mView.findViewById<ImageView>(R.id.flashSwitch).setOnClickListener{
            if(it.isSelected)
            {
                offFlash()
            }
            else
            {
                onFlash()
            }
        }
    }
    private fun initViews()
    {
        initialiseQRScanner()
        AddResultDialog()
    }
    private fun AddResultDialog()
    {
        scanResultDialog=ScanResultDialog(requireContext())
        scanResultDialog.AddOnDismissListener(object :ScanResultDialog.OnDismissListener{
            override fun onDismiss() {
                scannerView.resumeCameraPreview(this@Scanner_QRFragment)
            }
        })
    }
    private fun onFlash()
    {
        mView.findViewById<ImageView>(R.id.flashSwitch).isSelected=true
        scannerView.flash=true

    }
    private  fun offFlash()
    {
        mView.findViewById<ImageView>(R.id.flashSwitch).isSelected=false
        scannerView.flash=false
    }
    private fun initialiseQRScanner()
    {

        scannerView= ZXingScannerView(requireContext())
        scannerView.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(requireContext(), R.color.red))
        scannerView.setLaserColor(ContextCompat.getColor(requireContext(), R.color.red))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setAutoFocus(true)
        scannerView.setResultHandler(this)
        scannerView.setSquareViewFinder(true)
//        we have to connect the scanner with this scannerview
        mView.findViewById<FrameLayout>(R.id.CamScanner).addView(scannerView)
//        here we have integrate scanner view with the ui
        OpenQRCamera()
    }
    private fun OpenQRCamera()
    {
        scannerView.startCamera()       //this fun start the camera
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)   //Register ourselves as a handler for scan results
        scannerView.startCamera()     //start camera on Resume
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }
//   for the scanner result we have to write the function
    override fun handleResult(p0: Result?) {
//        Toast.makeText(requireContext(),p0?.text,Toast.LENGTH_SHORT).show()
//    after getting result of scan we have to reset it
    onScanResult(p0!!.text)

    }
    private fun onScanResult(text:String)
    {
        if(text.isEmpty())
        {
            Toast.makeText(requireContext(),"Empty QR Code",Toast.LENGTH_SHORT).show()
        }else
        {
            saveToOurDatabase(text)
        }
    }
    private fun saveToOurDatabase(result:String)
    {
//        when we inserted a database we need an id
        val  insertedDbID=dbHelper.insertScanResult(result)
        val  scanResult=dbHelper.getScanResult(insertedDbID)
        scanResultDialog.showDialog(scanResult)


    }
}



//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment Scanner_QRFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            Scanner_QRFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
