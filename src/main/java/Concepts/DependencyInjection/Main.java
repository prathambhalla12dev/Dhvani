package Concepts.DependencyInjection;

public class Main {
    public static void main(String[] args) {

        Engine electricEngine = new ElectricEngine();
        Engine petrolEngine = new PetrolEngine();

        Car car1 = new Car(petrolEngine);
        car1.drive();

        Car car2 = new Car(petrolEngine);
        car2.drive();
    }
}
