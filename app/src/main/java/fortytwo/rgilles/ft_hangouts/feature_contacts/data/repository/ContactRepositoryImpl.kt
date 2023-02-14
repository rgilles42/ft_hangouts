package fortytwo.rgilles.ft_hangouts.feature_contacts.data.repository

import fortytwo.rgilles.ft_hangouts.common.domain.relations.ContactWithMessages
import fortytwo.rgilles.ft_hangouts.feature_contacts.data.data_source.ContactDao
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactRepositoryImpl(
    private val dao: ContactDao
): ContactRepository {
    override fun getContacts(): Flow<List<Contact>> {
        return dao.getContacts()
    }

    override fun getContactsWithActiveConvs(): Flow<List<ContactWithMessages>> {
        return dao.getContactsWithActiveConvs().map { ListOfContactsWithMessages ->
            ListOfContactsWithMessages
                .filter { ContactWithMessages ->
                    ContactWithMessages.messages.isNotEmpty()
                }
        }
    }

    override fun getContactWithMessages(id: Int): Flow<ContactWithMessages> {
        return dao.getContactWithMessages(id)
    }

    override suspend fun getContactById(id: Int): Contact? {
        return dao.getContactById(id)
    }

    override suspend fun insertContact(contact: Contact) {
        dao.insertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        dao.deleteContact(contact)
    }
}