package com.network.shopmanager.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.network.shopmanager.data.models.Shop

@Database(
    entities = [
        Shop::class
    ],
    version = 1, exportSchema = false
)
abstract class MyRoom : RoomDatabase() {
    abstract fun daoShop(): DaoShop

    companion object {
        private var instance: MyRoom? = null

        @Synchronized
        fun getInstance(ctx: Context): MyRoom {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        ctx,
                        MyRoom::class.java,
                        "db_shop_manager"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        // .addCallback(roomCallBack)
                        .build()
            }

            return instance!!
        }
    }

}