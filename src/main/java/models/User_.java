package models;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public abstract class User_ {
    public static volatile SingularAttribute<User, Long> Id;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, Integer> email;
    public static volatile SingularAttribute<User, Integer> username;
    public static volatile SingularAttribute<User, Integer> role_name;
}
