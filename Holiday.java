// pretty much a structure, but with more functionality ;)

import java.util.GregorianCalendar;

public enum Holiday implements Comparable<Holiday>
{
	// all of the holidays
	laborDay  ( new GregorianCalendar( 2011, GregorianCalendar.SEPTEMBER , 2 , 15 , 30 ) , "Labor Day" ),
    fallHoliday  ( new GregorianCalendar( 2011, GregorianCalendar.OCTOBER , 20 , 15 , 30 ) , "the Fall holiday" ), //October 21 and 24, 2011 holidays, weekend starts October 20, 2011, 3:30 P.M.
    thanksgivingBreak  ( new GregorianCalendar( 2011, GregorianCalendar.NOVEMBER , 22 , 15 , 30 ) , "Thanksgiving Break" ), //Thanksgiving 2011; holiday starts November 22, 2011, 3:30 P.M.
    winterBreak  ( new GregorianCalendar( 2011, GregorianCalendar.DECEMBER , 18 , 12 , 40 ) , "Winter break" ),
    mlkDay  ( new GregorianCalendar( 2012 , GregorianCalendar.JANUARY , 13 , 15 , 30 ) , "MLK Day" ),
    presDay  ( new GregorianCalendar( 2012 , GregorianCalendar.FEBRUARY , 17 , 15 , 30 ) , "President's Day" ),
    springBreak  ( new GregorianCalendar( 2012 , GregorianCalendar.MARCH , 9 , 15 , 30 ) , "Spring Break" ),
    springHoliday  ( new GregorianCalendar( 2012 , GregorianCalendar.APRIL , 5 , 15 , 30) , "the Spring holiday" ),
    memorialDay  ( new GregorianCalendar( 2012 , GregorianCalendar.MAY , 25 , 15 , 30 ) , "Memorial Day" ),
    schoolEnd  ( new GregorianCalendar( 2012 , GregorianCalendar.JUNE , 1 , 12 , 40 ) , "Summer Break" );
	
	Holiday( GregorianCalendar date , String name )
	{
		this.date = date;
		this.name = name;
	}

	public GregorianCalendar date;
	public String name;
}
