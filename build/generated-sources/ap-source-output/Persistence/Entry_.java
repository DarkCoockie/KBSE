package Persistence;

import Persistence.Member;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-07T16:50:36")
@StaticMetamodel(Entry.class)
public class Entry_ { 

    public static volatile SingularAttribute<Entry, String> name;
    public static volatile SingularAttribute<Entry, String> description;
    public static volatile SingularAttribute<Entry, Integer> id;
    public static volatile SingularAttribute<Entry, Integer> stars;
    public static volatile SingularAttribute<Entry, Member> userName;
    public static volatile SingularAttribute<Entry, String> url;

}