package models;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table(name = "SapReferences", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})

public class SapReference implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long Id;
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private int size;
    @Column(name = "description")
    private String description;

    @Column(name = "allowedRackIds")
    private Integer[] allowedRackIds;

    public SapReference() {
    }

    public SapReference(String name, int size, String description, Integer[] allowedRackIds) {
        this.name = name;
        this.size = size;
        this.description = description;
        this.allowedRackIds = allowedRackIds;
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

    public void setSize(int size) {
        this.size = size;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

//    public void removeAllowedRack(String rackName){
//        ArrayList<String> tmp = new ArrayList<>();
//
//        for (int i = 0; i < allowedRacks.length; i++){
//            if (!allowedRacks[i].equals(rackName)){
//                tmp.add(allowedRacks[i]);
//            }
//        }
//        String[] temp = new String[tmp.size()];
//        for (int i = 0; i < tmp.size(); i++){
//            temp[i] = tmp.get(i);
//        }
//        allowedRacks = temp;
//    }

    public int getSize() {
        return size;
    }

//    public void addAllowedRack(String rackName){
//        String[] tmp = new String[allowedRacks.length + 1];
//        for (int i = 0; i < allowedRacks.length;i++){
//            tmp[i] = allowedRacks[i];
//        }
//        tmp[allowedRacks.length] = rackName;
//        allowedRacks = tmp;
//    }
//    public String[] getAllowedRacks() {
//        return allowedRacks;
//    }
//
//    public void setAllowedRacks(String[] allowedRacks) {
//        this.allowedRacks = allowedRacks;
//    }


    public Integer[] getAllowedRackIds() {
        return allowedRackIds;
    }

    public void setAllowedRackIds(Integer[] allowedRackIds) {
        this.allowedRackIds = allowedRackIds;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "SapReference{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", description='" + description + '\'' +
                ", allowedRackIds=" + Arrays.toString(allowedRackIds) +
                '}';
    }

    //    public boolean isAllowedRack(String rackNameForChecking) {
//        for (String s: allowedRacks){
//            if (s.equals(rackNameForChecking)){
//                return true;
//            }
//        }
//        return false;
//    }


}
