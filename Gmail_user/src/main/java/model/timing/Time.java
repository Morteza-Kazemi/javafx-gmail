package model.timing;

public class Time {
    DayPart dayPart;
    int hours;
    int minutes;
    int seconds;

    public Time(DayPart dayPart, int hours, int minutes, int seconds) {
        this.dayPart = dayPart;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
}
enum DayPart{
    AM,PM
}
