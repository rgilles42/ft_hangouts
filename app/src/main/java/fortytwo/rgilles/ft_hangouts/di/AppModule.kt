package fortytwo.rgilles.ft_hangouts.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fortytwo.rgilles.ft_hangouts.feature_contacts.data.data_source.ContactDatabase
import fortytwo.rgilles.ft_hangouts.feature_contacts.data.repository.ContactRepositoryImpl
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.repository.ContactRepository
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.AddContactUseCase
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.ContactUseCases
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.DeleteContactUseCase
import fortytwo.rgilles.ft_hangouts.feature_contacts.domain.use_case.GetContactsUseCase
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
    fun provideContactUseCases(repository: ContactRepository): ContactUseCases {
        return ContactUseCases(
            getContacts = GetContactsUseCase(repository),
            deleteContact = DeleteContactUseCase(repository),
            addContact = AddContactUseCase(repository)
        )
    }
}