package edu.uw.viewpager


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment() {

    private var callback: OnSearchListener? = null

    internal interface OnSearchListener {
        fun onSearchSubmitted(searchTerm: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            callback = context as OnSearchListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement OnSearchListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_search, container, false)

        val searchButton = rootView.findViewById<Button>(R.id.btn_search)
        searchButton.setOnClickListener {
            val text = rootView.findViewById<View>(R.id.txt_search) as EditText
            val searchTerm = text.text.toString()
            callback!!.onSearchSubmitted(searchTerm)
        }

        return rootView
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    companion object {
        fun newInstance(): SearchFragment {
            val args = Bundle()
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }



}
