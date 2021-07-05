package models;



import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "Cells", uniqueConstraints = {@UniqueConstraint(columnNames = {"address"})})
public class Cell implements Serializable,Comparable<Cell> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cell")
    private long Id;
    @Column(name = "address")
    private String address;
    @Column(name = "row")
    private int row;
    @Column(name = "col")
    private int col;
    @Column(name = "pallets")
    private Long[] palletIDs;
    @Column(name = "blocked")
    private boolean blocked;

    public Cell(String address, int row, int col) {
        this.address = address;
        this.row = row;
        this.col = col;
        this.palletIDs = new Long[6];
        this.blocked = false;

    }

    public long getId() {
        return Id;
    }


    public void setId(long id) {
        Id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setPalletIDs(Long[] palletIDs) {
        this.palletIDs = palletIDs;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "Id=" + Id +
                ", name='" + address + '\'' +
                ", row=" + row +
                ", col=" + col +
                ", palletIDs=" + Arrays.toString(palletIDs) +
                ", blocked=" + blocked + '\'' +
                '}';
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Long[] getPalletIDs() {
        return palletIDs;
    }

//    public void addPallet(Long palletID) {
//        // if size < max size add pallet
//        if (!isBlocked()) {
//            if (palletIDs == null ){
//                palletIDs = new ArrayList<>();
//                palletIDs.add(palletID);
//            } else if (palletIDs.get(0) == null) {
//                palletIDs.clear();
//                palletIDs.add(palletID);
//            } else palletIDs.add(palletID);
//        }//else sent error
//    }

//    public void pickUpPallet(int position,String palletMaterial) {
//        List<Long> tmp = new ArrayList<>();
//        tmp.addAll(palletIDs);
//        for (Pallet p : tmp) {
//            if (p != null && p.getMaterial().equals(palletMaterial) && p.getPosition() == position) {
//                palletIDs.remove(p);
//               // System.out.println(";");
//            }
//        }
//        if (palletIDs.size() == 0) palletIDs.add(null);
//    }

//    public boolean isContainReference(String material) {
//        if(palletIDs == null) return false;
//        for (Pallet p : palletIDs){
//            if (p.getMaterial().equals(material)) return true;
//        }
//        return false;
//    }

    public Cell() {
    }

    @Override
    public int compareTo(@NotNull Cell o) {
        return address.compareTo(o.address);
    }
}
