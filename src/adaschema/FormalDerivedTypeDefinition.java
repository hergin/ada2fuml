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
 * <p>Java class for Formal_Derived_Type_Definition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Formal_Derived_Type_Definition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="has_abstract_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="abstract" type="{}Abstract"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="has_limited_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="limited" type="{}Limited"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="has_synchronized_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="synchronized" type="{}Synchronized"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="subtype_mark_q" type="{}Expression_Class"/>
 *         &lt;element name="definition_interface_list_ql" type="{}Expression_List"/>
 *         &lt;element name="has_private_q">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="private" type="{}Private"/>
 *                   &lt;element name="not_an_element" type="{}Not_An_Element"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
@XmlType(name = "Formal_Derived_Type_Definition", propOrder = {
    "sloc",
    "hasAbstractQ",
    "hasLimitedQ",
    "hasSynchronizedQ",
    "subtypeMarkQ",
    "definitionInterfaceListQl",
    "hasPrivateQ"
})
public class FormalDerivedTypeDefinition
    extends JaxBSuperclass
{

    @XmlElement(required = true)
    protected SourceLocation sloc;
    @XmlElement(name = "has_abstract_q", required = true)
    protected FormalDerivedTypeDefinition.HasAbstractQ hasAbstractQ;
    @XmlElement(name = "has_limited_q", required = true)
    protected FormalDerivedTypeDefinition.HasLimitedQ hasLimitedQ;
    @XmlElement(name = "has_synchronized_q", required = true)
    protected FormalDerivedTypeDefinition.HasSynchronizedQ hasSynchronizedQ;
    @XmlElement(name = "subtype_mark_q", required = true)
    protected ExpressionClass subtypeMarkQ;
    @XmlElement(name = "definition_interface_list_ql", required = true)
    protected ExpressionList definitionInterfaceListQl;
    @XmlElement(name = "has_private_q", required = true)
    protected FormalDerivedTypeDefinition.HasPrivateQ hasPrivateQ;
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
     * Gets the value of the hasAbstractQ property.
     * 
     * @return
     *     possible object is
     *     {@link FormalDerivedTypeDefinition.HasAbstractQ }
     *     
     */
    public FormalDerivedTypeDefinition.HasAbstractQ getHasAbstractQ() {
        return hasAbstractQ;
    }

    /**
     * Sets the value of the hasAbstractQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormalDerivedTypeDefinition.HasAbstractQ }
     *     
     */
    public void setHasAbstractQ(FormalDerivedTypeDefinition.HasAbstractQ value) {
        this.hasAbstractQ = value;
    }

    /**
     * Gets the value of the hasLimitedQ property.
     * 
     * @return
     *     possible object is
     *     {@link FormalDerivedTypeDefinition.HasLimitedQ }
     *     
     */
    public FormalDerivedTypeDefinition.HasLimitedQ getHasLimitedQ() {
        return hasLimitedQ;
    }

    /**
     * Sets the value of the hasLimitedQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormalDerivedTypeDefinition.HasLimitedQ }
     *     
     */
    public void setHasLimitedQ(FormalDerivedTypeDefinition.HasLimitedQ value) {
        this.hasLimitedQ = value;
    }

    /**
     * Gets the value of the hasSynchronizedQ property.
     * 
     * @return
     *     possible object is
     *     {@link FormalDerivedTypeDefinition.HasSynchronizedQ }
     *     
     */
    public FormalDerivedTypeDefinition.HasSynchronizedQ getHasSynchronizedQ() {
        return hasSynchronizedQ;
    }

    /**
     * Sets the value of the hasSynchronizedQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormalDerivedTypeDefinition.HasSynchronizedQ }
     *     
     */
    public void setHasSynchronizedQ(FormalDerivedTypeDefinition.HasSynchronizedQ value) {
        this.hasSynchronizedQ = value;
    }

    /**
     * Gets the value of the subtypeMarkQ property.
     * 
     * @return
     *     possible object is
     *     {@link ExpressionClass }
     *     
     */
    public ExpressionClass getSubtypeMarkQ() {
        return subtypeMarkQ;
    }

    /**
     * Sets the value of the subtypeMarkQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressionClass }
     *     
     */
    public void setSubtypeMarkQ(ExpressionClass value) {
        this.subtypeMarkQ = value;
    }

    /**
     * Gets the value of the definitionInterfaceListQl property.
     * 
     * @return
     *     possible object is
     *     {@link ExpressionList }
     *     
     */
    public ExpressionList getDefinitionInterfaceListQl() {
        return definitionInterfaceListQl;
    }

    /**
     * Sets the value of the definitionInterfaceListQl property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpressionList }
     *     
     */
    public void setDefinitionInterfaceListQl(ExpressionList value) {
        this.definitionInterfaceListQl = value;
    }

    /**
     * Gets the value of the hasPrivateQ property.
     * 
     * @return
     *     possible object is
     *     {@link FormalDerivedTypeDefinition.HasPrivateQ }
     *     
     */
    public FormalDerivedTypeDefinition.HasPrivateQ getHasPrivateQ() {
        return hasPrivateQ;
    }

    /**
     * Sets the value of the hasPrivateQ property.
     * 
     * @param value
     *     allowed object is
     *     {@link FormalDerivedTypeDefinition.HasPrivateQ }
     *     
     */
    public void setHasPrivateQ(FormalDerivedTypeDefinition.HasPrivateQ value) {
        this.hasPrivateQ = value;
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
     *         &lt;element name="abstract" type="{}Abstract"/>
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
        "_abstract",
        "notAnElement"
    })
    public static class HasAbstractQ
        extends JaxBSuperclass
    {

        @XmlElement(name = "abstract")
        protected Abstract _abstract;
        @XmlElement(name = "not_an_element")
        protected NotAnElement notAnElement;

        /**
         * Gets the value of the abstract property.
         * 
         * @return
         *     possible object is
         *     {@link Abstract }
         *     
         */
        public Abstract getAbstract() {
            return _abstract;
        }

        /**
         * Sets the value of the abstract property.
         * 
         * @param value
         *     allowed object is
         *     {@link Abstract }
         *     
         */
        public void setAbstract(Abstract value) {
            this._abstract = value;
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
     *         &lt;element name="limited" type="{}Limited"/>
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
        "limited",
        "notAnElement"
    })
    public static class HasLimitedQ
        extends JaxBSuperclass
    {

        protected Limited limited;
        @XmlElement(name = "not_an_element")
        protected NotAnElement notAnElement;

        /**
         * Gets the value of the limited property.
         * 
         * @return
         *     possible object is
         *     {@link Limited }
         *     
         */
        public Limited getLimited() {
            return limited;
        }

        /**
         * Sets the value of the limited property.
         * 
         * @param value
         *     allowed object is
         *     {@link Limited }
         *     
         */
        public void setLimited(Limited value) {
            this.limited = value;
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
     *         &lt;element name="private" type="{}Private"/>
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
        "_private",
        "notAnElement"
    })
    public static class HasPrivateQ
        extends JaxBSuperclass
    {

        @XmlElement(name = "private")
        protected Private _private;
        @XmlElement(name = "not_an_element")
        protected NotAnElement notAnElement;

        /**
         * Gets the value of the private property.
         * 
         * @return
         *     possible object is
         *     {@link Private }
         *     
         */
        public Private getPrivate() {
            return _private;
        }

        /**
         * Sets the value of the private property.
         * 
         * @param value
         *     allowed object is
         *     {@link Private }
         *     
         */
        public void setPrivate(Private value) {
            this._private = value;
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
     *         &lt;element name="synchronized" type="{}Synchronized"/>
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
        "_synchronized",
        "notAnElement"
    })
    public static class HasSynchronizedQ
        extends JaxBSuperclass
    {

        @XmlElement(name = "synchronized")
        protected Synchronized _synchronized;
        @XmlElement(name = "not_an_element")
        protected NotAnElement notAnElement;

        /**
         * Gets the value of the synchronized property.
         * 
         * @return
         *     possible object is
         *     {@link Synchronized }
         *     
         */
        public Synchronized getSynchronized() {
            return _synchronized;
        }

        /**
         * Sets the value of the synchronized property.
         * 
         * @param value
         *     allowed object is
         *     {@link Synchronized }
         *     
         */
        public void setSynchronized(Synchronized value) {
            this._synchronized = value;
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
