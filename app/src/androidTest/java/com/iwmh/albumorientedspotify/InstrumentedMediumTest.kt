package com.iwmh.albumorientedspotify

import android.content.Context
import android.content.Intent
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import com.iwmh.albumorientedspotify.util.Constants
import net.openid.appauth.AuthState
import org.hamcrest.Matchers.notNullValue
import org.junit.*

import org.junit.runner.RunWith

private const val LAUNCH_TIMEOUT = 5000L

private const val WAIT_TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class InstrumentedMediumTest{

    companion object {

        // Device for UI Automator
        private lateinit var device: UiDevice

        // Key for EncryptedSharedPreferences.
        private lateinit var mainKey: MasterKey

        // initial AuthState value stored in Preferences.
        private var initialAuthState: AuthState = AuthState()

        private var packageName = "com.iwmh.albumorientedspotify"

        @BeforeClass
        @JvmStatic
        fun init() {

            // Setup the main key for EncryptedSharedPreferences.
            val context = ApplicationProvider.getApplicationContext<Context>()
            mainKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            // Backup the initial AuthState value.
            val prefs = EncryptedSharedPreferences.create(
                context,
                Constants.shared_prefs_file,
                mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            val initialAuthStateString = prefs.getString(Constants.auth_state_json, "")
            if (!initialAuthStateString.isNullOrEmpty()) {
                initialAuthState = AuthState.jsonDeserialize(initialAuthStateString)
            }

            // Remove all the EncryptedSharedPreferences.
            prefs.edit()
                .clear()
                .apply()
        }

        @AfterClass
        @JvmStatic
        fun end() {

            // In this instrumented test suites, I first clear the SharedPreference's contents,
            // in order to execute the auth flow.
            // After that, fresh token is stored in SharedPreferences.

            // If you want to run or debug the app, that token is going to be used,
            // without doing the auth flow all over again.
            // (It's just recycling the token from the instrumented test ...)

            /*
            // Restore the initial AuthState value.
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val prefs = EncryptedSharedPreferences.create(
                context,
                Constants.shared_prefs_file,
                mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            prefs.edit()
                .putString(Constants.auth_state_json, initialAuthState.jsonSerializeString())
                .apply()
             */

        }
    }

    @Before
    fun startApp(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Launch the app
        val intent = context.packageManager.getLaunchIntentForPackage(
            packageName
        )?.apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(packageName).depth(0)),
            LAUNCH_TIMEOUT
        )
    }

    @Test
    fun auth_page_to_home_page(){

        var auth = device.findObject(
            UiSelector().text("auth")
        )
        auth.click()

        // Wait until the Spotify authorization page is shown.
        device.wait(
            Until.findObject(
                By.textContains("You agree that")
            ), WAIT_TIMEOUT
        )

        // scroll to the end.
        device.swipe(
            device.displayWidth/2,
            device.displayHeight - 100,
            device.displayWidth/2,
            100, 2)

        // Click AGREE button.
        var button = device.wait(
            Until.findObject(
                By.text("AGREE")
            ), WAIT_TIMEOUT
        )
        button.click()

        var homeText = device.findObject(
            UiSelector().textContains("podcast information")
        )
        assert(homeText.exists())
    }

    @Test
    fun click_library_tab(){
        // Click the "Library" tab.
        var libraryTab = device.findObject(
            UiSelector().text("Library")
        )
        libraryTab.click()

        // Navigate to the Library tab content.
        var libraryContentText = device.findObject(
            UiSelector().text("Your library.")
        )
        assert(libraryContentText.exists())

    }






}