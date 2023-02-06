package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.InvalidContactException
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository

class AddContactUseCase(
    private val repository: ContactRepository
) {
    @Throws(InvalidContactException::class)
    suspend operator fun invoke(contact: Contact) {
        if (contact.firstName.isBlank() and contact.lastName.isBlank()){
            throw InvalidContactException("Contact name cannot be empty.")
        }
        if (contact.phoneNumber.isBlank() and contact.email.isBlank()){
            throw InvalidContactException("Contact email or phone number cannot be empty.")
        }
        repository.insertContact(contact)
    }
}