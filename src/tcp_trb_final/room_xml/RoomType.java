package tcp_trb_final.room_xml;//
// Este arquivo foi gerado pela Arquitetura JavaTM para Implementa��o de Refer�ncia (JAXB) de Bind XML, v2.2.8-b130911.1802 
// Consulte <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas as modifica��es neste arquivo ser�o perdidas ap�s a recompila��o do esquema de origem. 
// Gerado em: 2017.05.20 �s 01:01:03 AM BRT 
//


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java de roomType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="roomType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="feature_ids" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="number_of_places" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="available_for_allocation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="note" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roomType", propOrder = {
    "value"
})
public class RoomType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "feature_ids")
    protected String featureIds;
    @XmlAttribute(name = "number_of_places")
    protected String numberOfPlaces;
    @XmlAttribute(name = "available_for_allocation")
    protected String availableForAllocation;
    @XmlAttribute(name = "note")
    protected String note;

    /**
     * Obt�m o valor da propriedade value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Define o valor da propriedade value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
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

    /**
     * Obt�m o valor da propriedade featureIds.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatureIds() {
        return featureIds;
    }

    /**
     * Define o valor da propriedade featureIds.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatureIds(String value) {
        this.featureIds = value;
    }

    /**
     * Obt�m o valor da propriedade numberOfPlaces.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfPlaces() {
        return numberOfPlaces;
    }

    /**
     * Define o valor da propriedade numberOfPlaces.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfPlaces(String value) {
        this.numberOfPlaces = value;
    }

    /**
     * Obt�m o valor da propriedade availableForAllocation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvailableForAllocation() {
        return availableForAllocation;
    }

    /**
     * Define o valor da propriedade availableForAllocation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvailableForAllocation(String value) {
        this.availableForAllocation = value;
    }

    /**
     * Obt�m o valor da propriedade note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Define o valor da propriedade note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

}
