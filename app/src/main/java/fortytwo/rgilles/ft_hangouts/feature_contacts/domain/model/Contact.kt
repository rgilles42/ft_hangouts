package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val picturePath: String
)

class InvalidContactException(message: String): Exception(message)