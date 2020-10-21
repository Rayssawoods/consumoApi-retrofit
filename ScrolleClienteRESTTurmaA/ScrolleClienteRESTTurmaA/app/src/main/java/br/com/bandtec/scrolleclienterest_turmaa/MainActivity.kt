package br.com.bandtec.scrolleclienterest_turmaa

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // criando um objeto do tipo TextView
        val novoTv = TextView(baseContext)
        // baseContext é para "atrelar" o componete à Activity atual

        // configurando a TextView
        novoTv.text = "Meu texto em tempo de execução"
        novoTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        novoTv.setTextColor(Color.parseColor("#FF0099"))

        // adicionando a TextView no LinearLayout
        ll_conteudo.addView(novoTv)

        consumirApiSimples()
    }

    //funcao pra deletar filme
    fun deletarFilme(componente: View){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f861cfdc8a16a0016e6aacd.mockapi.io/bandtec-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiFilmesRequests::class.java)

        val dellFilmeUnico = requests.getFilme(Integer (63))

        dellFilmeUnico.enqueue(object:Callback<Filme>{
            override fun onFailure(call: Call<Filme>, t: Throwable) {
                Toast.makeText(baseContext, "ERRO $t" , Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Filme>, response: Response<Filme>) {
                Toast.makeText(baseContext, "Deletado", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun cadastrarFilme(componente: View){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f861cfdc8a16a0016e6aacd.mockapi.io/bandtec-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiFilmesRequests::class.java)

        val novoFilme = Filme(
            null,
            et_nome_filme.text.toString(),
            et_ano_filme.text.toString().toBigDecimal(),
            false,
            et_custo_filme.text.toString().toInt()
        )

        val callCriarFilme  = requests.postFilme(novoFilme)

        callCriarFilme.enqueue(object:Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(baseContext, "ERRO $t" , Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                //Toast.makeText(baseContext, "Cadastrado", Toast.LENGTH_SHORT).show()
                Toast.makeText(baseContext,
                    getString(R.string.txt_cadastrado),
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun consumirApiSimples(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://5f861cfdc8a16a0016e6aacd.mockapi.io/bandtec-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(ApiFilmesRequests::class.java)

        //vai buscar o filme com codigo 10
        val callFilmeUnico = requests.getFilme(Integer (10))

        callFilmeUnico.enqueue(object:Callback<Filme>{
            override fun onFailure(call: Call<Filme>, t: Throwable) {
                Toast.makeText(baseContext, "ERRO", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Filme>, response: Response<Filme>) {
                val novoTv = TextView(baseContext)

               // novoTv.text = "${response.body()?.nome} - ${response.body()?.custoProducao}"
                val filme = response.body()
                novoTv.text= getString(R.string.txt_filme,
                    filme?.nome,
                    filme?.ano,
                    filme?.custoProducao)

                novoTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                novoTv.setTextColor(Color.parseColor("#000000"))

                ll_conteudo.addView(novoTv)

            }

        })

    }

    fun consumirApi() {
        val retrofit = Retrofit.Builder()
         .baseUrl("https://5f861cfdc8a16a0016e6aacd.mockapi.io/bandtec-api/")
         .addConverterFactory(GsonConverterFactory.create())
         .build()
        //É criada a implementação em tempo real da Api
        val requests = retrofit.create(ApiFilmesRequests::class.java)



        //getFilmes é o endpoits que retorna uma call --preparação da chamada da api
        val callFilmes = requests.getFilmes()

        callFilmes.enqueue(object: Callback<List<Filme>>{
            override fun onFailure(call: Call<List<Filme>>, t: Throwable) {
                Toast.makeText(applicationContext, "Deu ruim $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Filme>>, response: Response<List<Filme>>) {
                response.body()?.forEach {
                    val novoTv = TextView(baseContext)

                    novoTv.text = "${it.nome} - ${it.custoProducao}"
                    novoTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                    novoTv.setTextColor(Color.parseColor("#000000"))

                    ll_conteudo.addView(novoTv)
                }
            }

        })

    }
}