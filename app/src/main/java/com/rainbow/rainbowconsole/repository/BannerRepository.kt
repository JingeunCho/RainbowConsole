package com.rainbow.rainbowconsole.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Transaction
import com.rainbow.rainbowconsole.model.BannerDTO
import kotlinx.coroutines.Deferred

interface BannerRepository {
    fun getBannerList() : CollectionReference?
    fun getBanner(documentId : String) : Deferred<BannerDTO?>
    fun addBanner(banner : BannerDTO,documentId: String) : Task<Void>
    fun deleteBanner(documentId : String) : Task<Transaction>
    fun editBanner(banner : BannerDTO, documentId : String) : Task<Transaction>
}