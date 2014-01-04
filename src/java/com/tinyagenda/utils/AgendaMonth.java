/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinyagenda.utils;

import com.tinyagenda.model.Person;
import com.tinyagenda.persist.GeneralDAO;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;

/**
 *
 * @author Pedro
 */
public class AgendaMonth{
    
    private int year;
    // month 0=january, 1=february, etc
    private int month;
    private int day;
    private int daysInMonth;
    // firstDay day of week where 0=monday, 1=tuesday, 2=wednesday, etc
    private int firstDay;
    private Calendar cal;
    private String monthName;
    private TreeMap<Integer,Integer> eventsPerDay;
    private Person user;
    
    public AgendaMonth(){
    }
    
    public AgendaMonth(Person user){
        this.user=user;
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DATE);
        builData(cal);
    }
    
    public AgendaMonth(int year, int month,Person user){
        cal=Calendar.getInstance();
        this.year=year;
        this.month=month;
        this.user=user;
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        day=0;
        builData(cal);
    }

    private void builData(Calendar cal){
        GeneralDAO gDao=new GeneralDAO();
        int[] daysPerMonth=new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
        int dayOfWeek;
        if( ((GregorianCalendar)cal).isLeapYear(year) ){
            daysPerMonth[1]=29;
        }
        daysInMonth=daysPerMonth[month];
        cal.set(Calendar.DATE, 1);
        dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
        if( dayOfWeek==Calendar.MONDAY ){
            firstDay=0;
        }else if( dayOfWeek==Calendar.TUESDAY ){
            firstDay=1;
        }else if( dayOfWeek==Calendar.WEDNESDAY ){
            firstDay=2;
        }else if( dayOfWeek==Calendar.THURSDAY ){
            firstDay=3;
        }else if( dayOfWeek==Calendar.FRIDAY ){
            firstDay=4;
        }else if( dayOfWeek==Calendar.SATURDAY ){
            firstDay=5;
        }else if( dayOfWeek==Calendar.SUNDAY ){
            firstDay=6;
        }
        if(day!=0){
            cal.set(Calendar.DATE, day);
        }
        monthName=buildName2View(cal.get(Calendar.MONTH), year);
        eventsPerDay=gDao.getCountEvents(month, year, daysInMonth, user);
    }
    
    private String buildName2View(int useMonth, int useYear){
        String[] months={"January","February","March","April","May","June","Jule",
            "August","September","October","November","December"};
        return months[useMonth]+" - "+useYear;
    }
    
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDaysinmonth() {
        return daysInMonth;
    }

    public int getFirstDay() {
        return firstDay;
    }
    
    public String getMonthname(){
        return monthName;
    }
    
    public int getEventsPerDay(int date){
        if( eventsPerDay!=null && eventsPerDay.containsKey(date) ){
            return eventsPerDay.get(date);
        }
        return 0;
    }
    
    /**
     * 
     * @param month a integer value to add or substract for the month, may be positive or negative
     * @return 
     */
    public String getLink2Month(int addMonth){
        int targetMonth=month+addMonth;
        int targetYear=year;
        int asbTarMo;
        int diff;
        if(targetMonth<=0){
            asbTarMo=Math.abs(targetMonth);
            if(asbTarMo>12){
                diff=asbTarMo/12;
                targetYear-=diff;
                targetMonth=-1*(asbTarMo-(diff*12));
            }
            targetYear--;
            targetMonth=12+targetMonth;
        }
        if(targetMonth>11){
            diff=targetMonth/12;
            targetYear+=diff;
            targetMonth-=(diff*12);
        }
        return "<a href=\"month?loadmonth="+targetMonth+"&loadyear="+targetYear+"\">"+buildName2View(targetMonth,targetYear)+"</a>";
    }
    
}
