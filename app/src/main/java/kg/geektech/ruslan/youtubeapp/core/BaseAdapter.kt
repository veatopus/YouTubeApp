package kg.geektech.ruslan.youtubeapp.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T>(
    private val holderLayoutId: Int,
    var data: MutableList<T>,
    val baseAdapterCallBack: IBaseAdapterCallBack<T>
) :
    RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {

    private var listener: IBaseAdapterClickListener? = null

    fun setListener(listener: IBaseAdapterClickListener) {
        this.listener = listener
    }

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

    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(model: T) {
            baseAdapterCallBack.onBind(itemView, model)

            itemView.setOnClickListener {
                listener?.onClick(adapterPosition)
            }
        }
    }

    interface IBaseAdapterCallBack<T> {
        fun onBind(view: View, model: T)
    }

    interface IBaseAdapterClickListener {
        fun onClick(pos: Int)
    }
}