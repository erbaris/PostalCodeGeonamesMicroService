package org.csystem.android.app.geonames.postalcodesearch

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.borasahin.android.library.geonames.postalcode.data.service.PostalCodeAppService
import com.borasahin.android.library.geonames.postalcode.data.service.mapper.geonames.IPostalCodeMapper
import com.gokhandiyaroglu.android.library.geonames.postalcodesearch.retrofit.api.IPostalCodeSearch
import com.gokhandiyaroglu.android.library.geonames.postalcodesearch.retrofit.data.entity.PostalCodes
import com.karandev.util.retrofit.RetrofitUtil
import dagger.hilt.android.AndroidEntryPoint
import org.csystem.android.app.geonames.postalcodesearch.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var postalCodeSearch: IPostalCodeSearch

    @Inject
    lateinit var postalCodeAppService: PostalCodeAppService

    @Inject
    lateinit var postalCodeMapper: IPostalCodeMapper

    private lateinit var mBinding: ActivityMainBinding

    private fun initBinding()
    {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }


    private fun responseCallback(response: Response<PostalCodes>)
    {
        val postalCodes = response.body()

        if (postalCodes != null) {
            val places = postalCodes.codes.map { it.placeName }.reduce { r, p -> "$r $p" }.toString()

            val dtos = postalCodes.codes.map { it.code = mBinding.mainActivityEditTextPostalCode.text.toString(); postalCodeMapper.toPostalCodeSaveDTO(it) }

            postalCodeAppService.savePostalCode(dtos, {Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()})
                        {Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()}

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, postalCodes.codes)

            mBinding.mainActivityListViewPostalCodes.adapter = adapter

            Toast.makeText(this, places, Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(this, "Problem Occurs", Toast.LENGTH_LONG).show()
    }

    private fun failCallback(call: Call<PostalCodes>, ex: Throwable)
    {
        Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG).show()
        call.cancel()
    }


    private fun initialize()
    {
        initBinding()
        initListButton()
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        initialize()
    }


    fun listPlacesButtonClicked(postalCodeText: String)
    {
        val call = postalCodeSearch.findPostalCode(postalCodeText)

        RetrofitUtil.enqueue(call, {_, r -> responseCallback(r)}) {c, ex -> failCallback(c, ex)}
    }

    private fun initListButton()
    {
        mBinding.mainActivityButtonList.setOnClickListener {listPlacesButtonClicked(mBinding.mainActivityEditTextPostalCode.text.toString())}
    }

}