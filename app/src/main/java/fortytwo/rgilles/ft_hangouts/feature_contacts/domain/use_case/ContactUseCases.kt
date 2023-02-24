package fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case

data class ContactUseCases(
    val getContacts: GetContactsUseCase,
    val getContactsWithActiveConvs: GetContactsWithActiveConvsUseCase,
    val getContactWithMessages: GetContactWithMessagesUseCase,
    val getInstantaneousContacts: GetInstantaneousContactsUseCase,
    val getContact: GetContactUseCase,
    val deleteContact: DeleteContactUseCase,
    val addContact: AddContactUseCase
)
