package Model.entities;

import Model.enums.Category;

import java.io.Serializable;
import java.util.Objects;

public class Products implements Serializable {
    private double id;
    private String name;
    private String manufacturer;
    private Model.enums.Category Category;
    private Double value;
    private int quanti;

    public Products() {
    }

    public Products(double id, String name, String manufacturer, Model.enums.Category category, Double value, int quanti) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        Category = category;
        this.value = value;
        this.quanti = quanti;
    }

    public double getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Model.enums.Category getCategory() {
        return Category;
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
        return "Product: \n" +
                "id: " + id + '\n' +
                "Name: " + name + '\n' +
                "Manufacturer: " + manufacturer + '\n' +
                "Category: " + Category + '\n' +
                "Value: " + value + '\n' +
                "Quantity: " + quanti;
    }
}
