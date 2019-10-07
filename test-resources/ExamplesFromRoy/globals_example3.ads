with Globals_Example1;

package Globals_Example3 is

   type Record3 is record
      I_Attribute : Globals_Example1.Itype;
   end record;

   function Unrelated (The_I : Globals_Example1.Itype)
       return Globals_Example1.Itype;

end Globals_Example3;
