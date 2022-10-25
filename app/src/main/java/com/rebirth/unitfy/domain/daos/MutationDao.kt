package com.rebirth.unitfy.domain.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rebirth.unitfy.domain.entities.Mutation
import com.rebirth.unitfy.domain.entities.MutationWithUnits

@Dao
interface MutationDao {

    @Query("SELECT * FROM mutation")
    suspend fun fetchAll(): List<Mutation>

    @Insert
    suspend fun create(vararg mutation: Mutation)


    @Transaction
    @Query("SELECT * from mutation m0 WHERE m0.convertion_unit_id = :cui0 and m0.invertion_unit_id = :iui0")
    suspend fun fetchByUnits(cui0: Long, iui0: Long): MutationWithUnits


    @Query("SELECT * from mutation m0 WHERE m0.convertion_unit_id = :cui0 and m0.invertion_unit_id = :iui0")
    suspend fun fetchMutationByUnits(cui0: Long, iui0: Long): Mutation

}