package desenv.danilo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import desenv.danilo.domain.interactor.ListCarUseCase
import desenv.danilo.modelbinding.AvaibleCoreBind
import desenv.danilo.presentation.data.DataFactoryCar
import org.junit.Rule
import org.junit.Test
import org.mockito.Captor
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class CarListViewModelTest{


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val listUserUseCase  = mock<ListCarUseCase>()
    @Captor
    private val captorListUser = argumentCaptor<((AvaibleCoreBind) -> Unit)>()
    @Captor
    private val captorError = argumentCaptor<(e: Throwable) -> Unit>()

    private val viewModel = CarListViewModel(listUserUseCase)

    @Test
    fun testExecuteRightMethodOnCreate(){
        viewModel.onCreate()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), any(), any(), eq(null))
    }

    @Test
    fun testExecuteRightMethod(){
        viewModel.listCars()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), any(), any(), eq(null))
    }

    @Test
    fun testShowLoad(){
        viewModel.showProgress()
        assertEquals(
            ViewState.Status.LOADING,
            viewModel.getStateListUser().value?.status
        )
    }


    @Test
    fun testListAllUsers() {
        viewModel.listCars()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), captorListUser.capture(), any(), any(), eq(null))
        captorListUser.firstValue.invoke(DataFactoryCar.dummyListUser())

        assertEquals(
            ViewState.Status.SUCCESS,
            viewModel.getStateListUser().value?.status
        )
    }

    @Test
    fun testListAllUsersQtdRight() {
        viewModel.listCars()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), captorListUser.capture(), any(), any(), eq(null))
        captorListUser.firstValue.invoke(DataFactoryCar.dummyListUser())

        assertEquals(
            ViewState.Status.SUCCESS,
            viewModel.getStateListUser().value?.status
        )

    }


    @Test
    fun testListAllTheSameList() {
        viewModel.listCars()
        verify(listUserUseCase, times(1))
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

    @Test
    fun testCatchErrorException() {
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.listCars()
        verify(listUserUseCase, times(1))
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

    @Test
    fun testCatchErrorIllegalArgumentExcpetion() {
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.listCars()
        verify(listUserUseCase, times(1))
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

    @Test
    fun testCatchErrorIRuntimeException() {
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.listCars()
        verify(listUserUseCase, times(1))
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

    @Test
    fun testCatchUnknownHostException(){
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.listCars()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(UnknownHostException("This is a Error of unknow exception"))

        testObserveError.assertValue {
            "This is a Error of unknow exception" == it.error?.message &&
                    ViewErrorState.TypeExibe.SNACK == it.type &&
                    R.string.error_connectin == it.idMessage
        }

        testObserveError.dispose()
    }


    @Test
    fun testCatchConnectException(){
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.listCars()
        verify(listUserUseCase, times(1))
            .executeUseCase(eq(null), any(), captorError.capture(), any(), eq(null))
        captorError.firstValue.invoke(ConnectException("This is a Error of connect exception"))

        testObserveError.assertValue {
            "This is a Error of connect exception" == it.error?.message &&
                    ViewErrorState.TypeExibe.SNACK == it.type &&
                    R.string.error_connectin == it.idMessage
        }

        testObserveError.dispose()
    }


    @Test
    fun testCatchTimeoutException(){
        val testObserveError = viewModel.getStatsViewError().test()
        viewModel.listCars()
        verify(listUserUseCase, times(1))
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