package jp.ra9.common.models.valueobjects.enums


// TODO: gsonでうまくenumを扱えないかを試行錯誤中のもの。動かない

interface EnumBase {
    companion object {
        inline fun <reified T : Enum<T>> convertEnum(primitive: Int?): Enum<T>? {
            val method = T::class.java.getMethod("toEnum", Int::class.javaObjectType)
            val result = method.invoke(null, primitive)
            return result as? Enum<T>
        }
    }
}