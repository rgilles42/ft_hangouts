package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

data class ContactUseCases(
    val getContacts: GetContactsUseCase,
    val getContact: GetContactUseCase,
    val deleteContact: DeleteContactUseCase,
    val addContact: AddContactUseCase
)