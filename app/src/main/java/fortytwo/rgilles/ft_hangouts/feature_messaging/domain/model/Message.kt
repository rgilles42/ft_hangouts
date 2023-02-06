package fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey val id: Int? = null,
    val recipientId: Int,
    val isIncoming: Boolean,
    val content: String,
)

class InvalidMessageException(message: String): Exception(message)