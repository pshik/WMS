package metamodels;

import models.Cell;
import models.Rack;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Rack.class)
public abstract class Rack_ {
    public static volatile SingularAttribute<Rack, Long>  Id;
    public static volatile SingularAttribute<Rack, String>  name;
    public static volatile SingularAttribute<Rack, Integer>  row;
    public static volatile SingularAttribute<Rack, Integer>  col;
    public static volatile SingularAttribute<Rack, Cell[][]> cells;
}
