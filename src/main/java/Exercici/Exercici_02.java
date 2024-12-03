package Exercici;

import db.ProductManager;
import db.SQLExecutor;
import models.Producte;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Exercici_02 {
    public void run() {

        // Crea la taula segons la sentència SQL si no existeix
        SQLExecutor.executeSQLFile("SQL/productes.sql");
        System.out.println("La taula ja existeix o s'ha creat.");


        Scanner scanner = new Scanner(System.in);
        ProductManager productManager = new ProductManager();

        try {
            boolean exit = false;

            while (!exit) {
                System.out.println("\n--- Exercici 2: Gestió de Productes ---");
                System.out.println("1. Crear un nou producte");
                System.out.println("2. Llegir tots els productes");
                System.out.println("3. Actualitzar un producte");
                System.out.println("4. Esborrar un producte");
                System.out.println("5. Sortir");
                System.out.print("Selecciona una opció: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character

                switch (option) {
                    case 1:
                        createProduct(productManager, scanner);
                        break;
                    case 2:
                        readAllProducts(productManager);
                        break;
                    case 3:
                        updateProduct(productManager, scanner);
                        break;
                    case 4:
                        deleteProduct(productManager, scanner);
                        break;
                    case 5:
                        exit = true;
                        System.out.println("Sortint de l'Exercici 2...");
                        break;
                    default:
                        System.out.println("Opció no vàlida. Torna-ho a intentar.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error executant Exercici 2: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private void createProduct(ProductManager productManager, Scanner scanner) {
        try {
            System.out.print("Introdueix el nom del producte: ");
            String nom = scanner.nextLine();
            System.out.print("Introdueix el preu del producte: ");
            double preu = scanner.nextDouble();
            scanner.nextLine(); // Clear the newline character

            Producte producte = new Producte(0, nom, preu);
            productManager.createProduct(producte);
            System.out.println("Producte creat correctament: " + producte);
        } catch (SQLException e) {
            System.err.println("Error creant el producte: " + e.getMessage());
        }
    }

    private void readAllProducts(ProductManager productManager) {
        try {
            List<Producte> productes = productManager.readAllProducts();
            System.out.println("\n--- Llista de Productes ---");
            if (productes.isEmpty()) {
                System.out.println("No hi ha productes disponibles.");
            } else {
                for (Producte producte : productes) {
                    System.out.println(producte);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error llegint els productes: " + e.getMessage());
        }
    }

    private void updateProduct(ProductManager productManager, Scanner scanner) {
        try {
            System.out.print("Introdueix l'ID del producte a actualitzar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
            System.out.print("Introdueix el nou nom del producte: ");
            String nom = scanner.nextLine();
            System.out.print("Introdueix el nou preu del producte: ");
            double preu = scanner.nextDouble();
            scanner.nextLine(); // Clear the newline character

            Producte producte = new Producte(id, nom, preu);
            productManager.updateProduct(producte);
            System.out.println("Producte actualitzat correctament: " + producte);
        } catch (SQLException e) {
            System.err.println("Error actualitzant el producte: " + e.getMessage());
        }
    }

    private void deleteProduct(ProductManager productManager, Scanner scanner) {
        try {
            System.out.print("Introdueix l'ID del producte a esborrar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            productManager.deleteProduct(id);
            System.out.println("Producte amb ID " + id + " esborrat correctament.");
        } catch (SQLException e) {
            System.err.println("Error esborrant el producte: " + e.getMessage());
        }
    }
}
