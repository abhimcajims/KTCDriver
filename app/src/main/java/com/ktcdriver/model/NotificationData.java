package com.ktcdriver.model;

import java.util.List;

/**
 * Created by Rakhi on 11/3/2018.
 */
public class NotificationData {

    /**
     * status : 1
     * message : Successful
     * count_notification : 4
     * notification_data : [{"notification_id":"4","type":"MSG","message_data":"d asdhash dsahdsahd asjdsa dsald asdasd","status":"0"},{"notification_id":"3","type":"DUTY","message_data":"d asd asdsa dnasdsad\r\nasdasd\r\nasdas\r\ndas\r\ndas\r\ndas\r\ndas\r\ndasd\r\nas","status":"0"},{"notification_id":"2","type":"MSG","message_data":"d asdhash dsahdsahd asjdsa dsald asdasd","status":"0"},{"notification_id":"1","type":"DUTY","message_data":"d asd asdsa dnasdsad\r\nasdasd\r\nasdas\r\ndas\r\ndas\r\ndas\r\ndas\r\ndasd\r\nas","status":"0"}]
     */

    private String status;
    private String message;
    private String count_notification;
    private List<NotificationDataBean> notification_data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCount_notification() {
        return count_notification;
    }

    public void setCount_notification(String count_notification) {
        this.count_notification = count_notification;
    }

    public List<NotificationDataBean> getNotification_data() {
        return notification_data;
    }

    public void setNotification_data(List<NotificationDataBean> notification_data) {
        this.notification_data = notification_data;
    }

    public static class NotificationDataBean {
        /**
         * notification_id : 4
         * type : MSG
         * message_data : d asdhash dsahdsahd asjdsa dsald asdasd
         * status : 0
         */

        private String notification_id;
        private String type;
        private String message_data;
        private String status;

        public String getNotification_id() {
            return notification_id;
        }

        public void setNotification_id(String notification_id) {
            this.notification_id = notification_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage_data() {
            return message_data;
        }

        public void setMessage_data(String message_data) {
            this.message_data = message_data;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
