package Model.entities;

import Model.enums.Categoria;

import java.util.Objects;

public class Products {
    private String name;
    private double id;
    private Categoria categoria;
    private Double value;

    public  Products(){}

    public Products(String name, double id, Categoria categoria, Double value) {
        this.name = name;
        this.id = id;
        this.categoria = categoria;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getId() {
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
        return "Product: \n" +
                "name: " + name + "\n" +
                "id: " + id + "\n" +
                "categoria: " + categoria + "\n" +
                "value: R$:" + value;
    }
}
