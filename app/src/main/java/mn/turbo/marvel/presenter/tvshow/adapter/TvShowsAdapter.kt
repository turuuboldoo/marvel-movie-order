package mn.turbo.marvel.presenter.tvshow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mn.turbo.marvel.R
import mn.turbo.marvel.common.setImageUrl
import mn.turbo.marvel.databinding.ItemTvShowBinding
import mn.turbo.marvel.domain.model.TvShow

class TvShowsAdapter(
    private val onClick: (tvShow: TvShow) -> Unit = {}
) : ListAdapter<TvShow, TvShowsAdapter.TvShowViewHolder>(TvShowDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val listItemBinding: ItemTvShowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_tv_show,
            parent,
            false
        )

        return TvShowViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        getItem(position)?.let { tvShow ->
            holder.binding.apply {
                this.tvShow = tvShow

                posterImageView.setImageUrl(tvShow.coverUrl)

                root.setOnClickListener {
                    onClick.invoke(tvShow)
                }
            }
        }
    }

    class TvShowViewHolder(val binding: ItemTvShowBinding) : RecyclerView.ViewHolder(binding.root)
}

class TvShowDiffUtil : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) =
        newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) =
        oldItem == newItem
}
