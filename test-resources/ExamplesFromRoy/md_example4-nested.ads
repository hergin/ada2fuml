package Md_Example4.Nested is

   type T is new Md_Example4.T with record
      Child_Attribute : Globals_Example1.Itype;
   end record;

   procedure Do_It (The_T : T);

end Md_Example4.Nested;
