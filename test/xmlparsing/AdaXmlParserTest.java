package xmlparsing;

import adaschema.CompilationUnit;
import adaschema.DefiningIdentifier;
import adaschema.OrdinaryTypeDeclaration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdaXmlParserTest {

    @Test
    public void parseAndProduceCompilationUnit() {
        String SomeClassWithBooleanAttribute = "<?xml version='1.0' encoding='UTF-8' ?>\n" +
                "<compilation_unit unit_kind='A_Package' unit_class='A_Public_Declaration' unit_origin='An_Application_Unit' unit_full_name='SomeClass' def_name='SomeClass' source_file='someclass.ads'>\n" +
                "   <sloc line='1' col='1' endline='7' endcol='14'/>\n" +
                "   <context_clause_elements_ql>\n" +
                "   </context_clause_elements_ql>\n" +
                "   <unit_declaration_q>\n" +
                "      <package_declaration>\n" +
                "         <sloc line='1' col='1' endline='7' endcol='14'/>\n" +
                "         <names_ql>\n" +
                "            <defining_identifier def_name='SomeClass' def='ada://package/SomeClass-1:9' type='null'>\n" +
                "               <sloc line='1' col='9' endline='1' endcol='17'/>\n" +
                "            </defining_identifier>\n" +
                "         </names_ql>\n" +
                "         <aspect_specifications_ql>\n" +
                "         </aspect_specifications_ql>\n" +
                "         <visible_part_declarative_items_ql>\n" +
                "            <ordinary_type_declaration>\n" +
                "               <sloc line='3' col='4' endline='5' endcol='14'/>\n" +
                "               <names_ql>\n" +
                "                  <defining_identifier def_name='SomeClass' def='ada://ordinary_type/SomeClass-1:9/SomeClass-3:9' type='null'>\n" +
                "                     <sloc line='3' col='9' endline='3' endcol='17'/>\n" +
                "                  </defining_identifier>\n" +
                "               </names_ql>\n" +
                "               <discriminant_part_q>\n" +
                "                  <not_an_element>\n" +
                "                     <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                  </not_an_element>\n" +
                "               </discriminant_part_q>\n" +
                "               <type_declaration_view_q>\n" +
                "                  <record_type_definition>\n" +
                "                     <sloc line='3' col='22' endline='5' endcol='13'/>\n" +
                "                     <has_abstract_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_abstract_q>\n" +
                "                     <has_limited_q>\n" +
                "                        <not_an_element>\n" +
                "                           <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                        </not_an_element>\n" +
                "                     </has_limited_q>\n" +
                "                     <record_definition_q>\n" +
                "                        <record_definition>\n" +
                "                           <sloc line='3' col='22' endline='5' endcol='13'/>\n" +
                "                           <has_limited_q>\n" +
                "                              <not_an_element>\n" +
                "                                 <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                              </not_an_element>\n" +
                "                           </has_limited_q>\n" +
                "                           <record_components_ql>\n" +
                "                              <component_declaration>\n" +
                "                                 <sloc line='4' col='7' endline='4' endcol='28'/>\n" +
                "                                 <names_ql>\n" +
                "                                    <defining_identifier def_name='someBoolean' def='ada://component/SomeClass-1:9/SomeClass-3:9/someBoolean-4:7' type='null'>\n" +
                "                                       <sloc line='4' col='7' endline='4' endcol='17'/>\n" +
                "                                    </defining_identifier>\n" +
                "                                 </names_ql>\n" +
                "                                 <has_aliased_q>\n" +
                "                                    <not_an_element>\n" +
                "                                       <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                                    </not_an_element>\n" +
                "                                 </has_aliased_q>\n" +
                "                                 <object_declaration_view_q>\n" +
                "                                    <component_definition>\n" +
                "                                       <sloc line='4' col='21' endline='4' endcol='27'/>\n" +
                "                                       <has_aliased_q>\n" +
                "                                          <not_an_element>\n" +
                "                                             <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                                          </not_an_element>\n" +
                "                                       </has_aliased_q>\n" +
                "                                       <component_definition_view_q>\n" +
                "                                          <subtype_indication>\n" +
                "                                             <sloc line='4' col='21' endline='4' endcol='27'/>\n" +
                "                                             <has_aliased_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </has_aliased_q>\n" +
                "                                             <has_null_exclusion_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </has_null_exclusion_q>\n" +
                "                                             <subtype_mark_q>\n" +
                "                                                <identifier ref_name='Boolean' ref='ada://ordinary_type/Standard-1:1/Boolean-1:1' type='null'>\n" +
                "                                                   <sloc line='4' col='21' endline='4' endcol='27'/>\n" +
                "                                                </identifier>\n" +
                "                                             </subtype_mark_q>\n" +
                "                                             <subtype_constraint_q>\n" +
                "                                                <not_an_element>\n" +
                "                                                   <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
                "                                                </not_an_element>\n" +
                "                                             </subtype_constraint_q>\n" +
                "                                          </subtype_indication>\n" +
                "                                       </component_definition_view_q>\n" +
                "                                    </component_definition>\n" +
                "                                 </object_declaration_view_q>\n" +
                "                                 <initialization_expression_q>\n" +
                "                                    <not_an_element>\n" +
                "                                       <sloc line='1' col='1' endline='0' endcol='0'/>\n" +
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


        CompilationUnit result = AdaXmlParser.parseAndProduceCompilationUnit(SomeClassWithBooleanAttribute);

        Assertions.assertEquals("A_Package", result.getUnitKind());
        Assertions.assertEquals(14, result.getSloc().getEndcol().intValue());

        // Name of the package
        Assertions.assertEquals("SomeClass", (((DefiningIdentifier) (result.getUnitDeclarationQ().getPackageDeclaration().getNamesQl().getNotAnElementOrDefiningIdentifierOrDefiningCharacterLiteral().get(0))).getDefName()));

        // Name of the class
        Assertions.assertEquals("SomeClass", (((DefiningIdentifier) ((OrdinaryTypeDeclaration) result.getUnitDeclarationQ().getPackageDeclaration().getVisiblePartDeclarativeItemsQl().getNotAnElementOrOrdinaryTypeDeclarationOrTaskTypeDeclaration().get(0)).getNamesQl().getNotAnElementOrDefiningIdentifierOrDefiningCharacterLiteral().get(0)).getDefName()));
    }
}