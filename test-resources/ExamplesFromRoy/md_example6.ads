with Globals_Example1;
with Ada.Finalization;

package Md_Example6 is

   type T is new Ada.Finalization.Controlled with record
      Attribute : Globals_Example1.Itype;
   end record;

   -- Ada Rules state that all tagged operations must be defined here

   function Do_It (The_T : T) return Globals_Example1.Itype;
   procedure Initialize(The_T : in out T);

   -- Did not declare procedure Finalize and thus there is no destructor

end Md_Example6;
