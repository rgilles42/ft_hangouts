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
            throw InvalidContactException("EXCEPTION_CONTACT_NAME_EMPTY")
        }
        if (contact.phoneNumber.isNotBlank() && !Patterns.PHONE.matcher(contact.phoneNumber).matches()){
            throw InvalidContactException("EXCEPTION_CONTACT_PHONE_INVALID")
        }
        if (contact.email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(contact.email).matches()){
            throw InvalidContactException("EXCEPTION_CONTACT_EMAIL_INVALID")
        }
        repository.insertContact(contact)
    }
}