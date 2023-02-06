package fortytwo.rgilles.ft_hangouts.common.domain.relations

import androidx.room.Embedded
import androidx.room.Relation
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message

data class ContactWithMessages(
    @Embedded val contact: Contact,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipientId"
    )
    val messages: List<Message>
)
