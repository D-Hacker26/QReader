package com.example.ascan.RecentsHistory

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ascan.R
import com.example.ascan.RecentsHistory.adapter.ScanResultsListAdapter
import com.example.ascan.db.DBHelper
import com.example.ascan.db.DatabaseHelper
import com.example.ascan.db.database.ScanResultDataBase
import com.example.ascan.db.entities.ScanResult

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecentFragments.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecentFragments : Fragment() {
//    FIRST we have to create a enum class by which we can decide which list to be shown
    enum class ScanResultListType{
        TOTAL_RESULT,FAVOURITE_CLASS
    }
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    companion object{
        private const val ARGUMENT_RESULT_LIST_TYPE="ArgumentResultListType"
        fun newInstance(screenType: ScanResultListType):RecentFragments{
//            when we initiate this frag ,we will pass all the results  and we will put it in the bundles
//            and in the arguments we have to pass this bundle ,after this we have to catch it in onCreate
            val bundle=Bundle()
            bundle.putSerializable(ARGUMENT_RESULT_LIST_TYPE,screenType)
            val fragment=RecentFragments()
            fragment.arguments=bundle
            return fragment
        }
    }
    private lateinit var resultType:ScanResultListType
    private lateinit var mView: View
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }
    private fun handleArguments()
    {
        resultType=requireArguments().getSerializable(ARGUMENT_RESULT_LIST_TYPE)as ScanResultListType

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_recent_fragments, container, false)
        init()
        ShowListOfResults()
        setrefreshLayout()
        onClicks()
        return mView
    }
    private fun onClicks()
    {
        mView.findViewById<ImageView>(R.id.removeAll).setOnClickListener {
            showRemoveAllScanResultsDialog()
        }
    }
    private fun showRemoveAllScanResultsDialog()
    {
        context?.let {
            AlertDialog.Builder(it,R.style.CustomAlertDialog)
                .setTitle("Delete All Records")
                .setMessage("Are you really want to delete this Scan Records?")
                .setPositiveButton("Delete All"){ _, _ ->  //dialog,which
                    clearScanResultsRecords()
                }
                .setNegativeButton("Cancel"){ dialog, _ ->
                    dialog.cancel()
                }.show()
        }
    }
    private fun clearScanResultsRecords()
    {
        when(resultType){
            ScanResultListType.TOTAL_RESULT->dbHelper.removeAllScanResultsRecords()
            ScanResultListType.FAVOURITE_CLASS->dbHelper.removeAllFavouriteScanResultsRecords()
        }
        mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView)?.adapter?.notifyDataSetChanged()
        ShowListOfResults()
    }
    private fun setrefreshLayout()
    {
        mView.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh).setOnRefreshListener{
            mView.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh).isRefreshing=false
            ShowListOfResults()
        }
    }
    private fun init()
    {
        dbHelper=DatabaseHelper(ScanResultDataBase.getAppDatabase(requireContext())!!)
    }
    private fun ShowListOfResults()
    {
        when(resultType)
        {
            ScanResultListType.TOTAL_RESULT->{
                showTotalScanResults()
            }
            ScanResultListType.FAVOURITE_CLASS-> {
                showOnlyFavouriteScanResults()
            }
        }
    }
    private fun showTotalScanResults()
    {
        val listOfAllScanResult=dbHelper.getTotalScanResults()
        showScanResults(listOfAllScanResult)
        mView.findViewById<TextView>(R.id.tvHeaderText).text="Recent ScanResults"
    }
    private fun showOnlyFavouriteScanResults()
    {
        val listOfAllFavouriteResults=dbHelper.getTotalFavouriteScanResults()
        showScanResults(listOfAllFavouriteResults)
        mView.findViewById<TextView>(R.id.tvHeaderText).text="Recent FavouriteScanResults"
    }

    private fun showScanResults(listOfAllScanResults: List<ScanResult>) {
        if(listOfAllScanResults.isEmpty())
        {
            showEmptyState()
        }
        else
        {
            initRecyclerView(listOfAllScanResults)
        }
    }
    private fun showEmptyState()
    {
        mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView).visibility=View.GONE
        mView.findViewById<ImageView>(R.id.noResultFound).visibility=View.VISIBLE
        mView.findViewById<ImageView>(R.id.removeAll).visibility=View.GONE
    }
    private fun showRecyclerView()
    {
        mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView).visibility=View.VISIBLE
        mView.findViewById<ImageView>(R.id.noResultFound).visibility=View.GONE
        mView.findViewById<ImageView>(R.id.removeAll).visibility=View.VISIBLE
    }
    private fun initRecyclerView(listOfAllScanResults: List<ScanResult>)
    {
        mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView).layoutManager=LinearLayoutManager(requireContext())
        mView.findViewById<RecyclerView>(R.id.scannedHistoryRecyclerView).adapter=ScanResultsListAdapter(dbHelper,requireContext(),listOfAllScanResults.toMutableList())
        showRecyclerView()
    }
}


//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment RecentFragments.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            RecentFragments().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}