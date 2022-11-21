package mn.turbo.marvel.presenter.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mn.turbo.marvel.R
import mn.turbo.marvel.common.setImageUrl
import mn.turbo.marvel.databinding.ItemRelatedMovieBinding
import mn.turbo.marvel.domain.model.RelatedMovie

class RelatedAdapter(
    private val deviceWidth: Int,
    private val onClick: (movie: RelatedMovie) -> Unit = {}
) : ListAdapter<RelatedMovie, RelatedAdapter.ViewHolder>(RelatedMovieDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RelatedAdapter.ViewHolder {
        val dp12 = parent.context.resources.getDimensionPixelSize(R.dimen.margin_12dp)

        val itemWidth = deviceWidth / 4

        val params = FrameLayout.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, dp12, dp12)

        val binding: ItemRelatedMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_related_movie,
            parent,
            false
        )

        binding.root.layoutParams = params
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RelatedAdapter.ViewHolder, position: Int) {
        getItem(position).let { relatedMovie ->
            holder.binding.apply {
                imageView.setImageUrl(relatedMovie.coverUrl)

                root.setOnClickListener {
                    onClick.invoke(relatedMovie)
                }
            }
        }
    }

    inner class ViewHolder(
        val binding: ItemRelatedMovieBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

class RelatedMovieDiffUtil : DiffUtil.ItemCallback<RelatedMovie>() {
    override fun areItemsTheSame(oldItem: RelatedMovie, newItem: RelatedMovie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RelatedMovie, newItem: RelatedMovie) =
        oldItem == newItem
}