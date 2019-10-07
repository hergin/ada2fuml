with Globals_Example1;

package Md_Example4 is

   type T is tagged record
      Attribute : Globals_Example1.Itype;
   end record;

   -- Ada Rules state that all tagged operations must be defined here

   function Unrelated (The_I : Globals_Example1.Itype)
      return Globals_Example1.Itype;

   -- No tagged operations can be defined here because the previous
   -- function dosn't involve the tagged type.


end Md_Example4;
