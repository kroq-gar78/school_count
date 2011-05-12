
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.GregorianCalendar;

/**
 * @version 2.0
 */

public class SchoolCountdown
{
    /**
     * @param args the command line arguments
     */

    //public static final GregorianCalendar schoolStart = new GregorianCalendar( 2009 , 7 , 24 , 8 , 30 );
    //public static final long schoolTime = schoolEnd.getTimeInMillis() - schoolStart.getTimeInMillis(); //number of milliseconds between start and end of school
    public static final int longConverter = (int)Math.pow( 2 , 16 );

    /*private static int days = 0;
    private static int daysRounded = 0;
    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;*/
    private static JPanel timerDisplay;
    private static JPanel helpDisplay;
    private static JLabel statementEnd = new JLabel();
    private static JLabel statementClosest = new JLabel();
    public static final long millisToSec = (long)Math.pow( 10 , 3 );
    public static final int hour_sec = 60*60; //num of seconds in an hour
    public static final GregorianCalendar schoolEnd = new GregorianCalendar( 2011, 5 , 6 , 12 , 40 ); //June 6, 2011, 12:40 P.M.
    public static final GregorianCalendar aprilHoliday = new GregorianCalendar( 2011, 3 , 21 , 3 , 30 ); //April 22, 2011 Holiday, weekend start April 22, 2011, 3:30 P.M.
    public static final GregorianCalendar mayHoliday = new GregorianCalendar( 2011, 4, 27, 3, 30 ); //May 30, 2011 Holiday, weekend start May 27, 2011, 3:30 P.M.
    public static final GregorianCalendar springBreak = new GregorianCalendar( 2011, 2, 11, 3, 30 ); //spring break; though program written after start of (during) Spring Break; only for testing purposes

    public static void main(String[] args)
    {
        TrayIcon icon = null;
        GregorianCalendar today = new GregorianCalendar();
        //JLabel timer = new JLabel();
        //JProgressBar progress = new JProgressBar( 0 , (int) (1000) ); //divide to fit into int data range
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
        //if after school is over, open popup and close
        if( today.after( schoolEnd ) )
        {
            JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
            System.exit( 0 );
        }
        //exit if the system tray isn't supported
        if( !SystemTray.isSupported() )
        {
            JOptionPane.showMessageDialog( null , "This system does not support the tray icon feature. Terminating now." , "Error" , JOptionPane.ERROR_MESSAGE );
            System.exit( 0 );
        }

        
        //setup procedure - JPanel displays
        //labels
        //statement.setHorizontalTextPosition( JLabel.LEFT );
        //progress bar

        timerDisplay = new JPanel();
        timerDisplay.setLayout( new GridLayout(2,1) );
        //display.add( statement );
        //display.setSize( 300 , 145 );
        timerDisplay.add( statementClosest );
        timerDisplay.add( statementEnd );
        //display.add( progress );
        helpDisplay = new JPanel();


        //setup procedure - Tray Icon
        System.out.println( "running..." );
        SystemTray tray = SystemTray.getSystemTray(); //retrieve instance
        Image img = Toolkit.getDefaultToolkit().getImage( "schoolCountdown.gif" ); //retrieve image
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
        ActionListener helpListener = new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                JOptionPane.showMessageDialog( null , helpDisplay , "School Countdown Timer - Help" , JOptionPane.INFORMATION_MESSAGE );
            }
        };
        //create Popup menu
        PopupMenu menu = new PopupMenu();
        MenuItem timerItem = new MenuItem( "Timer" ); timerItem.addActionListener( timerListener ); //create "info' meun item
        MenuItem helpItem = new MenuItem( "Help" ); helpItem.addActionListener( helpListener ); //create "help" menu item
        MenuItem exitItem = new MenuItem( "Exit" ); exitItem.addActionListener( exitListener ); //create "exit" menu item
        menu.add( timerItem );
        menu.addSeparator();
        //menu.add( helpItem );
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
                //calculation logic
                //today = new GregorianCalendar(); //update calendar to right now
                if( new GregorianCalendar().after( schoolEnd ) ) //if after school is over, open popup and close
                {
                    JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
                    System.exit( 0 );
                }
                //days = 0;
                /*//narrow down by month and then calculateto day
                if( today.get(Calendar.MONTH) != Calendar.JUNE)
                {
                    days += 4;
                    if( today.get( Calendar.MONTH ) == Calendar.APRIL )
                    {
                        days += 31;
                        days += ( 30 - today.get( Calendar.DATE ) );
                    }
                    else if( today.get( Calendar.MONTH ) == Calendar.MAY )
                    {
                        days += ( 31 - today.get( Calendar.DATE ) );
                    }
                }
                else if( today.get( Calendar.MONTH ) == Calendar.JUNE )
                {
                    days += ( 4 - today.get( Calendar.DATE ) );
                }
                //calculate to hour*/

                //find millis between today and end of school
                /*timeBetween = ( schoolEnd.getTimeInMillis() - today.getTimeInMillis() );
                days = (int)(timeBetween / (60 * 60 * 24 * 1000L)); //find days
                timeBetween %= 60 * 60 * 24 * 1000L; //take away amount of days
                hours = (int) ( timeBetween / ( 60 * 60 * 1000L ) );//find hours
                timeBetween %= 60 * 60 * 1000L;//take away hours from total
                minutes = (int) ( timeBetween / ( 60 * 1000L ) );//find minutes
                timeBetween %= 60 *1000L; //take away total from minutes
                seconds = (int) ( timeBetween / 1000L ); //find seconds*/
                //timeBetween %= 1000L; //take away total from seconds
                //calculate days rounded value
                //daysRounded = days;
                //if( hours > 12 ) daysRounded++;
                //setStrings or values
                //statementEnd.setText( daysRounded + " days until school is over!" );



                int[] untilClosest = timeRemaining( new GregorianCalendar() , holidays[earliestHoliday] );
                int[] untilSummer = timeRemaining( new GregorianCalendar() , holidays[holidays.length-1] );
                System.out.println( endStatement);
                statementClosest.setText( "Only " + untilClosest[0] + " days, " + untilClosest[1] + " hours, " + untilClosest[2] + " minutes, and " + untilClosest[3] + " seconds until the closest holiday! and" );
                statementEnd.setText( "Only " + untilSummer[0] + " days, " + untilSummer[1] + " hours, " + untilSummer[2] + " minutes, and " + untilSummer[3] + " seconds until summer break!" )

                //progress.setOrientation( (int) (timeBetween/longConverter ));
                //progress.setValue( (int) (Math.pow(10, 5) * (timeBetween / schoolTime)));

                //System.out.println( days + " , " + hours + " , " + minutes + " , " + seconds );

                Thread.sleep((int) (1*(1000))); //update every 1 second
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static int[] timeLeft( GregorianCalendar date ) //days, hours, minutes, seconds
    {
        int[] timeLeft = new int[4];
        long timeLong = date.getTimeInMillis() - new GregorianCalendar().getTimeInMillis();
        timeLeft[0] = (int)(timeLong / (60 * 60 * 24 * 1000L)); //find days
        timeLong %= 60 * 60 * 24 * 1000L; //take away amount of days
        timeLeft[1] = (int) ( timeLong / ( 60 * 60 * 1000L ) );//find hours
        timeLong %= 60 * 60 * 1000L;//take away hours from total
        timeLeft[2] = (int) ( timeLong / ( 60 * 1000L ) );//find minutes
        timeLong %= 60 *1000L; //take away total from minutes
        timeLeft[3] = (int) ( timeLong / 1000L ); //find seconds

        return timeLeft;
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
