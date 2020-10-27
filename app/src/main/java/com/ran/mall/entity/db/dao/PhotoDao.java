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
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ran.mall.entity.db.entity.PhotoEntity;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface PhotoDao {

    @Insert
    void insertAll(PhotoEntity... mPhotoEntity);

    @Query("SELECT * FROM photo where photo_flowId = :flowId")
    Maybe<List<PhotoEntity>> loadPhotoSync(String flowId);

    @Query("SELECT * FROM photo where photo_flowId = :flowId and photo_carnumber = :carNumber")
    Maybe<List<PhotoEntity>> loadPhotoByIdCarnumberCarSync(String flowId, String carNumber);

    @Query("SELECT * FROM photo where userName = :userName")
    Maybe<List<PhotoEntity>> loadUserNamePhoto(String userName);

    @Query("SELECT * FROM photo")
    Maybe<List<PhotoEntity>> loadPhotoAll();


    @Query("DELETE FROM photo where photo_systemTime = :systemtime")
    void delete(String systemtime);

    @Query("UPDATE photo SET photo_success = :success where id = :id")
    void update(int id, String success);


}
