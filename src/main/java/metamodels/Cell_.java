package metamodels;

import models.Cell;
import models.Pallet;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.ArrayList;
import java.util.List;

@StaticMetamodel(Cell.class)
public abstract class Cell_ {
    public static volatile SingularAttribute<Cell, Long> Id;
    public static volatile SingularAttribute<Cell, String>  name;
    public static volatile SingularAttribute<Cell, Integer>  row;
    public static volatile SingularAttribute<Cell, Integer>  col;
    public static volatile SingularAttribute<Cell, Long[]>  palletIDs;
    public static volatile SingularAttribute<Cell, Boolean>  blocked;
}
