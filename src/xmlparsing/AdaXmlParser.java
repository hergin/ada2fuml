package xmlparsing;

import adaschema.CompilationUnit;
import adaschema.DefiningIdentifier;
import adaschema.JaxBSuperclass;
import adaschema.OrdinaryTypeDeclaration;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringReader;
import java.net.URL;
import java.util.jar.JarException;

public class AdaXmlParser {

    public static CompilationUnit parseAndProduceCompilationUnit(String adaXmlString) {
        CompilationUnit result = null;

        JAXBContext jaxbContext = null;
        Unmarshaller jaxbUnmarshaller = null;

        try {
            jaxbContext = JAXBContext.newInstance(CompilationUnit.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setListener(new Unmarshaller.Listener() {
                @Override
                public void afterUnmarshal(Object target, Object parent) {
                    super.afterUnmarshal(target, parent);
                    ((JaxBSuperclass)target).setParent((JaxBSuperclass) parent);
                }
            });
            jaxbUnmarshaller.setEventHandler(new ValidationEventHandler() {
                @Override
                public boolean handleEvent(ValidationEvent event) {
                    return true;
                }
            });
            result = (CompilationUnit) jaxbUnmarshaller.unmarshal(new StringReader(adaXmlString));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return result;
    }

}
