package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.model.Contact
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository

class GetContactUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(id: Int): Contact? {
        return repository.getContactById(id)
    }
}