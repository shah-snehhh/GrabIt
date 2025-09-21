package com.example.grabit.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.grabit.data.model.UserResponse
import com.example.grabit.data.storage.UserPreferences.PreferencesKey.KEY_USER_JSON
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

const val PREF_USER_PREFERENCES = "grab_it_user_preferences"

@Singleton
class UserPreferences @Inject constructor(
    @Named(PREF_USER_PREFERENCES) private val preferencesDataStore: DataStore<Preferences>,
) {
    private val userJsonAdapter: JsonAdapter<UserResponse> =
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            .adapter(UserResponse::class.java)


    private object PreferencesKey {
        val INTRO_SHOWN = booleanPreferencesKey("intro_shown")
        val KEY_USER_JSON = stringPreferencesKey("current_user")
    }

    suspend fun isIntroShown(): Boolean {
        val prefs = preferencesDataStore.data.first()
        return prefs[PreferencesKey.INTRO_SHOWN] ?: false
    }

    suspend fun setIntroShown(value: Boolean) {
        preferencesDataStore.edit { prefs ->
            prefs[PreferencesKey.INTRO_SHOWN] = value
        }
    }

    var currentUser: UserResponse?
        get() = runBlocking {
            preferencesDataStore.data.first()[KEY_USER_JSON]?.let {
                val user = userJsonAdapter.fromJson(it)
                return@let user
            }
        }
        set(newUser) = runBlocking {
            if (newUser == null) {
                preferencesDataStore.edit {
                    it.remove(KEY_USER_JSON)
                }
            } else {
                preferencesDataStore.edit { preferences ->
                    preferences[KEY_USER_JSON] = userJsonAdapter.toJson(newUser)
                }
            }
        }

}
