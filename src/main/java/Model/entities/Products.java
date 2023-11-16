package Model.entities;

import Model.enums.Categoria;

import java.io.Serializable;
import java.util.Objects;

public class Products implements Serializable {
    private String name;
    private double id;
    private Categoria categoria;
    private Double value;
    private int quanti;

    public  Products(){}

    public Products(String name, double id, Categoria categoria, Double value, int quanti) {
        this.name = name;
        this.id = id;
        this.categoria = categoria;
        this.value = value;
        this.quanti = quanti;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getQuanti() {
        return quanti;
    }

    public void setQuanti(int quanti) {
        this.quanti = quanti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Products products)) return false;
        return Double.compare(getId(), products.getId()) == 0 && Objects.equals(getName(), products.getName()) && getCategoria() == products.getCategoria();
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId(), getCategoria());
    }

    @Override
    public String toString() {
        return "Product:" +
                "\nname: " + name + "\n" +
                "ID: " + id + "\n" +
                "Categoria: " + categoria + "\n" +
                "Valor R$:" + value + "\n" +
                "Quantidade : " + quanti + "\n" +
                "-----------------------------\n";
    }
}
