package estrutura;//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementa��o de Refer�ncia (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modifica��es neste arquivo ser�o perdidas ap�s a recompila��o do esquema de origem. 
// Gerado em: 2017.05.20 �s 01:01:03 AM BRT 
//


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java group interface and Java element interface
 * generated in the generated package. 
 * <p>An estrutura.ObjectFactory allows you to programatically
 * construct new instances of the Java representation 
 * for XML group. The Java representation of XML
 * group can consist of schema derived interfaces
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Allocation_QNAME = new QName("", "allocation");
    //private final static QName _CourseTypeGroup_QNAME = new QName("", "group");
    //private final static QName _CoursesTypeCourse_QNAME = new QName("", "course");

    /**
     * Create a new estrutura.ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AllocationType }
     * 
     */
    public AllocationType createAllocationType() {
        return new AllocationType();
    }

    /**
     * Create an instance of {@link GroupType }
     * 
     */
    public GroupType createGroupType() {
        return new GroupType();
    }

    /**
     * Create an instance of {@link CourseType }
     * 
     */
    public CourseType createCourseType() {
        return new CourseType();
    }

    /**
     * Create an instance of {@link FeatureType }
     * 
     */
    public FeatureType createFeatureType() {
        return new FeatureType();
    }

    /**
     * Create an instance of {@link SessionType }
     * 
     */
    public SessionType createSessionType() {
        return new SessionType();
    }

    /**
     * Create an instance of {@link FeaturesType }
     * 
     */
    public FeaturesType createFeaturesType() {
        return new FeaturesType();
    }

    /**
     * Create an instance of {@link CoursesType }
     * 
     */
    public CoursesType createCoursesType() {
        return new CoursesType();
    }

    /**
     * Create an instance of {@link RoomType }
     * 
     */
    public RoomType createRoomType() {
        return new RoomType();
    }

    /**
     * Create an instance of {@link BuildingType }
     * 
     */
    public BuildingType createBuildingType() {
        return new BuildingType();
    }

    /**
     * Create an instance of {@link BuildingsType }
     * 
     */
    public BuildingsType createBuildingsType() {
        return new BuildingsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllocationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "allocation")
    public JAXBElement<AllocationType> createAllocation(AllocationType value) {
        return new JAXBElement<AllocationType>(_Allocation_QNAME, AllocationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GroupType }{@code >}}
     * 
     */
    /*@XmlElementDecl(namespace = "", name = "group", scope = estrutura.CourseType.class)
    public JAXBElement<estrutura.GroupType> createCourseTypeGroup(estrutura.GroupType value) {
        return new JAXBElement<estrutura.GroupType>(_CourseTypeGroup_QNAME, estrutura.GroupType.class, estrutura.CourseType.class, value);
    }*/

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CourseType }{@code >}}
     * 
     */
    /*@XmlElementDecl(namespace = "", name = "course", scope = estrutura.CoursesType.class)
    public JAXBElement<estrutura.CourseType> createCoursesTypeCourse(estrutura.CourseType value) {
        return new JAXBElement<estrutura.CourseType>(_CoursesTypeCourse_QNAME, estrutura.CourseType.class, estrutura.CoursesType.class, value);
    }*/

}
