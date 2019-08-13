package SomeClass is

   type SomeClass1 is record
      someAttribute : Integer := 1;
   end record;

   type SomeClass2 is record
      someAttribute2 : SomeClass1;
   end record;

end SomeClass;
