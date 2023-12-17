package Model.entities;

import Model.enums.Category;

import java.io.Serializable;
import java.util.Objects;

public class Products implements Serializable {
    private final int id;
    private final String name;
    private final String manufacturer;
    private final Category category;
    private Double value;
    private int quanti;


    public Products(int id, String name, String manufacturer, Category category, Double value, int quanti) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
        this.value = value;
        this.quanti = quanti;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Category getCategory() {
        return category;
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
        return Double.compare(getId(), products.getId()) == 0 && Objects.equals(getName(), products.getName()) && Objects.equals(getManufacturer(), products.getManufacturer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getManufacturer());
    }

    @Override
    public String toString() {
        String ProdutoString = name + " Qnt: " + quanti + " R$:" + value + " Id: " + id + "\n" + "---------------------------------\n";
        return ProdutoString;
    }
}
