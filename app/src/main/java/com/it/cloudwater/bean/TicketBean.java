package com.it.cloudwater.bean;

/**
 * Created by hpc on 2017/7/28.
 */

public class TicketBean {
    public String ticketName;
    public String time;
    public int number;
    public String ticketImg;

    public TicketBean(String ticketName, String time, int number, String ticketImg) {
        this.ticketName = ticketName;
        this.time = time;
        this.number = number;
        this.ticketImg = ticketImg;
    }
}
