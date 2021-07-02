package models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Reference.class)
public abstract class Reference_ {

    public static volatile SingularAttribute<Reference, Long> Id;
    public static volatile SingularAttribute<Reference, String> reference;
    public static volatile SingularAttribute<Reference, Integer> size;
    public static volatile SingularAttribute<Reference, Integer> description;
    public static volatile SingularAttribute<Reference, String[]> allowedRacks;

}
