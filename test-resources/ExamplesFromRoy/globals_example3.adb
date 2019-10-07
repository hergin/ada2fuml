package body Globals_Example3 is

   function Unrelated (The_I : Globals_Example1.Itype)
       return Globals_Example1.Itype is
   begin
      return 5;
   end Unrelated;

end Globals_Example3;
