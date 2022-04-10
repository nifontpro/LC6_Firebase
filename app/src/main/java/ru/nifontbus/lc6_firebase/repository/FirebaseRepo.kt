package ru.nifontbus.lc6_firebase.repository

import kotlinx.coroutines.flow.StateFlow
import ru.nifontbus.lc6_firebase.model.Bio
import ru.nifontbus.lc6_firebase.model.Resource

interface FirebaseRepo {

    val bioList: StateFlow<List<Bio>>

    suspend fun addBio(bio: Bio): Resource<Unit>

    suspend fun deleteBio(id: String): Resource<Unit>

}