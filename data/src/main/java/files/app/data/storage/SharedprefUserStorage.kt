package files.app.data.storage

import android.content.Context

private const val SHARED_PREFS_NAME = "shared_prefs_name"
private const val KEY_FIRST_NAME = "key_first_name"
private const val KEY_LAST_NAME = "key_last_name"
private const val DEFAULT_NAME = "default_name"

class SharedprefUserStorage(context: Context) : UserStorage {
    val pref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    override fun save(user: User): Boolean {
        try {
            pref.edit().putString(KEY_FIRST_NAME, user.firstName).apply()
            pref.edit().putString(KEY_LAST_NAME, user.lastName).apply()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun get(): User {
        val firstName = pref.getString(KEY_FIRST_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
        val lastName = pref.getString(KEY_LAST_NAME, DEFAULT_NAME) ?: DEFAULT_NAME
        return User(firstName, lastName)
    }

}