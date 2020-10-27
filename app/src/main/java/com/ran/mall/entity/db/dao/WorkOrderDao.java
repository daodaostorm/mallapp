/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ran.mall.entity.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ran.mall.entity.db.entity.WorkOrderEntity;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface WorkOrderDao {


    @Insert
    void insertAll(WorkOrderEntity... mWorkOrderEntits);

    //通过flowId查找该B端下是否有该flowId
    @Query("select * from workorder where flowId = :flowId")
    Maybe<WorkOrderEntity> loadWorkorder(String flowId);

    //通过userName查找该B端下的flowId数组
    @Query("select * from workorder where userName = :userName")
    Maybe<List<WorkOrderEntity>> loadUserNameWorkorder(String userName);




    @Delete()
    void delete(WorkOrderEntity workOrderEntits);


    @Query("DELETE FROM workorder where flowId = :flowId")
    void delete(String flowId);


}
