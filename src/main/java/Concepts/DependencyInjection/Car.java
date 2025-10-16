package Concepts.DependencyInjection;

public record Car(Engine engine) {

    public void drive() {
        engine.start();
        System.out.println("Driving Car");
    }
}
