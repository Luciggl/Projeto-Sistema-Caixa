package Model.entities;

import Model.enums.Category;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Products implements Serializable {
    private int id;
    private final String name;
    private String manufacturer;
    private Category category;
    private BigDecimal value;
    private int quanti;


    public Products(int id, String name, String manufacturer, Category category, BigDecimal value, int quanti) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.category = category;
        this.value = value;
        this.quanti = quanti;
    }

    public Products(String name) {
        this.name = name;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
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
        return "\n" + name + " Qnt: " + quanti + " R$:" + value + " Id: " + id + "\n" + "---------------------------------------------------------------------------------\n";
    }
}