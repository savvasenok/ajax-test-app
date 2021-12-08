package xyz.savvamirzoyan.share.ajaxtest.data.db

interface ContactDataToDbMapper {

    fun mapToDb(name: String, surname: String, email: String, photoUrl: String): ContactDb

    class Base : ContactDataToDbMapper {
        override fun mapToDb(name: String, surname: String, email: String, photoUrl: String) =
            ContactDb(name, surname, email, photoUrl)
    }
}
