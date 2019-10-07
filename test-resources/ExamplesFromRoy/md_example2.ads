with Globals_Example1;

package Md_Example2 is

   type Record_With_Integer_Rtype is record
      Attribute : Globals_Example1.Itype;
   end record;

   function Compute_An_Itype (The_Rtype : Record_With_Integer_Rtype)
      return Globals_Example1.Itype;

end Md_Example2;
