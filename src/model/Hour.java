package model;

public class Hour {
	private int hours;
	private int minutes;
	private int seconds;
	
	/**
	 * Empty constructor
	 */
	public Hour() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
	}
	
	/**
	 * Constructor with a String
	 * @param hour String in the format : "hh:mm:ss"
	 */
	public Hour(String hour) {
		
		if(!hour.isEmpty()) {
			this.hours = Integer.parseInt(hour.substring(0,hour.indexOf(":")));
			this.minutes = Integer.parseInt(hour.substring(hour.indexOf(":") + 1, hour.lastIndexOf(":")));
			this.seconds = Integer.parseInt(hour.substring(hour.lastIndexOf(":") + 1));
		}
		else {
			hours = 0;
			minutes = 0;
			seconds = 0;
		}
		
		/*this.hours = Integer.parseInt(hour.substring(0, 2));
		this.minutes = Integer.parseInt(hour.substring(3, 5));
		this.seconds = Integer.parseInt(hour.substring(6));*/
	}
	
	/**
	 * Constructor with 3 int
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public Hour(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	/**
	 * Add a number of seconds to the time
	 * @param secondsAdd Seconds to add
	 */
	public void addTime(int secondsAdd) {
		seconds += secondsAdd;
		
		if(seconds >= 60) {
			int minutesAdd = seconds / 60;
			
			minutes += minutesAdd;
			seconds -= 60 * minutesAdd;
			
			if(minutes >= 60) {
				int hoursAdd = minutes / 60;
				
				hours += hoursAdd;
				minutes -= 60 * hoursAdd;
			}
		}
	}
	
	/**
	 * Get the string of the time
	 * @return String of the time
	 */
	public String getTimeString() {
		String timeString;
		
		String hoursString = (hours < 10) ? "0" + Integer.toString(hours) : Integer.toString(hours);
		String minutesString = (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);
		String secondsString = (seconds < 10) ? "0" + Integer.toString(seconds) : Integer.toString(seconds);
		
		timeString = hoursString + ":" + minutesString + ":" + secondsString;
		
		return timeString;
	}
	
	/**
	 * return the possibly waiting time before delivery, or return negative if impossible delivery
	 * @param windowStart the min border of the time window of the delivery
	 * @param windowEnd the max border of the time window of the delivery
	 * @param duration the duration of the delivery in seconds
	 * @return the waiting duration or negative if impossible
	 */
	
	public int waitDurationToWindow(Hour windowStart, Hour windowEnd, int duration) {
		
		Hour windowMinAfterDuration = new Hour(windowStart.getTimeString());
		windowMinAfterDuration.addTime(duration);
		
		if(!Hour.aLessThanB(windowMinAfterDuration, windowEnd)) {
			return Integer.MIN_VALUE;
		}
		
		Hour timeAfterDuration = new Hour(this.getTimeString());
		timeAfterDuration.addTime(duration);
		
		if(Hour.aLessThanB(this, windowStart)) {
			return Hour.aMinusB(windowStart, this);
		}
		else if (Hour.aLessThanB(timeAfterDuration, windowEnd)) {
			return 0;
		}
		else {
			return Hour.aMinusB(windowEnd, timeAfterDuration);
		}

	}
	
	/**
	 * Determine if a is less than b
	 * @param a
	 * @param b
	 * @return true if a is less than b, else return false
	 */
	public static boolean aLessThanB(Hour a, Hour b) {
		boolean result = false;
		
		if(a.hours < b.hours) {
			result = true;
		}
		else if(a.hours == b.hours) {
			if(a.minutes < b.minutes) {
				result = true;
			}
			else if (a.minutes == b.minutes) {
				if(a.seconds < b.seconds) {
					result = true;
				}
			}
		}
		return result;
	}
	
	/**
	 * Determine the result of a - b
	 * @param a
	 * @param b
	 * @return The result of a - b
	 */
	public static int aMinusB(Hour a, Hour b) {
		int result = 0;
		
		result += (a.hours - b.hours) * 3600;
		result += (a.minutes - b.minutes) * 60;
		result += (a.seconds - b.seconds);
		
		return result;
	}
}
