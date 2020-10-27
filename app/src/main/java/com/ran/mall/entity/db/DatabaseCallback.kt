package com.ran.mall.entity.db

import com.ran.mall.entity.db.entity.PhotoEntity
import com.ran.mall.entity.db.entity.WorkOrderEntity


/**
 * Created by Justin on 2018/8/23/023 14:14.
 * email：WjqJustin@163.com
 * effect：
 */
interface DatabaseCallback {

    fun onAdded()

    fun onAddPhotoed()

    fun onDeletedWorkOrder()

    fun onDeletePhoto()

    fun onUpDate()

    fun onError(err: String)

    fun onQuery()

    fun onLoadWorkEntits(workOrders: WorkOrderEntity)

    fun onLoadUserNameWorkEntits(workOrders:  List<WorkOrderEntity>)

    fun onLoadPhotoPath(paths: List<PhotoEntity>)

    fun onLoadFlowCarNumberCarpicsPhotoPath(paths: List<PhotoEntity>)

}