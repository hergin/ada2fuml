
package Gm_Unit_Rank_Types is


  -- This type defines an extended numeric ranking.  0 indicates no
  -- rank, 1 is the highest rank and 99 is the lowest rank.
  --
  subtype Extended_Numeric_Rank_Type is Integer range 0 .. 99;

  -- The following constant defines the value returned when a given
  -- object is not described in a particular Guidance and therefore has
  -- no rank value.
  --
  No_Numeric_Rank : constant Extended_Numeric_Rank_Type := 0;


  -- This type defines a numeric ranking.  1 is the highest rank and 99 is
  -- the lowest rank.
  --
  subtype Numeric_Rank_Type is Extended_Numeric_Rank_Type range 1 .. 99;

  Default_Numeric_Rank : constant Numeric_Rank_Type := 1;


  -- This type defines an extended ranking.  The numeric ranking is
  -- supplemented by the values of Restricted, Not Applicable, and
  -- Blank.
  --
  type Rank_Type is (Unknown, Restricted, Not_Applicable, Zero, One, Two, Three,
                     Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve,
                     Thirteen, Fourteen, Fifteen, Sixteen, Seventeen, Eighteen,
                     Nineteen, Twenty, Twenty_One, Twenty_Two, Twenty_Three,
                     Twenty_Four, Twenty_Five, Twenty_Six, Twenty_Seven,
                     Twenty_Eight, Twenty_Nine, Thirty, Thirty_One, Thirty_Two,
                     Thirty_Three, Thirty_Four, Thirty_Five, Thirty_Six,
                     Thirty_Seven, Thirty_Eight, Thirty_Nine, Forty, Forty_One,
                     Forty_Two, Forty_Three, Forty_Four, Forty_Five, Forty_Six,
                     Forty_Seven, Forty_Eight, Forty_Nine, Fifty, Fifty_One,
                     Fifty_Two, Fifty_Three, Fifty_Four, Fifty_Five, Fifty_Six,
                     Fifty_Seven, Fifty_Eight, Fifty_Nine, Sixty, Sixty_One,
                     Sixty_Two, Sixty_Three, Sixty_Four, Sixty_Five,
                     Sixty_Six, Sixty_Seven, Sixty_Eight, Sixty_Nine, Seventy,
                     Seventy_One, Seventy_Two, Seventy_Three, Seventy_Four,
                     Seventy_Five, Seventy_Six, Seventy_Seven, Seventy_Eight,
                     Seventy_Nine, Eighty, Eighty_One, Eighty_Two, Eighty_Three,
                     Eighty_Four, Eighty_Five, Eighty_Six, Eighty_Seven,
                     Eighty_Eight, Eighty_Nine, Ninety, Ninety_One,
                     Ninety_Two, Ninety_Three, Ninety_Four, Ninety_Five,
                     Ninety_Six, Ninety_Seven, Ninety_Eight, Ninety_Nine);

  Default_Rank : constant Rank_Type := Unknown;


  -- This type defines a system unit and its associated numeric rank.
  --
  type Unit_Numeric_Rank_Type is
    record
      Unit : Integer;
      Rank : Numeric_Rank_Type;
    end record;

  Default_Unit_Numeric_Rank : constant Unit_Numeric_Rank_Type :=
      Unit_Numeric_Rank_Type'(Unit => 0,
                              Rank => Default_Numeric_Rank);

  -- This type defines a system unit and its associated extended rank.
  --
  type Unit_Rank_Type is
    record
      Unit : Integer;
      Rank : Rank_Type;
    end record;

  Default_Unit_Rank : constant Unit_Rank_Type :=
      Unit_Rank_Type'(Unit => 0,
                      Rank => Default_Rank);

end Gm_Unit_Rank_Types;
