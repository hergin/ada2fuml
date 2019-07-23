package Test1 is

   type SomeClass is record
      someAttribute : Integer := 1;
   end record;

   type SomeClass2 is tagged record
      someAttribute2 : Integer := 2;
   end record;

   type SomeClass3 is record
      someAttribute3 : Integer := 3;
   end record;

   rogueVariable : Integer := 4;

   function someFunction (Self : in SomeClass) return Integer;
   
   procedure someProcedure (Self : in SomeClass);

   procedure someUnrelatedProcedure;

end Test1;
