package fortytwo.rgilles.ft_hangouts.feature_contacts.data.data_source

import androidx.room.*
import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun         getContacts(): Flow<List<Contact>>

    @Transaction
    @Query("SELECT * FROM contact")
    fun         getContactsWithActiveConvs(): Flow<List<ContactWithMessages>>

    @Transaction
    @Query("SELECT * FROM contact WHERE id = :id")
    fun         getContactWithMessages(id: Int): Flow<ContactWithMessages>

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun getContactById(id: Int): Contact?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)
}