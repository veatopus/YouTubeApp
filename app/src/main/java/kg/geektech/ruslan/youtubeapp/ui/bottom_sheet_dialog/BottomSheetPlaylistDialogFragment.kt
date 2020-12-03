package kg.geektech.ruslan.youtubeapp.ui.bottom_sheet_dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kg.geektech.ruslan.youtubeapp.R
import kg.geektech.ruslan.youtubeapp.core.BaseAdapter
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsItem
import kg.geektech.ruslan.youtubeapp.data.models.detailPlaylist.DetailsPlaylist
import kg.geektech.ruslan.youtubeapp.databinding.BottomSheetPlaylistListBinding
import kg.geektech.ruslan.youtubeapp.ui.bottom_sheet_dialog.adapter.BottomSheetAdapter

class BottomSheetPlaylistDialogFragment(private val detailsPlaylist: DetailsPlaylist, private val listener: BaseAdapter.IBaseAdapterClickListener<DetailsItem>) : BottomSheetDialogFragment() {

    private var binding: BottomSheetPlaylistListBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val bottomSheetView: View = View.inflate(context, R.layout.bottom_sheet_playlist_list, null)
        dialog.setContentView(bottomSheetView)

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        bottomSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        binding = BottomSheetPlaylistListBinding.bind(bottomSheetView)
        val adapter = BottomSheetAdapter()
        binding!!.recyclerView.adapter = adapter
        adapter.data = detailsPlaylist.items

        adapter.listener = object : BaseAdapter.IBaseAdapterClickListener<DetailsItem>{
            override fun onClick(model: DetailsItem) {
                adapter.data.forEach { it.isChange = false }
                model.isChange = true
                adapter.notifyDataSetChanged()
                listener.onClick(model)
            }
        }

        return dialog
    }
}