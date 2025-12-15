package com.example.workmateapp.data.cache

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheManager @Inject constructor(
    context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )
    
    companion object {
        private const val PREFS_NAME = "countries_cache_prefs"
        private const val KEY_COUNTRIES_LAST_UPDATE = "countries_last_update"
        private const val CACHE_VALIDITY_DURATION_MS = 30 * 60 * 1000L // 30 minutes
    }
    
    fun isCacheStale(): Boolean {
        val lastUpdate = prefs.getLong(KEY_COUNTRIES_LAST_UPDATE, 0L)
        if (lastUpdate == 0L) return true
        
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastUpdate) > CACHE_VALIDITY_DURATION_MS
    }
    
    fun updateCacheTimestamp() {
        prefs.edit()
            .putLong(KEY_COUNTRIES_LAST_UPDATE, System.currentTimeMillis())
            .apply()
    }
    
    fun hasCache(): Boolean {
        return prefs.getLong(KEY_COUNTRIES_LAST_UPDATE, 0L) > 0L
    }
    
    fun clearCache() {
        prefs.edit()
            .remove(KEY_COUNTRIES_LAST_UPDATE)
            .apply()
    }
}
