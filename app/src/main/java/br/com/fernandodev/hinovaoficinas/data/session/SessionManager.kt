// app/src/main/java/br/com/fernandodev/hinovaoficinas/data/session/SessionManager.kt
package br.com.fernandodev.hinovaoficinas.data.session

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * Gerencia sessão via DataStore.
 */
object SessionManager {

    // DataStore
    private val Context.sessionDataStore by preferencesDataStore(name = "session")
    private val KEY_USER_JSON: Preferences.Key<String> = stringPreferencesKey("user_json")
    private val gson = Gson()

    // Modelo único de usuário salvo na sessão
    data class LoginUser(
        val Id: Long? = null,
        val Nome: String? = null,
        val Codigo_mobile: String? = null,
        val Cpf: String? = null,
        val Email: String? = null,
        val Situacao: String? = null,
        val Telefone: String? = null
    )

    suspend fun setUser(context: Context, user: LoginUser?) {
        context.sessionDataStore.edit { prefs ->
            if (user == null) prefs.remove(KEY_USER_JSON)
            else prefs[KEY_USER_JSON] = gson.toJson(user)
        }
    }

    fun userFlow(context: Context): Flow<LoginUser?> =
        context.sessionDataStore.data.map { prefs ->
            val json = prefs[KEY_USER_JSON] ?: return@map null
            runCatching { gson.fromJson(json, LoginUser::class.java) }.getOrNull()
        }

    suspend fun getUser(context: Context): LoginUser? =
        userFlow(context).firstOrNull()

    suspend fun getUserOnce(context: Context): LoginUser? = getUser(context)

    suspend fun clear(context: Context) {
        context.sessionDataStore.edit { it.clear() }
    }
}
