package br.com.roadmaps.kotlinproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        intent.getStringExtra("Name")
    }

    fun showToast (view: View){
        toast("Hi there!")
    }

    fun showLongToast (view: View){
//        longToast("Wow,Hi there!")
//        snackbar(view, "Teste")
//
//        alert("Hi, I'm Roy", "Have you tried turning it off and on again?") {
//            yesButton { toast("Oh…") }
//            noButton {}
//        }.show()

//        val countries = listOf("Russia", "USA", "Japan", "Australia")
//        selector("Where are you from?", countries, { dialogInterface, i ->
//            toast("So you're living in ${countries[i]}, right?")
//        })

//        val dialog = progressDialog(message = "Please wait a bit…", title = "Fetching data")

        indeterminateProgressDialog("Wait", "Title")

    }

}
