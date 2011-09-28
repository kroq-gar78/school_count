/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author Aditya Vaidya <kroq.gar78@gmail.com>
 * @version 2.0
 */

public class SchoolCountdown
{
    /**
     * @param args the command line arguments
     */

    public static final int longConverter = (int)Math.pow( 2 , 16 );

    public static final String iconName = "schoolCountdown.gif";
    private static JPanel timerDisplay;
    private static JLabel statementEnd = new JLabel();
    private static JLabel statementClosest = new JLabel();
    public static final long millisToSec = (long)Math.pow( 10 , 3 );
    public static final int hour_sec = 60*60; //num of seconds in an hour
    
    static class Holiday implements Comparable<Holiday>
	{
		public Holiday( GregorianCalendar date , String name )
		{
			this.date = date;
			this.name = name;
			this.length = 1;
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
		public int length; //amount of days of holiday (excluding regular weekends)
	}
	
    //public static Holiday laborDay, schoolEnd = new Holiday();
    public static Holiday laborDay = new Holiday( new GregorianCalendar( 2011, Calendar.SEPTEMBER , 2 , 3 , 30 ) , "Labor Day" );
    public static Holiday fallHoliday = new Holiday( new GregorianCalendar( 2011, Calendar.OCTOBER , 20 , 3 , 30 ) , "the Fall holiday" ); //October 21 and 24, 2011 holidays, weekend starts October 20, 2011, 3:30 P.M.
    public static Holiday thanksgivingBreak = new Holiday( new GregorianCalendar( 2011, Calendar.NOVEMBER , 22 , 3 , 30 ) , "Thanksgiving Break" ); //Thanksgiving 2011; holiday starts November 22, 2011, 3:30 P.M.
    public static Holiday winterBreak = new Holiday( new GregorianCalendar( 2011, Calendar.DECEMBER , 18 , 12 , 40 ) , "Winter break" );
    public static Holiday mlkDay = new Holiday( new GregorianCalendar( 2012 , Calendar.JANUARY , 13 , 3 , 30 ) , "MLK Day" );
    public static Holiday presDay = new Holiday( new GregorianCalendar( 2012 , Calendar.FEBRUARY , 17 , 3 , 30 ) , "President's Day" );
    public static Holiday springBreak = new Holiday( new GregorianCalendar( 2012 , Calendar.MARCH , 9 , 3 , 30 ) , "Spring Break" );
    public static Holiday springHoliday = new Holiday( new GregorianCalendar( 2012 , Calendar.APRIL , 5 , 3 , 30) , "the Spring holiday" );
    public static Holiday memorialDay = new Holiday( new GregorianCalendar( 2012 , Calendar.MAY , 25 , 3 , 30 ) , "Memorial Day" );
    public static Holiday schoolEnd = new Holiday( new GregorianCalendar( 2012 , Calendar.JUNE , 1 , 12 , 40 ) , "Summer Break" );
    
    public static void main(String[] args)
    {
        TrayIcon icon = null;
        GregorianCalendar today = new GregorianCalendar();
        //JLabel timer = new JLabel();
        //JProgressBar progress = new JProgressBar( 0 , (int) (1000) ); //divide to fit into int data range
        Holiday[] holidays = { laborDay , fallHoliday , thanksgivingBreak , winterBreak , mlkDay , presDay , springBreak , springHoliday , memorialDay , schoolEnd };
		//sorts in chronological order
        java.util.Arrays.sort( holidays );
        //check when the closest one is (not passed already)
        int earliestHoliday = 0; 
        
        //print array for confirmation
        /*for( int i = 0; i < holidays.length; i++ )
        {
			System.out.println( holidays[i].name );
		}*/
        if( today.after( schoolEnd.date ) ) //if after school is over, open popup and close
        {
            JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
            System.exit( 0 );
        }
        if( !SystemTray.isSupported() ) //exit if the system tray isn't supported
        {
            JOptionPane.showMessageDialog( null , "This system does not support the tray icon feature. Terminating now." , "Error" , JOptionPane.ERROR_MESSAGE );
            System.exit( 0 );
        }
        
        //setup procedure - JPanel displays
        timerDisplay = new JPanel();
        timerDisplay.setLayout( new GridLayout(2,1) );
        timerDisplay.add( statementClosest );
        timerDisplay.add( statementEnd );
		
		//set look and feel to system for better integration into the desktop
		try
		{
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
			System.out.println( UIManager.getSystemLookAndFeelClassName() );
		}
		catch( Exception e )
		{
			System.err.println( "Failed to retrieve system look and feel. Falling back to default look and feel.\n" );
			e.printStackTrace();
			JOptionPane.showMessageDialog( null , "School Countdown Timer failed to retrieve the system look and feel. Falling back to default look and feel." , "Warning" , JOptionPane.WARNING_MESSAGE );
		}
        //setup procedure - Tray Icon
        System.out.println( "running..." );
        SystemTray tray = SystemTray.getSystemTray(); //retrieve instance
        BufferedImage img = null;
        URL imgURL = null;
        
        try
        {
			imgURL = new SchoolCountdown().getClass().getResource(iconName);
			img = ImageIO.read(imgURL);
		}
		catch( Exception e ) //usually MalformedURLException
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog( null , "School Countdown Timer encountered an error while\ntrying to load the icon. Terminating now." , "Error" , JOptionPane.ERROR_MESSAGE );
			System.err.println( "Error loading Icon. Now exiting..." );
			System.exit(1); //exit with error code
		}
		
