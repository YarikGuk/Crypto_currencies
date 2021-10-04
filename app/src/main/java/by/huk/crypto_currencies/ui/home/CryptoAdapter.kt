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
import by.huk.crypto_currencies.ui.utils.PRICE_CHANGE
import by.huk.crypto_currencies.ui.utils.SORT_BY_MARKET_CAP
import by.huk.crypto_currencies.ui.utils.USD
import com.squareup.picasso.Picasso

class CryptoAdapter(val viewModel: MainViewModel):RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    private var cryptoList = ArrayList<CryptoEntity>()

    fun initialize(list: List<CryptoEntity>) {
        if(cryptoList.isNullOrEmpty()) {
            cryptoList = list.toMutableList() as ArrayList<CryptoEntity>
        }else{
            cryptoList.addAll(list.toMutableList() as ArrayList<CryptoEntity>)
        }
        notifyDataSetChanged()
    }


    inner class CryptoViewHolder(private val binding: ItemCryptoBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bind(itemView:View,position: Int){
            val cryptoItem = cryptoList[position]

            binding.token = cryptoItem
            Picasso.get().load(cryptoItem.image).error(R.drawable.ic_money).into(binding.itemIcon)
            binding.itemPrice.text = String.format("%.2f",cryptoItem.currentPrice)

            if (position == cryptoList.lastIndex) viewModel.loadNextPage(USD,SORT_BY_MARKET_CAP,20,true,PRICE_CHANGE)


        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): CryptoAdapter.CryptoViewHolder {
        val binding = DataBindingUtil.inflate<ItemCryptoBinding>(LayoutInflater.from(parent.context),R.layout.item_crypto,parent,false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoAdapter.CryptoViewHolder, position: Int) {
        holder.bind(holder.itemView,position)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}