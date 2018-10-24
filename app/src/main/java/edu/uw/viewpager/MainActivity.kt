package edu.uw.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity(), MovieListFragment.OnMovieSelectedListener, SearchFragment.OnSearchListener {

    private var searchFragment: SearchFragment? = null
    private var listFragment: MovieListFragment? = null
    private var detailFragment: DetailFragment? = null
    private var viewPager: ViewPager? = null
    private var pagerAdapter: PagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSearchSubmitted(searchTerm: String) {
        listFragment = MovieListFragment.newInstance(searchTerm)
        pagerAdapter!!.notifyDataSetChanged()
        viewPager!!.currentItem = 1 //hard-code the shift
    }

    //respond to search button clicking
    fun handleSearchClick(v: View) {
        val text = findViewById<View>(R.id.txt_search) as EditText
        val searchTerm = text.text.toString()

        val fragment = MovieListFragment.newInstance(searchTerm)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment, MOVIE_LIST_FRAGMENT_TAG)
        ft.addToBackStack(null)
        ft.commit()
    }

    override fun onMovieSelected(movie: Movie) {
        val fragment = DetailFragment.newInstance(movie)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment, MOVIE_DETAIL_FRAGMENT_TAG)
        ft.addToBackStack(null) //remember for the back button
        ft.commit()
    }
    private inner class MoviePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            //Hard-code the ordering as an example
            if (position == 0) return searchFragment
            if (position == 1) return listFragment
            return if (position == 2) detailFragment else null
            //just in case
        }

        //work-around for Fragment replacement
        override fun getItemPosition(`object`: Any?): Int {
            return PagerAdapter.POSITION_NONE
        }

        override fun getCount(): Int {
            return if (listFragment == null) {
                1
            } else if (detailFragment == null) {
                2
            } else {
                3
            }
        }
    }

    override fun onBackPressed() {
        if (viewPager!!.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager!!.currentItem = viewPager!!.currentItem - 1
        }
    }

    companion object {

        private val TAG = "MainActivity"
        val MOVIE_LIST_FRAGMENT_TAG = "MoviesListFragment"
        val MOVIE_DETAIL_FRAGMENT_TAG = "DetailFragment"
    }
}
