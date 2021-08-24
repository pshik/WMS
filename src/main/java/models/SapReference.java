package models;

import com.sun.istack.NotNull;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "SapReferences", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})

public class SapReference implements Comparable {
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
    private Long[] allowedRackIds;

 //   private StringProperty allowedRacks;

    public SapReference() {
    }

    public SapReference(String name, int size, String description, Long[] allowedRackIds) {
        this.name = name;
        this.size = size;
        this.description = description;
        this.allowedRackIds = allowedRackIds;
      //  setAllowedRacks();
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

//    public String getAllowedRacks() {
//        return allowedRacks.get();
//    }
//
//    public StringProperty allowedRacksProperty() {
//        return allowedRacks;
//    }
//    private void setAllowedRacks(){
//        StringBuilder rackIds = new StringBuilder();
//
//        for (Integer n : allowedRackIds) {
//            rackIds.append(n).append(",");
//        }
//
//        rackIds.deleteCharAt(rackIds.length() - 1);
//        allowedRacks = new SimpleStringProperty(this, rackIds.toString());
//    }
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


    public Long[] getAllowedRackIds() {
        return allowedRackIds;
    }

    public void setAllowedRackIds(Long[] allowedRackIds) {
        this.allowedRackIds = allowedRackIds;
       // setAllowedRacks();
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder rackIds = new StringBuilder();

        for (Long n : allowedRackIds) {
            rackIds.append(n).append(",");
        }

        rackIds.deleteCharAt(rackIds.length() - 1);

        return "SapReference{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", description='" + description + '\'' +
                ", allowedRackIds=" + rackIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SapReference reference = (SapReference) o;
        return size == reference.size && name.equals(reference.name) && description.equals(reference.description) && Arrays.equals(allowedRackIds, reference.allowedRackIds);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, size, description);
        result = 31 * result + Arrays.hashCode(allowedRackIds);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        SapReference s = (SapReference) o;
        return this.getName().compareTo(s.getName());
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
