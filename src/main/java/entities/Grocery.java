package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grocery")
@NamedQuery(name = "Grocery.deleteAllRows", query = "DELETE from Grocery")
public class Grocery {
    @Id
    @Column(name = "ID_Ra_500prod")
    private String idRa500prod;

    @Column(name = "Navn")
    private String name;

    @Column(name = "DSK_Kategori")
    private String dskCategory;

    @Column(name = "Unit")
    private String unit;

    @Column(name = "Agriculture")
    private Double agriculture;

    @Column(name = "iLUC")
    private Double iLUC;

    @Column(name = "Food_processing")
    private Double foodProcessing;

    @Column(name = "Packaging")
    private Double packaging;

    @Column(name = "Transport")
    private Double transport;

    @Column(name = "Retail")
    private Double retail;

    @Column(name = "`Total_kg_CO2-eq/kg`")
    private Double totalKgCo2EqKg;

    @OneToMany(mappedBy = "grocery", orphanRemoval = true)
    private List<GroceryLine> groceryLines = new ArrayList<>();

    public Grocery() {
    }

    public Grocery(String idRa500prod, String name, String dskCategory, String unit, Double agriculture, Double iLUC, Double foodProcessing, Double packaging, Double transport, Double retail, Double totalKgCo2EqKg) {
        this.idRa500prod = idRa500prod;
        this.name = name;
        this.dskCategory = dskCategory;
        this.unit = unit;
        this.agriculture = agriculture;
        this.iLUC = iLUC;
        this.foodProcessing = foodProcessing;
        this.packaging = packaging;
        this.transport = transport;
        this.retail = retail;
        this.totalKgCo2EqKg = totalKgCo2EqKg;
    }

    public Double getTotalKgCo2EqKg() {
        return totalKgCo2EqKg;
    }

    public void setTotalKgCo2EqKg(Double totalKgCo2EqKg) {
        this.totalKgCo2EqKg = totalKgCo2EqKg;
    }

    public Double getRetail() {
        return retail;
    }

    public void setRetail(Double retail) {
        this.retail = retail;
    }

    public Double getTransport() {
        return transport;
    }

    public void setTransport(Double transport) {
        this.transport = transport;
    }

    public Double getPackaging() {
        return packaging;
    }

    public void setPackaging(Double packaging) {
        this.packaging = packaging;
    }

    public Double getFoodProcessing() {
        return foodProcessing;
    }

    public void setFoodProcessing(Double foodProcessing) {
        this.foodProcessing = foodProcessing;
    }

    public Double getILUC() {
        return iLUC;
    }

    public void setILUC(Double iLUC) {
        this.iLUC = iLUC;
    }

    public Double getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(Double agriculture) {
        this.agriculture = agriculture;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDskCategory() {
        return dskCategory;
    }

    public void setDskCategory(String dskCategory) {
        this.dskCategory = dskCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdRa500prod() {
        return idRa500prod;
    }

    public void setIdRa500prod(String idRa500prod) {
        this.idRa500prod = idRa500prod;
    }

    public List<GroceryLine> getGroceryLines() {
        return groceryLines;
    }

    public void addGroceryLine(GroceryLine groceryLine) {
        groceryLines.add(groceryLine);
        groceryLine.setGrocery(this);
    }

    @Override
    public String toString() {
        return "Grocery{" +
                "idRa500prod='" + idRa500prod + '\'' +
                ", name='" + name + '\'' +
                ", dskCategory='" + dskCategory + '\'' +
                ", unit='" + unit + '\'' +
                ", agriculture=" + agriculture +
                ", iLUC=" + iLUC +
                ", foodProcessing=" + foodProcessing +
                ", packaging=" + packaging +
                ", transport=" + transport +
                ", retail=" + retail +
                ", totalKgCo2EqKg=" + totalKgCo2EqKg +
                ", groceryLines=" + //groceryLines +
                '}';
    }
}