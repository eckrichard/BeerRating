package hu.bme.aut.android.hf.beerrating.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hf.beerrating.data.model.Reviews
import hu.bme.aut.android.hf.beerrating.databinding.ReviewListBinding

class ReviewAdapter(private val listener: ReviewClickListener) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val reviews = mutableListOf<Reviews>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewViewHolder (
        ReviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        holder.binding.tvBeerName.text = review.beer.name
        holder.binding.tvALCVOL.text = "${review.beer.alc} %"
        holder.binding.tvLastReviewDate.text = review.lastChange.toString()
        holder.binding.tvBeerCategory.text = review.beer.category.name
        holder.binding.tvRating.text = review.rating.toString()
        holder.binding.tvBreweryName.text = review.beer.brewery.name

        holder.binding.listItem.setOnClickListener {
            listener.openReview(review)
        }

        holder.binding.ibModify.setOnClickListener {
            listener.openUpdate(review)
        }

        holder.binding.ibRemove.setOnClickListener {
            delete(review)
            listener.onItemDeleted(review, it)
        }
    }

    private fun delete(review: Reviews) {
        var idx: Int = reviews.indexOf(review)
        reviews.remove(review)
        notifyItemRemoved(idx)
    }

    fun update(reviewListItem: List<Reviews>) {
        reviews.clear()
        reviews.addAll(reviewListItem)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = reviews.size

    interface ReviewClickListener {
        fun onItemDeleted(item: Reviews, view: View)
        fun openReview(item: Reviews)
        fun openUpdate(item: Reviews)
    }

    inner class ReviewViewHolder(val binding: ReviewListBinding) : RecyclerView.ViewHolder(binding.root)
}