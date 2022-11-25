package com.network.shopmanager.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.network.shopmanager.data.models.Seller
import com.network.shopmanager.data.models.Shop
import com.network.shopmanager.data.models.Token
import com.network.shopmanager.utils.Constants
import com.network.shopmanager.utils.Constants.SELLERS
import com.network.shopmanager.utils.Constants.SHOPS
import com.network.shopmanager.utils.Constants.TOKEN
import com.network.shopmanager.utils.Objects.AUTH
import com.network.shopmanager.utils.Resource
import com.network.shopmanager.utils.Status
import com.network.shopmanager.utils.waitMoment
import java.util.*

const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val gson = Gson()
    private val DB_FIRESTORE = Firebase.firestore
    private val DB_FIREBASE_STORAGE = FirebaseStorage.getInstance()

    //  -----------   signIn  and  signUp  ---------------

    private val _resultSignIn = MutableLiveData<Resource<Seller>>()
    val resultSignIn: LiveData<Resource<Seller>> = _resultSignIn

    private val _resultSignUp = MutableLiveData<Resource<Seller>>()
    val resultSignUp: LiveData<Resource<Seller>> = _resultSignUp

    private val _addMagazine = MutableLiveData<Resource<Shop>>()
    val addMagazine: LiveData<Resource<Shop>> = _addMagazine

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


    fun addMagazine(magazine: Shop) {
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
}