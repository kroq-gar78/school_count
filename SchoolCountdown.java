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
//import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JLabel; //holds timer for school to end
import javax.swing.JPanel; //to hold updates in the dialog that pops up from sys-tray
//import javax.swing.JProgressBar; //to show progress until school is over
import javax.swing.JOptionPane;
//import javax.imageio.ImageIO;
//import java.io.IOException;


/**
 *
 * @author kroq-gar78 <java-apps@vaidya.info>
 */
public class SchoolCountdown
{
    /**
     * @param args the command line arguments
     */

    //public static final GregorianCalendar schoolStart = new GregorianCalendar( 2009 , 7 , 24 , 8 , 30 );
    public static final GregorianCalendar schoolEnd = new GregorianCalendar( 2011 , 5 , 6 , 12 , 40); //end of school
    public static final GregorianCalendar presday = new GregorianCalendar( 2011 , 1 , 18 , 3 , 30 );
    //public static final long schoolTime = schoolEnd.getTimeInMillis() - schoolStart.getTimeInMillis(); //number of milliseconds between start and end of school
    public static final int longConverter = (int)Math.pow( 2 , 16 );

    /*private static int days = 0;
    private static int daysRounded = 0;
    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;*/
    private static JPanel timerDisplay;
    private static JPanel helpDisplay;
    private static JLabel statementPresday = new JLabel();
    private static JLabel statementEnd = new JLabel();

    public static void main(String[] args)
    {
        TrayIcon icon = null;
        GregorianCalendar today = new GregorianCalendar();
        //JLabel timer = new JLabel();
        //JProgressBar progress = new JProgressBar( 0 , (int) (1000) ); //divide to fit into int data range

        //if after school is over, open popup and close
        if( today.after( schoolEnd ) )
        {
            JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
            System.exit( 0 );
        }

        //int days = 0;

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
        timerDisplay.add( statementPresday );
        timerDisplay.add( statementEnd );
        //display.add( progress );
        helpDisplay = new JPanel();


        //setup procedure - Tray Icon
        System.out.println( "running..." );
        SystemTray tray = SystemTray.getSystemTray(); //retrieve instance
        Image img = Toolkit.getDefaultToolkit().getImage( "schoolCountdown.png" ); //retrieve image
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
                long timeBetween = 0;
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
                int[] timeLeft = timeLeft( schoolEnd );
                icon.setToolTip( ( timeLeft[1] > 12 ? timeLeft[0]+1: timeLeft[0] ) + " days until school is over!" ); //do rounding and set tooltip at same time
                statementEnd.setText( timeLeft[0] + " days, " + timeLeft[1] + " hours, " + timeLeft[2] + " minutes,\n and " + timeLeft[3] + " seconds until school is over!" );
                
                timeLeft = timeLeft( presday );
                statementPresday.setText( timeLeft[0] + " days, " + timeLeft[1] + " hours, " + timeLeft[2] + " minutes,\n and " + timeLeft[3] + " seconds until President's Day weekend!" );
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
}
