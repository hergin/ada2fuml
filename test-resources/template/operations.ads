package SomePackage is

   type SomeClass is record
      someAttribute : Integer := 1;
   end record;

   function someFunction (Self : in SomeClass) return Integer;

   procedure someProcedure (Self : in SomeClass);

   procedure someUnrelatedProcedure;

end SomePackage;
