package Concepts.DependencyInjection;

public class Notification {

    private final MessageService messageService;
    public Notification(MessageService messageService) { // notification depends on interface, not on the concrete class
        this.messageService = messageService;
    }
    public void notifyUser(String message) {
        messageService.sendMessage(message);
    }
}

// private EmailService emailService = new EmailService(); // here notification is tied to emails service
// you cannot switch to SMS service without editing it

// MessageService service = new SmsService(); // works with no change to Notification class (Done in Main Class)

// Instead of Notification creating EmailService, it accepts it from outside using constructor

