package body SomeClass is
   
   function someFunction (Self : in SomeClass) return Integer is
   begin
      return Self.someAttribute;
   end someFunction;

   procedure someProcedure (Self : in SomeClass) is
   begin
      null;
   end someProcedure;

   procedure someUnrelatedProcedure is
   begin
      null;
   end someUnrelatedProcedure;
   
end SomeClass;
