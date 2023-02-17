package fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TransmissionStatus {
    SENDING, RECEIVED, SENT, NOT_SENT
}

@Entity
data class Message(
    @PrimaryKey val id: Int? = null,
    val recipientPhoneNumber: String?,
    val recipientId: Int?,
    val content: String,
    val isIncoming: Boolean,
    val hasTransmitted: TransmissionStatus,
    val timestamp: Long
)

class InvalidMessageException(message: String): Exception(message)