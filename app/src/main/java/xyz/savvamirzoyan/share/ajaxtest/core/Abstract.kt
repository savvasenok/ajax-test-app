package xyz.savvamirzoyan.share.ajaxtest.core

abstract class Abstract {

    interface Object<T, M : Mapper> {
        fun map(mapper: M): T
    }

    interface CloudObject<T, M : Mapper> : Object<T, M>
    interface DbObject<T, M : Mapper> : Object<T, M>

    interface DataObject

    interface DomainObject

    interface UiObject

    interface Mapper
}