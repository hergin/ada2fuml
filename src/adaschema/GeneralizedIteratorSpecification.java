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
 * <p>Java class for Generalized_Iterator_Specification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Generalized_Iterator_Specification">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="names_ql" type="{}Defining_Name_List"/>
 *         &lt;element name="has_reverse_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="reverse" type="{}Reverse"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="iteration_scheme_name_q" type="{}Element_Class"/>
 *       &lt;/sequence>
 *       &lt;attribute name="checks" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Generalized_Iterator_Specification", propOrder = {
    "sloc",
    "namesQl",
    "hasReverseQ",
    "iterationSchemeNameQ"
})
public class GeneralizedIteratorSpecification
    extends JaxBSuperclass
{

    @XmlElement(required = true)
    protected SourceLocation sloc;
    @XmlElement(name = "names_ql", required = true)
    protected DefiningNameList namesQl;
    @XmlElement(name = "has_reverse_q", required = true)
    protected GeneralizedIteratorSpecification.HasReverseQ hasReverseQ;
    @XmlElement(name = "iteration_scheme_name_q", required = true)
    protected ElementClass iterationSchemeNameQ;
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
     * Gets the value of the namesQl property.
     * 
     * @return
     *     possible object is
     *     {@link DefiningNameList }
     *     
     */
    public DefiningNameList getNamesQl() {
        return namesQl;
    }

    /**
     * Sets the value of the namesQl property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefiningNameList }
     *     
     */
    public void setNamesQl(DefiningNameList value) {
        this.namesQl = value;
    }

    /**
     * Gets the value of the hasReverseQ property.
     * 
     * @return
     *     possible object is
     *     {@link GeneralizedIteratorSpecification.HasReverseQ }
     *     
     */
    public GeneralizedIteratorSpecification.HasReverseQ getHasReverseQ() {
        return hasReverseQ;
    }

    /**
     * Sets the value of the hasReverseQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneralizedIteratorSpecification.HasReverseQ }
     *     
     */
    public void setHasReverseQ(GeneralizedIteratorSpecification.HasReverseQ value) {
        this.hasReverseQ = value;
    }

    /**
     * Gets the value of the iterationSchemeNameQ property.
     * 
     * @return
     *     possible object is
     *     {@link ElementClass }
     *     
     */
    public ElementClass getIterationSchemeNameQ() {
        return iterationSchemeNameQ;
    }

    /**
     * Sets the value of the iterationSchemeNameQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElementClass }
     *     
     */
    public void setIterationSchemeNameQ(ElementClass value) {
        this.iterationSchemeNameQ = value;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element name="reverse" type="{}Reverse"/>
     *         &lt;element name="not_an_element" type="{}Not_An_Element"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "reverse",
        "notAnElement"
    })
    public static class HasReverseQ
        extends JaxBSuperclass
    {

        protected Reverse reverse;
        @XmlElement(name = "not_an_element")
        protected NotAnElement notAnElement;

        /**
         * Gets the value of the reverse property.
         * 
         * @return
         *     possible object is
         *     {@link Reverse }
         *     
         */
        public Reverse getReverse() {
            return reverse;
        }

        /**
         * Sets the value of the reverse property.
         * 
         * @param value
         *     allowed object is
         *     {@link Reverse }
         *     
         */
        public void setReverse(Reverse value) {
            this.reverse = value;
        }

        /**
         * Gets the value of the notAnElement property.
         * 
         * @return
         *     possible object is
         *     {@link NotAnElement }
         *     
         */
        public NotAnElement getNotAnElement() {
            return notAnElement;
        }

        /**
         * Sets the value of the notAnElement property.
         * 
         * @param value
         *     allowed object is
         *     {@link NotAnElement }
         *     
         */
        public void setNotAnElement(NotAnElement value) {
            this.notAnElement = value;
        }

    }

}
