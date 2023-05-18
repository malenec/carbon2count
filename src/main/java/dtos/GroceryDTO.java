package dtos;

import entities.Grocery;

import java.util.List;
import java.util.stream.Collectors;

public class GroceryDTO {
    private String idRa500prod;
    private String name;
    private String dskCategory;
    private String unit;
    private Double agriculture;
    private Double iLUC;
    private Double foodProcessing;
    private Double packaging;
    private Double transport;
    private Double retail;
    private Double totalKgCo2EqKg;


    public GroceryDTO(Grocery g) {
        this.idRa500prod = g.getIdRa500prod();
        this.name = g.getName();
        this.dskCategory = g.getDskCategory();
        this.unit = g.getUnit();
        this.agriculture = g.getAgriculture();
        this.iLUC = g.getILUC();
        this.foodProcessing = g.getFoodProcessing();
        this.packaging = g.getPackaging();
        this.transport = g.getTransport();
        this.retail = g.getRetail();
        this.totalKgCo2EqKg = g.getTotalKgCo2EqKg();
    }



    public static List<GroceryDTO> getDtos(List<Grocery> groceries) {
        return groceries.stream().map(g -> new GroceryDTO(g)).collect(Collectors.toList());
    }


    public String getIdRa500prod() {
        return idRa500prod;
    }

    public String getName() {
        return name;
    }

    public String getDskCategory() {
        return dskCategory;
    }

    public String getUnit() {
        return unit;
    }

    public Double getAgriculture() {
        return agriculture;
    }

    public Double getiLUC() {
        return iLUC;
    }

    public Double getFoodProcessing() {
        return foodProcessing;
    }

    public Double getPackaging() {
        return packaging;
    }

    public Double getTransport() {
        return transport;
    }

    public Double getRetail() {
        return retail;
    }

    public Double getTotalKgCo2EqKg() {
        return totalKgCo2EqKg;
    }

    @Override
    public String toString() {
        return "GroceryDTO{" +
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
                '}';
    }
}
