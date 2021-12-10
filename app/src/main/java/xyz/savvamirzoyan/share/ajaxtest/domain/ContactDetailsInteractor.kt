package xyz.savvamirzoyan.share.ajaxtest.domain

import xyz.savvamirzoyan.share.ajaxtest.data.ContactsRepository

interface ContactDetailsInteractor {

    suspend fun fetchUser(userId: Int): ContactDomain
    suspend fun deleteUser(userId: Int)
    suspend fun updateUser()

    fun updateName(text: String)
    fun updateSurname(text: String)
    fun updateEmail(text: String)

    fun isNameOrSurnameValid(text: String): Boolean
    fun isEmailValid(text: String): Boolean
    fun isSaveButtonEnabled(): Boolean

    class Base(
        private val contactsRepository: ContactsRepository,
        private val contactDataToDomainMapper: ContactDataToDomainMapper
    ) : ContactDetailsInteractor {

        private lateinit var contactDomain: ContactDomain

        private var _name: String = ""
        private var _surname: String = ""
        private var _email: String = ""

        override suspend fun fetchUser(userId: Int): ContactDomain {
            val result = contactsRepository.read(userId).map(contactDataToDomainMapper)
            contactDomain = result

            return contactDomain
        }

        override suspend fun deleteUser(userId: Int) {
            contactsRepository.delete(userId)
        }

        override suspend fun updateUser() {
            val updater = object : ContactDomainUpdate {
                override fun update(
                    id: Int,
                    photoUrl: String,
                    thumbnailUrl: String
                ) = ContactDomain(id, _name, _surname, _email, photoUrl, thumbnailUrl)
            }
            val newValue = contactDomain.update(updater)

            contactsRepository.update(newValue)
        }

        override fun isNameOrSurnameValid(text: String): Boolean = text.isNotBlank()

        override fun isEmailValid(text: String): Boolean =
            android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()

        // TODO: Prettify
        override fun isSaveButtonEnabled(): Boolean {
            val a = isNameOrSurnameValid(_name)
            val b = isNameOrSurnameValid(_surname)
            val c = isEmailValid(_email)
            val d = contactDomain.matchName(_name)
            val e = contactDomain.matchSurname(_surname)
            val f = contactDomain.matchEmail(_email)

            return a && b && c && !(d && e && f)
        }

        override fun updateName(text: String) {
            _name = text
        }

        override fun updateSurname(text: String) {
            _surname = text
        }

        override fun updateEmail(text: String) {
            _email = text
        }
    }
}
