package desenv.danilo.domain.executor

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    val mainThread: Scheduler
    val newThread: Scheduler
    val io: Scheduler
    val computation: Scheduler
}