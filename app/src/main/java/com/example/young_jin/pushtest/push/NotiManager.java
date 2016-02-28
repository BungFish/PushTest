package com.example.young_jin.pushtest.push;

/**
 * Created by Young-Jin on 2016-02-28.
 */
public class NotiManager {
    private Notification notification;

    public NotiManager() {
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void makeNoti(int notification_id) {
        notification.makeNoti(notification_id);
    }
}
