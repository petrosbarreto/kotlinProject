package br.com.roadmaps.kotlinproject

/**
 * Created by sidd on 24/05/18.
 */
class Car (name: String, year:Int): Vehicle (nome = name , ano = year){
    var name: String
    var year: Int

    init{
        this.name = name;
        this.year = year;
    }

    override fun toString(): String {
        return "Carro $name, ano $year"
    }

}