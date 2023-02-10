package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository

import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun         getContacts(): Flow<List<Contact>>
    fun         getContactsWithExistingConversation(): Flow<List<ContactWithMessages>>
    suspend fun getContactById(id: Int): Contact?
    suspend fun insertContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
}