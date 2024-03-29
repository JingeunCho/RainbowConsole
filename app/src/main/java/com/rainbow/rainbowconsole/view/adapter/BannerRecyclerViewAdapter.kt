package com.rainbow.rainbowconsole.view.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rainbow.rainbowconsole.config.AppConfig
import com.rainbow.rainbowconsole.model.controller.BannerController
import com.rainbow.rainbowconsole.databinding.DesignBannerItemBinding
import com.rainbow.rainbowconsole.model.data_class.BannerDTO
import com.rainbow.rainbowconsole.view_model.fragment.BannerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("NotifyDataSetChanged")
class BannerRecyclerViewAdapter(private val bannerItems : ArrayList<BannerDTO>, private val bannerIds : ArrayList<String>, private val bannerViewModel: BannerViewModel) : RecyclerView.Adapter<BannerRecyclerViewAdapter.ViewHolder>(){

    private val editBannerDialogFragment = AppConfig.editBannerDialogFragment

    inner class ViewHolder(val binding : DesignBannerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerRecyclerViewAdapter.ViewHolder {
        val view = DesignBannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerRecyclerViewAdapter.ViewHolder, position: Int) {
        val data = bannerItems[position]
        val documentId = bannerIds[position]
        val binding = holder.binding
        initView(data, documentId, binding, position)

    }

    override fun getItemCount(): Int = bannerItems.size

    private fun initView(bannerItem : BannerDTO, documentId : String, binding : DesignBannerItemBinding, position: Int){
        binding.textDescription.text = bannerItem.description
        binding.btnEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", 1)
            bundle.putString("documentId", documentId)
            editBannerDialogFragment.arguments = bundle
            editBannerDialogFragment.show((binding.root.context  as AppCompatActivity).supportFragmentManager, "editDialog")


        }
        binding.btnDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(binding.root.context)
            dialog.setTitle("배너 삭제")
                .setMessage("해당 배너를 삭제하시겠습니까?")
                .setPositiveButton("삭제"){ _, _ ->
                    bannerViewModel.deleteBanner(documentId)
                        .addOnFailureListener {
                            Snackbar.make(binding.root, "삭제에 실패 했습니다 잠시 후 다시 시도해주세요", Snackbar.LENGTH_SHORT).show()
                        }

                }
                .setNegativeButton("취소"){_, _ ->

                }
        }

    }

}