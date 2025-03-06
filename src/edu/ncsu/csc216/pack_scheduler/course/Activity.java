package edu.ncsu.csc216.pack_scheduler.course;

/**
 * An activity in the WolfScheduler system. An activity is an item with a title, meeting days, 
 * and a start and end time if applicable.
 * @author Kergan Sanderson
 */
public abstract class Activity implements Conflict {

    /** Upper bound on hours in a day. */
    private static final int UPPER_HOUR = 24;
    
    /** Upper bound on minutes in an hour. */
    private static final int UPPER_MINUTE = 60;
    
    /** Activity's title. */
    private String title;
    
    /** Activity's meeting days */
    private String meetingDays;
    
    /** Activity's starting time */
    private int startTime;
    
    /** Activity's ending time */
    private int endTime;

    /**
     * Creates an Activity given the title, meeting days, and start and end times.
     * @param title title of Activity
     * @param meetingDays meeting days for Activity as series of chars
     * @param startTime start time for Activity
     * @param endTime end time for Activity
     */
    public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }
    
    /**
     * Returns a representation of the Activity with only essential items.
     * Items should be listed in constructor order.
     * @return an array of some of the details of the Activity
     */
    public abstract String[] getShortDisplayArray();
    
    /**
     * Returns a representation of the Activity with all details.
     * Items should be listed in constructor order.
     * @return an array of all of the details of the Activity
     */
    public abstract String[] getLongDisplayArray();
    
    /**
     * Checks if the given activity is a duplicate of this one.
     * @param activity the activity to be compared to this one
     * @return true if the given activity is a duplicate of this one.
     */
    public abstract boolean isDuplicate(Activity activity);

    /**
     * Returns the Activity's title.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the Activity's title. If the title is null or an empty string, an IllegalArgumentException
     * is thrown.
     * @param title the title to set
     * @throws IllegalArgumentException if title parameter is invalid
     */
    public void setTitle(String title) {
        if (title == null || "".equals(title)) {
            throw new IllegalArgumentException("Invalid title.");
        }
        this.title = title;
    }

    /**
     * Sets the Activity's meeting days, start time, and end time.
     * If the start time is an invalid military time, the end time is an invalid military time, the
     * end time is less than the start time, or a start time or end time is listed when the meeting 
     * days is 'A', then an IllegalArgumentException is thrown.
     * @param meetingDays the days of the week that the course will meet
     * @param startTime the time that the course will start on meeting days
     * @param endTime the time that the course will end on meeting days
     * @throws IllegalArgumentException if meetingDays parameter is invalid
     * @throws IllegalArgumentException if startTime parameter is invalid
     * @throws IllegalArgumentException if endTime parameter is invalid
     */
    public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
        int startHours = startTime / 100;
        int startMinutes = startTime % 100;
        int endHours = endTime / 100;
        int endMinutes = endTime % 100;
        
        if (startHours < 0 || startHours >= UPPER_HOUR) {
            throw new IllegalArgumentException("Invalid meeting days and times.");
        }
        if (startMinutes < 0 || startMinutes >= UPPER_MINUTE) {
            throw new IllegalArgumentException("Invalid meeting days and times.");
        }
        if (endHours < 0 || endHours >= UPPER_HOUR) {
            throw new IllegalArgumentException("Invalid meeting days and times.");
        }
        if (endMinutes < 0 || endMinutes >= UPPER_MINUTE) {
            throw new IllegalArgumentException("Invalid meeting days and times.");
        }
        
        if (endTime < startTime) {
            throw new IllegalArgumentException("Invalid meeting days and times.");
        }
        
        // all valid
        this.meetingDays = meetingDays;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the Activity's meeting days and times in a single string. The times are converted to
     * standard 12 hour time instead of military time.
     * @return the Activity's meeting days and times
     */
    public String getMeetingString() {
        if ("A".equals(this.meetingDays)) {
            return "Arranged";
        }
        String days = this.meetingDays;
        String start = getTimeString(this.startTime);
        String end = getTimeString(this.endTime);
        return days + " " + start + "-" + end;
    }

    /**
     * Converts a time string from military time to 12 hour time with AM or PM. If hours
     * or minutes are "0", they are represented as "00" instead.
     * @param time the given time in 24 hour military time format as an integer
     * @return a representation of a time in standard 12 hour form
     */
    private String getTimeString(int time) {
        int hours = time / 100;
        int minutes = time % 100;
        String outputH = hours + "";
        String outputM = minutes + "";
        String amPm = "AM";
        
        // add extra 0 to make 00 if needed
        if ("0".equals(outputH)) {
            outputH += "0";
        }
        if ("0".equals(outputM)) {
            outputM += "0";
        }
        
        // subtract 12 if needed and make PM
        if (hours >= UPPER_HOUR / 2) {
            if (hours != UPPER_HOUR / 2 ) {
                outputH = hours - (UPPER_HOUR / 2) + "";
            }
            amPm = "PM";
        }
        
        return outputH + ":" + outputM + amPm;
    }
    
    /**
     * Checks for conflict between another activity and this activity. Activities have a conflict if 
     * they have an overlap of times on at least one shared meeting day. If there is a conflict, a 
     * ConflictException is thrown.
     * @param possibleConflictingActivity an Activity that may conflict with this one
     * @throws ConflictException if the given Activity conflicts with this one
     */
    @Override
    public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
        // information about the days and times of the Activity to be checked
        String pMeetingDays = possibleConflictingActivity.getMeetingDays();
        int pStartTime = possibleConflictingActivity.getStartTime();
        
        // tracks whether or not a meeting day of one Activity is in the other
        boolean sharesDay = false;
        
        // skip the checks if either activity has arranged meeting days
        if (!("A".equals(this.meetingDays) || "A".equals(pMeetingDays))) {
         // if any meeting day is found in common, the times need to be checked for similarities
            for (int i = 0; i < pMeetingDays.length(); i++) {
                char temp = pMeetingDays.charAt(i);
                // if temp has an index in the other meeting days string, they share a meeting day
                if (this.meetingDays.indexOf(temp) != -1) {
                    sharesDay = true;
                    break;
                }
            }
        }
        
        // check the times for overlap if at least one day is shared.
        if (sharesDay && pStartTime <= this.endTime) {
            if (pStartTime >= this.startTime) {
                throw new ConflictException();
            } else if (possibleConflictingActivity.getEndTime() >= this.startTime) {
                throw new ConflictException();
            }
        }
    }

    /**
     * Returns the Activity's meeting days as a series of chars.
     * @return the meetingDays
     */
    public String getMeetingDays() {
        return meetingDays;
    }

    /**
     * Returns the Activity's start time.
     * @return the startTime
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Returns the Activity's end time.
     * @return the endTime
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Generates a hashCode for the Activity using all the fields.
     * @return hashCode for Activity
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + endTime;
        result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
        result = prime * result + startTime;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    /**
     * Compares a given Activity for equality to this Activity on all fields.
     * @param obj the Object to compare
     * @return true if the Activities are the same on all fields
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Activity other = (Activity) obj;
        if (endTime != other.endTime)
            return false;
        if (meetingDays == null) {
            if (other.meetingDays != null)
                return false;
        } else if (!meetingDays.equals(other.meetingDays))
            return false;
        if (startTime != other.startTime)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

}