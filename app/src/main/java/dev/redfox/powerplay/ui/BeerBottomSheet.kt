package dev.redfox.powerplay.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.powerplay.databinding.BeerBottomSheetBinding
import dev.redfox.powerplay.models.BeerModel


@AndroidEntryPoint
class BeerBottomSheet(val beerModel: BeerModel): BottomSheetDialogFragment(){

    companion object {
        const val TAG ="BeerBottomSheetDialogFragment"
    }

    lateinit var binding: BeerBottomSheetBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val beerInflater = LayoutInflater.from(requireContext())
        binding = BeerBottomSheetBinding.inflate(beerInflater)

        binding.apply{
            Picasso.get().load(beerModel.image_url).into(beerPic)
            beerName.text = beerModel.name
            beerTag.text = beerModel.tagline
            brewDate.text = "First Brewed: " + beerModel.first_brewed
            beerPh.text = "pH: " + beerModel.ph
            beerDescription.text = beerModel.description
        }

        return binding.root
    }

}