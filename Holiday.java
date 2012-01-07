// pretty much a structure, but with fmore functionality ;)

import java.util.GregorianCalendar;

public class Holiday implements Comparable<Holiday>
{
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