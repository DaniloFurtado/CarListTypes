package desenv.danilo.presentation

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import desenv.danilo.domain.interactor.ListCarUseCase
import desenv.danilo.modelbinding.AvaibleCoreBind
import desenv.danilo.presentation.data.DataFactoryCar
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class CarListViewModelTestSpek: Spek({

    beforeEachTest {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    afterEachTest { ArchTaskExecutor.getInstance().setDelegate(null) }

    val listUserUseCase = mock<ListCarUseCase>()

    val captorListUser = argumentCaptor<((AvaibleCoreBind) -> Unit)>()

    val captorError = argumentCaptor<(e: Throwable) -> Unit>()

    val viewModel = CarListViewModel(listUserUseCase)


    describe("When open the app") {

        it("then exec the right method to list veichiles") {
            viewModel.listCars()
            verify(listUserUseCase, times(1))
                .executeUseCase(eq(null), any(), any(), any(), eq(null))
        }

        it("then show the load right") {
            viewModel.showProgress()
            assertEquals(
                ViewState.Status.LOADING,
                viewModel.getStateListUser().value?.status
            )
        }
    }

    describe("When open the app must list all users") {
        it("must list all users successfull") {
            viewModel.listCars()
            verify(listUserUseCase, times(2))
                .executeUseCase(eq(null), captorListUser.capture(), any(), any(), eq(null))
            captorListUser.firstValue.invoke(DataFactoryCar.dummyListUser())

            assertEquals(
                ViewState.Status.SUCCESS,
                viewModel.getStateListUser().value?.status
            )
        }

        it("must list quantity right of cars") {
            viewModel.listCars()
            verify(listUserUseCase, times(3))
                .executeUseCase(eq(null), captorListUser.capture(), any(), any(), eq(null))
            captorListUser.firstValue.invoke(DataFactoryCar.dummyListUser())

            assertEquals(
                ViewState.Status.SUCCESS,
                viewModel.getStateListUser().value?.status
            )
        }

        it("must return the same list returned from UseCase") {
            viewModel.listCars()
            verify(listUserUseCase, times(4))
                .executeUseCase(eq(null), captorListUser.capture(), any(), any(), eq(null))
            val items = DataFactoryCar.dummyListUser()
            captorListUser.firstValue.invoke(items)

            assertEquals(
                items,
                viewModel.getStateListUser().value?.data
            )

            assertEquals(
                items.vehicles,
                viewModel.getStateListUser().value?.data?.vehicles
            )
        }
    }


    describe("When happen any error") {
        it("Must catch exeption error") {
            val testObserveError = viewModel.getStatsViewError().test()
            viewModel.listCars()
            verify(listUserUseCase, times(5))
                .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
            captorError.firstValue.invoke(Exception("Error Test"))

            testObserveError.assertValue {
                "Error Test" == it.error?.message
            }
            testObserveError.assertValue {
                ViewErrorState.TypeExibe.SNACK == it.type
            }
            testObserveError.assertValue {
                R.string.error == it.idMessage
            }

            testObserveError.dispose()
        }

        it("Mus catch error ArgumentExcpetion"){
            val testObserveError = viewModel.getStatsViewError().test()
            viewModel.listCars()
            verify(listUserUseCase, times(6))
                .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
            captorError.firstValue.invoke(IllegalArgumentException("Error Argument wrong Test"))

            testObserveError.assertValue {
                "Error Argument wrong Test" == it.error?.message
            }

            testObserveError.assertValue {
                ViewErrorState.TypeExibe.SNACK == it.type
            }
            testObserveError.assertValue {
                R.string.invalid_paramter == it.idMessage
            }

            testObserveError.dispose()
        }

        it("musta catch error RuntimeException"){
            val testObserveError = viewModel.getStatsViewError().test()
            viewModel.listCars()
            verify(listUserUseCase, times(7))
                .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
            captorError.firstValue.invoke(RuntimeException("This is a Error of runtime exception"))

            testObserveError.assertValue {
                "This is a Error of runtime exception" == it.error?.message
            }

            testObserveError.assertValue {
                ViewErrorState.TypeExibe.SNACK == it.type
            }
            testObserveError.assertValue {
                R.string.error_api == it.idMessage
            }

            testObserveError.dispose()
        }

        it("must catch erro TimeOutException"){
            val testObserveError = viewModel.getStatsViewError().test()
            viewModel.listCars()
            verify(listUserUseCase, times(8))
                .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
            captorError.firstValue.invoke(TimeoutException("This is a Error of timeout exception"))

            testObserveError.assertValue {
                "This is a Error of timeout exception" == it.error?.message &&
                        ViewErrorState.TypeExibe.SNACK == it.type &&
                        R.string.error_time_out == it.idMessage
            }

            testObserveError.dispose()
        }
    }

})