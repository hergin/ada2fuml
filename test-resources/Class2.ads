with Class1; use Class1;
package Class2 is

    type Class1Array is array (Natural range <>) of Class1.Class1;

    Min : constant := 1;
    type Class2(Max : Natural := 1) is record
        Class1List : Class1Array (Min .. Max);
    end record;

    function Initialize(Max : Natural) return Class2;


end Class2;