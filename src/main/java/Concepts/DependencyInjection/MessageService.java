package Concepts.DependencyInjection;

public interface MessageService {
    void sendMessage(String message);
}

// All methods in an interface are implicitly public and abstract in Java

// Any class that implements message service must implement send message function