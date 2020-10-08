package xmlparsing;

import exceptions.InvalidSignatureException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GenericXmlParser {

    public static Document getDocumentFromXML(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            try (ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes())) {
                return builder.parse(stream);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return builder.newDocument();
        } catch (SAXException e) {
            e.printStackTrace();
            return builder.newDocument();
        }
    }

    public static boolean isValidSignature(String signature) {
        // TODO to implement
        return signature.matches("[[a-zA-Z_$][a-zA-Z_$0-9]* *>]+");
    }

    public static List<Node> getNodesWithSignatureStartingFrom(Node startingNode, String signature) throws InvalidSignatureException {
        if (!isValidSignature(signature))
            throw new InvalidSignatureException();

        StringTokenizer tokenizer = new StringTokenizer(signature, ">", false);

        Node rootNode = startingNode;
        String firstToken = tokenizer.nextToken();

        if (!rootNode.getNodeName().equals(firstToken)) {
            // TODO throw exception here because the signature doesn't start with root element
            return new ArrayList<Node>();
        }

        List<Node> currentNodes = new ArrayList<>();
        currentNodes.add(rootNode);

        List<Node> nextSetOfCurrentNodes = new ArrayList<>();

        while (tokenizer.hasMoreElements()) {
            String currentToken = tokenizer.nextToken();

            nextSetOfCurrentNodes.clear();

            for (Node currentNode : currentNodes) {
                NodeList childNodes = currentNode.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node currentChildNode = childNodes.item(i);
                    if (currentChildNode.getNodeType() == Node.ELEMENT_NODE
                            && currentChildNode.getNodeName().equals(currentToken)) {
                        nextSetOfCurrentNodes.add(currentChildNode);
                    }
                }
            }

            currentNodes.clear();
            currentNodes.addAll(nextSetOfCurrentNodes);
        }

        return nextSetOfCurrentNodes;
    }

    public static List<Node> getNodesWithSignature(Document document, String signature) throws InvalidSignatureException {
        return getNodesWithSignatureStartingFrom(document.getDocumentElement(), signature);
    }

}
