with Globals_Example1;

package Md_Example7 is
   type T is tagged record
      Attribute : Globals_Example1.Itype;
   end record;

   type T2 is new T with record
      Child_Attribute : Globals_Example1.Itype;
   end record;

   type T3 is new T2 with null record;

end Md_Example7;
