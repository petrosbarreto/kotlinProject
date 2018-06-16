package br.com.roadmaps.kotlinproject

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.github.rodlibs.persistencecookie.PersistentCookieStore
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    var mDialog: ProgressDialog? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()

        var myCookieStore = PersistentCookieStore(this);
        if ((!myCookieStore.getCookies().isEmpty()) &&
                myCookieStore.getCookies().get(0).getDomain()
                        .equals("teste-aula-ios.herokuapp.com")) {
            getComments()
        }else{
            login()
        }


        textView.text = "Novo valor";

        var nome:String = "Chibata"

        println(parOuImpar(1));


        var nome2 = "Chibata 2"

        nome2 = "$nome2 $nome";

        val nome3 = "$nome2"

        val s:Any = "Maria Madalena"

        println(s as String)
        println(s as? Int)

        if(s is String){
            println("$s é uma string")
        }

        var nome4:String? = "Chibata"
        nome4 = null



        if(nome4 != null) {
            println(nome4.length)
        }

        println(alterarNome("Sidd", "Moraes"));

        println(somar(10,20))
       // getInteger("sidd", valueInt = 10);
        val car = Car("Corsa", 2001);
        println(car)



        val ints = listOf(1,2,3,4,5)
        var list = filtrar(ints) { numerosPares(it) }
        println(list);


        val ints2 = listOf(1,2,3,4,5,10,20,30)
        var list2 = filtrar(ints2){ numeroMaiorQue3(it)}
        filtrar(ints2) { numeroMaiorQue3(it) }
        println(list2);


    }

    fun login(){
        if(isNetworkAvaliabe()) {
            var progress =indeterminateProgressDialog ("Aguarde...", "Login" )
//            mDialog = ProgressDialog.show(this, "Aguarde...", ""
//                    , true, true)
            var net = Network2(this)
            net.login(object : Network.HttpCallback {
                override fun onSuccess(response: String) {
                    runOnUiThread {
                        progress.cancel()

                        toast("SUCESSO")
//                        getComments();
                    }
                }

                override fun onFailure(response: String?, throwable: Throwable?) {
                    runOnUiThread {
                        progress.cancel();
                        longToast("FALHA:  " + response)
                    }
                }
            })
        }
    }

    fun getComments(){
        if(isNetworkAvaliabe()) {
            var progress = indeterminateProgressDialog ("Coletando comentários...", "Aguarde...")
            var net = Network2(this)
            net.getComments(object : Network.HttpCallback {
                override fun onSuccess(response: String) {
                    runOnUiThread {
                        progress.cancel()
                        var jsonArray = JSONArray(response)

//                        swipeRefresh?.isRefreshing = false
//                        setGridView(response)
                        longToast("Ok peguei os comentarios")
                    }
                }

                override fun onFailure(response: String?, throwable: Throwable?) {
                    runOnUiThread {
                        progress.cancel()
//                        swipeRefresh?.isRefreshing = false
                        toast("FALHA:  " + response)
                    }
                }
            })
        }
    }


    @SuppressLint("MissingPermission")
    fun isNetworkAvaliabe(): Boolean{
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return if (info != null && info.isConnected) {
            true
        } else {
            false
        }
    }

    fun somar(a: Int, b: Int):Int = a + b

    fun alterarNome(firstName: String, secondName: String ): String{
        return "$secondName $firstName"
    }

    fun String.ascii():String{
        var s = ""
        for (c in this){
            s += c.toInt()
        }
        return s;
    }

    fun main(){
        var nome = "Siddhartha"
        println("Teste ${nome.length}")
        println("Teste sem quebra de linha $nome")
        println("Teste = ${nome.ascii()}")
        textView.text = "Sidd Top"
    }

    fun nextActivity(view: View){
//               startActivity(Intent(this@MainActivity, Main2Activity::class.java)
//                .putExtra("Name", "Sidd")
//                .putExtra("Value", 10)
//                .putExtra("Value2", 2.10));

        startActivity<Main2Activity>()
        startActivity<Main2Activity>("Name" to "Sidd", "Name2" to "Chibata", "Value" to 10)
    }

    fun getInteger(s: String?, valueInt: Int): Int{
        if(s != null){
            return s.toInt();
        }
        return valueInt;
    }

    fun parOuImpar(a: Int): String{
        return if (a % 2 == 0) "par" else "impar";
    }

    fun enviarEmail (usario: String, titulo: String? = null): String{
        val s = titulo?: "Bem Vindo"
        return "$s $usario"
    }


    fun numerosPares(numero: Int) = numero % 2 == 0

    fun numeroMaiorQue3(numero: Int) = numero > 3

    fun filtrar (list: List<Int>, filtro:(Int) -> (Boolean)): List<Int>{
        val newList = arrayListOf<Int>()
        for(item in list){
            if(filtro(item)){
                newList.add(item)
            }
        }
        return newList
    }
}


