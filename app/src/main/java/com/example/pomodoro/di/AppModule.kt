package com.example.pomodoro.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.pomodoro.data.datastore.Abstract
import com.example.pomodoro.data.datastore.SettingsManagerImpl
import com.example.pomodoro.data.roomdatabase.DurationDao
import com.example.pomodoro.data.roomdatabase.DurationDatabase
import com.example.pomodoro.data.auth_data.AuthData
import com.example.pomodoro.data.auth_data.AuthDataImpl
import com.example.pomodoro.repository.PomodoroRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDurationDao(durationDatabase: DurationDatabase): DurationDao
            = durationDatabase.durationDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): DurationDatabase
            = Room.databaseBuilder(
        context,
        DurationDatabase::class.java,
        "duration_db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ), produceFile = { context.preferencesDataStoreFile("user_settings") }
        )
    }

    @Provides
    fun provideUserPref(dataStore: DataStore<Preferences>): Abstract = SettingsManagerImpl(dataStore)

    @Provides
    @Singleton
    fun providesFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthImpl(firebaseAuth: FirebaseAuth): AuthData {
        return AuthDataImpl(firebaseAuth = firebaseAuth)
    }
}