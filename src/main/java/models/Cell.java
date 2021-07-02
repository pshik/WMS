package models;



import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
@Table(name = "Cells", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Cell implements Serializable,Comparable<Cell> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long Id;
    @Column(name = "name")
    private String name;
    @Column(name = "row")
    private int row;
    @Column(name = "col")
    private int col;
    @Column(name = "pallets")
    private ArrayList<Pallet> pallets = new ArrayList<>();
    @Column(name = "blocked")
    private boolean blocked;

    public Cell(String name, int row, int col, Pallet pallet) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.pallets.add(pallet);
        this.blocked = false;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setPallets(ArrayList<Pallet> pallets) {
        this.pallets = pallets;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "name='" + name + '\'' +
                ", row='" + row + '\'' +
                ", col='" + col + '\'' +
                ", pallets=" + pallets +
                ", blocked=" + blocked +
                '}';
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public ArrayList<Pallet> getPallets() {
        if (pallets == null){
            return null;
        }else if (pallets.size() > 0) {
            if (pallets.get(0) == null) return null;
            return pallets;
        } else {
            pallets.add(null);
            return pallets;
        }
    }

    public void addPallet(Pallet pallet) {
        // if size < max size add pallet
        if (!isBlocked()) {
            if (pallets == null ){
                pallets = new ArrayList<>();
                pallets.add(pallet);
            } else if (pallets.get(0) == null) {
                pallets.clear();
                pallets.add(pallet);
            } else pallets.add(pallet);
        }//else sent error
    }

    public void pickUpPallet(int position,String pallet) {
        ArrayList<Pallet> tmp = new ArrayList<>();
        tmp.addAll(pallets);
        for (Pallet p : tmp) {
            if (p != null && p.getMaterial().equals(pallet) && p.getPosition() == position) {
                pallets.remove(p);
               // System.out.println(";");
            }
        }
        if (pallets.size() == 0) pallets.add(null);
    }

    public boolean isContainReference(String material) {
        if(pallets == null) return false;
        for (Pallet p : pallets){
            if (p.getMaterial().equals(material)) return true;
        }
        return false;
    }

    public Cell() {
    }

    @Override
    public int compareTo(@NotNull Cell o) {
        return name.compareTo(o.name);
    }
}
