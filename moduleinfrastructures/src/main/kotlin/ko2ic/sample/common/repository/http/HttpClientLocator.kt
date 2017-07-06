package ko2ic.sample.common.repository.http


interface HttpClientLocator {

    fun <T> lookup(clazz: Class<T>): T
}