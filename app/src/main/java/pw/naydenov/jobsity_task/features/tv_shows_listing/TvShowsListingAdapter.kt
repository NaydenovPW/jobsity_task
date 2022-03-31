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
import pw.naydenov.jobsity_task.network.pojo.TvShow

class TvShowsListingAdapter(var tvShows: List<TvShow>, val listener: TvShowClickListener) :
    RecyclerView.Adapter<TvShowsListingAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TvShowViewHolder {
        val view = TvShowViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.view_holder_tv_show, viewGroup, false)
        )
        return view
    }

    override fun onBindViewHolder(viewholder: TvShowViewHolder, i: Int) {
        viewholder.setContent(tvShows[i], listener)
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: AppCompatTextView = itemView.findViewById(R.id.view_holder_tv_show_name)
        var image: AppCompatImageView = itemView.findViewById(R.id.view_holder_tv_show_image)

        fun setContent(tvShow: TvShow, listener: TvShowClickListener) {
            name.text = tvShow.name
            tvShow.image?.let {
                Glide
                    .with(itemView.context)
                    .load(tvShow.image.medium)
                    .error(R.drawable.no_image)
                    .into(image)
            }
            itemView.setOnClickListener { listener.onItemClick(tvShow) }
        }
    }

    fun addItems(moreShows: List<TvShow>, isAppend: Boolean) {
        if (isAppend) {
            val oldSize = tvShows.size
            (tvShows as ArrayList).addAll(moreShows)
            val newSize = tvShows.size
            notifyItemRangeChanged(oldSize, newSize)
        } else {
            tvShows = moreShows
            notifyDataSetChanged()
        }
    }

    interface TvShowClickListener {
        fun onItemClick(tvShow: TvShow)
    }

}