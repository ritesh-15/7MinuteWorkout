package com.example.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkout.databinding.ItemExerciseStatusBinding

// recycle view adapter
class ExerciseStatusAdapter(val items:ArrayList<ExerciseModal>):RecyclerView.Adapter<
        ExerciseStatusAdapter.ViewHolder
        >() {

          inner  class ViewHolder(binding:ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root){
                val tvItem = binding.tvItem

            }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model :ExerciseModal = items[position]
        holder.tvItem.text = model.getId().toString()

        when{
            model.getIsSelected() -> {
                holder.tvItem.background = ContextCompat.
                getDrawable(holder.itemView.context,R.drawable.item_ciruclar_thing_color_backgound)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))

            }
            model.getIsCompleted() -> {
                holder.tvItem.background = ContextCompat.
                getDrawable(holder.itemView.context,R.drawable.item_circular_color_background)
                holder.tvItem.setTextColor(Color.parseColor("#ffffff"))

            }
            else -> {

                holder.tvItem.background = ContextCompat.
                getDrawable(holder.itemView.context,R.drawable.item_ciruclar_gray_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,
            false
            ))
    }

    override fun getItemCount(): Int {
        return items.size
    }

}