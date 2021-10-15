package by.huk.crypto_currencies.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import by.huk.crypto_currencies.MainViewModel
import by.huk.crypto_currencies.R
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.databinding.ItemCryptoBinding
import by.huk.crypto_currencies.ui.utils.SORT_BY_MARKET_CAP
import by.huk.crypto_currencies.ui.utils.SORT_BY_PRICE
import by.huk.crypto_currencies.ui.utils.SORT_BY_VOLUME
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CryptoAdapter(val viewModel: HomeViewModel) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    var sort = viewModel.checkedItem
    private var cryptoList = ArrayList<CryptoEntity>()

    fun initialize(list: List<CryptoEntity>) {
        if (cryptoList.isNullOrEmpty()) {
            cryptoList = list.toMutableList() as ArrayList<CryptoEntity>
        } else {
            cryptoList.addAll(list.toMutableList() as ArrayList<CryptoEntity>)
        }
        notifyDataSetChanged()
    }

    fun refreshList(checkedItem: Int) {
        cryptoList.clear()
        sort = checkedItem
        notifyDataSetChanged()
    }


    inner class CryptoViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemView: View, position: Int) {

            val cryptoItem = cryptoList[position]

            binding.token = cryptoItem
            binding.itemPrice.text = String.format("%.2f", cryptoItem.currentPrice).plus("\t$")

            binding.itemIcon.apply {
                transitionName = cryptoItem.name
                Glide.with(context)
                    .load(cryptoItem.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(this)
            }
            binding.executePendingBindings()

            if (position == cryptoList.lastIndex) {
                when (sort) {
                    0 -> viewModel.loadNextPage(SORT_BY_MARKET_CAP)
                    1 -> viewModel.loadNextPage(SORT_BY_PRICE)
                    2 -> viewModel.loadNextPage(SORT_BY_VOLUME)
                }
            }



            binding.cardView.setOnClickListener {
                val direction = HomeFragmentDirections.showDetailsFragment(cryptoItem)
                val extras = FragmentNavigatorExtras(
                    binding.itemPrice to cryptoItem.id
                )
                itemView.findNavController().navigate(direction, extras)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CryptoAdapter.CryptoViewHolder {
        val binding =
            DataBindingUtil.inflate<ItemCryptoBinding>(LayoutInflater.from(parent.context),
                R.layout.item_crypto,
                parent,
                false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoAdapter.CryptoViewHolder, position: Int) {
        holder.bind(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }


}