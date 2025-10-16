package Concepts.DependencyInjection;

public class SmsService implements MessageService {
    public void sendMessage(String message) {
        System.out.println("SMS sent " + message);
    }
}
