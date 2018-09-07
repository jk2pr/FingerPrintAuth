package com.hoppers.fingerprintauth

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.annotation.NonNull
import java.io.IOException
import java.security.*

import javax.crypto.*

internal class Encryptor {

    var encryption: ByteArray? = null
    var iv: ByteArray? = null

    @Throws(UnrecoverableEntryException::class, NoSuchAlgorithmException::class, KeyStoreException::class, NoSuchProviderException::class, NoSuchPaddingException::class, InvalidKeyException::class, IOException::class, InvalidAlgorithmParameterException::class, SignatureException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun encryptText(alias: String, textToEncrypt: String): ByteArray {

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias))

        iv = cipher.iv

        encryption = cipher.doFinal(textToEncrypt.toByteArray(charset("UTF-8")))
        return encryption!!
    }

    @NonNull
    @Throws(NoSuchAlgorithmException::class, NoSuchProviderException::class, InvalidAlgorithmParameterException::class)
    private fun getSecretKey(alias: String): SecretKey {

        val keyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)

        keyGenerator.init(KeyGenParameterSpec.Builder(alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build())

        return keyGenerator.generateKey()
    }

    companion object {

        private val TRANSFORMATION = "AES/GCM/NoPadding"
        private val ANDROID_KEY_STORE = "AndroidKeyStore"
    }
}