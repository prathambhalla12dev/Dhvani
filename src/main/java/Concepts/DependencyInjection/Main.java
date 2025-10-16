package Concepts.DependencyInjection;

public class Main {
    public static void main(String[] args) {

        MessageService messageService = new EmailService(); // creates the service
        // The variable type (MessageService) decides what methods can be called.
        // The object type (EmailService) decides how those methods behave (implementation)
        // You can’t do new MessageService() because interfaces cannot be instantiated - they are just contracts, not actual implementations
        // We cannot create object of an interface

        messageService.sendMessage("Hello World");
        Notification notification = new Notification(messageService); // passing the dependency
        notification.notifyUser("Hello World");

        MessageService messageService2 = new SmsService();
        messageService2.sendMessage("Hello World");
        Notification notification2 = new Notification(messageService2);
        notification2.notifyUser("Hello World");
    }
}
