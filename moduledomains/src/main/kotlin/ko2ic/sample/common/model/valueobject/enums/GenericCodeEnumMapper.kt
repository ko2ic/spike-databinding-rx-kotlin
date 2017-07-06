package jp.ra9.common.models.valueobjects.enums


// TODO: gsonでうまくenumを扱えないかを試行錯誤中のもの

interface GenericCodeEnumMapper<E, P> where E : Enum<E> {

    fun getValues(): Array<E>

    val E.rawValue: P

    fun toEnum(primitive: P): E {
        for (type in getValues()) {
            if (type.rawValue == primitive) {
                return type
            }
        }
        throw IllegalArgumentException("間違った値を設定しています:${primitive}")
    }
}