        ActionListener exitListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                System.exit( 0 );
            }
        };
        ActionListener timerListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                JOptionPane.showMessageDialog( null , timerDisplay , "School Countdown Timer notification" , JOptionPane.INFORMATION_MESSAGE );
            }
        };
        //create Popup menu
        PopupMenu menu = new PopupMenu();
        MenuItem timerItem = new MenuItem( "Timer" ); timerItem.addActionListener( timerListener ); //create "info' meun item
        MenuItem exitItem = new MenuItem( "Exit" ); exitItem.addActionListener( exitListener ); //create "exit" menu item
        menu.add( timerItem );
        menu.addSeparator();
        menu.add( exitItem );
        icon = new TrayIcon( img , "days until school is over..." , menu ); //instantiate tray icon
        icon.setImageAutoSize( true ); //auto-resize icon for computer
        icon.addMouseListener
        (
                new MouseListener()
                {
                    public void mouseClicked(MouseEvent e)
                    {
                        if( e.getButton() == MouseEvent.BUTTON1 )
                        {
                            JOptionPane.showMessageDialog( null , timerDisplay , "School Countdown Timer notification" , JOptionPane.INFORMATION_MESSAGE );
                        }
                    }

                    public void mousePressed(MouseEvent e)
                    {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void mouseReleased(MouseEvent e)
                    {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void mouseEntered(MouseEvent e)
                    {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void mouseExited(MouseEvent e)
                    {
                        //throw new UnsupportedOperationException("Not supported yet.");
                    }
                }
        );

        //add icon to tray
        try
        {
            tray.add( icon );
            icon.displayMessage( "Welcome" , "Welcome to School Countdown Timer" , TrayIcon.MessageType.NONE );
        }
        catch( AWTException e )
        {
            System.err.println( e );
            JOptionPane.showMessageDialog( null , "School Countdown Timer has experienced an unknown error." , "Error" , JOptionPane.ERROR_MESSAGE );
            System.exit(0);
        }

        //timed update
        for( ;; )
        {
            try
            {
                if( new GregorianCalendar().after( schoolEnd ) ) //if after school is over, open popup and close
                {
                    JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
                    System.exit( 0 );
                }
                while( new GregorianCalendar().after( holidays[earliestHoliday].date ) ) //check when the closest one is (not passed already)
                {
					earliestHoliday++;
				}
                
                int[] untilClosest = timeRemaining( new GregorianCalendar() , holidays[earliestHoliday].date );
                int[] untilSummer = timeRemaining( new GregorianCalendar() , holidays[holidays.length-1].date );
                statementClosest.setText( "Only " + untilClosest[0] + " days, " + untilClosest[1] + " hours, " + untilClosest[2] + " minutes, and " + untilClosest[3] + " seconds until " + holidays[earliestHoliday].name + " and" );
                statementEnd.setText( "Only " + untilSummer[0] + " days, " + untilSummer[1] + " hours, " + untilSummer[2] + " minutes, and " + untilSummer[3] + " seconds until " + holidays[holidays.length-1].name + "!" );
                //icon.setToolTip( ( untilSummer[1] > 12 ? untilSummer[0]+1: untilSummer[0] ) + " days until school is over!" ); //do rounding and set tooltip at same time
				icon.setToolTip( (untilSummer[0] <= 90 ? (( untilSummer[1] > 12 ? untilSummer[0]+1: untilSummer[0] ) + " days until school is over!" ) : 
					(( untilClosest[1] > 12 ? untilClosest[0]+1: untilClosest[0]) + " days until the closest holiday!" )  ) ); //do rounding, choose which day to count to, and set tooltip at same time!
                
                Thread.sleep((int) (1*(1000))); //update every 1 second
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
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
    
    public static boolean runningFromJAR()
    {
		String className = new SchoolCountdown().getClass().getName().replace('.','/');
		String classJar = new SchoolCountdown().getClass().getResource("/"+className+".class").toString();
		if( classJar.startsWith("jar:") ) return true;
		return false;
	}
}
