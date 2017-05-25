package room_allocations.xml;//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementa��o de Refer�ncia (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modifica��es neste arquivo ser�o perdidas ap�s a recompila��o do esquema de origem. 
// Gerado em: 2017.05.20 �s 01:01:03 AM BRT 
//


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de groupType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="groupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="session" type="{}sessionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="number_of_students" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="teacher" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "groupType", propOrder = {
    "session"
})
public class GroupType {

    protected List<SessionType> session;
    @XmlAttribute(name = "number_of_students")
    protected String numberOfStudents;
    @XmlAttribute(name = "teacher")
    protected String teacher;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the session property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the session property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSession().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SessionType }
     * 
     * 
     */
    public List<SessionType> getSession() {
        if (session == null) {
            session = new ArrayList<SessionType>();
        }
        return this.session;
    }

    /**
     * Obt�m o valor da propriedade numberOfStudents.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfStudents() {
        return numberOfStudents;
    }

    /**
     * Define o valor da propriedade numberOfStudents.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfStudents(String value) {
        this.numberOfStudents = value;
    }

    /**
     * Obt�m o valor da propriedade teacher.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * Define o valor da propriedade teacher.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacher(String value) {
        this.teacher = value;
    }

    /**
     * Obt�m o valor da propriedade id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define o valor da propriedade id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
