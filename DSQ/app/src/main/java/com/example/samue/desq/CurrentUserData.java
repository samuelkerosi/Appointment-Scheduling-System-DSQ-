package com.example.samue.desq;

public class CurrentUserData {

    public static class User {
        public static UserData userData;

        public static String getId() {
            return userData.id;
        }

        public static void setId(String id) {
            userData.id = id;
        }

        public static String getName() {
            return userData.name;
        }

        public static void setName(String name) {
            userData.name = name;
        }

        public static String getPhoneNumber() { return userData.phoneNumber; }

        public static void setPhoneNumber(String phoneNumber) { userData.phoneNumber = phoneNumber; }

        public static String getNotificationToken() { return userData.notificationToken; }

        public static void setNotificationToken(String notificationToken) { userData.notificationToken = notificationToken; }
    }

}
