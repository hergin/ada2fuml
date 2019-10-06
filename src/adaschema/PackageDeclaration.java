//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.07.08 at 10:38:38 AM EDT 
//


package adaschema;

import exceptions.NamingException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Package_Declaration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Package_Declaration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sloc" type="{}Source_Location"/>
 *         &lt;element name="names_ql" type="{}Defining_Name_List"/>
 *         &lt;element name="aspect_specifications_ql" type="{}Element_List"/>
 *         &lt;element name="visible_part_declarative_items_ql" type="{}Declarative_Item_List"/>
 *         &lt;element name="private_part_declarative_items_ql" type="{}Declarative_Item_List"/>
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
@XmlType(name = "Package_Declaration", propOrder = {
    "sloc",
    "namesQl",
    "aspectSpecificationsQl",
    "visiblePartDeclarativeItemsQl",
    "privatePartDeclarativeItemsQl"
})
public class PackageDeclaration
    extends JaxBSuperclass
{

    @XmlElement(required = true)
    protected SourceLocation sloc;
    @XmlElement(name = "names_ql", required = true)
    protected DefiningNameList namesQl;
    @XmlElement(name = "aspect_specifications_ql", required = true)
    protected ElementList aspectSpecificationsQl;
    @XmlElement(name = "visible_part_declarative_items_ql", required = true)
    protected DeclarativeItemList visiblePartDeclarativeItemsQl;
    @XmlElement(name = "private_part_declarative_items_ql", required = true)
    protected DeclarativeItemList privatePartDeclarativeItemsQl;
    @XmlAttribute(name = "checks")
    protected String checks;

    /**
     * HELPER_METHOD
     * @return
     */
    public List<OrdinaryTypeDeclaration> getOrdinaryTypes() {
        List<OrdinaryTypeDeclaration> ordinaryTypes = new ArrayList<>();

        for (Object o: visiblePartDeclarativeItemsQl.getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration()) {
            if(o instanceof OrdinaryTypeDeclaration)
                ordinaryTypes.add((OrdinaryTypeDeclaration) o);
        }

        for (Object o: privatePartDeclarativeItemsQl.getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration()) {
            if(o instanceof OrdinaryTypeDeclaration)
                ordinaryTypes.add((OrdinaryTypeDeclaration) o);
        }

        return ordinaryTypes;
    }

    /**
     * HELPER_METHOD
     * @return
     */
    public List<VariableDeclaration> getVariableDeclarations() {
        List<VariableDeclaration> variableDeclarations = new ArrayList<>();

        for (Object o: visiblePartDeclarativeItemsQl.getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration()) {
            if(o instanceof VariableDeclaration)
                variableDeclarations.add((VariableDeclaration) o);
        }

        for (Object o: privatePartDeclarativeItemsQl.getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration()) {
            if(o instanceof VariableDeclaration)
                variableDeclarations.add((VariableDeclaration) o);
        }

        return variableDeclarations;
    }

    /**
     * HELPER_METHOD
     * @return
     */
    public List<FunctionDeclaration> getFunctionDeclarations() {
        List<FunctionDeclaration> functionDeclarations = new ArrayList<>();

        for (Object o: visiblePartDeclarativeItemsQl.getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration()) {
            if(o instanceof FunctionDeclaration)
                functionDeclarations.add((FunctionDeclaration) o);
        }

        for (Object o: privatePartDeclarativeItemsQl.getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration()) {
            if(o instanceof FunctionDeclaration)
                functionDeclarations.add((FunctionDeclaration) o);
        }

        return functionDeclarations;
    }

    /**
     * HELPER_METHOD
     * @return
     */
    public List<ProcedureDeclaration> getProcedureDeclarations() {
        List<ProcedureDeclaration> procedureDeclarations = new ArrayList<>();

        for (Object o: visiblePartDeclarativeItemsQl.getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration()) {
            if(o instanceof ProcedureDeclaration)
                procedureDeclarations.add((ProcedureDeclaration) o);
        }

        for (Object o: privatePartDeclarativeItemsQl.getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration()) {
            if(o instanceof ProcedureDeclaration)
                procedureDeclarations.add((ProcedureDeclaration) o);
        }

        return procedureDeclarations;
    }

    /**
     * HELPER_METHOD
     * @return
     */
    public String getName() throws NamingException {
        try {
            for (var thing : getNamesQl().getNotAnElementOrDefiningIdentifierOrDefiningCharacterLiteral()) {
                if (thing instanceof DefiningIdentifier) {
                    return ((DefiningIdentifier) thing).getDefName();
                }
            }
        } catch (Exception e) {
            throw new NamingException("Package has some different naming structure than expected!", e);
        }
        throw new NamingException("Package has some different naming structure than expected!");
    }

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
     * Gets the value of the aspectSpecificationsQl property.
     * 
     * @return
     *     possible object is
     *     {@link ElementList }
     *     
     */
    public ElementList getAspectSpecificationsQl() {
        return aspectSpecificationsQl;
    }

    /**
     * Sets the value of the aspectSpecificationsQl property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElementList }
     *     
     */
    public void setAspectSpecificationsQl(ElementList value) {
        this.aspectSpecificationsQl = value;
    }

    /**
     * Gets the value of the visiblePartDeclarativeItemsQl property.
     * 
     * @return
     *     possible object is
     *     {@link DeclarativeItemList }
     *     
     */
    public DeclarativeItemList getVisiblePartDeclarativeItemsQl() {
        return visiblePartDeclarativeItemsQl;
    }

    /**
     * Sets the value of the visiblePartDeclarativeItemsQl property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclarativeItemList }
     *     
     */
    public void setVisiblePartDeclarativeItemsQl(DeclarativeItemList value) {
        this.visiblePartDeclarativeItemsQl = value;
    }

    /**
     * Gets the value of the privatePartDeclarativeItemsQl property.
     * 
     * @return
     *     possible object is
     *     {@link DeclarativeItemList }
     *     
     */
    public DeclarativeItemList getPrivatePartDeclarativeItemsQl() {
        return privatePartDeclarativeItemsQl;
    }

    /**
     * Sets the value of the privatePartDeclarativeItemsQl property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeclarativeItemList }
     *     
     */
    public void setPrivatePartDeclarativeItemsQl(DeclarativeItemList value) {
        this.privatePartDeclarativeItemsQl = value;
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
