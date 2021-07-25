package exporter;

import exceptions.StillHavePlaceHolderException;
import exceptions.UnknownParameterException;
import exceptions.UnknownPropertyException;
import model.*;
import model.Class;
import model.Package;
import model.auxiliary.HierarchicalElement;
import model.parameters.*;
import model.properties.*;

import java.util.List;
import java.util.stream.Collectors;

public class Processor {

	// private List<String> structIds;

    private static final String specDate = "20110701";      // Rhapsody version 8.4
    // private static final String specDate = "20090901";   // MagicDraw version 17.05
    // private static final String specDate = "20131001";

    public static String processUML(UML inputUML, StillHavePlaceholderExceptionPolicy policy) throws StillHavePlaceHolderException, UnknownParameterException, UnknownPropertyException {

        if(inputUML.hasPlaceholders() && policy.equals(StillHavePlaceholderExceptionPolicy.Throw)) {
        	StillHavePlaceHolderException pe = new StillHavePlaceHolderException(inputUML.getName()+" still has placeholders!", inputUML.getItemsWithPlaceholders());
            throw pe;
        }

        StringBuilder string = new StringBuilder();
        string.append("<?xml version='1.0' encoding='UTF-8'?>" + System.lineSeparator());
        string.append("<xmi:XMI xmi:version='2.1'" + System.lineSeparator());
        string.append("         xmlns:uml='http://www.omg.org/spec/UML/" + specDate + "'" + System.lineSeparator());
        string.append("         xmlns:StandardProfile='http://www.omg.org/spec/UML/" + specDate + "/StandardProfile'" + System.lineSeparator());
        string.append("         xmlns:xmi='http://www.omg.org/spec/XMI/" + specDate + "'>" + System.lineSeparator());
        string.append("   <uml:Model xmi:type='uml:Model' xmi:id='" + inputUML.getId() + "' name='" + inputUML.getName() + "'>" + System.lineSeparator());

        for (Package p : inputUML.getPackages()) {
            string.append(processPackage(p));
        }
        for (Class c : inputUML.getClasses()) {
            string.append(processClass(c));
        }
        for (Interface i : inputUML.getInterfaces()) {
            string.append(processInterface(i));
        }
        for (Association a : inputUML.getAssociations()) {
            string.append(processAssociation(a));
        }
        for (Enumeration e : inputUML.getEnumerations()) {
            string.append(processEnumeration(e));
        }
        for (CustomPrimitive cp:inputUML.getCustomPrimitives()) {
            string.append(processCustomPrimitive(cp));
        }

        string.append(System.lineSeparator() + "   </uml:Model>" + System.lineSeparator());
        string.append("</xmi:XMI>");

        return string.toString().replace("'", "\"");
    }

    private static String processCustomPrimitive(CustomPrimitive cp) {
        StringBuilder string = new StringBuilder();

        string.append("<packagedElement xmi:type='uml:PrimitiveType' xmi:id='"+cp.getId()+"' name='"+cp.getName()+"'>");

        if(cp.getSuperPrimitive()!=null) {
            // this has regular super primitive
            string.append(System.lineSeparator() + "<generalization xmi:type='uml:Generalization' xmi:id='"+Processor.uuidGenerator()+"'>");
            string.append("<general href='http://www.omg.org/spec/UML/" + specDate
                    + "/PrimitiveTypes.xmi#" + cp.getSuperPrimitive().toString() + "'/>");
            string.append(System.lineSeparator() + "</generalization>");
        } else if(cp.getSuperCustomPrimitives().size()!=0) {
            string.append(System.lineSeparator() + "<generalization xmi:type='uml:Generalization' xmi:id='"+Processor.uuidGenerator()+"' general='"+cp.getSuperCustomPrimitives().get(0).getId()+"'/>");
        }

        string.append("</packagedElement>");

        return string.toString();
    }

