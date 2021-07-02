package models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table(name = "References", uniqueConstraints = {@UniqueConstraint(columnNames = {"reference"})})
public class Reference implements Serializable,Comparable<Reference> {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private long Id;
        @Column(name = "reference")
        private String reference;
        @Column(name = "size")
        private int size;
        @Column(name = "description")
        private String description;
        @Column(name = "allowedRacks")
        private String[] allowedRacks;

        public Reference() {
        }

        public Reference(String reference, String description, int size, String... list) {
            this.reference = reference;
            this.size = size;
            this.description = description;
            this.allowedRacks = list;
        }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
            return reference;
        }

        public void removeAllowedRack(String rackName){
            ArrayList<String> tmp = new ArrayList<>();

            for (int i = 0; i < allowedRacks.length; i++){
                if (!allowedRacks[i].equals(rackName)){
                    tmp.add(allowedRacks[i]);
                }
            }
            String[] temp = new String[tmp.size()];
            for (int i = 0; i < tmp.size(); i++){
                temp[i] = tmp.get(i);
            }
            allowedRacks = temp;
        }

        public int getSize() {
            return size;
        }

        public void addAllowedRack(String rackName){
            String[] tmp = new String[allowedRacks.length + 1];
            for (int i = 0; i < allowedRacks.length;i++){
                tmp[i] = allowedRacks[i];
            }
            tmp[allowedRacks.length] = rackName;
            allowedRacks = tmp;
        }
        public String[] getAllowedRacks() {
            return allowedRacks;
        }

        public void setAllowedRacks(String[] allowedRacks) {
            this.allowedRacks = allowedRacks;
        }


        @Override
        public String toString() {
            return "SAPReference{" +
                    "reference='" + reference + '\'' +
                    ", size=" + size +
                    ", description='" + description + '\'' +
                    ", allowedRacks=" + Arrays.toString(allowedRacks) +
                    '}';
        }

        public String getDescription() {
            return description;
        }

        public boolean isAllowedRack(String rackNameForChecking) {
            for (String s: allowedRacks){
                if (s.equals(rackNameForChecking)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public int compareTo(@NotNull Reference o) {
            return reference.compareTo(o.reference);
        }
}
