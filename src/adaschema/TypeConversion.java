//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.07.08 at 10:38:38 AM EDT 
//


package adaschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Type_Conversion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Type_Conversion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="converted_or_qualified_subtype_mark_q" type="{}Expression_Class"/>
 *         &lt;element name="converted_or_qualified_expression_q" type="{}Expression_Class"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="checks" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Type_Conversion", propOrder = {
    "sloc",
    "convertedOrQualifiedSubtypeMarkQ",
    "convertedOrQualifiedExpressionQ"
})
public class TypeConversion
    extends JaxBSuperclass
{

    @XmlElement(required = true)
    protected SourceLocation sloc;
    @XmlElement(name = "converted_or_qualified_subtype_mark_q", required = true)
    protected ExpressionClass convertedOrQualifiedSubtypeMarkQ;
    @XmlElement(name = "converted_or_qualified_expression_q", required = true)
    protected ExpressionClass convertedOrQualifiedExpressionQ;
    @XmlAttribute(name = "type", required = true)
    protected String type;
    @XmlAttribute(name = "checks")
    protected String checks;

    /**
     * Gets the value of the sloc property.
     * 
     * @return
     *     possible object is
     *     {@link SourceLocation }
     *     
     */
    public SourceLocation getSloc() {
        return sloc;
    }

    /**
     * Sets the value of the sloc property.
     * 
     * @param value
     *     allowed object is
     *     {@link SourceLocation }
     *     
     */
    public void setSloc(SourceLocation value) {
        this.sloc = value;
    }

    /**
     * Gets the value of the convertedOrQualifiedSubtypeMarkQ property.
     * 
     * @return
     *     possible object is
     *     {@link ExpressionClass }
     *     
     */
    public ExpressionClass getConvertedOrQualifiedSubtypeMarkQ() {
        return convertedOrQualifiedSubtypeMarkQ;
    }

    /**
     * Sets the value of the convertedOrQualifiedSubtypeMarkQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressionClass }
     *     
     */
    public void setConvertedOrQualifiedSubtypeMarkQ(ExpressionClass value) {
        this.convertedOrQualifiedSubtypeMarkQ = value;
    }

    /**
     * Gets the value of the convertedOrQualifiedExpressionQ property.
     * 
     * @return
     *     possible object is
     *     {@link ExpressionClass }
     *     
     */
    public ExpressionClass getConvertedOrQualifiedExpressionQ() {
        return convertedOrQualifiedExpressionQ;
    }

    /**
     * Sets the value of the convertedOrQualifiedExpressionQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressionClass }
     *     
     */
    public void setConvertedOrQualifiedExpressionQ(ExpressionClass value) {
        this.convertedOrQualifiedExpressionQ = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the checks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChecks() {
        return checks;
    }

    /**
     * Sets the value of the checks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChecks(String value) {
        this.checks = value;
    }

}