    private static String processAssociation(Association a) {
        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:Association' xmi:id='" + a.getId() + "'>");
        for(AssociationProperty aProperty:a.getProperties()) {
            string.append("<memberEnd xmi:idref='" + aProperty.getId() + "'/>");
        }
        string.append(System.lineSeparator() + "</packagedElement>");

        return string.toString();
    }

    private static String processException(Except e) throws UnknownPropertyException{
        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:Class' xmi:id='" + e.getId() + "' name='" + e.getName() + "'>");

        processExceptContents(e, string);

        string.append(System.lineSeparator() + "</packagedElement>");

        return string.toString();
    }

    private static String processEnumeration(Enumeration e) throws UnknownPropertyException {
        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:Enumeration' xmi:id='" + e.getId()
                      + "' name='" + e.getName() + "' visibility='" + e.getVisibility().toString().toLowerCase() + "'>");
        for(AbstractProperty aProperty:e.getProperties()) {
            string.append(processProperty(aProperty));
        }
        for (EnumerationLiteral aLiteral : e.getLiterals()) {
            string.append(processEnumerationLiteral(aLiteral));
        }
        string.append(System.lineSeparator() + "</packagedElement>");

        return string.toString();
    }

    private static String processEnumerationLiteral(EnumerationLiteral aLiteral) {
        StringBuilder string = new StringBuilder();

        string.append("<ownedLiteral xmi:type='uml:EnumerationLiteral' xmi:id='"
                + aLiteral.getId() + "' name='" + aLiteral.getName() + "'/>");

        return string.toString();
    }

    private static String processPackage(Package p) throws UnknownParameterException, UnknownPropertyException {
        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:Package' xmi:id='").append(p.getId()).append("' name='").append(p.getName()).append("'>");

        for (Class c : p.getClasses()) {
            string.append(processClass(c));
        }
        for (Struct s : p.getStructs()) {
            string.append(processStruct(s));
        }
        for (Array a : p.getArrays()) {
            string.append(processArray(a));
        }
        for (Interface i : p.getInterfaces()) {
            string.append(processInterface(i));
        }
        for (Package sub : p.getSubPackages()) {
            string.append(processPackage(sub));
        }
        for (Enumeration e : p.getEnumerations()) {
            string.append(processEnumeration(e));
        }
        for (CustomPrimitive cp:p.getCustomPrimitives()) {
            string.append(processCustomPrimitive(cp));
        }
        for (Except e : p.getExceptions()) {
            string.append(processException(e));
        }
        if (p.hasProperties()) {
        	string.append(processProperties(p.getProperties()));
        }
        string.append(System.lineSeparator() + "</packagedElement>");
        return string.toString();
    }

    private static String processClass(Class c) throws UnknownParameterException, UnknownPropertyException {

        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:Class' xmi:id='" + c.getId() + "' name='" + c.getName() + "'>");

        for (Class superClass : c.getSuperClasses()) {
            string.append(processesSuperClass(superClass));
        }
        processClassContents(c, string);

        string.append(System.lineSeparator() + "</packagedElement>");

        return string.toString();
    }

    private static String processStruct(Struct s) throws UnknownParameterException, UnknownPropertyException {

        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:DataType' xmi:id='" + s.getId() + "' name='" + s.getName() + "'>");

        processStructContents(s, string);

        string.append(System.lineSeparator() + "</packagedElement>");

        return string.toString();
    }

    private static String processArray(Array a) { // throws UnknownParameterException, UnknownPropertyException {

        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:DataType' xmi:id='" + a.getId() + "' name='" + a.getName() + "'/>");

//        processArrayContents(a, string);
//        string.append(System.lineSeparator() + "</packagedElement>");

//        model.auxiliary.HierarchicalElement h = a.getSubElement();
//        if (h instanceof PrimitiveProperty) {
//        	// Primitive types are not written to XMI
//        } else if (h instanceof Struct) {
//        	Struct s = (Struct) h;
//        	processStruct(s);
//        } else if (h instanceof Except) {
//        	Except e = (Except) h;
//        	processExcept(e);
//        } else if (h instanceof Enumeration) {
//        	Enumeration e = (Enumeration) h;
//        	processEnumeration(e);
//        } else {
//        	System.err.println("Array Sub Element Type " + h + " not recognized");
//        }
        
        return string.toString();
    }

    private static String processInterface(Interface i) throws UnknownParameterException, UnknownPropertyException {

        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:Interface' xmi:id='" + i.getId() + "' name='" + i.getName() + "'>");

        for (Interface superInterface : i.getSuperInterfaces()) {
            string.append(processesSuperInterface(superInterface));
        }

        processInterfaceContents(i, string);

        string.append(System.lineSeparator() + "</packagedElement>");

        return string.toString();
    }

    private static String processProperties(List<AbstractProperty> properties) throws UnknownPropertyException {

        StringBuilder string = new StringBuilder();

    	string.append(System.lineSeparator() + "<packagedElement xmi:type='uml:DataType' xmi:id='" + Processor.uuidGenerator() + "' name='Constants'>");

        for (AbstractProperty p : properties) {
        	Variable v = (Variable) p;
        	String isReadOnly = (v.isConstant()) ? " isReadOnly='true'" : "";

            string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName()
                    + "' visibility='" + p.getVisibility().toString().toLowerCase() + "'" + isReadOnly + ">");
            string.append(System.lineSeparator() + "<type href='http://www.omg.org/spec/UML/" + specDate
            		+ "/PrimitiveTypes.xmi#" + v.getType().toString() + "'/>");
            if (v.hasDefault()) {
                string.append(System.lineSeparator() + "<defaultValue xmi:type='uml:Literal" + v.getType().toString()
                		+ "' xmi:id='" + Processor.uuidGenerator()
                		+ "' value='" + v.getDefaultValue().toString() + "'/>");
            }
            string.append("</ownedAttribute>");
        }

        string.append(System.lineSeparator() + "</packagedElement>");

        return string.toString();
    }


    private static String processesSuperClass(Class superClass) {
        StringBuilder string = new StringBuilder();
        string.append(System.lineSeparator() + "<generalization xmi:type='uml:Generalization' xmi:id='" + Processor.uuidGenerator() + "' general='" + superClass.getId() + "'/>");
        return string.toString();
    }

    private static String processesSuperInterface(Interface superInterface) {
        StringBuilder string = new StringBuilder();
        string.append(System.lineSeparator() + "<generalization xmi:type='uml:Generalization' xmi:id='" + Processor.uuidGenerator() + "' general='" + superInterface.getId() + "'/>");
        return string.toString();
    }

    private static String processesNestedClass(Class c) throws UnknownParameterException, UnknownPropertyException {
        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<nestedClassifier xmi:type='uml:Class' xmi:id='" + c.getId() + "' name='" + c.getName() + "'>");

        processClassContents(c, string);

        string.append(System.lineSeparator() + "</nestedClassifier>");

        return string.toString();
    }

    private static void processClassContents(Class c, StringBuilder string) throws UnknownPropertyException, UnknownParameterException {
        for (AbstractProperty p : c.getProperties()) {
            string.append(processProperty(p));
        }
        for (Operation o : c.getOperations()) {
            string.append(processOperation(o));
        }
        for (Class nestedClass : c.getNestedClasses()) {
            string.append(processesNestedClass(nestedClass));
        }
    }

    private static void processExceptContents(Except e, StringBuilder string) throws UnknownPropertyException {
        for (AbstractProperty p : e.getProperties()) {
            string.append(processProperty(p));
        }
    }

    private static void processStructContents(Struct s, StringBuilder string) throws UnknownPropertyException {
        for (AbstractProperty p : s.getProperties()) {
            string.append(processProperty(p));
        }
    }


    private static void processInterfaceContents(Interface i, StringBuilder string)
    		throws UnknownPropertyException, UnknownParameterException {
        for (AbstractProperty p : i.getProperties()) {
            string.append(processProperty(p));
        }
        for (Operation o : i.getOperations()) {
            string.append(processOperation(o));
        }
        for (Enumeration e : i.getEnumerations()) {
            string.append(processEnumeration(e));
        }
        for (Struct s : i.getStructs()) {
            string.append(processStruct(s));
        }
        for (Except e : i.getExceptions()) {
            string.append(processExcept(e));
        }
    }



    private static String processOperation(Operation o) throws UnknownPropertyException, UnknownParameterException {
        StringBuilder string = new StringBuilder();

        string.append(System.lineSeparator() + "<ownedOperation xmi:type='uml:Operation' xmi:id='" + o.getId()
                     + "' name='" + o.getName() + "' visibility='" + o.getVisibility().toString().toLowerCase() + "'>");

        for (Except e : o.getExceptions()) {
            string.append(processExcept(e));
        }
        for (AbstractParameter p : o.getParameters()) {
            string.append(processParameter(p));
        }

        string.append(System.lineSeparator() + "</ownedOperation>");

        return string.toString();
    }



    private static String processExcept(Except e) {
        String result = System.lineSeparator() + "<raisedException xmi:idref='" + e.getId() + "'/>";
    	return result;
    }


    private static String processParameter(AbstractParameter param) throws UnknownParameterException {
        StringBuilder string = new StringBuilder();

        if (param instanceof PrimitiveParameter) {
            PrimitiveParameter pParam = (PrimitiveParameter) param;
            string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' direction='" + param.getDirection().toString().toLowerCase() + "'>"); // Todo Check if visibility matters here
            string.append(System.lineSeparator() + "<type href='http://www.omg.org/spec/UML/" + specDate + "/PrimitiveTypes.xmi#" + pParam.getType().toString() + "'/>");
            if (pParam.getDefaultValue() != null) {
                string.append(System.lineSeparator() + "<defaultValue xmi:type='uml:Literal" + pParam.getType().toString() + "' xmi:id='" + Processor.uuidGenerator() + "' value='" + pParam.getDefaultValue().toString() + "'/>");
            }
            string.append(System.lineSeparator() + "</ownedParameter>");
        } else if (param instanceof ClassParameter) {
            ClassParameter cParam = (ClassParameter) param;
            if(cParam.hasPlaceholder()) {
                string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' type='" + cParam.getPlaceholder() + "' direction='" + param.getDirection().toString().toLowerCase() + "'/>");
            } else {
                string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' type='" + cParam.getType().getId() + "' direction='" + param.getDirection().toString().toLowerCase() + "'/>");
            }
        } else if (param instanceof EnumerationParameter) {
        	EnumerationParameter eParam = (EnumerationParameter) param;
        	if(eParam.hasPlaceholder()) {
                string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' type='"
                        + eParam.getPlaceholder() + "' direction='" + param.getDirection().toString().toLowerCase() + "'/>");
            } else {
                string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' type='"
                        + eParam.getType().getId() + "' direction='" + param.getDirection().toString().toLowerCase() + "'/>");
            }
        } else if (param instanceof StructParameter) {
        	StructParameter sParam = (StructParameter) param;
        	Struct theStruct = sParam.getType();
            string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' type='"
                         + theStruct.getId() + "' direction='" + param.getDirection().toString().toLowerCase() + "'/>");

        } else if (param instanceof CustomPrimitiveParameter) {
            CustomPrimitiveParameter cpParam = (CustomPrimitiveParameter) param;

            if(cpParam.hasPlaceholder()) {
                string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' type='"
                        + cpParam.getPlaceholder() + "' direction='" + param.getDirection().toString().toLowerCase() + "'/>");
            } else {
                string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' type='"
                        + cpParam.getType().getId() + "' direction='" + param.getDirection().toString().toLowerCase() + "'/>");
            }

        } else if (param instanceof ArrayParameter) {
            ArrayParameter aParam = (ArrayParameter) param;
            HierarchicalElement subElement = aParam.getSubElementType();
            if (subElement instanceof CustomPrimitive) {
            	CustomPrimitive customElement = (CustomPrimitive) subElement;
            	String typeName = customElement.getSuperPrimitive().toString();
                string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName()
                             + "' visibility='public' direction='" + param.getDirection().toString().toLowerCase() + "' >");
                string.append(System.lineSeparator() + "<type href='http://www.omg.org/spec/UML/" + specDate + "/PrimitiveTypes.xmi#" + typeName + "'/>");
            } else {
                String subElementID = subElement.getId();
                string.append(System.lineSeparator() + "<ownedParameter xmi:type='uml:Parameter' xmi:id='" + param.getId() + "' name='" + param.getName() + "' visibility='public' type='"
                             // + aParam.getType().getId()
                		     // + aParam.getSubElementType().getId()
                			 + subElementID
                             + "' direction='" + param.getDirection().toString().toLowerCase() + "'>");            	
            }
            string.append(System.lineSeparator() + "<lowerValue xmi:type='uml:LiteralInteger' xmi:id='" + uuidGenerator() + "' value='" + aParam.getLower() + "'/>");
            string.append(System.lineSeparator() + "<upperValue xmi:type='uml:LiteralUnlimitedNatural' xmi:id='" + uuidGenerator() + "' value='" + aParam.getUpper() + "'/>");
            string.append(System.lineSeparator() + "</ownedParameter>");
        } else {
            throw new UnknownParameterException("An unknown parameter (than Primitive or Class) showed up. Signature: "+param.getSignature());
        }
        return string.toString();
    }



    private static String processProperty(AbstractProperty p) throws UnknownPropertyException {
        StringBuilder string = new StringBuilder();

        if (p instanceof Variable) {
        	Variable v = (Variable) p;
        	String isReadOnly = (v.isConstant()) ? " isReadOnly='true'" : "";

            string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName()
                    + "' visibility='" + p.getVisibility().toString().toLowerCase() + "'" + isReadOnly + ">");
            string.append(System.lineSeparator() + "<type href='http://www.omg.org/spec/UML/" + specDate
            		+ "/PrimitiveTypes.xmi#" + v.getType().toString() + "'/>");
            if (v.hasDefault()) {
                string.append(System.lineSeparator() + "<defaultValue xmi:type='uml:Literal"
                        + v.getType().toString()
                		+ "' xmi:id='" + Processor.uuidGenerator()
                		+ "' value='" + v.getDefaultValue().toString() + "'/>");
            }
        } else if (p instanceof PrimitiveProperty) {
            PrimitiveProperty pp = (PrimitiveProperty) p;
            string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName()
                          + "' visibility='" + p.getVisibility().toString().toLowerCase() + "'>" );
            string.append(System.lineSeparator() + "<type href='http://www.omg.org/spec/UML/" + specDate + "/PrimitiveTypes.xmi#" + pp.getType().toString() + "'/>");
            if (pp.hasDefault()) {
                string.append(System.lineSeparator() + "<defaultValue xmi:type='uml:Literal"
                          + pp.getType().toString() + "' xmi:id='" + Processor.uuidGenerator()
                          + "' value='" + pp.getDefaultValue().toString() + "'/>");
            }
        } else if (p instanceof AssociationProperty) {
            AssociationProperty ap = (AssociationProperty) p;
            String selfId = ((Class) ap.getParent()).getId();
            List<String> typeIds = ap.getAssociation().getProperties().stream().map(prop-> ((Class) prop.getParent()).getId()).filter(prop->!prop.equals(selfId)).collect(Collectors.toList());
            string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName() + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + typeIds.get(0) + "' association='" + ap.getAssociation().getId() + "'>");
        } else if (p instanceof ClassProperty) {
            ClassProperty cp = (ClassProperty) p;
            if(cp.hasPlaceholder()) {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName() + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + cp.getPlaceholder() + "'>");
            } else {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName() + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + cp.getType().getId() + "'>");
            }
        } else if (p instanceof EnumerationProperty) {
            EnumerationProperty ep = (EnumerationProperty) p;
            if(ep.hasPlaceholder()) {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName() + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + ep.getPlaceholder() + "'>");
            } else {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName() + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + ep.getType().getId() + "'>");
            }
        } else if (p instanceof CustomPrimitiveProperty) {
            CustomPrimitiveProperty cpp1 = (CustomPrimitiveProperty) p;
            if(cpp1.hasPlaceholder()) {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName() + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + cpp1.getPlaceholder() + "'>");
            } else {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName() + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + cpp1.getType().getId() + "'>");
            }
        } else if (p instanceof ArrayProperty) {
        	ArrayProperty ap1 = (ArrayProperty) p;
            if(ap1.hasPlaceholder()) {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName()
                + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + ap1.getPlaceholder() + "'>");
            } else {
            	Array arrayType = ap1.getType();
            	HierarchicalElement subElement = arrayType.getSubElement();
            	if (subElement instanceof StructProperty) {
            		StructProperty sp = (StructProperty) subElement;
                    string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName()
                    + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + sp.getType().getId() + "' >");
            		
            	} else {
            		PrimitiveProperty pp = (PrimitiveProperty) subElement;
                    string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName()
                    + "' visibility='" + p.getVisibility().toString().toLowerCase() + "'>");
                    string.append(System.lineSeparator() + "<type href='http://www.omg.org/spec/UML/" + specDate + "/PrimitiveTypes.xmi#" + pp.getType().toString() + "'>");
            	}
                string.append(System.lineSeparator() + "<lowerValue xmi:type='uml:LiteralInteger' xmi:id='" + uuidGenerator() + "' value='" + arrayType.getLower() + "'/>");
                string.append(System.lineSeparator() + "<upperValue xmi:type='uml:LiteralUnlimitedNatural' xmi:id='" + uuidGenerator() + "' value='" + arrayType.getUpper() + "'/>");

            }
        } else if (p instanceof StructProperty) {
        	StructProperty sp1 = (StructProperty) p;
            if(sp1.hasPlaceholder()) {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName()
                + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + sp1.getPlaceholder() + "'>");
            } else {
                string.append(System.lineSeparator() + "<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName()
                + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + sp1.getType().getId() + "'>");
            }
        } else if(p instanceof Property) {
            Property p1 = (Property) p;
            string.append("<ownedAttribute xmi:type='uml:Property' xmi:id='" + p.getId() + "' name='" + p.getName() + "' visibility='" + p.getVisibility().toString().toLowerCase() + "' type='" + p1.getReference() + "'>");
        } else {
            throw new UnknownPropertyException("An unknown parameter (than Primitive, Class, or Association) showed up. Signature: "+p.getSignature());
        }

        if(p.getLower()!=null && !p.getLower().isEmpty()) {
            string.append("<lowerValue xmi:type='uml:LiteralInteger' xmi:id='"+Processor.uuidGenerator()+"' value='"+p.getLower()+"'/>");
        }
        if(p.getUpper()!=null && !p.getUpper().isEmpty()) {
            string.append("<upperValue xmi:type='uml:LiteralUnlimitedNatural' xmi:id='"+Processor.uuidGenerator()+"' value='"+p.getUpper()+"'/>");
        }

        string.append("</ownedAttribute>");


        return string.toString();
    }

    public static int ID_COUNTER = 0;

    public static String uuidGenerator() {
        //UUID uuid = UUID.randomUUID();
        //return uuid.toString();
        return "ID" + ID_COUNTER++;
    }
}
