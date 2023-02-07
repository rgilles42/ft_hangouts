package fortytwo.rgilles.ft_hangouts.common.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import fortytwo.rgilles.ft_hangouts.feature_contacts.data.data_source.ContactDao
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_messaging.data.data_source.MessageDao
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message

@Database(
    entities = [Contact::class, Message::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {
    abstract val contactDao: ContactDao
    abstract val messageDao: MessageDao

    companion object {
        const val DATABASE_NAME = "contacts_db"
    }
}