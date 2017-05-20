package estrutura;//
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
 * <p>Classe Java de sessionType complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conte�do esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="sessionType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="room_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="duration" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="building_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="weekday" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="start_time" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="requires_building_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="requires_room_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="feature_ids" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="features_ids" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sessionType", propOrder = {
    "value"
})
public class SessionType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "room_id")
    protected String roomId;
    @XmlAttribute(name = "duration")
    protected String duration;
    @XmlAttribute(name = "building_id")
    protected String buildingId;
    @XmlAttribute(name = "weekday")
    protected String weekday;
    @XmlAttribute(name = "start_time")
    protected String startTime;
    @XmlAttribute(name = "requires_building_id")
    protected String requiresBuildingId;
    @XmlAttribute(name = "requires_room_id")
    protected String requiresRoomId;
    @XmlAttribute(name = "feature_ids")
    protected String featureIds;
    @XmlAttribute(name = "features_ids")
    protected String featuresIds;

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
     * Obt�m o valor da propriedade roomId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Define o valor da propriedade roomId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomId(String value) {
        this.roomId = value;
    }

    /**
     * Obt�m o valor da propriedade duration.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Define o valor da propriedade duration.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Obt�m o valor da propriedade buildingId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuildingId() {
        return buildingId;
    }

    /**
     * Define o valor da propriedade buildingId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuildingId(String value) {
        this.buildingId = value;
    }

    /**
     * Obt�m o valor da propriedade weekday.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     * Define o valor da propriedade weekday.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeekday(String value) {
        this.weekday = value;
    }

    /**
     * Obt�m o valor da propriedade startTime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Define o valor da propriedade startTime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Obt�m o valor da propriedade requiresBuildingId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiresBuildingId() {
        return requiresBuildingId;
    }

    /**
     * Define o valor da propriedade requiresBuildingId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiresBuildingId(String value) {
        this.requiresBuildingId = value;
    }

    /**
     * Obt�m o valor da propriedade requiresRoomId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiresRoomId() {
        return requiresRoomId;
    }

    /**
     * Define o valor da propriedade requiresRoomId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiresRoomId(String value) {
        this.requiresRoomId = value;
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
     * Obt�m o valor da propriedade featuresIds.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeaturesIds() {
        return featuresIds;
    }

    /**
     * Define o valor da propriedade featuresIds.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeaturesIds(String value) {
        this.featuresIds = value;
    }

}
