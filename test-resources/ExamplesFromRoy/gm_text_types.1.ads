
package Gm_Text_Types is

  -- This type defines the text block name length.
  subtype Name_Length_Type is Positive range 1 .. 25;

  -- This type defines a text block name associated with a text block.
  subtype Name_Type is String (Name_Length_Type);

  Default_Name : constant Name_Type := Name_Type'(others => 'a');

end Gm_Text_Types;
