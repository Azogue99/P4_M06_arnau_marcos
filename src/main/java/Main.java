
import db.DatabaseInitializer;
import Exercici.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciant BBDD...");
        DatabaseInitializer.createDatabaseIfNotExists();

        // EXERCICI 1
        System.out.println("Iniciant Exercici 01...");
        new Exercici_01().run();

        // EXERCICI 2
        System.out.println("Iniciant Exercici 02...");
        new Exercici_02().run();

        // EXERCICI 3
        System.out.println("Running Exercici 03...");
        new Exercici_03().run();
    }
}
