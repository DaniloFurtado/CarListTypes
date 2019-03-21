package desenv.danilo.domain

import desenv.danilo.domain.executor.BaseSchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class SingleUseCase<T, in Params> constructor(
    private val schedulerProvider: BaseSchedulerProvider
): BaseUseCase(){

    abstract fun buildSingleUseCase(params: Params? = null): Single<T>

    open fun executeUseCase(
        params: Params? = null,
        onNext: (T) -> Unit,
        onError: (e: Throwable) -> Unit,
        onSubscribe: (() -> Unit)? = null,
        onTerminate: (() -> Unit)? = null
    ){
        val single = this.buildSingleUseCase(params)
            .subscribeOn(schedulerProvider.io)
            .observeOn(schedulerProvider.mainThread)
            .doOnSubscribe{onSubscribe?.invoke() }
            .doAfterTerminate{onTerminate?.invoke()}
        addDisposable(single.subscribe(
            { t: T ->
                onNext.invoke(t)
            },
            { error ->
                onError.invoke(error)
            }
        ))
    }

    private fun addDisposable(disposable: Disposable) {
        disposeLast()
        lastDisposable = disposable
        lastDisposable?.let { compositeDisposable.add(it) }
    }
}