// pretty much a structure, but with more functionality ;)

import java.util.GregorianCalendar;

public class Holiday implements Comparable<Holiday>
{
	// all of the holidays; might turn into an enum later
	public static final Holiday laborDay = new Holiday( new GregorianCalendar( 2011, GregorianCalendar.SEPTEMBER , 2 , 15 , 30 ) , "Labor Day" );
    public static final Holiday fallHoliday = new Holiday( new GregorianCalendar( 2011, GregorianCalendar.OCTOBER , 20 , 15 , 30 ) , "the Fall holiday" ); //October 21 and 24, 2011 holidays, weekend starts October 20, 2011, 3:30 P.M.
    public static final Holiday thanksgivingBreak = new Holiday( new GregorianCalendar( 2011, GregorianCalendar.NOVEMBER , 22 , 15 , 30 ) , "Thanksgiving Break" ); //Thanksgiving 2011; holiday starts November 22, 2011, 3:30 P.M.
    public static final Holiday winterBreak = new Holiday( new GregorianCalendar( 2011, GregorianCalendar.DECEMBER , 18 , 12 , 40 ) , "Winter break" );
    public static final Holiday mlkDay = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.JANUARY , 13 , 15 , 30 ) , "MLK Day" );
    public static final Holiday presDay = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.FEBRUARY , 17 , 15 , 30 ) , "President's Day" );
    public static final Holiday springBreak = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.MARCH , 9 , 15 , 30 ) , "Spring Break" );
    public static final Holiday springHoliday = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.APRIL , 5 , 15 , 30) , "the Spring holiday" );
    public static final Holiday memorialDay = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.MAY , 25 , 15 , 30 ) , "Memorial Day" );
    public static final Holiday schoolEnd = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.JUNE , 1 , 12 , 40 ) , "Summer Break" );
	
	public Holiday( GregorianCalendar date , String name )
	{
		this.date = date;
		this.name = name;
	}

	public boolean equals( Object o )
	{
		if(!(o instanceof Holiday ) )
		{
			return false;
		}
		Holiday h = (Holiday)o;
		return (h.date==this.date)&&(h.name==this.name);
	}
	public int compareTo( Holiday o )
	{
		return ( this.date.after(o.date) ? 1:-1 );
	}
	public int compareTo( GregorianCalendar o )
	{
		return ( this.date.after(o) ? 1:-1 );
	}
	
	public GregorianCalendar date;
	public String name;
}