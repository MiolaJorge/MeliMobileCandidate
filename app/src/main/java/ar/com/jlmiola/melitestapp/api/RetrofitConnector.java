package ar.com.jlmiola.melitestapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ar.com.jlmiola.melitestapp.tools.Constants.URL_BASE_MELI;

public class RetrofitConnector {
    public WebServiceApi meliWebServiceApi;

    public RetrofitConnector() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_BASE_MELI).addConverterFactory(GsonConverterFactory.create()).build();
        this.meliWebServiceApi = retrofit.create(WebServiceApi.class);
    }

}
