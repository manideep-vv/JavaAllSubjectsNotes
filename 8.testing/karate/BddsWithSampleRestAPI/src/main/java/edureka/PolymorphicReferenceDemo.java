package edureka;
//59 assignment
public class PolymorphicReferenceDemo {
    public static void main(String[] args) {
        Notification notification;
        notification =new EmailNotification();
        notification.notifyUser();
        notification = new SMSNotification();
        notification.notifyUser();
    }
}
