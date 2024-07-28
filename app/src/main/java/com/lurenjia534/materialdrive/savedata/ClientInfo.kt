package com.lurenjia534.materialdrive.savedata

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

// ClientInfo.kt
@Entity(tableName = "client_info")
data class ClientInfo(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "client_id") val clientId: String,
    @ColumnInfo(name = "client_secret") val clientSecret: String,
    @ColumnInfo(name = "tenant_id") val tenantId: String,
    @ColumnInfo(name = "user_id") val userId: String
)

// ClientInfoDao.kt
@Dao
interface ClientInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(clientInfo: ClientInfo)

    @Query("SELECT * FROM client_info WHERE id = :id")
    suspend fun getClientInfo(id: Int): ClientInfo?

    @Update
    suspend fun update(clientInfo: ClientInfo)

    @Delete
    suspend fun delete(clientInfo: ClientInfo)
}
