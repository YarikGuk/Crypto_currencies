package by.huk.crypto_currencies.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import by.huk.crypto_currencies.MainViewModel
import by.huk.crypto_currencies.R
import by.huk.crypto_currencies.data.entities.crypto.CryptoEntity
import by.huk.crypto_currencies.databinding.ItemCryptoBinding
import by.huk.crypto_currencies.ui.utils.*
import com.squareup.picasso.Picasso

class CryptoAdapter(val viewModel: MainViewModel) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    var sort = 0
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
            Picasso.get().load(cryptoItem.image).error(R.drawable.ic_money).into(binding.itemIcon)
            binding.itemPrice.text = String.format("%.2f", cryptoItem.currentPrice)


            if (position == cryptoList.lastIndex) {
                when (sort) {
                    0 -> viewModel.loadNextPage(SORT_BY_MARKET_CAP)
                    1 -> viewModel.loadNextPage(SORT_BY_PRICE)
                    2 -> viewModel.loadNextPage(SORT_BY_VOLUME)
                }
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