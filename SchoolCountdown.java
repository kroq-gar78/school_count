/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AWTException;
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

    public static final GregorianCalendar schoolStart = new GregorianCalendar( 2009 , 7 , 24 , 8 , 30 );
    public static final GregorianCalendar schoolEnd = new GregorianCalendar( 2010 , 5 , 4 , 12 , 40); //end of school
    //public static final long schoolTime = schoolEnd.getTimeInMillis() - schoolStart.getTimeInMillis(); //number of milliseconds between start and end of school
    public static final int longConverter = (int)Math.pow( 2 , 16 );

    private static int days = 0;
    private static int daysRounded = 0;
    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;
    private static JPanel timerDisplay;
    private static JPanel helpDisplay;
    private static JLabel statement = new JLabel();

    public static void main(String[] args)
    {
        TrayIcon icon = null;
        GregorianCalendar today = new GregorianCalendar();
        JLabel timer = new JLabel();
        //JProgressBar progress = new JProgressBar( 0 , (int) (1000) ); //divide to fit into int data range

        //if after school is over, open popup and close
        if( today.after( schoolEnd ) )
        {
            JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
            System.exit( 0 );
        }

        //int days = 0;
        if( SystemTray.isSupported() )
        {
            //setup procedure - JPanel displays
            //labels
            //statement.setHorizontalTextPosition( JLabel.LEFT );
            //progress bar

            timerDisplay = new JPanel();
            //display.add( statement );
            //display.setSize( 300 , 145 );
            timerDisplay.add( timer );
            //display.add( progress );
            helpDisplay = new JPanel();


            //setup procedure - Tray Icon
            System.out.println( "running..." );
            SystemTray tray = SystemTray.getSystemTray(); //retrieve instance
            Image img = Toolkit.getDefaultToolkit().getImage( "./schoolCountdown.png" ); //retrieve image
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
                                JOptionPane.showMessageDialog( null , statement , "School Countdown Timer notification" , JOptionPane.INFORMATION_MESSAGE );
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
                    today = new GregorianCalendar(); //update calendar to right now
                    if( today.after( schoolEnd ) ) //if after school is over, open popup and close
                    {
                        JOptionPane.showMessageDialog( null , "HAPPY SUMMER!!!!!!" , "School Countdown Timer Notification" , JOptionPane.INFORMATION_MESSAGE );
                        System.exit( 0 );
                    }
                    days = 0;
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
                    timeBetween = ( schoolEnd.getTimeInMillis() - today.getTimeInMillis() );
                    days = (int)(timeBetween / (60 * 60 * 24 * 1000L)); //find days
                    timeBetween %= 60 * 60 * 24 * 1000L; //take away amount of days
                    hours = (int) ( timeBetween / ( 60 * 60 * 1000L ) );//find hours
                    timeBetween %= 60 * 60 * 1000L;//take away hours from total
                    minutes = (int) ( timeBetween / ( 60 * 1000L ) );//find minutes
                    timeBetween %= 60 *1000L; //take away total from minutes
                    seconds = (int) ( timeBetween / 1000L ); //find seconds
                    timeBetween %= 1000L; //take away total from seconds
                    //calculate days rounded value
                    if( hours >= 12 ) daysRounded = days+1;
                    else daysRounded = days;
                    //setStrings or values
                    statement.setText( daysRounded + " days until school is over!" );
                    icon.setToolTip( statement.getText() );
                    timer.setText( days + " days, " + hours + " hours, " + minutes + " minutes,\n and " + seconds + " seconds until school is over!" );
                    //progress.setOrientation( (int) (timeBetween/longConverter ));
                    //progress.setValue( (int) (Math.pow(10, 5) * (timeBetween / schoolTime)));

                    //System.out.println( days + " , " + hours + " , " + minutes + " , " + seconds );

                    Thread.sleep((int) (.5*(1000))); //update every 1/2 second
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog( null , "This system does not support the tray icon feature. Terminating now." , "Error" , JOptionPane.ERROR_MESSAGE );
            System.exit( 0 );
        }
    }

}
