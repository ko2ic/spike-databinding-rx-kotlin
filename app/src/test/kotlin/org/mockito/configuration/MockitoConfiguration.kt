package org.mockito.configuration

import org.mockito.internal.stubbing.defaultanswers.ReturnsSmartNulls
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer


open class MockitoConfiguration : DefaultMockitoConfiguration() {
    override fun getDefaultAnswer(): Answer<Any> {
        return ReturnsSmartNulls()
    }
//    override fun getDefaultAnswer(): Answer<Any> {
//        return object : ReturnsEmptyValues() {
//            override fun answer(inv: InvocationOnMock): Any {
//                val type = inv.method.returnType
//                if (type.isAssignableFrom(Observable::class.java!!)) {
//                    return Observable.error<RuntimeException>(createException(inv))
//                } else if (type.isAssignableFrom(Single::class.java!!)) {
//                    return Single.error<RuntimeException>(createException(inv))
//                } else {
//                    return super.answer(inv)
//                }
//            }
//        }
//    }

    private fun createException(
            invocation: InvocationOnMock): RuntimeException {
        val s = invocation.toString()
        return RuntimeException("No mock defined for invocation " + s)
    }
}
