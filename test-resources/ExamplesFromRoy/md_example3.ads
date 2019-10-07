with Globals_Example1;

package Md_Example3 is

   type Record_With_Integer_Rtype is record
      Attribute : Globals_Example1.Itype;
   end record;


   function Unrelated (The_I : Globals_Example1.Itype)
      return Globals_Example1.Itype;


end Md_Example3;
