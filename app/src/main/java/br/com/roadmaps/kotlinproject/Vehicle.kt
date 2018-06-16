package br.com.roadmaps.kotlinproject

/**
 * Created by sidd on 08/06/18.
 */
open class Vehicle (nome: String, ano: Int) {
    val nome: String
    val ano: Int

    init {
        this.nome = nome;
        this.ano = ano;
    }

    open fun acelerar (velocidade: Int){
        println("Acelerando até $velocidade")
    }

    override fun toString(): String {
        return "Automovel $nome, ano: $ano"
    }

}