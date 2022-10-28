package com.rebirth.unitfy.di.modules

import android.content.Context
import androidx.room.Room
import com.rebirth.unitfy.domain.UnitfyDatabase
import com.rebirth.unitfy.domain.daos.ConvertioUnitDao
import com.rebirth.unitfy.domain.daos.MutationDao
import com.rebirth.unitfy.domain.daos.UnitClassificationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UnitfyDatabase {
        return Room
            .databaseBuilder(context, UnitfyDatabase::class.java, "unitfy-database")
            .createFromAsset("database/unitfy.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideUnitClassificationDao(unitfyDatabase: UnitfyDatabase): UnitClassificationDao {
        return unitfyDatabase.unitClassificationDao()
    }

    @Provides
    @Singleton
    fun provideConvertioUnitDao(unitfyDatabase: UnitfyDatabase): ConvertioUnitDao {
        return unitfyDatabase.convertioUnitDao()
    }

    @Provides
    @Singleton
    fun provideMutationDao(unitfyDatabase: UnitfyDatabase): MutationDao {
        return unitfyDatabase.mutationDao()
    }

}