package metamodels;

import models.SapReference;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SapReference.class)
public abstract class SapReference_ {

    public static volatile SingularAttribute<SapReference, Long> Id;
    public static volatile SingularAttribute<SapReference, String> reference;
    public static volatile SingularAttribute<SapReference, Integer> size;
    public static volatile SingularAttribute<SapReference, Integer> description;
    public static volatile SingularAttribute<SapReference, String[]> allowedRacks;

}
