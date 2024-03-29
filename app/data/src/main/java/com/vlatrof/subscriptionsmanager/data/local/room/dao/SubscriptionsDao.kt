package com.vlatrof.subscriptionsmanager.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vlatrof.subscriptionsmanager.data.local.room.entities.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionsDao {

    @Query("SELECT * FROM subscriptions")
    fun getAll(): List<SubscriptionEntity>

    @Query("SELECT * FROM subscriptions")
    fun getAllFlow(): Flow<List<SubscriptionEntity>>

    @Query("SELECT * FROM subscriptions WHERE id=:id ")
    fun getById(id: Int): SubscriptionEntity

    @Query("DELETE FROM subscriptions")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(subscription: SubscriptionEntity)

    @Query("DELETE FROM subscriptions WHERE id = :id")
    fun deleteById(id: Int)

    @Update
    fun update(subscription: SubscriptionEntity)
}
