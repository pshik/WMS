package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class Pallet implements Serializable {

    private String material;

    private int size;

    private Date loadingDate;

    private int position;


    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public Date getLoadingDate() {
        return loadingDate;
    }


//size 1 - small, 2 - middle, 3 - big


    @Override
    public String toString() {
        return "Pallet{" +
                "material='" + material + '\'' +
                ", size=" + size +
                ", loadingDate=" + loadingDate +
                ", position=" + position +
                '}';
    }

    public Pallet(String material, int size, Date loadingDate,int position) {
        this.material = material;
        this.size = size;
        this.loadingDate = loadingDate;
        this.position = position;
    }

    public String getMaterial() {
        return material;
    }

    public int getSize() {
        return size;
    }

    public Pallet() {

    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setLoadingDate(Date loadingDate) {
        this.loadingDate = loadingDate;
    }

}
