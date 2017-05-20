package estrutura;//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementa��o de Refer�ncia (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modifica��es neste arquivo ser�o perdidas ap�s a recompila��o do esquema de origem. 
// Gerado em: 2017.05.20 �s 01:01:03 AM BRT 
//


import javax.xml.bind.annotation.*;


/**
 * <p>Classe Java de allocationType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="allocationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="course" type="{}coursesType"/>
 *         &lt;element name="features" type="{}featuresType"/>
 *         &lt;element name="buildings" type="{}buildingsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="allocation")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allocationType", propOrder = {
    "courses",
    "features",
    "buildings"
})
public class AllocationType {

    @XmlElement(required = true)
    protected CoursesType courses;
    @XmlElement(required = true)
    protected FeaturesType features;
    @XmlElement(required = true)
    protected BuildingsType buildings;

    /**
     * Obt�m o valor da propriedade course.
     * 
     * @return
     *     possible object is
     *     {@link CoursesType }
     *     
     */
    public CoursesType getCourses() {
        return courses;
    }

    /**
     * Define o valor da propriedade course.
     * 
     * @param value
     *     allowed object is
     *     {@link CoursesType }
     *     
     */
    public void setCourses(CoursesType value) {
        this.courses = value;
    }

    /**
     * Obt�m o valor da propriedade features.
     * 
     * @return
     *     possible object is
     *     {@link FeaturesType }
     *     
     */
    public FeaturesType getFeatures() {
        return features;
    }

    /**
     * Define o valor da propriedade features.
     * 
     * @param value
     *     allowed object is
     *     {@link FeaturesType }
     *     
     */
    public void setFeatures(FeaturesType value) {
        this.features = value;
    }

    /**
     * Obt�m o valor da propriedade buildings.
     * 
     * @return
     *     possible object is
     *     {@link BuildingsType }
     *     
     */
    public BuildingsType getBuildings() {
        return buildings;
    }

    /**
     * Define o valor da propriedade buildings.
     * 
     * @param value
     *     allowed object is
     *     {@link BuildingsType }
     *     
     */
    public void setBuildings(BuildingsType value) {
        this.buildings = value;
    }

}
