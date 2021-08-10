package models;



import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Cells", uniqueConstraints = {@UniqueConstraint(columnNames = {"address"})})
public class Cell implements Serializable {
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
    private Pallet[] pallets;
    @Column(name = "blocked")
    private boolean blocked;

    public Cell(String address, int row, int col) {
        this.address = address;
        this.row = row;
        this.col = col;
        this.pallets = new Pallet[3];
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
                ", pallets=" + pallets +
                ", blocked=" + blocked + '\'' +
                '}';
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Pallet[] getPallets() {
        return pallets;
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

    public boolean addPallet(Pallet testPallet) {
        if(checkPallets(testPallet)) {
            for (int i = 0; i < pallets.length; i++) {
                if (pallets[i] == null) {
                    pallets[i] = testPallet;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkPallets(Pallet testPallet) {
        if(pallets[0] == null && pallets[1] == null && pallets[2] == null){
            return true;
        }
        switch (testPallet.getPosition()){
            case 2:
                for(Pallet p: pallets){
                    if(p != null){
                        return p.getPosition() != 1 && p.getPosition() != 2 && p.getPosition() != 4 && p.getPosition() != 5;
                    }
                }
                break;
            case 3:
                for(Pallet p: pallets){
                    if(p != null){
                        return p.getPosition() != 1 && p.getPosition() != 3 && p.getPosition() != 5 && p.getPosition() != 6;
                    }
                }
                break;
            case 4:
                for(Pallet p: pallets){
                    if(p != null){
                        return p.getPosition() != 1 && p.getPosition() != 2 && p.getPosition() != 4;
                    }
                }
                break;
            case 5:
                for(Pallet p: pallets){
                    if(p != null){
                        return p.getPosition() != 1 && p.getPosition() != 2 && p.getPosition() != 3 && p.getPosition() != 5;
                    }
                }
                break;
            case 6:
                for(Pallet p: pallets){
                    if(p != null){
                        return p.getPosition() != 1 && p.getPosition() != 3 && p.getPosition() != 6;
                    }
                }
                break;
            default:return false;
        }
        return false;
    }
    public void deletePallet(int position) {
        for(int i = 0;i < pallets.length;i++){
            if(pallets[i] !=null) {
                if (pallets[i].getPosition() == position) {
                    pallets[i] = null;
                }
            }
        }
    }
}
