package dev.redfox.powerplay.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.powerplay.R
import dev.redfox.powerplay.adapters.BeerAdapter
import dev.redfox.powerplay.databinding.FragmentBeerBinding
import dev.redfox.powerplay.models.BeerModel
import dev.redfox.powerplay.viewmodels.BeerViewModel

@AndroidEntryPoint
class BeerFragment : Fragment() {
    private lateinit var beerAdapter: BeerAdapter

    private var _binding: FragmentBeerBinding? = null
    private val binding
        get() = _binding!!
    

    var beerList = ArrayList<BeerModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBeerBinding.inflate(inflater, container, false)
        
        val beerViewModel: BeerViewModel by viewModels()
        
        beerViewModel.getAllBeers()
        
        beerViewModel.response.observe(viewLifecycleOwner, Observer { 
            beerList = it.body() as ArrayList<BeerModel>
            
            beerAdapter = BeerAdapter(beerList)
            
            binding.apply { 
                this.beerRecyclerView.setHasFixedSize(true)
                this.beerRecyclerView.adapter = beerAdapter
                this.beerRecyclerView.layoutManager = LinearLayoutManager(context)
                this.shimmerEffect.visibility = View.INVISIBLE
            }


            beerAdapter.onItemClick = {
                Toast.makeText(context, "Share WhatsApp", Toast.LENGTH_SHORT).show()

                sendMessage(it.name)

            }

            beerAdapter.onItemLongClick = {
                val dialog = BeerBottomSheet(it)
                dialog.setCancelable(true)
                dialog.show(parentFragmentManager, "BeerBottomSheetDialogFragment")
            }
            
        })
        
        return binding.root
    }


    fun sendMessage(message:String) {

        // Creating intent with action send
        val intent = Intent(Intent.ACTION_SEND)

        // Setting Intent type
        intent.type = "text/plain"

        // Setting whatsapp package name
        intent.setPackage("com.whatsapp")

        // Give your message here
        intent.putExtra(Intent.EXTRA_TEXT, message)



        // Starting Whatsapp
        startActivity(intent)
    }

}