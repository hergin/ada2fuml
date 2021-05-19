package template;

import exceptions.StillHavePlaceHolderException;
import exceptions.UnknownParameterException;
import exceptions.UnknownPropertyException;
import exporter.Processor;
import exporter.StillHavePlaceholderExceptionPolicy;
import model.UML;
import org.w3c.dom.Document;
import template.model.Template;
import utils.XMLUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TemplateGUI extends JFrame implements ActionListener {

    JTextArea templateArea, xmlArea;

    public TemplateGUI() {
        super("Templates Basic GUI");

        UIManager.put("Label.font", new FontUIResource(new Font("Calibri", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Calibri", Font.PLAIN, 20)));
        UIManager.put("TextArea.font", new FontUIResource(new Font("Courier New", Font.PLAIN, 15)));

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        JLabel templateLabel = new JLabel("Template");
        GridBagConstraints templateLabelConstraints = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(templateLabel, templateLabelConstraints);

        templateArea = new JTextArea();
        templateArea.setText("/bookstore              -- UML\n" +
                "> book                  -- Class in classes\n" +
                ">> @category            -- name\n" +
                ">> title                -- PrimitiveProperty(String) in properties\n" +
                ">>> @value              -- defaultValue\n" +
                ">> author               -- PrimitiveProperty(String) in properties\n" +
                ">>> @value              -- defaultValue\n" +
                ">> year                 -- PrimitiveProperty(Integer) in properties\n" +
                ">>> @value              -- defaultValue\n" +
                ">> price                -- PrimitiveProperty(Real) in properties\n" +
                ">>> @value              -- defaultValue");
        GridBagConstraints templateAreaConstraints = new GridBagConstraints(0, 1, 1, 10, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(templateArea, templateAreaConstraints);

        JLabel xmlLabel = new JLabel("XML");
        GridBagConstraints xmlLabelConstraints = new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(xmlLabel, xmlLabelConstraints);

        xmlArea = new JTextArea();
        xmlArea.setText("<bookstore>\n" +
                "<book category=\"cooking\">\n" +
                "<title lang=\"en\">Everyday Italian</title>\n" +
                "<author>Giada De Laurentiis</author>\n" +
                "<year>2005</year>\n" +
                "<price>30.00</price>\n" +
                "</book>\n" +
                "<book category=\"children\">\n" +
                "<title lang=\"en\">Harry Potter</title>\n" +
                "<author>J K. Rowling</author>\n" +
                "<year>2005</year>\n" +
                "<price>29.99</price>\n" +
                "</book>\n" +
                "<book category=\"web\">\n" +
                "<title lang=\"en\">XQuery Kick Start</title>\n" +
                "<author>James McGovern</author>\n" +
                "<author>Per Bothner</author>\n" +
                "<author>Kurt Cagle</author>\n" +
                "<author>James Linn</author>\n" +
                "<author>Vaidyanathan Nagarajan</author>\n" +
                "<year>2003</year>\n" +
                "<price>49.99</price>\n" +
                "</book>\n" +
                "<book category=\"web\" cover=\"paperback\">\n" +
                "<title lang=\"en\">Learning XML</title>\n" +
                "<author>Erik T. Ray</author>\n" +
                "<year>2003</year>\n" +
                "<price>39.95</price>\n" +
                "</book>\n" +
                "</bookstore>");
        GridBagConstraints xmlAreaConstraints = new GridBagConstraints(1, 1, 1, 10, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(xmlArea, xmlAreaConstraints);

        JButton generateUML = new JButton("Generate UML");
        GridBagConstraints generateUMLConstraints = new GridBagConstraints(0, 12, 2, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        generateUML.addActionListener(this);
        panel.add(generateUML, generateUMLConstraints);

        setPreferredSize(new Dimension(800, 400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public static void main(String[] args) {
        new TemplateGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Template template = TemplateParser.parseTemplateFromString(templateArea.getText());
        Document xmlDocument = XMLUtils.convertStringToDocument(xmlArea.getText());
        UML result = TemplateInterpreter.interpret(xmlDocument, template);

        try {
            String resultXMI = Processor.processUML(result, StillHavePlaceholderExceptionPolicy.ByPass);
            Files.write(Paths.get("Overall.xmi"), resultXMI.getBytes());
            Desktop.getDesktop().open(new File("."));

        } catch (IOException | StillHavePlaceHolderException | UnknownParameterException | UnknownPropertyException ioException) {
            ioException.printStackTrace();
        }


    }
}
