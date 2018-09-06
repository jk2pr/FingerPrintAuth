package com.hoppers.fingerprintauth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.security.UnrecoverableEntryException


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View) {

        when (v.id) {
            R.id.decrypt ->
                decryptText()


        }
    }

    private val TAG = MainActivity::class.java.simpleName
    private val enc by lazy { Encryptor() }
    private val dec by lazy { Decryptor() }
    private val SAMPLE_ALIAS = "MYALIAS"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        decrypt.setOnClickListener(this)
        encryptText()

    }


    private fun encryptText() {
        try {
            val encryptedText = enc
                    .encryptText(SAMPLE_ALIAS, "Jitendra")
            tvEncryptedText.text = Base64.encodeToString(encryptedText, Base64.DEFAULT)


        } catch (e: Exception) {
            Log.e(TAG, "onClick() called with: " + e.message, e)

        }
    }


    private fun decryptText() {
        try {
            tvEncryptedText.text = dec
                    .decryptData(SAMPLE_ALIAS, enc.encryption!!, enc.iv!!)
        } catch (e: UnrecoverableEntryException) {
            Log.e(TAG, "decryptData() called with: " + e.message, e)

        }
    }
}
