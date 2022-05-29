package io.tjohander.marvelouscomicservice.util

import io.tjohander.marvelouscomicservice.model.MarvelAuthComponents
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class MarvelAuthGenerator {

    companion object {
        fun buildAuthString(
            timeStamp: Instant,
            marvelApiPublicKey: String,
            marvelApiPrivateKey: String
        ): MarvelAuthComponents {
            val preHashString =
                "${timeStamp.toEpochMilli()}$marvelApiPrivateKey$marvelApiPublicKey"
            val hashed: String = DigestUtils.md5Hex(preHashString)
            return MarvelAuthComponents(
                ts = timeStamp.toEpochMilli().toString(),
                publicKey = marvelApiPublicKey,
                md5Hash = hashed
            )
        }
    }

}