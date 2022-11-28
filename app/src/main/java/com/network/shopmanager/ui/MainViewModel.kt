package com.network.shopmanager.ui

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.network.shopmanager.data.models.Geo
import com.network.shopmanager.data.models.Seller
import com.network.shopmanager.data.models.Shop
import com.network.shopmanager.data.models.Token
import com.network.shopmanager.utils.Constants.IMAGES
import com.network.shopmanager.utils.Constants.SELLERS
import com.network.shopmanager.utils.Constants.SHOPS
import com.network.shopmanager.utils.Constants.TOKEN
import com.network.shopmanager.utils.Objects
import com.network.shopmanager.utils.Objects.AUTH
import com.network.shopmanager.utils.Objects.DB_LOCAL
import com.network.shopmanager.utils.Resource
import com.network.shopmanager.utils.Status
import com.network.shopmanager.utils.waitMoment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val gson = Gson()
    private val DB_FIRESTORE = Firebase.firestore
    private val DB_FIREBASE_STORAGE = FirebaseStorage.getInstance()

    fun getRealTimeUpdates() {
        getRealTimeUpdateShops()
        // boshqa observablelar va listenerlar
    }

    //  -----------   signIn  and  signUp  ---------------

    private val _resultSignIn = MutableLiveData<Resource<Seller>>()
    val resultSignIn: LiveData<Resource<Seller>> = _resultSignIn

    private val _resultSignUp = MutableLiveData<Resource<Seller>>()
    val resultSignUp: LiveData<Resource<Seller>> = _resultSignUp


    fun signOut() {
//        DB_LOCAL.daoGroup().deleteAdminGroups(AUTH.uid ?: "")
//        DB_LOCAL.daoUser().deleteUsersByAdminId(AUTH.uid ?: "")
//        DB_LOCAL.daoPeriod().deleteRatingPeriodsByAdminId(AUTH.uid ?: "")
//        DB_LOCAL.daoAll().deleteRatingAllsByAdminId(AUTH.uid ?: "")

        waitMoment(200) {
            AUTH.signOut()
        }
    }

    fun signIn(email: String, password: String) {
        _resultSignIn.value = Resource(
            status = Status.LOADING
        )
        AUTH.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    _resultSignIn.value = Resource(
                        status = Status.SUCCESS,
                        message = "Muvaffaqiyatli kirdingiz!"
                    )
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    _resultSignIn.value = Resource(
                        status = Status.ERROR,
                        message = "Login yoki parol xato kiritildi !"
                    )
                }
            }
    }


    fun createSeller(seller: Seller) {
        val email = seller.login
        val password = seller.password

        _resultSignUp.value = Resource(status = Status.LOADING)
        AUTH.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                val isNewUser = task.result.signInMethods?.isEmpty() ?: false
                if (isNewUser) {
                    Log.e(TAG, "Is New User!")
                    AUTH.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { taskCreate ->
                            if (taskCreate.isSuccessful) {
                                Log.d(TAG, "createUserWithEmail:success")
                                val userFirebase = AUTH.currentUser
                                seller.id = userFirebase?.uid ?: UUID.randomUUID().toString()
                                updateToken()
                                saveSeller(seller)
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                _resultSignUp.value = Resource(
                                    status = Status.ERROR,
                                    message = "Xatolik ! Boshqatdan urinib ko'ring"
                                )
                            }
                        }
                } else {
                    Log.e(TAG, "Is Old User!")
                    _resultSignUp.value = Resource(
                        status = Status.ERROR,
                        message = "Bu Login allaqachon ro'yxatdan o'tgan"
                    )
                }
            }
    }


    private fun saveSeller(seller: Seller) {
        DB_FIRESTORE.collection(SELLERS).document(seller.id)
            .set(seller)
            .addOnSuccessListener {
                _resultSignUp.value = Resource(
                    status = Status.SUCCESS,
                    data = seller,
                    message = "Muvaffaqiyatli ro'yxatdan o'tdingiz!"
                )
            }
            .addOnFailureListener {
                _resultSignUp.value = Resource(
                    status = Status.ERROR,
                    message = "Xatolik !"
                )
                it.printStackTrace()
            }
    }


    // token

    private fun updateToken() {
        val token =
            "abcdefghijklmnopqrstuvwxyz123456789".toList().shuffled()
                .joinToString(separator = "", limit = 8, truncated = "")
        DB_FIRESTORE.collection(TOKEN).document(TOKEN).set(Token(token, System.currentTimeMillis()))
    }

    private val _resultToken = MutableLiveData<Resource<Token>>()
    val resultToken: LiveData<Resource<Token>> = _resultToken

    fun fetchToken() {
        _resultToken.value = Resource(status = Status.LOADING, message = "Kuting...")
        DB_FIRESTORE.collection(TOKEN).document(TOKEN).get()
            .addOnSuccessListener { snapshot ->
                val newToken = snapshot.toObject(Token::class.java)
                _resultToken.value = Resource(status = Status.SUCCESS, data = newToken)
            }
            .addOnFailureListener {
                _resultToken.value = Resource(status = Status.ERROR, message = "Token noto'g'ri")
            }
    }

    // get location
    private val _resultLocation = MutableLiveData<Resource<Geo>>()
    val resultLocation: LiveData<Resource<Geo>> = _resultLocation

    @DelicateCoroutinesApi
    fun getCurrentLocation() {
        _resultLocation.value = Resource(status = Status.LOADING)
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.APP)
        if (ContextCompat.checkSelfPermission(
                Objects.APP,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        val mLocation = task.result
                        val geo = Geo(
                            latitude = mLocation.latitude,
                            longitude = mLocation.longitude
                        )
                        _resultLocation.value = Resource(status = Status.SUCCESS, data = geo)
                    }
                }
            } catch (e: Exception) {
                _resultLocation.value = Resource(status = Status.ERROR)

                e.printStackTrace()
            }
        }
    }


    // shops
    private val _addMagazine = MutableLiveData<Resource<Shop>>()
    val addMagazine: LiveData<Resource<Shop>> = _addMagazine

    fun addMagazine(magazine: Shop, imageBytes: ByteArray? = null) {
        _addMagazine.value = Resource(
            status = Status.LOADING,
            message = "Kuting..."
        )
        if (imageBytes != null) {
            uploadImage(imageBytes, magazine.id) {
                magazine.photo = it
                updateShop(magazine)
            }
        } else {
            updateShop(magazine)
        }
    }

    private fun updateShop(magazine: Shop) {
        DB_FIRESTORE.collection(SHOPS).document(magazine.id)
            .set(magazine)
            .addOnSuccessListener {
                _addMagazine.value = Resource(
                    status = Status.SUCCESS,
                    message = " Do'kon muvaffaqaiyatli qo'shildi"
                )
            }
            .addOnFailureListener {
                _addMagazine.value = Resource(
                    status = Status.ERROR,
                    message = "Xatolik"
                )
                it.printStackTrace()
            }
    }

    private fun getRealTimeUpdateShops() {
        var lastUpdateTime = 0L
        try {
            lastUpdateTime = DB_LOCAL.daoShop().getShopsLastDate()
        } catch (e: Exception) {

        }

        DB_FIRESTORE.collection(SHOPS)
            .whereGreaterThanOrEqualTo("date", lastUpdateTime)
            // .whereIn("date", groupIds)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            try {
                                val shopRemote = dc.document.toObject(Shop::class.java)
                                Log.d(TAG, "shopRemote :$shopRemote")
                                DB_LOCAL.daoShop().addShop(shopRemote)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(object : DisposableSingleObserver<Long>() {
                                        override fun onSuccess(t: Long) {
                                            Log.d(TAG, "shopRemote ADDED :$shopRemote")
                                        }

                                        override fun onError(e: Throwable) {
                                            e.printStackTrace()
                                        }
                                    })
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                        DocumentChange.Type.MODIFIED -> {
                            try {
                                val shopRemote = dc.document.toObject(Shop::class.java)
                                DB_LOCAL.daoShop().updateShop(shopRemote)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(object : DisposableSingleObserver<Int>() {
                                        override fun onSuccess(t: Int) {}
                                        override fun onError(e: Throwable) {
                                            e.printStackTrace()
                                        }
                                    })
                                Log.d(
                                    TAG,
                                    "shopRemote Type.MODIFIED :$shopRemote"
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        DocumentChange.Type.REMOVED -> {
                            try {
                                val shopRemote = dc.document.toObject(Shop::class.java)
                                DB_LOCAL.daoShop().deleteShop(shopRemote)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(object : DisposableSingleObserver<Int>() {
                                        override fun onSuccess(t: Int) {}
                                        override fun onError(e: Throwable) {
                                            e.printStackTrace()
                                        }
                                    })

                                Log.d(
                                    TAG,
                                    "shopRemote Type.REMOVED :$shopRemote"
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
                Log.d(TAG, "getRealTimeUpdateShops")
            }
    }

    // images upload and download

    private fun uploadImage(fileBytes: ByteArray, id: String, function: (String) -> Unit) {
        DB_FIREBASE_STORAGE.getReference("$IMAGES/" + id)
            .putBytes(fileBytes)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    DB_FIREBASE_STORAGE.getReference("$IMAGES/" + id).downloadUrl.addOnSuccessListener {
                        Log.d(TAG, "link to ImageShop : $it")
                        function(it.toString())
                    }
                } else {
                    function("")
                }
            }
    }
}


