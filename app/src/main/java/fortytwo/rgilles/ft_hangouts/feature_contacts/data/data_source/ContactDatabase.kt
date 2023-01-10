package fortytwo.rgilles.ft_hangouts.feature_contacts.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact

@Database(
    entities = [Contact::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {
    abstract val contactDao: ContactDao
}