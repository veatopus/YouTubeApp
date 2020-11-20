package kg.geektech.ruslan.youtubeapp.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    private val holderLayoutId: Int,
    var data: MutableList<T>
) :
    RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {

    var listener: IBaseAdapterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(holderLayoutId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    abstract fun onBind(view: View, model: T)

    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(model: T) {
            onBind(itemView, model)

            itemView.setOnClickListener {
                listener?.onClick(adapterPosition)
            }
        }
    }

    interface IBaseAdapterClickListener {
        fun onClick(pos: Int)
    }
}