package metamodels;

import models.Pallet;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import java.util.Date;

@StaticMetamodel(Pallet.class)
public abstract class Pallet_ {
    public static volatile SingularAttribute<Pallet, Long> Id;
    public static volatile SingularAttribute<Pallet, String> material;
    public static volatile SingularAttribute<Pallet, Integer> size;
    public static volatile SingularAttribute<Pallet, Date> loadingDate;
    public static volatile SingularAttribute<Pallet, Integer> position;
}
