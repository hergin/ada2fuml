package PrimitivePropertyTypes is

   type SomeClass is record
      someInteger : Integer := 1;
      someNatural : Natural := 2;
      someBoolean : Boolean := False;
      someString : String(1 .. 5) := "hello";
      someFloat : Float := 1.1;
   end record;

end PrimitivePropertyTypes;
