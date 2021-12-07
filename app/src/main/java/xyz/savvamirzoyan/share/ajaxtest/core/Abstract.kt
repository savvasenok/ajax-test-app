package xyz.savvamirzoyan.share.ajaxtest.core

abstract class Abstract {

    interface Object<T, M : Mapper> {
        fun map(mapper: M): T
    }

    interface Mapper
}