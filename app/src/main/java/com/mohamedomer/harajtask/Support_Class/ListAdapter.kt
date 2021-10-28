package com.mohamedomer.harajtask.Support_Class

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mohamedomer.harajtask.R

class ListAdapter(private val activityContext: Context, list: List<CarsModel>) :
    ArrayAdapter<CarsModel?>(
        activityContext,
        R.layout.activity_main,
        list
    ) {
    private val list: List<CarsModel>

    @SuppressLint("InflateParams")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val viewHolder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(activityContext).inflate(R.layout.cars_view, null)
            viewHolder = ViewHolder()
            viewHolder.cartitle = view!!.findViewById<View>(R.id.carName) as TextView?
            viewHolder.carHoldername = view.findViewById<View>(R.id.carHolderName) as TextView?
            viewHolder.carDate = view.findViewById<View>(R.id.carUpdateTime) as TextView?
            viewHolder.carImage = view.findViewById<View>(R.id.carImage) as ImageView?
            viewHolder.comment = view.findViewById<View>(R.id.carComments) as TextView?
            viewHolder.city = view.findViewById<View>(R.id.carLocation) as TextView?


            viewHolder.cartitle?.text = list[position].title
            viewHolder.carHoldername?.text = list[position].username
            viewHolder.carDate?.text = list[position].date
            viewHolder.comment?.text = list[position].commentCount.toString()
            viewHolder.carLocation?.text = list[position].city
            viewHolder.city?.text = list[position].city
            try {
                Glide.with(activityContext).load(list[position].thumbURL)
                    .error(R.drawable.ic_user).into(viewHolder.carImage!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            //  viewHolder.CheckStatus.setText(list.get(position).getCheckStatus());
            view.setTag(viewHolder)
        } else {
            viewHolder = view.tag as ViewHolder
        }
        return view
    }

    internal inner class ViewHolder {
        var cartitle: TextView? = null
        var carHoldername: TextView? = null
        var comment: TextView? = null
        var carLocation: TextView? = null
        var carDate: TextView? = null
        var textBody : String? = null
        var city : TextView? = null
        var carImage : ImageView?= null
    }

    companion object {
        const val TAG = "ListView"
    }

    init {
        this.list = list
    }
}