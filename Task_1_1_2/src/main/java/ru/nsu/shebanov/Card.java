package ru.nsu.shebanov;

public class Card {
    String name;
    int weight;

    Card(String name, int weight){
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.weight + ")";
    }
}
