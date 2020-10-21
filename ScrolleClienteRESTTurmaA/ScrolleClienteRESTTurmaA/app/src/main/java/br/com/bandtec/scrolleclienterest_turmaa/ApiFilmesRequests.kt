package br.com.bandtec.scrolleclienterest_turmaa

import retrofit2.Call
import retrofit2.http.*

interface ApiFilmesRequests {

    //Mapeamento de 2 endpoits
    //Se a chamada do metodo obter sucesso ele vai trazer aq resposta em uma LISTA de filmes
    @GET("/filmes")
    fun getFilmes(): Call<List<Filme>>


    //Se uma chamada não retornar nada, colocar Call<Void> que significa que não retorna nada
    //Se a chamada do metodo obter sucesso ele vai trazer a resposta em um FILME
     @GET("/filmes/{id}")
    fun getFilme(@Path("id") id:Integer): Call<Filme>

    @DELETE("/filmes/{id}")
    fun deleteFilme(@Path("id") id:Integer):Call<Void>
    //Call<Void> Indica que este EndPoint não vai retornar nada

    //Cadastro de novo Filme
    @POST("/filmes")
    fun postFilme(@Body novoFilme: Filme): Call<Void>




}