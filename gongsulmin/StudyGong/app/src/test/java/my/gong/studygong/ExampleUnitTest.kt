package my.gong.studygong

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import my.gong.studygong.data.source.upbit.TestRepo
import org.junit.Test
import org.koin.test.KoinTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest: KoinTest{

    @Test
    fun addition_isCorrect() {
//        runBlocking {
//            println(TestRepo().getCoinCurrencyByCoroutinedeferred())
//        }

        runBlocking {
            TestRepo().getCoinCurrencyByCoroutineFlow().collect {
                println(" 홀홀홀     ${it}")
            }
        }


        println(" Test Finished   ")
    }
}


