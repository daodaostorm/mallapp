package com.ran.mall.entity

import com.ran.mall.entity.db.DatabaseCallback
import com.ran.mall.entity.db.entity.PhotoEntity
import com.ran.mall.entity.db.entity.WorkOrderEntity

/**
 * Created by Justin on 2018/11/20/020 18:28.
 * email：WjqJustin@163.com
 * effect：
 */
 open class DataBaseImp :DatabaseCallback {
   override fun onLoadFlowCarNumberCarpicsPhotoPath(paths: List<PhotoEntity>) {

   }

   override fun onAdded() {
    }

    override fun onAddPhotoed() {
    }

    override fun onDeletedWorkOrder() {
    }

    override fun onDeletePhoto() {
    }

    override fun onUpDate() {
    }

    override fun onError(err: String) {
    }

    override fun onQuery() {
    }

    override fun onLoadWorkEntits(workOrders: WorkOrderEntity) {
    }

    override fun onLoadUserNameWorkEntits(workOrders: List<WorkOrderEntity>) {
    }

    override fun onLoadPhotoPath(paths: List<PhotoEntity>) {
    }

}