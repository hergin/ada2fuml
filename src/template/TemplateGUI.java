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
        GridBagConstraints templateLabelConstraints = new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(templateLabel, templateLabelConstraints);

        templateArea = new JTextArea();
        templateArea.setText("/compilation_unit                                                   -- UML\n" +
                "> @def_name                                                         -- name\n" +
                "> unit_declaration_q/package_declaration                            -- Package in packages\n" +
                ">> names_ql/defining_identifier/@def_name                           -- name\n" +
                ">> visible_part_declarative_items_ql/ordinary_type_declaration      -- Class in classes\n" +
                ">>> names_ql/defining_identifier/@def_name                          -- name\n" +
                ">>> type_declaration_view_q/record_type_definition/record_definition_q/record_definition/record_components_ql/component_declaration -- ClassProperty in properties\n" +
                ">>>> names_ql/defining_identifier/@def_name                         -- name\n" +
                ">> visible_part_declarative_items_ql/subtype_declaration            -- Primitive in primitives\n" +
                ">>> names_ql/defining_identifier/@def_name                          -- name\n" +
                ">>> type_declaration_view_q/subtype_indication/subtype_mark_q       -- superPrimitive\n" +
                ">>>> identifier/@ref_name                                           -- name");
        GridBagConstraints templateAreaConstraints = new GridBagConstraints(1, 1, 1, 10, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        JScrollPane templateScrollPane = new JScrollPane(templateArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        templateScrollPane.setPreferredSize(new Dimension(100, 100));
        panel.add(templateScrollPane, templateAreaConstraints);

        JLabel xmlLabel = new JLabel("XML");
        GridBagConstraints xmlLabelConstraints = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        panel.add(xmlLabel, xmlLabelConstraints);

        xmlArea = new JTextArea();
        xmlArea.setText("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<compilation_unit unit_kind=\"A_Package\" unit_class=\"A_Public_Declaration\" unit_origin=\"An_Application_Unit\" unit_full_name=\"Globals_Example1\" def_name=\"Globals_Example1\" source_file=\"globals_example1.ads\">\n" +
                "   <sloc line=\"1\" col=\"1\" endline=\"13\" endcol=\"21\"/>\n" +
                "   <context_clause_elements_ql>\n" +
                "   </context_clause_elements_ql>\n" +
                "   <unit_declaration_q>\n" +
                "      <package_declaration>\n" +
                "         <sloc line=\"1\" col=\"1\" endline=\"13\" endcol=\"21\"/>\n" +
                "         <names_ql>\n" +
                "            <defining_identifier def_name=\"Globals_Example1\" def=\"ada://package/Globals_Example1-1:9\" type=\"null\">\n" +
                "               <sloc line=\"1\" col=\"9\" endline=\"1\" endcol=\"24\"/>\n" +
                "            </defining_identifier>\n" +
                "         </names_ql>\n" +
                "         <aspect_specifications_ql>\n" +
                "         </aspect_specifications_ql>\n" +
                "         <visible_part_declarative_items_ql>\n" +
                "            <ordinary_type_declaration>\n" +
                "               <sloc line=\"2\" col=\"4\" endline=\"2\" endcol=\"29\"/>\n" +
                "               <names_ql>\n" +
                "                  <defining_identifier def_name=\"Itype\" def=\"ada://ordinary_type/Globals_Example1-1:9/Itype-2:9\" type=\"null\">\n" +
                "                     <sloc line=\"2\" col=\"9\" endline=\"2\" endcol=\"13\"/>\n" +
                "                  </defining_identifier>\n" +
                "               </names_ql>\n" +
                "               <discriminant_part_q>\n" +
                "                  <not_an_element>\n" +
                "                     <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                  </not_an_element>\n" +
                "               </discriminant_part_q>\n" +
                "               <type_declaration_view_q>\n" +
                "                  <derived_type_definition>\n" +
                "                     <sloc line=\"2\" col=\"18\" endline=\"2\" endcol=\"28\"/>\n" +
                "                     <has_abstract_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_abstract_q>\n" +
                "                     <has_limited_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_limited_q>\n" +
                "                     <parent_subtype_indication_q>\n" +
                "                        <subtype_indication>\n" +
                "                           <sloc line=\"2\" col=\"22\" endline=\"2\" endcol=\"28\"/>\n" +
                "                           <has_aliased_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                              </not_an_element>\n" +
                "                           </has_aliased_q>\n" +
                "                           <has_null_exclusion_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                              </not_an_element>\n" +
                "                           </has_null_exclusion_q>\n" +
                "                           <subtype_mark_q>\n" +
                "                              <identifier ref_name=\"Integer\" ref=\"ada://ordinary_type/Standard-1:1/Integer-1:1\" type=\"null\">\n" +
                "                                 <sloc line=\"2\" col=\"22\" endline=\"2\" endcol=\"28\"/>\n" +
                "                              </identifier>\n" +
                "                           </subtype_mark_q>\n" +
                "                           <subtype_constraint_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                              </not_an_element>\n" +
                "                           </subtype_constraint_q>\n" +
                "                        </subtype_indication>\n" +
                "                     </parent_subtype_indication_q>\n" +
                "                  </derived_type_definition>\n" +
                "               </type_declaration_view_q>\n" +
                "               <aspect_specifications_ql>\n" +
                "               </aspect_specifications_ql>\n" +
                "            </ordinary_type_declaration>\n" +
                "            <ordinary_type_declaration>\n" +
                "               <sloc line=\"3\" col=\"4\" endline=\"3\" endcol=\"27\"/>\n" +
                "               <names_ql>\n" +
                "                  <defining_identifier def_name=\"Ftype\" def=\"ada://ordinary_type/Globals_Example1-1:9/Ftype-3:9\" type=\"null\">\n" +
                "                     <sloc line=\"3\" col=\"9\" endline=\"3\" endcol=\"13\"/>\n" +
                "                  </defining_identifier>\n" +
                "               </names_ql>\n" +
                "               <discriminant_part_q>\n" +
                "                  <not_an_element>\n" +
                "                     <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                  </not_an_element>\n" +
                "               </discriminant_part_q>\n" +
                "               <type_declaration_view_q>\n" +
                "                  <derived_type_definition>\n" +
                "                     <sloc line=\"3\" col=\"18\" endline=\"3\" endcol=\"26\"/>\n" +
                "                     <has_abstract_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_abstract_q>\n" +
                "                     <has_limited_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_limited_q>\n" +
                "                     <parent_subtype_indication_q>\n" +
                "                        <subtype_indication>\n" +
                "                           <sloc line=\"3\" col=\"22\" endline=\"3\" endcol=\"26\"/>\n" +
                "                           <has_aliased_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                              </not_an_element>\n" +
                "                           </has_aliased_q>\n" +
                "                           <has_null_exclusion_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                              </not_an_element>\n" +
                "                           </has_null_exclusion_q>\n" +
                "                           <subtype_mark_q>\n" +
                "                              <identifier ref_name=\"Float\" ref=\"ada://ordinary_type/Standard-1:1/Float-1:1\" type=\"null\">\n" +
                "                                 <sloc line=\"3\" col=\"22\" endline=\"3\" endcol=\"26\"/>\n" +
                "                              </identifier>\n" +
                "                           </subtype_mark_q>\n" +
                "                           <subtype_constraint_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                              </not_an_element>\n" +
                "                           </subtype_constraint_q>\n" +
                "                        </subtype_indication>\n" +
                "                     </parent_subtype_indication_q>\n" +
                "                  </derived_type_definition>\n" +
                "               </type_declaration_view_q>\n" +
                "               <aspect_specifications_ql>\n" +
                "               </aspect_specifications_ql>\n" +
                "            </ordinary_type_declaration>\n" +
                "            <ordinary_type_declaration>\n" +
                "               <sloc line=\"5\" col=\"4\" endline=\"7\" endcol=\"14\"/>\n" +
                "               <names_ql>\n" +
                "                  <defining_identifier def_name=\"Record_With_Integer_Rtype\" def=\"ada://ordinary_type/Globals_Example1-1:9/Record_With_Integer_Rtype-5:9\" type=\"null\">\n" +
                "                     <sloc line=\"5\" col=\"9\" endline=\"5\" endcol=\"33\"/>\n" +
                "                  </defining_identifier>\n" +
                "               </names_ql>\n" +
                "               <discriminant_part_q>\n" +
                "                  <not_an_element>\n" +
                "                     <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                  </not_an_element>\n" +
                "               </discriminant_part_q>\n" +
                "               <type_declaration_view_q>\n" +
                "                  <record_type_definition>\n" +
                "                     <sloc line=\"5\" col=\"38\" endline=\"7\" endcol=\"13\"/>\n" +
                "                     <has_abstract_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_abstract_q>\n" +
                "                     <has_limited_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_limited_q>\n" +
                "                     <record_definition_q>\n" +
                "                        <record_definition>\n" +
                "                           <sloc line=\"5\" col=\"38\" endline=\"7\" endcol=\"13\"/>\n" +
                "                           <has_limited_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                              </not_an_element>\n" +
                "                           </has_limited_q>\n" +
                "                           <record_components_ql>\n" +
                "                              <component_declaration>\n" +
                "                                 <sloc line=\"6\" col=\"7\" endline=\"6\" endcol=\"24\"/>\n" +
                "                                 <names_ql>\n" +
                "                                    <defining_identifier def_name=\"Attribute\" def=\"ada://component/Globals_Example1-1:9/Record_With_Integer_Rtype-5:9/Attribute-6:7\" type=\"null\">\n" +
                "                                       <sloc line=\"6\" col=\"7\" endline=\"6\" endcol=\"15\"/>\n" +
                "                                    </defining_identifier>\n" +
                "                                 </names_ql>\n" +
                "                                 <has_aliased_q>\n" +
                "                                    <not_an_element>\n" +
                "                                       <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                    </not_an_element>\n" +
                "                                 </has_aliased_q>\n" +
                "                                 <object_declaration_view_q>\n" +
                "                                    <component_definition>\n" +
                "                                       <sloc line=\"6\" col=\"19\" endline=\"6\" endcol=\"23\"/>\n" +
                "                                       <has_aliased_q>\n" +
                "                                          <not_an_element>\n" +
                "                                             <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                          </not_an_element>\n" +
                "                                       </has_aliased_q>\n" +
                "                                       <component_definition_view_q>\n" +
                "                                          <subtype_indication>\n" +
                "                                             <sloc line=\"6\" col=\"19\" endline=\"6\" endcol=\"23\"/>\n" +
                "                                             <has_aliased_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </has_aliased_q>\n" +
                "                                             <has_null_exclusion_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </has_null_exclusion_q>\n" +
                "                                             <subtype_mark_q>\n" +
                "                                                <identifier ref_name=\"Itype\" ref=\"ada://ordinary_type/Globals_Example1-1:9/Itype-2:9\" type=\"null\">\n" +
                "                                                   <sloc line=\"6\" col=\"19\" endline=\"6\" endcol=\"23\"/>\n" +
                "                                                </identifier>\n" +
                "                                             </subtype_mark_q>\n" +
                "                                             <subtype_constraint_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </subtype_constraint_q>\n" +
                "                                          </subtype_indication>\n" +
                "                                       </component_definition_view_q>\n" +
                "                                    </component_definition>\n" +
                "                                 </object_declaration_view_q>\n" +
                "                                 <initialization_expression_q>\n" +
                "                                    <not_an_element>\n" +
                "                                       <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                    </not_an_element>\n" +
                "                                 </initialization_expression_q>\n" +
                "                                 <aspect_specifications_ql>\n" +
                "                                 </aspect_specifications_ql>\n" +
                "                              </component_declaration>\n" +
                "                           </record_components_ql>\n" +
                "                        </record_definition>\n" +
                "                     </record_definition_q>\n" +
                "                  </record_type_definition>\n" +
                "               </type_declaration_view_q>\n" +
                "               <aspect_specifications_ql>\n" +
                "               </aspect_specifications_ql>\n" +
                "            </ordinary_type_declaration>\n" +
                "            <ordinary_type_declaration>\n" +
                "               <sloc line=\"9\" col=\"4\" endline=\"11\" endcol=\"14\"/>\n" +
                "               <names_ql>\n" +
                "                  <defining_identifier def_name=\"Record_With_Float_Rtype\" def=\"ada://ordinary_type/Globals_Example1-1:9/Record_With_Float_Rtype-9:9\" type=\"null\">\n" +
                "                     <sloc line=\"9\" col=\"9\" endline=\"9\" endcol=\"31\"/>\n" +
                "                  </defining_identifier>\n" +
                "               </names_ql>\n" +
                "               <discriminant_part_q>\n" +
                "                  <not_an_element>\n" +
                "                     <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                  </not_an_element>\n" +
                "               </discriminant_part_q>\n" +
                "               <type_declaration_view_q>\n" +
                "                  <record_type_definition>\n" +
                "                     <sloc line=\"9\" col=\"36\" endline=\"11\" endcol=\"13\"/>\n" +
                "                     <has_abstract_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_abstract_q>\n" +
                "                     <has_limited_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_limited_q>\n" +
                "                     <record_definition_q>\n" +
                "                        <record_definition>\n" +
                "                           <sloc line=\"9\" col=\"36\" endline=\"11\" endcol=\"13\"/>\n" +
                "                           <has_limited_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                              </not_an_element>\n" +
                "                           </has_limited_q>\n" +
                "                           <record_components_ql>\n" +
                "                              <component_declaration>\n" +
                "                                 <sloc line=\"10\" col=\"7\" endline=\"10\" endcol=\"24\"/>\n" +
                "                                 <names_ql>\n" +
                "                                    <defining_identifier def_name=\"Attribute\" def=\"ada://component/Globals_Example1-1:9/Record_With_Float_Rtype-9:9/Attribute-10:7\" type=\"null\">\n" +
                "                                       <sloc line=\"10\" col=\"7\" endline=\"10\" endcol=\"15\"/>\n" +
                "                                    </defining_identifier>\n" +
                "                                 </names_ql>\n" +
                "                                 <has_aliased_q>\n" +
                "                                    <not_an_element>\n" +
                "                                       <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                    </not_an_element>\n" +
                "                                 </has_aliased_q>\n" +
                "                                 <object_declaration_view_q>\n" +
                "                                    <component_definition>\n" +
                "                                       <sloc line=\"10\" col=\"19\" endline=\"10\" endcol=\"23\"/>\n" +
                "                                       <has_aliased_q>\n" +
                "                                          <not_an_element>\n" +
                "                                             <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                          </not_an_element>\n" +
                "                                       </has_aliased_q>\n" +
                "                                       <component_definition_view_q>\n" +
                "                                          <subtype_indication>\n" +
                "                                             <sloc line=\"10\" col=\"19\" endline=\"10\" endcol=\"23\"/>\n" +
                "                                             <has_aliased_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </has_aliased_q>\n" +
                "                                             <has_null_exclusion_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </has_null_exclusion_q>\n" +
                "                                             <subtype_mark_q>\n" +
                "                                                <identifier ref_name=\"Ftype\" ref=\"ada://ordinary_type/Globals_Example1-1:9/Ftype-3:9\" type=\"null\">\n" +
                "                                                   <sloc line=\"10\" col=\"19\" endline=\"10\" endcol=\"23\"/>\n" +
                "                                                </identifier>\n" +
                "                                             </subtype_mark_q>\n" +
                "                                             <subtype_constraint_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </subtype_constraint_q>\n" +
                "                                          </subtype_indication>\n" +
                "                                       </component_definition_view_q>\n" +
                "                                    </component_definition>\n" +
                "                                 </object_declaration_view_q>\n" +
                "                                 <initialization_expression_q>\n" +
                "                                    <not_an_element>\n" +
                "                                       <sloc line=\"1\" col=\"1\" endline=\"0\" endcol=\"0\"/>\n" +
                "                                    </not_an_element>\n" +
                "                                 </initialization_expression_q>\n" +
                "                                 <aspect_specifications_ql>\n" +
                "                                 </aspect_specifications_ql>\n" +
                "                              </component_declaration>\n" +
                "                           </record_components_ql>\n" +
                "                        </record_definition>\n" +
                "                     </record_definition_q>\n" +
                "                  </record_type_definition>\n" +
                "               </type_declaration_view_q>\n" +
                "               <aspect_specifications_ql>\n" +
                "               </aspect_specifications_ql>\n" +
                "            </ordinary_type_declaration>\n" +
                "         </visible_part_declarative_items_ql>\n" +
                "         <private_part_declarative_items_ql>\n" +
                "         </private_part_declarative_items_ql>\n" +
                "      </package_declaration>\n" +
                "   </unit_declaration_q>\n" +
                "   <pragmas_after_ql>\n" +
                "   </pragmas_after_ql>\n" +
                "</compilation_unit>\n");
        GridBagConstraints xmlAreaConstraints = new GridBagConstraints(0, 1, 1, 10, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        JScrollPane xmlScrollPane = new JScrollPane(xmlArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        xmlScrollPane.setPreferredSize(new Dimension(100, 100));
        panel.add(xmlScrollPane, xmlAreaConstraints);

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
