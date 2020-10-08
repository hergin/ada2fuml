package xmlparsing;

import exceptions.InvalidSignatureException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

import static extractor.ExtractorTests.GetAdaXMLFromResource;
import static org.junit.jupiter.api.Assertions.*;

class GenericXmlParserTest {

    @Test
    void getNodeWithSignature() throws InvalidSignatureException {
        String xmlString = GetAdaXMLFromResource("ExamplesFromRoy/xmi-files/globals_example1.ads.xml");
        Document xmlDocument = GenericXmlParser.getDocumentFromXML(xmlString);

        List<Node> ordinaryTypeDeclarationNodes = GenericXmlParser.getNodesWithSignature(xmlDocument, "compilation_unit>unit_declaration_q>package_declaration>visible_part_declarative_items_ql>ordinary_type_declaration");
        assertEquals(4, ordinaryTypeDeclarationNodes.size());

        List<Node> definingIdentifiersInOrdinaryTypeDeclarations = GenericXmlParser.getNodesWithSignature(xmlDocument, "compilation_unit>unit_declaration_q>package_declaration>visible_part_declarative_items_ql>ordinary_type_declaration>names_ql>defining_identifier");
        assertEquals(4, definingIdentifiersInOrdinaryTypeDeclarations.size());
    }

    @Test
    void getNodesWithSignatureStartingFrom() throws InvalidSignatureException {
        String xmlString = GetAdaXMLFromResource("ExamplesFromRoy/xmi-files/globals_example1.ads.xml");
        Document xmlDocument = GenericXmlParser.getDocumentFromXML(xmlString);

        List<Node> ordinaryTypeDeclarationNodes = GenericXmlParser.getNodesWithSignature(xmlDocument, "compilation_unit>unit_declaration_q>package_declaration>visible_part_declarative_items_ql>ordinary_type_declaration");
        assertEquals(4, ordinaryTypeDeclarationNodes.size());

        List<Node> defining_identifier_list = GenericXmlParser.getNodesWithSignatureStartingFrom(ordinaryTypeDeclarationNodes.get(0), "ordinary_type_declaration>names_ql>defining_identifier");
        assertEquals(1, defining_identifier_list.size());
        assertEquals("Itype", defining_identifier_list.get(0).getAttributes().getNamedItem("def_name").getNodeValue());

        List<Node> identifierOfSubType_List = GenericXmlParser.getNodesWithSignatureStartingFrom(ordinaryTypeDeclarationNodes.get(1), "ordinary_type_declaration>type_declaration_view_q>derived_type_definition>parent_subtype_indication_q>subtype_indication>subtype_mark_q>identifier");
        assertEquals("Float", identifierOfSubType_List.get(0).getAttributes().getNamedItem("ref_name").getNodeValue());
    }

    @Test
    void getNodeWithSignature_invalidSignature() {
        String xmlString = GetAdaXMLFromResource("ExamplesFromRoy/xmi-files/globals_example1.ads.xml");
        Document xmlDocument = GenericXmlParser.getDocumentFromXML(xmlString);
        assertThrows(InvalidSignatureException.class, () -> {
            List<Node> ordinaryTypeDeclarationNodes = GenericXmlParser.getNodesWithSignature(xmlDocument, ".compilation_unit>unit_declaration_q>package_declaration>visible_part_declarative_items_ql>ordinary_type_declaration");
        });
    }


    @Test
    void isValidSignature() {
        assertTrue(GenericXmlParser.isValidSignature("compilation_unit>unit_declaration_q>package_declaration>visible_part_declarative_items_ql>ordinary_type_declaration"));
        assertTrue(GenericXmlParser.isValidSignature("compilation_unit>unit_declaration_q>package_declaration> visible_part_declarative_items_ql > ordinary_type_declaration"));
        assertTrue(GenericXmlParser.isValidSignature("_compi1lation_unit>unit_declaration_q>package_declaration> visible_part_declarative_items_ql > ordinary_type_3declaration"));
        assertFalse(GenericXmlParser.isValidSignature("compilation_unit>unit_declaration_q>package_declaration>  > ordinary_type_declaration"));
        assertFalse(GenericXmlParser.isValidSignature("compilation_unit unit_declaration_q>package_declaration>  > ordinary_type_declaration"));
    }

    @Test
    void getDocumentFromXML_basicXML() {
        String input = "<?xml version = \"1.0\"?>\n" +
                "<class>\n" +
                "   <student rollno = \"393\">\n" +
                "      <firstname>dinkar</firstname>\n" +
                "      <lastname>kad</lastname>\n" +
                "      <nickname>dinkar</nickname>\n" +
                "      <marks>85</marks>\n" +
                "   </student>\n" +
                "   \n" +
                "   <student rollno = \"493\">\n" +
                "      <firstname>Vaneet</firstname>\n" +
                "      <lastname>Gupta</lastname>\n" +
                "      <nickname>vinni</nickname>\n" +
                "      <marks>95</marks>\n" +
                "   </student>\n" +
                "   \n" +
                "   <student rollno = \"593\">\n" +
                "      <firstname>jasvir</firstname>\n" +
                "      <lastname>singn</lastname>\n" +
                "      <nickname>jazz</nickname>\n" +
                "      <marks>90</marks>\n" +
                "   </student>\n" +
                "</class>";
        Document result = GenericXmlParser.getDocumentFromXML(input);

        assertEquals("class", result.getDocumentElement().getNodeName());
        assertEquals(3, result.getDocumentElement().getElementsByTagName("student").getLength());
        for (int i = 0; i < result.getDocumentElement().getChildNodes().getLength(); i++) {
            if (result.getDocumentElement().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                assertEquals("student", result.getDocumentElement().getChildNodes().item(i).getNodeName());
            }
        }
    }

    @Test
    void getDocumentFromXML_basicAdaXML() {
        String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
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
                "</compilation_unit>\n";
        Document result = GenericXmlParser.getDocumentFromXML(input);

        Element compilationUnit = result.getDocumentElement();
        assertEquals("compilation_unit", compilationUnit.getNodeName());

        NodeList unitDeclarationQ_List = compilationUnit.getElementsByTagName("unit_declaration_q");
        assertEquals(1, unitDeclarationQ_List.getLength());

        NodeList ordinaryTypeDeclaration_List = compilationUnit.getElementsByTagName("ordinary_type_declaration");
        assertEquals(4, ordinaryTypeDeclaration_List.getLength());
    }
}