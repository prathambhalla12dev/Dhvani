package Concepts.DependencyInjection;

public class EmailService implements MessageService {
    public void sendMessage(String message) {
        System.out.println("Email Sent " + message);
    }
}

// actual implementation of send message method in message service