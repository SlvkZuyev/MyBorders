package com.overtimedevs.bordersproject.data.data_source.remote

import com.overtimedevs.bordersproject.data.data_source.remote.model.CountryDto
import dagger.Provides
import io.reactivex.Observable
import retrofit2.http.*
import javax.inject.Singleton

interface CountryApi {
    @FormUrlEncoded
    @Headers(
        "Content-type: application/x-www-form-urlencoded",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
        "Cookie: Apache=W_GmRg-AAABfCZsILM-ca-b4EkjA; kayak=ZA6fLWZdWHv4a8wWUcpz; kmkid=A-0zK-C7Q_mEyYCeRKkaIm0; kayak.mc=AYdy2F__RHy74AYKSnEvCn3gBCC5K2DYeW9pykF7taYlsYKb3xMzgr5dpfR1slfIhYRYTWu_fvj9mmrkk75ClrS4OzJdq63YUSQlzeR_71I1p5gOCoWx_amJuZ93UVJ4I9QfoHwl-3wfGicjIdL-NFmCA-XhLp7p29d3d6f2ji9-foBGG2YM_5p5bxQrysuHc8FeyiYVPCIrjaq6E0r4xZq_H1986M2dvm0jlQ4mZANViA9DRiDiLhAOzxRnsRh3TNC34Bc0UNREvw8oH48D3Hd1R83LVw-bdABPgUk0luolG5eRSt_lypmyXSWkm9OgniboD2PVloBMzaesWqriX3W9KyEwurkagvwEOs63PdWisENlXEXtFiqbGxl1_Mjs53B3ELbeOHUniYdHSpVwVaweRlVXmT5WzsKmAluuY6eX; g_state={\"i_p\":1635264138204,\"i_l\":4}; cluster=5; p1.med.sid=R-5wRjkkupes\$4kzKN0cmVV-C8twaMDy0sanJxxScRQAicIWWPaCY9b1DDskWXeFy; csid=b041fbd7-f47d-439a-8d3f-c3ec201523d9"
    )
    @POST("charm/horizon/uiapi/seo/marketing/travelrestrictions/CountriesTravelRestrictionsAction")
    fun getCountries(@Field("originCountryCode") originCountryCode : String = "ua") : Observable<List<CountryDto>>
}