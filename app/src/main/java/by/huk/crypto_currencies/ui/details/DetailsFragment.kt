package by.huk.crypto_currencies.ui.details

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.huk.crypto_currencies.R
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.data.entities.crypto.MarketChart
import by.huk.crypto_currencies.databinding.FragmentDetailsBinding
import by.huk.crypto_currencies.ui.utils.*
import com.bumptech.glide.Glide
import com.yabu.livechart.model.DataPoint
import com.yabu.livechart.model.Dataset
import com.yabu.livechart.view.LiveChart
import com.yabu.livechart.view.LiveChartStyle
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class DetailsFragment() : Fragment() {

    private val viewModel by inject<DetailsViewModel>()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var items: Array<TextView>
    private var coin: CryptoEntity? = null
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coin = args.coin
        items = arrayOf(binding.item24h,
            binding.item1w,
            binding.item1m,
            binding.item1y,
            binding.itemAll)

        with(binding) {
            token = coin
            price.text = convertPrice(coin?.currentPrice!!)
            marketCap.text = convertPrice(coin?.marketCap!!)
            percent.text = convertPercent(coin?.priceChangePercentage24hInCurrency!!)
            loadingAnim.setBackgroundResource(R.drawable.custom_progressbar_anim)
        }
        Glide.with(this).load(coin?.image).into(binding.symbolToolbar)
        val anim = binding.loadingAnim.background as AnimatedVectorDrawable
        binding.executePendingBindings()

        var sparkline = coin?.sparklineIn7d.orEmpty()
        val list = mutableListOf<DataPoint>()
        for (i in sparkline.indices step 2) {
            list.add(DataPoint(i.toFloat(), sparkline[i].toFloat()))
        }
        drawChart(list)

        viewModel.isLoading.onEach {
            binding.isLoading = it
            if (it) anim.start()
        }.launchWhenStarted(lifecycleScope)

        viewModel.chartList.onEach {
            if (it.isNotEmpty()) drawChart(convertList(it))
        }.launchWhenStarted(lifecycleScope)

        binding.item24h.setOnClickListener {
            setBackgroundColor(0)
            viewModel.loadMarketChart(coin!!.id, DAY)
        }
        binding.item1w.setOnClickListener {
            setBackgroundColor(1)
            drawChart(list)
        }
        binding.item1m.setOnClickListener {
            setBackgroundColor(2)
            viewModel.loadMarketChart(coin!!.id, MONTH)
        }
        binding.item1y.setOnClickListener {
            setBackgroundColor(3)
            viewModel.loadMarketChart(coin!!.id, YEAR)
        }
        binding.itemAll.setOnClickListener {
            setBackgroundColor(4)
            viewModel.loadMarketChart(coin!!.id, MAX)
        }

        binding.detailsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun convertList(list: List<MarketChart>): MutableList<DataPoint> {
        val dpList = mutableListOf<DataPoint>()
        for (i in list.indices step 5) {
            dpList.add(DataPoint(i.toFloat(), list[i].price))
        }
        return dpList
    }

    private fun drawChart(list: MutableList<DataPoint>) {
        val chart = binding.priceChart
        val style = LiveChartStyle().apply {
            mainColor = requireContext().getColor(R.color.second_anim_color)
            pathStrokeWidth = 8f
        }

        chart.setDataset(Dataset(list))
            .setLiveChartStyle(style)
            .setOnTouchCallbackListener(object : LiveChart.OnTouchCallback {
                override fun onTouchCallback(point: DataPoint) {
                    binding.viewPoint.visibility = View.VISIBLE
                    binding.viewPoint.text =
                        point.y.toString().plus(requireContext().getString(R.string.dollar))
                }

                override fun onTouchFinished() {
                    binding.viewPoint.visibility = View.GONE
                }
            })
            .drawLastPointLabel()
            .drawSmoothPath()
            .drawDataset()


    }

    private fun convertPercent(percent: Double): String {
        return if (percent > 0) {
            getString(R.string.plus).plus(String.format("%.2f", percent))
                .plus(getString(R.string.percent))
        } else String.format("%.2f", percent).plus(getString(R.string.percent))
    }

    private fun convertPrice(price: Double): String {
        return String.format("%.2f", price).plus(getString(R.string.dollar))
    }

    private fun convertPrice(long: Long): String {
        val price = long.toDouble().div(1000000000.0)
        return if (price > 0) getString(R.string.dollar).plus(String.format("%.2f", price)
            .plus(getString(R.string.billion)))
        else getString(R.string.dollar).plus(String.format("%.2f", long.toDouble()))

    }

    @SuppressLint("ResourceAsColor")
    fun setBackgroundColor(checkedItem: Int) {
        items.forEach {
            it.setBackgroundResource(R.drawable.shape_white)
            it.setTextColor(Color.GRAY)
        }
        items[checkedItem].setBackgroundResource(R.drawable.shape_green)
        items[checkedItem].setTextColor(Color.WHITE)
    }

}