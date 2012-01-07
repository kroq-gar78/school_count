/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * @author Aditya Vaidya <kroq.gar78@gmail.com>
 * @version 2.6
 */

public class SchoolCountdown
{
    public static final String iconName = "schoolCountdown.gif";
    private static JPanel timerDisplay;
    private static JLabel statementEnd;
    private static JLabel statementClosest;
    public static final long millisToSec = (long)(1000); // number of milliseconds in a seconds
    public static final int hoursToSec = 60*60; //num of seconds in an hour
    
    // pretty much a struct, but with more functionality ;)
    static class Holiday implements Comparable<Holiday>
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
	
    //public static Holiday laborDay, schoolEnd = new Holiday();
    public static Holiday laborDay = new Holiday( new GregorianCalendar( 2011, GregorianCalendar.SEPTEMBER , 2 , 15 , 30 ) , "Labor Day" );
    public static Holiday fallHoliday = new Holiday( new GregorianCalendar( 2011, GregorianCalendar.OCTOBER , 20 , 15 , 30 ) , "the Fall holiday" ); //October 21 and 24, 2011 holidays, weekend starts October 20, 2011, 3:30 P.M.
    public static Holiday thanksgivingBreak = new Holiday( new GregorianCalendar( 2011, GregorianCalendar.NOVEMBER , 22 , 15 , 30 ) , "Thanksgiving Break" ); //Thanksgiving 2011; holiday starts November 22, 2011, 3:30 P.M.
    public static Holiday winterBreak = new Holiday( new GregorianCalendar( 2011, GregorianCalendar.DECEMBER , 18 , 12 , 40 ) , "Winter break" );
    public static Holiday mlkDay = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.JANUARY , 13 , 15 , 30 ) , "MLK Day" );
    public static Holiday presDay = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.FEBRUARY , 17 , 15 , 30 ) , "President's Day" );
    public static Holiday springBreak = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.MARCH , 9 , 15 , 30 ) , "Spring Break" );
    public static Holiday springHoliday = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.APRIL , 5 , 15 , 30) , "the Spring holiday" );
    public static Holiday memorialDay = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.MAY , 25 , 15 , 30 ) , "Memorial Day" );
    public static Holiday schoolEnd = new Holiday( new GregorianCalendar( 2012 , GregorianCalendar.JUNE , 1 , 12 , 40 ) , "Summer Break" );

    /**
     * The main execution sequence and loop
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        TrayIcon icon = null;
        Holiday[] holidays_array = { laborDay , fallHoliday , thanksgivingBreak , winterBreak , mlkDay , presDay , springBreak , springHoliday , memorialDay , schoolEnd };
		ArrayList<Holiday> holidays = new ArrayList<Holiday>(java.util.Arrays.asList(holidays_array));
		//sorts in chronological order
        java.util.Collections.sort( holidays );
        
        //print array for confirmation
        /*for( int i = 0; i < holidays.length; i++ )
        {
			System.out.println( holidays[i].name );
		}*/
        if( new GregorianCalendar().after( schoolEnd.date ) ) //if after school is over, open popup and close
        {
            JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
            System.exit(0);
        }
        if( !SystemTray.isSupported() ) //exit if the system tray isn't supported
        {
            JOptionPane.showMessageDialog( null , "This system does not support the tray icon feature. Terminating now." , "School Countdown: Error" , JOptionPane.ERROR_MESSAGE );
            System.exit(0);
        }
        
        //setup procedure - JPanel displays
        statementClosest = new JLabel();
        statementEnd = new JLabel();
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
        System.out.println( "Running..." );
        SystemTray tray = SystemTray.getSystemTray(); //retrieve instance
        BufferedImage img = null;
        URL imgURL = null;
        
        try
        {
			imgURL = new SchoolCountdown().getClass().getResource(iconName); //gets the URL for the image, regardless of JAR or not
			img = ImageIO.read(imgURL);
		}
		catch( Exception e ) //usually MalformedURLException
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog( null , "School Countdown Timer encountered an error while\ntrying to load the icon. Terminating now." , "Error" , JOptionPane.ERROR_MESSAGE );
			System.err.println( "Error loading icon. Now exiting..." );
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
        icon.setImageAutoSize(true); //auto-resize icon for computer
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
                    
                    // just some unneeded and unnecessary functions that aren't implemented
                    public void mousePressed(MouseEvent e) {}
                    public void mouseReleased(MouseEvent e) {}
                    public void mouseEntered(MouseEvent e) {}
                    public void mouseExited(MouseEvent e) {}
                }
        );

        //add icon to tray
        try
        {
            tray.add(icon);
            icon.displayMessage( "Welcome" , "Welcome to School Countdown Timer" , TrayIcon.MessageType.NONE );
        }
        catch( AWTException e )
        {
            System.err.println(e);
            JOptionPane.showMessageDialog( null , "School Countdown Timer has experienced an unknown error." , "Error" , JOptionPane.ERROR_MESSAGE );
            System.exit(1);
        }

        //timed update
        for(;;)
        {
            try
            {
                if( new GregorianCalendar().after( schoolEnd ) ) //if after school is over, open popup and close
                {
                    JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
                    System.exit( 0 );
                }
                while( new GregorianCalendar().after( holidays.get(0).date ) ) //check when the closest one is (not passed already)
                {
					holidays.remove(0);
				}
				
                String[] statements = generateMessages( holidays );
                
                statementClosest.setText( statements[0] );
                statementEnd.setText( statements[1] );
                icon.setToolTip( statements[2] ); //do rounding, choose which day to count to, and set tooltip at same time!
                
                Thread.sleep((int)(1*(1000))); //update every 1 second
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

            remainingVals[0] = seconds/(24*hoursToSec); //days
            seconds %= (24*hoursToSec);
            remainingVals[1] = seconds/hoursToSec; //hours
            seconds %= hoursToSec;
            remainingVals[2] = seconds/60; //minutes
            seconds %= 60;
            remainingVals[3] = seconds; //seconds

            return remainingVals;
    }
    
    public static String[] generateMessages( Holiday earliest , Holiday summer )
    {
		int[] untilClosest = timeRemaining( new GregorianCalendar() , earliest.date );
        int[] untilSummer = timeRemaining( new GregorianCalendar() , summer.date );
                
		String earliestMsg = "Only " + untilClosest[0] + " day" + (untilClosest[0]==1 ? "": "s" ) + ", " + untilClosest[1] + " hour" + (untilClosest[1]==1 ? "": "s" ) + ", " + untilClosest[2] + " minute" + (untilClosest[2]==1 ? "": "s" ) + ", and " + untilClosest[3] + " second" + (untilClosest[3]==1 ? "": "s" ) + " until " + earliest.name + " and";
		String schoolEndMsg = "Only " + untilSummer[0] + " day" + (untilSummer[0]==1 ? "": "s" ) + ", " + untilSummer[1] + " hour" + (untilSummer[1]==1 ? "": "s" ) + ", " + untilSummer[2] + " minute" + (untilSummer[2]==1 ? "": "s" ) + ", and " + untilSummer[3] + " second" + (untilSummer[3]==1 ? "": "s" ) + " until " + summer.name + "!";
		String tooltip = tooltip = ( (untilSummer[0] <= 90 ? (( untilSummer[1] > 12 ? untilSummer[0]+1: untilSummer[0] ) + " day" + (untilSummer[0]==1 ? "":"s") + " until school is over!" ) : 
			(( untilClosest[1] > 12 ? untilClosest[0]+1: untilClosest[0]) + " day" + (untilClosest[0]==1 ? "":"s" ) + " until the closest holiday!" )  ) ); //do rounding, choose which day to count to, and set tooltip at same time!
		
		String[] messages = { earliestMsg , schoolEndMsg, tooltip };
		return messages; //messages[0],messages[1]=timer text; messages[2]=tooltip
	}
	public static String[] generateMessages( ArrayList<Holiday> holidays )
	{
		return generateMessages( holidays.get(0) , holidays.get(holidays.size()-1) );
	}
}
