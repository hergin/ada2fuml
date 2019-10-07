with Globals_Example1;
with Md_Example4;

package Md_Example5 is

   type T is new Md_Example4.T with record
      Child_Attribute : Globals_Example1.Itype;
   end record;

   procedure Display_It (The_T : T);

end Md_Example5;
