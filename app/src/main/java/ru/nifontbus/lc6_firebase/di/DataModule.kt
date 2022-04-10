package ru.nifontbus.lc6_firebase.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.nifontbus.lc6_firebase.repository.FirebaseRepo
import ru.nifontbus.lc6_firebase.repository.FirebaseRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideEventsRepository(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore
    ): FirebaseRepo = FirebaseRepoImpl(
        context = context,
        firestore = firestore
    )

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

}