package desenv.danilo.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class BaseViewModel: ViewModel(), LifecycleObserver {

    private val statusViewError = PublishSubject.create<ViewErrorState>()

    fun getStatsViewError(): PublishSubject<ViewErrorState> {
        return statusViewError
    }

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    // se tiver algum disposable no viewModel, extension function para adicionar ao composite disposable
    fun Disposable.disposableToClear() =
        disposable.add(this)


    // Tratamento geral de erros para ViewModels notificarem em view com a messagem ou acão correta
    fun errorHandle(throwable: Throwable) {
        when (throwable) {
            is IllegalArgumentException -> statusViewError.onNext(
                ViewErrorState(
                    type  =  ViewErrorState.TypeExibe.SNACK,
                    error = throwable,
                    idMessage = R.string.invalid_paramter
                )
            )
            is RuntimeException -> statusViewError.onNext(
                ViewErrorState(
                    type  =  ViewErrorState.TypeExibe.SNACK,
                    error = throwable,
                    idMessage = R.string.error_api
                )
            )

            is UnknownHostException, is ConnectException -> statusViewError.onNext(
                ViewErrorState(
                    type  =  ViewErrorState.TypeExibe.SNACK,
                    error = throwable,
                    idMessage = R.string.error_connectin,
                    retry = true
                )
            )

            is TimeoutException -> statusViewError.onNext(
                ViewErrorState(
                    type  =  ViewErrorState.TypeExibe.SNACK,
                    error = throwable,
                    idMessage = R.string.error_time_out,
                    retry = true
                )
            )

            is javax.net.ssl.SSLHandshakeException, is javax.net.ssl.SSLProtocolException -> statusViewError.onNext(
                ViewErrorState(
                    type  =  ViewErrorState.TypeExibe.ALERT,
                    error = throwable,
                    idMessage = R.string.security_update_google_paly,
                    retry = true
                )
            )


            else -> statusViewError.onNext(
                ViewErrorState(
                    type  =  ViewErrorState.TypeExibe.SNACK,
                    error = throwable,
                    idMessage = R.string.error
                )
            )
        }
    }

}