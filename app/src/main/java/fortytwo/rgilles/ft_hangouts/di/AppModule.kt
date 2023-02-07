package fortytwo.rgilles.ft_hangouts.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fortytwo.rgilles.ft_hangouts.common.data.data_source.ContactDatabase
import fortytwo.rgilles.ft_hangouts.feature_contacts.data.repository.ContactRepositoryImpl
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.*
import fortytwo.rgilles.ft_hangouts.feature_messaging.data.repository.MessageRepositoryImpl
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.repository.MessageRepository
import fortytwo.rgilles.ft_hangouts.feature_messaging.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContactDatabase(app: Application): ContactDatabase {
        return Room.databaseBuilder(
            app,
            ContactDatabase::class.java,
            ContactDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactRepository(db: ContactDatabase): ContactRepository {
        return ContactRepositoryImpl(db.contactDao)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(db: ContactDatabase): MessageRepository {
        return MessageRepositoryImpl(db.messageDao)
    }

    @Provides
    @Singleton
    fun provideContactUseCases(repository: ContactRepository): ContactUseCases {
        return ContactUseCases(
            getContacts = GetContactsUseCase(repository),
            getContactsWithActiveConvs = GetContactsWithActiveConvsUseCase(repository),
            getContact = GetContactUseCase(repository),
            deleteContact = DeleteContactUseCase(repository),
            addContact = AddContactUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideMessageUseCases(repository: MessageRepository): MessageUseCases {
        return MessageUseCases(
            getMessagesOfContact = GetMessagesOfContactUseCase(repository),
            getMessage = GetMessageUseCase(repository),
            deleteMessage = DeleteMessageUseCase(repository),
            addMessage = AddMessageUseCase(repository)
        )
    }
}