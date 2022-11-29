package com.network.shopmanager.data.room

import androidx.room.*
import com.network.shopmanager.data.models.Shop
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface DaoShop {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShop(shop: Shop): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShops(shops: List<Shop>): Single<List<Long>>

    @Update
    fun updateShop(shop: Shop): Single<Int>

    @Delete
    fun deleteShop(shop: Shop): Single<Int>

    @Query("select * from shops")
    fun getShops(): Flowable<List<Shop>>

    @Query("select date from shops order by date desc limit 1")
    fun getShopsLastDate(): Long

    @Query("select count(*) from shops")
    fun getShopsCount(): Int

    @Query("select * from shops where id=:id")
    fun getShopById(id: String): Shop

    @Query("DELETE FROM shops WHERE id = :id")
    fun deleteShopById(id: String): Single<Int>
}

