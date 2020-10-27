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

package com.ran.mall.entity.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.ran.mall.entity.db.converter.DateConverter;
import com.ran.mall.entity.db.dao.HistoryReportDao;
import com.ran.mall.entity.db.dao.LocalPhotoDao;
import com.ran.mall.entity.db.dao.PhotoDao;
import com.ran.mall.entity.db.dao.WorkOrderDao;
import com.ran.mall.entity.db.entity.HistoryReportEntity;
import com.ran.mall.entity.db.entity.LocalPhotoEntity;
import com.ran.mall.entity.db.entity.PhotoEntity;
import com.ran.mall.entity.db.entity.WorkOrderEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@Database(entities = {WorkOrderEntity.class, PhotoEntity.class, HistoryReportEntity.class, LocalPhotoEntity.class}, version = 4)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "txt-app-db";

    public abstract WorkOrderDao workOrderDao();

    public abstract PhotoDao photoDao();

    public abstract LocalPhotoDao localPhotoDao();

    public abstract HistoryReportDao HistoryReportDao();


    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context);
                }
            }
        }
        return sInstance;
    }


    @NonNull
    private static AppDatabase buildDatabase(final Context appContext) {
        //addMigrations(MIGRATION_1_2)
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration()
                .build();
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    //当有新工单 待上传
    public void addWorkOrder(DatabaseCallback databaseCallback, WorkOrderEntity... mWorkOrderEntits) {
        Completable.fromAction(() -> sInstance.workOrderDao().insertAll(mWorkOrderEntits))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onAdded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }

    //当有新工单 待上传 记录该工单的照片
    public void addLocalPhoto(DatabaseCallback databaseCallback, LocalPhotoEntity... photoEntity) {
        Completable.fromAction(() -> sInstance.localPhotoDao().insertAll(photoEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onAddPhotoed();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }

    //当有新工单 待上传 记录该工单的照片
    public void addPhoto(DatabaseCallback databaseCallback, PhotoEntity... photoEntity) {
        Completable.fromAction(() -> sInstance.photoDao().insertAll(photoEntity))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onAddPhotoed();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }

    //通过flowId,获取数据库中对应的新工单
    public void getWorkOrder(DatabaseCallback databaseCallback, String flowId) {
        sInstance.workOrderDao()
                .loadWorkorder(flowId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<WorkOrderEntity>() {
                    @Override
                    public void accept(WorkOrderEntity workOrderEntity) throws Exception {
                        databaseCallback.onLoadWorkEntits(workOrderEntity);
                    }
                });
    }

    public void getUserNameWorkOrder(DatabaseCallback databaseCallback, String userName) {
        sInstance.workOrderDao()
                .loadUserNameWorkorder(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<WorkOrderEntity>>() {
                    @Override
                    public void accept(List<WorkOrderEntity> workOrderEntites) throws Exception {
                        databaseCallback.onLoadUserNameWorkEntits(workOrderEntites);
                    }
                });
    }

    public void getLocalPhotoPathList(LocalPhotoDatabaseCallback databaseCallback) {
        sInstance.localPhotoDao()
                .loadPhotoAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<LocalPhotoEntity>>() {
                    @Override
                    public void accept(List<LocalPhotoEntity> localPhotoEntity) throws Exception {
                        databaseCallback.onLoadPhotoPath(localPhotoEntity);
                    }
                });
    }

    public void deleteLocalPhotoByPath(LocalPhotoDatabaseCallback databaseCallback, String path) {
        Completable.fromAction(() -> sInstance.localPhotoDao().delete(path))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onDeletePhoto();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }

    //通过flowId,获取数据库中对应的新工单
    public void getPhotoPathList(DatabaseCallback databaseCallback, String flowId) {
        sInstance.photoDao()
                .loadPhotoSync(flowId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<PhotoEntity>>() {
                    @Override
                    public void accept(List<PhotoEntity> photoEntities) throws Exception {
                        databaseCallback.onLoadPhotoPath(photoEntities);
                    }
                });
    }

    //通过flowId, carNumber获取数据库中对应的车损照
    public void loadPhotoByIdCarnumberCarSync(DatabaseCallback databaseCallback, String flowId, String carNumber) {
        sInstance.photoDao()
                .loadPhotoByIdCarnumberCarSync(flowId, carNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<PhotoEntity>>() {
                    @Override
                    public void accept(List<PhotoEntity> photoEntities) throws Exception {
                        databaseCallback.onLoadFlowCarNumberCarpicsPhotoPath(photoEntities);
                    }
                });
    }

    //通过userName,获取数据库中对应的新工单
    public void getUserNamePhotoPathList(DatabaseCallback databaseCallback, String userName) {
        sInstance.photoDao()
                .loadUserNamePhoto(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<PhotoEntity>>() {
                    @Override
                    public void accept(List<PhotoEntity> photoEntities) throws Exception {
                        databaseCallback.onLoadPhotoPath(photoEntities);
                    }
                });
    }

    //通过flowId,获取数据库中所有新工单
    public void getAllPhotoPathList(DatabaseCallback databaseCallback) {
        sInstance.photoDao()
                .loadPhotoAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<PhotoEntity>>() {
                    @Override
                    public void accept(List<PhotoEntity> photoEntities) throws Exception {
                        databaseCallback.onLoadPhotoPath(photoEntities);
                    }
                });
    }

    //当有新工单 删除
    public void deleteWorkOrder(DatabaseCallback databaseCallback, WorkOrderEntity workOrderEntits) {
        Completable.fromAction(() -> sInstance.workOrderDao().delete(workOrderEntits))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onDeletedWorkOrder();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }


    //上传完成 删除对应工单
    public void deleteWorkOrder(DatabaseCallback databaseCallback, String flowId) {
        Completable.fromAction(() -> sInstance.workOrderDao().delete(flowId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onDeletedWorkOrder();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }

    //上传完成 删除照片
    public void deletePhoto(DatabaseCallback databaseCallback, String systemtime) {
        Completable.fromAction(() -> sInstance.photoDao().delete(systemtime))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onDeletePhoto();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }

    //上传完成 更新照片
    public void updatePhoto(DatabaseCallback databaseCallback, int id, String success) {
        Completable.fromAction(() -> sInstance.photoDao().update(id, success))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onUpDate();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }

    //获取数据库的全部历史记录
    public void getAllHistoryReport(DatabaseCallback1 databaseCallback) {
        sInstance.HistoryReportDao()
                .queryHistoryReport()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<HistoryReportEntity>>() {
                    @Override
                    public void accept(List<HistoryReportEntity> entities) throws Exception {
                        databaseCallback.getHistoryReport(entities);
                    }
                });
    }

    //当有新工单 待上传
    public void addHistoryReport(DatabaseCallback1 databaseCallback, HistoryReportEntity... historyReport) {
        Completable.fromAction(() -> sInstance.HistoryReportDao().insertAll(historyReport))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        databaseCallback.onAdded();
                    }

                    @Override
                    public void onError(Throwable e) {
                        databaseCallback.onError(e.getMessage());
                    }
                });

    }

}
