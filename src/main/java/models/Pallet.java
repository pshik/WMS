package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Pallets", uniqueConstraints = {@UniqueConstraint(columnNames = {"material"})})
public class Pallet implements Serializable,Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long Id;
    @Column(name = "material")
    private String material;
    @Column(name = "size")
    private int size;
    @Column(name = "loadingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loadingDate;
    @Column(name = "position")
    private int position;
    @Column(name = "address")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public Pallet(String material, int size, Date loadingDate,String address) {
        this.material = material;
        this.size = size;
        this.loadingDate = loadingDate;
        position = 0;
        this.address = address;
    }

    public String getMaterial() {
        return material;
    }

    public int getSize() {
        return size;
    }

    public Pallet() {

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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

    @Override
    public int compareTo( Object o) {
        Pallet tmp = (Pallet) o;
        if (this.getLoadingDate().equals(tmp.getLoadingDate())) return 0;
        return this.getLoadingDate().before(tmp.getLoadingDate())? -1: 1;
    }
}
