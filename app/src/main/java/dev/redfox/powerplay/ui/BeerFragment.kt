package dev.redfox.powerplay.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.powerplay.adapters.BeerAdapter
import dev.redfox.powerplay.databinding.FragmentBeerBinding
import dev.redfox.powerplay.models.BeerModel
import dev.redfox.powerplay.viewmodels.BeerViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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
                shareToWhatsApp(it)
            }

            beerAdapter.onItemLongClick = {
                val dialog = BeerBottomSheet(it)
                dialog.setCancelable(true)
                dialog.show(parentFragmentManager, "BeerBottomSheetDialogFragment")
            }
            
        })
        
        return binding.root
    }

    fun shareToWhatsApp(it: BeerModel){
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {

                var shareText = "Name: ${it.name},\nTag: ${it.tagline}"
                val bitmapPath: String =
                    MediaStore.Images.Media.insertImage(context?.contentResolver, bitmap, "Beer", null)
                val bitmapUri = Uri.parse(bitmapPath)
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "image/*"
                shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
                shareIntent.setPackage("com.whatsapp")
                try {
                    context?.startActivity(shareIntent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(context, "Whatsapp is not installed on your device", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        }

        Picasso.get()
            .load(it.image_url)
            .into(target)

    }

}