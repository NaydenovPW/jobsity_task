package pw.naydenov.jobsity_task.features.tv_shows_listing

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pw.naydenov.jobsity_task.R

class TvShowsListingAdapter(var tvShows: List<TvShow>, val listener: TvShowClickListener) :
    RecyclerView.Adapter<TvShowsListingAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TvShowViewHolder {
        val view = TvShowViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.view_holder_tv_show, viewGroup, false)
        )
        return view
    }

    override fun onBindViewHolder(viewholder: TvShowViewHolder, i: Int) {
        viewholder.setContent(tvShows[i])
        viewholder.itemView.setOnClickListener { listener.onItemClick(tvShows[i].id) }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: AppCompatTextView = itemView.findViewById(R.id.view_holder_tv_show_name)
        var image: AppCompatImageView = itemView.findViewById(R.id.view_holder_tv_show_image)

        fun setContent(tvShow: TvShow) {
            name.text = tvShow.name
            tvShow.image?.let {
                Glide
                    .with(itemView.context)
                    .load(tvShow.image.medium)
                    .error(R.drawable.no_image)
                    .into(image)
            }
        }
    }

    fun addItems(moreShows: List<TvShow>, isAppend: Boolean) {
        if (isAppend) {
            Log.e("TAG", "addItems: APPEND")
            val oldSize = tvShows.size
            (tvShows as ArrayList).addAll(moreShows)
            val newSize = tvShows.size
            notifyItemRangeChanged(oldSize, newSize)
        } else {
            Log.e("TAG", "addItems: REPLACE [${moreShows.size}]")
            tvShows = moreShows
            notifyDataSetChanged()
        }
    }

    interface TvShowClickListener {
        fun onItemClick(id: Int)
    }

}