package desenv.danilo.carlisttypes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.uber.autodispose.lifecycle.autoDisposable
import desenv.danilo.carlisttypes.adapter.CarListAdatper
import desenv.danilo.carlisttypes.databinding.ActivityMainBinding
import desenv.danilo.carlisttypes.util.gone
import desenv.danilo.carlisttypes.util.show
import desenv.danilo.modelbinding.VehicleBind
import desenv.danilo.presentation.CarListViewModel
import desenv.danilo.presentation.CarVmFactory
import desenv.danilo.presentation.ViewErrorState
import desenv.danilo.presentation.ViewState
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import javax.inject.Inject

class CarListActivity : BaseActivity() {

    private lateinit var factoryVm: CarVmFactory

    @Inject
    fun setFactory(facotryInjected: CarVmFactory){
        factoryVm = facotryInjected
    }

    @Inject
    lateinit var carListAdapter: CarListAdatper

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CarListViewModel by lazy {
        ViewModelProviders.of(this,
            factoryVm)
            .get(CarListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        addObserver(viewModel)
        initActivity()
        carListAdapter.setInilistenerClick(::userSelected)
    }

    private fun initActivity() {
        // Ouvindo retorno da api com a lista de usuarios
        viewModel.getStateListUser()
            .observe(this, Observer {
                when(it.status){
                    ViewState.Status.SUCCESS -> {
                        finishLoad()
                        binding.itemCore = it.data
                        initRecyclerView(it.data?.vehicles)
                    }
                    ViewState.Status.LOADING -> {
                        progressBar.show()
                    }
                }
            })

        // Ouvindo se retornar algum erro
        viewModel.getStatsViewError()
            .autoDisposable(scopeProvider)
            .subscribe {
                when(it.type) {
                    ViewErrorState.TypeExibe.SNACK -> {
                        finishLoad()
                        showSnackError(rootLayouMain, it, ::loadList)
                    }
                    ViewErrorState.TypeExibe.TOAST -> {
                        finishLoad()
                        toast(it.idMessage).show()
                    }
                    ViewErrorState.TypeExibe.ALERT -> {
                        finishLoad()
                        alert(getString(it.idMessage)) {
                            title = getString(R.string.algo_errado)
                            isCancelable = false
                            if (it.retry){
                                positiveButton(R.string.retry) {
                                    loadList()
                                }
                                cancelButton {

                                }
                            }else
                                yesButton {

                                }
                        }.show()
                    }
                }
            }
    }

    private fun loadList(){
        viewModel.listCars()
    }

    private fun finishLoad() {
        recyclerList.show()
        progressBar.gone()
    }

    private fun initRecyclerView(listCars: List<VehicleBind>?) {

        if (listCars!= null) {
            // Define o Staggered para o efeito Waterfall
            recyclerList.layoutManager = LinearLayoutManager(this)
            carListAdapter.setItems(listCars)
            recyclerList.adapter = carListAdapter
        }
    }

    private fun userSelected(vehicle: VehicleBind, position: Int, view: View){
        // configura a chamada com animação para Próxima Activity
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            view,
            position.toString()
        )
        val bundle = Bundle()
        bundle.putParcelable(ShowDetailsActivity.VEHICLE_SELECTED, vehicle)
        bundle.putInt(ShowDetailsActivity.ID_TRANSITION, position)
        startActivity(
            Intent(this, ShowDetailsActivity::class.java)
                .apply {
                    putExtras(bundle)
                }, options.toBundle())
    }
}
