package fortytwo.rgilles.ft_hangouts.feature_messaging.data.data_source

import androidx.room.*
import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

//    @Query("SELECT * FROM messages")
//    fun         getMessages(): Flow<List<Message>>

    @Transaction
    @Query("SELECT * FROM contact WHERE id = :contactId")
    fun         getContactWithMessagesByContactId(contactId: Int): Flow<ContactWithMessages> // if doesn't work replace with Flow<List<CwM>> and cope

    @Query("SELECT * FROM message WHERE id = :id")
    suspend fun getMessageById(id: Int): Message?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)
}