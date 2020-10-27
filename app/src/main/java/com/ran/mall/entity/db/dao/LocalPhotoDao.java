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

import com.ran.mall.entity.db.entity.LocalPhotoEntity;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface LocalPhotoDao {

    @Insert
    void insertAll(LocalPhotoEntity... mPhotoEntity);


    @Query("SELECT * FROM localphoto order by photo_systemTime desc")
    Maybe<List<LocalPhotoEntity>> loadPhotoAll();

    @Query("DELETE FROM localphoto where photo_path = :path")
    void delete(String path);



}
