import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * <h1>MyCalendarTester class</h1>
 * MyCalendarTester can test or run all the methods existing in MyCalendar class via
 * 7 types of menu (Load, View by, Create, Go to, Event list, Delete, and quit)
 * Certain menu like view by or delete have sub menus.
 * MyCalendarTester can create, delete, access and display events along with Calendar 
 * either in day view or month view.
 * It can also create or load events.txt for scheduled events. 
 * 
 * @author Jeong Ook Moon
 * @version 1.0
 * @since 2017-09-17
 */
public class MyCalendarTester 
{
	private static Scanner sc;
	/**
	 * this is main method of MyCalendarTester where it shows today's calendar in month view and various menu
	 * @param args unused
	 */
	public static void main(String[] args) 
	{
		// Initiating MyCalendar object and by constructor of MyCalendar it holds today's date info
		MyCalendar cal = new MyCalendar();
		// initiate new arraylist, put today's date info into this array, and implement showmonthview along with this array
		// to display today's month view calendar
		ArrayList<Integer> today = new ArrayList<Integer>();
		today.add(cal.getDate());
		cal.showMonthView(today);
		// terminator to make sure user inputs one of the option letters
		boolean terminator = false;
		while(terminator == false)
		{			
			System.out.println();
			mainMenu();		
			sc = new Scanner(System.in);
			String cin = sc.nextLine();
			// load, modes data from events.txt
			if(cin.equals("l")||cin.equals("L"))
			{
				System.out.println();
				System.out.println("[L]oad");
				System.out.println();
				cal.deleteAll();
				cal.readText();
			}
			// view by gives option to select day view or month view.
			else if(cin.equals("v")||cin.equals("V"))
			{
				
				boolean inner_terminator = false;
				
				while(inner_terminator == false)
				{
					System.out.println();
					System.out.println("[V]iew by");
					System.out.println();
					System.out.println("Please select one of the following options:");
					System.out.println("[D]ay view or [M]onth view or [Q] for main menu?");
					String cin2 = sc.nextLine();
					// dayview, user can change next or previous day. also it shows the event on that day.
					// user can also choose to go back to main menu
					if(cin2.equals("d")||cin2.equals("D"))
					{
						System.out.println();
						System.out.println("[D]ay View");
						System.out.println();
						cal.showDayView();
						boolean inner_terminator2 = false;
						while(inner_terminator2 == false)
						{
							System.out.println();
							System.out.println("[P]revious or [N]ext or [M]ain?");
							String cin3 = sc.nextLine();
							if(cin3.equals("p")||cin3.equals("P"))
							{
								cal.ShowPreviousDay();
							}
							else if(cin3.equals("n")||cin3.equals("N"))
							{
								cal.ShowNextDay();								
							}
							else if(cin3.equals("m")||cin3.equals("M"))
							{
								inner_terminator = true;
								inner_terminator2 = true;
							}
							else
							{
								System.out.println("Please enter one of the option letters");
							}
						}
					}
					// the user can change to either next or previous month
					// user can also choose to go back to main menu
					// it also displays days that have scheduled events and also list of those events
					else if(cin2.equals("m")||cin2.equals("M"))
					{
						System.out.println();
						System.out.println("[M]onth View");
						System.out.println();
						ArrayList<Integer> sample = new ArrayList<Integer>();
						sample = cal.getDaysOfMonth(cal.getTotalDate());
						cal.showMonthView(sample);		
						boolean inner_terminator2 = false;
						while(inner_terminator2 == false)
						{
							System.out.println();
							cal.showEventByMonth(cal.getTotalDate());
							System.out.println();
							System.out.println("[P]revious or [N]ext or [M]ain?");
							String cin3 = sc.nextLine();
							if(cin3.equals("p")||cin3.equals("P"))
							{
								cal.ShowPreviousMonth();
							}
							else if(cin3.equals("n")||cin3.equals("N"))
							{
								cal.ShowNextMonth();								
							}
							else if(cin3.equals("m")||cin3.equals("M"))
							{
								inner_terminator = true;
								inner_terminator2 = true;
							}
							else
							{
								System.out.println("Please enter one of the option letters");
							}
						}
					}
					else if(cin2.equals("q")||cin2.equals("Q"))
					{
						inner_terminator = true;
					}
					else
					{
						System.out.println("Please enter one of the option letters");
					}
				}
			}
			// user can create new event
			else if(cin.equals("c")||cin.equals("C"))
			{
				boolean noConflict = false;
				MyEvent ex = new MyEvent();
				String titleInfo="";
				String dateInput="";
				String sTimeInput="";
				String eTimeInput="";
				int monthInfo = 0;
				int dayInfo = 0;
				int yearInfo = 0;
				int sTimeHour = 0;
				int sTimeMin = 0;
				int eTimeHour = 0;
				int eTimeMin = 0;
				// it checks if there's time conflict or bigevent conflict
				while(noConflict == false)
				{
					System.out.println();
					System.out.println("[C]reate");
					System.out.println();
					System.out.println("Please enter following information");
					System.out.println("Title:");
					titleInfo = sc.nextLine();
					boolean correct = false;
					// checking input is correct
					while(correct == false)
					{
						Date dateInfo = null;
						SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						System.out.println("MM/dd/yyyy?");
						dateInput = sc.nextLine();
						try
						{
						    dateInfo = dateFormat.parse(dateInput);
						} 
						catch (ParseException e)
						{ 
							System.out.println("Invalid format");
						}
						if (!dateFormat.format(dateInfo).equals(dateInput))
						{
						    System.out.println("Please enter the valid date");
						}
						else
						{
							String date[] = dateInput.split("/");
							int dateValues[] = new int[date.length];
							for (int i=0; i<dateValues.length; i++)
							{
								dateValues[i] = Integer.valueOf(date[i]);
							}
							yearInfo = dateValues[2];
							monthInfo = dateValues[0];
							dayInfo = dateValues[1];
							correct = true;
						}
					}
					SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
					correct = false;
					// checking input is correct
					while(correct == false)
					{
						System.out.println("Starting time (HH:MM)?");
						//System.out.println("HH:" );
						Date timeInfo = null;
						sTimeInput = sc.nextLine();
						try
						{
						    timeInfo = timeFormat.parse(sTimeInput);
						} 
						catch (ParseException e)
						{ 
							System.out.println("Invalid format");
						}
						if (!timeFormat.format(timeInfo).equals(sTimeInput))
						{
						    System.out.println("Please enter the valid time");
						}
						else
						{
							String timeString[] = sTimeInput.split(":");
							int timeValues[] = new int[timeString.length];
							for (int i=0; i<timeValues.length; i++)
							{
								timeValues[i] = Integer.valueOf(timeString[i]);
							}
							sTimeHour = timeValues[0];
							sTimeMin = timeValues[1];
							correct = true;
						}
					}
					// notification about big event
					System.out.println();
					System.out.println("For unknown ending time, please enter starting time value instead");
					System.out.println("ex) Starting time: 15:00 Ending time: 15:00 for traveling to Korea");
					System.out.println("Note: no other event can be scheduled after such event on the same day");
					System.out.println();
					// checking if input is correct
					// ending time must be greater or equal to starting time
					correct = false;
					while(correct == false)
					{
						System.out.println("Ending time (HH:MM)?");
						//System.out.println("HH:" );
						Date timeInfo = null;
						eTimeInput = sc.nextLine();
						try
						{
						    timeInfo = timeFormat.parse(eTimeInput);
						} 
						catch (ParseException e)
						{ 
							System.out.println("Invalid format"+"\n");
						}
						if (!timeFormat.format(timeInfo).equals(eTimeInput))
						{
						    System.out.println("Please enter the valid time"+"\n");
						}
						else
						{
							String timeString[] = eTimeInput.split(":");
							int timeValues[] = new int[timeString.length];
							for (int i=0; i<timeValues.length; i++)
							{
								timeValues[i] = Integer.valueOf(timeString[i]);
							}
							eTimeHour = timeValues[0];
							eTimeMin = timeValues[1];
							// if ending time = starting time means big event got scheduled
							if((eTimeHour*100+eTimeMin) >= (sTimeHour*100+sTimeMin))
							{
								if((eTimeHour*100+eTimeMin) == (sTimeHour*100+sTimeMin))
								{
									System.out.println("\n"+"A big event has been scheduled!"+"\n"+"No other event can be scheduled after this event on this date");
									System.out.println();
								}
								correct = true;
							}
							else
							{
								System.out.println("Ending time must be greater or equal to the starting time");
							}
						}
					}
				// actual checks on both time conflict and bigevent conflict	
				boolean bigEvent = cal.checkBigEvent(yearInfo*10000+monthInfo*100+dayInfo, (sTimeHour*100)+sTimeMin, (eTimeHour*100)+eTimeMin);
				boolean timeConflict = cal.checkConflict(yearInfo*10000+monthInfo*100+dayInfo, (sTimeHour*100)+sTimeMin, (eTimeHour*100)+eTimeMin);
				// when there's no conflict
				if(bigEvent == false && timeConflict == false)
				{
					noConflict = true;
				}
				// when there's some conflict
				if(bigEvent == true)
				{
					System.out.println("\n"+"No event can be scheduled after the big event on the same date");
					cal.showEventByDay(yearInfo*10000+monthInfo*100+dayInfo);
					System.out.println("Please schedule the event on different time or date");
				}
				if(timeConflict == true)
				{
					System.out.println("\n"+"Time conflict!");
					cal.showEventByDay(yearInfo*10000+monthInfo*100+dayInfo);
					System.out.println("Please schedule the event on different time or date");
				}
			}
				ex.setTitle(titleInfo);
				ex.setDate(yearInfo*10000+monthInfo*100+dayInfo);
				ex.setS_Time((sTimeHour*100)+sTimeMin);
				ex.setE_Time((eTimeHour*100)+eTimeMin);
				cal.insertHashmap(yearInfo*10000+monthInfo*100+dayInfo, ex);
			}
			// go to menu lets user to go to certain day and see the day view 
			else if(cin.equals("g")||cin.equals("G"))
			{
				System.out.println();
				System.out.println("[G]o to");
				System.out.println();
				String dateInput="";
				int monthInfo = 0;
				int dayInfo = 0;
				int yearInfo = 0;
				boolean correct = false;
				//checking for correct input
				while(correct == false)
				{
					Date dateInfo = null;
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					System.out.println("MM/dd/yyyy?");
					dateInput = sc.nextLine();
					try
					{
					    dateInfo = dateFormat.parse(dateInput);
					} 
					catch (ParseException e)
					{ 
						System.out.println("Invalid format");
					}
					if (!dateFormat.format(dateInfo).equals(dateInput))
					{
					    System.out.println("Please enter the valid date");
					}
					else
					{
						String date[] = dateInput.split("/");
						int dateValues[] = new int[date.length];
						for (int i=0; i<dateValues.length; i++)
						{
							dateValues[i] = Integer.valueOf(date[i]);
						}
						yearInfo = dateValues[2];
						monthInfo = dateValues[0];
						dayInfo = dateValues[1];
						correct = true;
					}
				}
				cal.showEventByDay(yearInfo*10000+monthInfo*100+dayInfo);
			}
			// showing all scheduled events
			else if(cin.equals("e")||cin.equals("E"))
			{
				System.out.println();
				System.out.println("[E]vent List");
				System.out.println();
				cal.getAllEvents();
			}
			// delete menu shows two options selected or all
			// user can also go back to main menu
			else if(cin.equals("d")||cin.equals("D"))
			{
				boolean inner_terminator = false;
				while(inner_terminator == false)
				{
					System.out.println();
					System.out.println("[D]elete");
					System.out.println();
					System.out.println("Please select one of the following options:");
					System.out.println("[S]elected or [A]ll or [M]ain menu?");
					String cin2 = sc.nextLine();
					// all events in selected day can be deleted
					if(cin2.equals("s")||cin2.equals("S"))
					{
						System.out.println();
						System.out.println("[S]eleted");
						System.out.println();
						String dateInput="";
						int monthInfo = 0;
						int dayInfo = 0;
						int yearInfo = 0;
						boolean correct = false;
						// checks if the input is correct while getting input
						while(correct == false)
						{
							cal.getAllEvents();
							Date dateInfo = null;
							SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							System.out.println("MM/dd/yyyy?");
							dateInput = sc.nextLine();
							try
							{
							    dateInfo = dateFormat.parse(dateInput);
							} 
							catch (ParseException e)
							{ 
								System.out.println("Invalid format");
							}
							if (!dateFormat.format(dateInfo).equals(dateInput))
							{
							    System.out.println("Please enter the valid date");
							}
							else
							{
								String date[] = dateInput.split("/");
								int dateValues[] = new int[date.length];
								for (int i=0; i<dateValues.length; i++)
								{
									dateValues[i] = Integer.valueOf(date[i]);
								}
								yearInfo = dateValues[2];
								monthInfo = dateValues[0];
								dayInfo = dateValues[1];
								correct = true;
							}
						}
						cal.deleteDay(yearInfo*10000+monthInfo*100+dayInfo);
					}
					// deleting all events
					else if(cin2.equals("a")||cin2.equals("A"))
					{
						cal.deleteAll();
						System.out.println("All the scheduled events got deleted");
					}
					// going back to main menu
					else if(cin2.equals("m")||cin2.equals("M"))
					{
						inner_terminator = true;
					}
					else
					{
						System.out.println("Please enter one of the option letters");
					}
				}
			}
			// writes all scheduled events in events.txt
			else if(cin.equals("q")||cin.equals("Q"))
			{
				System.out.println();
				System.out.println("[Q]uit");
				System.out.println();
				cal.writeInText();
				terminator = true;
			}
			// checking if user puts one of the options
			else
			{
				System.out.println("Please enter one of the option letters");
			}
		}
		System.out.println("\n"+"Farewell!"+"\n");		
	}
	/**
	 * shows main menu options
	 */
	private static void mainMenu()
	{
		System.out.println("Please select one of the following options:");
		System.out.println(String.format("%-12s %-16s %-12s", "[L]oad", "[V]iew by", "[C]reate" ));
		System.out.println(String.format("%-12s %-16s %-12s", "[G]o", "[E]vent list", "[D]elete" ));
		System.out.println("[Q]uit");
	}
}

