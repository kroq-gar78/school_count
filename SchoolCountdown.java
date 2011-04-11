import java.util.GregorianCalendar;

//feature test version; no gui
public class SchoolCountdown
{
	public static final long millisToSec = (long)Math.pow( 10 , 3 );
	public static final int hour_sec = 60*60; //num of seconds in an hour
	public static final GregorianCalendar schoolEnd = new GregorianCalendar( 2011, 5 , 6 , 12 , 40 ); //June 6, 2011, 12:40 P.M.
	public static final GregorianCalendar aprilHoliday = new GregorianCalendar( 2011, 3 , 21 , 3 , 30 ); //April 22, 2011 Holiday, weekend start April 22, 2011, 3:30 P.M.
	public static final GregorianCalendar mayHoliday = new GregorianCalendar( 2011, 4, 27, 3, 30 ); //May 30, 2011 Holiday, weekend start May 27, 2011, 3:30 P.M.
	public static final GregorianCalendar springBreak = new GregorianCalendar( 2011, 2, 11, 3, 30 ); //spring break; though program written after start of (during) Spring Break; only for testing purposes
	
	public static void main(String[] args)
	{
		GregorianCalendar[] holidays = { schoolEnd , aprilHoliday, mayHoliday, springBreak };
		//sorts in chronological order
		java.util.Arrays.sort( holidays );
		//System.out.println( holidays[0] );
		//check when the closest one is (not passed already)
		int earliestHoliday = holidays.length-1; //"iSave"; index of earliest hliday still to come in array "holidays"; if for loop somehow fails, default to summer
		for(int i = 0; i < holidays.length; i++ )
		{
			if( holidays[i].after(new GregorianCalendar()) )
			{
				earliestHoliday = i;
				break;
			}
		}
		//System.out.println(holidays[earliestHoliday]);
		
		for( ;; ) //always, until program termination
		{
			/*int[] endLeft = timeRemaining( new GregorianCalendar() , schoolEnd ); //time until end of school
			String endStatement = "Only " + endLeft[0] + " days, " + endLeft[1] + " hours, " + endLeft[2] + " minutes, and " + endLeft[3] + " seconds until school is over!";
			System.out.println( endStatement );*/
			try
			{
				Thread.sleep( 1000 * 1 ); //wait 1 second before sarting again
			}
			catch( Exception e ) { e.printStackTrace(); }
			int[] untilClosest = timeRemaining( new GregorianCalendar() , holidays[earliestHoliday] );
			int[] untilSummer = timeRemaining( new GregorianCalendar() , holidays[holidays.length-1] );
			String endStatement = "Only " + untilClosest[0] + " days, " + untilClosest[1] + " hours, " + untilClosest[2] + " minutes, and " + untilClosest[3] + " seconds until the closest holiday! and\nOnly " + untilSummer[0] + " days, " + untilSummer[1] + " hours, " + untilSummer[2] + " minutes, and " + untilSummer[3] + " seconds until summer break!";
			System.out.println( endStatement);
			
		}
	}
	
	public static int[] timeRemaining( GregorianCalendar start , GregorianCalendar end )
	{
		int[] remainingVals = new int[4]; //days, hours, minutes, seconds; array to return
		//weeks?
		
		int seconds = (int)((end.getTimeInMillis() - start.getTimeInMillis())/millisToSec); //seconds remaining
		
		remainingVals[0] = seconds/(24*hour_sec); //days
		seconds %= (24*hour_sec);
		remainingVals[1] = seconds/hour_sec; //hours
		seconds %= hour_sec;
		remainingVals[2] = seconds/60; //minutes
		seconds %= 60;
		remainingVals[3] = seconds; //seconds
		
		return remainingVals;
	}
}
