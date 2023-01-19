package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Contact(
    @PrimaryKey val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val birthday: LocalDate?
)

class InvalidContactException(message: String): Exception(message)