package my.gong.studygong

import my.gong.studygong.di.repositoryModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest: KoinTest{

    @Before
    fun before() {
        startKoin {
            modules(
                repositoryModule
            )
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun addition_isCorrect() {
        val hello = MyApplication()

        Thread.sleep(1000)

    }
}


