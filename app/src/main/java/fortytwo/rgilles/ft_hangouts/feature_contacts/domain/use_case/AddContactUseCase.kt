package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

import android.util.Patterns
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
        if (contact.phoneNumber.isNotBlank() && !Patterns.PHONE.matcher(contact.phoneNumber).matches()){
            throw InvalidContactException("Contact phone number is invalid.")
        }
        if (contact.email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(contact.email).matches()){
            throw InvalidContactException("Contact email is invalid.")
        }
        repository.insertContact(contact)
    }
}