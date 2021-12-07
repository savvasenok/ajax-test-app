package xyz.savvamirzoyan.share.ajaxtest.ui

import xyz.savvamirzoyan.share.ajaxtest.core.Abstract

data class ContactUi(
    private val id: Int,
    private val fullName: String,
    private val photoUrl: String
) : Abstract.Object<Unit, Abstract.Mapper> {
    override fun map(mapper: Abstract.Mapper) = Unit
}
