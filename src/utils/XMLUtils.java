package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.StringReader;

public class XMLUtils {

    public static NodeList getAllAncestorNodesWithThePath(Node xmlNode, String path, int levelAbove) {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = null;
        Node startNode = xmlNode;
        for (int i = 0; i <= levelAbove; i++) {
            startNode = startNode.getParentNode();
        }
        try {
            expr = xpath.compile(stripNamespace(path));
            NodeList nl = (NodeList) expr.evaluate(startNode, XPathConstants.NODESET);
            return nl;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NodeList getAllNodesWithThePath(Node xmlNode, String path) {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = null;
        try {
            expr = xpath.compile(stripNamespace(path));
            NodeList nl = (NodeList) expr.evaluate(xmlNode, XPathConstants.NODESET);
            return nl;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String stripNamespace(String path) {
        return path.replaceAll("[a-zA-Z0-9]*:", "");
    }

    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
