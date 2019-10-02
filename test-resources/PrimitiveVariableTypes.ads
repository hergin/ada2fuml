with Class1; use Class1;

package PrimitivePropertyTypes is

      someInteger : Integer := 1;
      someNatural : Natural := 2;
      someBoolean : Boolean := False;
      someString : String(1 .. 5) := "hello";
      someFloat : Float := 1.1;
      anotherFloat : Float;

      someClass1 : Class1.Class1;

      function someFunction (Self : in Integer) return Class1.Class1;

end PrimitivePropertyTypes;
