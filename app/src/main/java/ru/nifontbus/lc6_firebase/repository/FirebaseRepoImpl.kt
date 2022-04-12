package ru.nifontbus.lc6_firebase.repository

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ru.nifontbus.lc6_firebase.R
import ru.nifontbus.lc6_firebase.model.Bio
import ru.nifontbus.lc6_firebase.model.Resource

class FirebaseRepoImpl(
    private val context: Context,
    firestore: FirebaseFirestore
) : FirebaseRepo {

    //    private val db: FirebaseFirestore by lazy { Firebase.firestore }
    private val bioCollection = firestore.collection(BIO_COLLECTION)

    private val _bioList = MutableStateFlow<List<Bio>>(emptyList())
    override val bioList = _bioList.asStateFlow()

    init {
        subscribeToBioDataUpdates()
    }

    private fun subscribeToBioDataUpdates() = CoroutineScope(Dispatchers.Default).launch {
        bioCollection.addSnapshotListener { querySnapshot, error ->
            error?.let {
                return@addSnapshotListener
            }
            querySnapshot?.let { snapshot ->
                val newBioList = mutableListOf<Bio>()
                for (document in snapshot) {
                    val bio = documentToBio(document)
                    bio?.let {
                        newBioList.add(it)
                    }
                }
                _bioList.value = newBioList
            }
        }
    }

    override suspend fun addBio(bio: Bio): Resource<Unit> {
        return try {
            bioCollection.add(bioToMap(bio)).await()
            Resource.Success(context.getString(R.string.sAddBioOk))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.sAddBioError))
        }
    }

    override suspend fun deleteBio(id: String): Resource<Unit> {
        return try {
            bioCollection.document(id).delete().await()
            Resource.Success(context.getString(R.string.sDelBioOk))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: context.getString(R.string.sDelBioError))
        }
    }

    /**
     * Безопасное чтение из БД и преобразование в объект
     */
    private fun documentToBio(document: QueryDocumentSnapshot): Bio? {
        return try {
            Bio(
                date = document.get(DATE_FIELD)?.toString(),
                time = document.get(TIME_FIELD)?.toString(),
                sys = document.get(SYS_FIELD)?.toString()?.toInt(),
                dia = document.get(DIA_FIELD)?.toString()?.toInt(),
                pulse = document.get(PULSE_FIELD)?.toString()?.toInt(),
                id = document.id
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun bioToMap(bio: Bio): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        with(bio) {
            date?.let {
                map[DATE_FIELD] = it
            }
            time?.let {
                map[TIME_FIELD] = it
            }
            sys?.let {
                map[SYS_FIELD] = it
            }
            dia?.let {
                map[DIA_FIELD] = it
            }
            pulse?.let {
                map[PULSE_FIELD] = it
            }
        }
        return map
    }

    companion object {

        // Collection name
        private const val BIO_COLLECTION = "bio_data"

        // Class serialization
        private const val DATE_FIELD = "date"
        private const val TIME_FIELD = "time"
        private const val SYS_FIELD = "sys"
        private const val DIA_FIELD = "dia"
        private const val PULSE_FIELD = "pulse"

    }
}