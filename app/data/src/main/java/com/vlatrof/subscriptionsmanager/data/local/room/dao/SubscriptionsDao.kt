package com.vlatrof.subscriptionsmanager.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vlatrof.subscriptionsmanager.data.local.room.entities.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionsDao {

    @Query("SELECT * FROM subscriptions")
    fun getAllSubscriptions(): Flow<List<SubscriptionEntity>>

    @Query("DELETE FROM subscriptions")
    fun deleteAllSubscriptions()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(subscription: SubscriptionEntity)

}