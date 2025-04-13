import java.util.Scanner;

public class GameManager {
    private MapGenerator mapGenerator;
    private PlayerManager playerManager;
    private EncounterManager encounterManager;
    private char[][] map;

    public GameManager() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w Dungeon Adventure!");

        // Wybór rozmiaru mapy
        System.out.println("Podaj wielkość mapy (od 5 do 10, np. 7 dla mapy 7x7):");
        int size = 0;
        while (true) {
            try {
                size = scanner.nextInt();
                scanner.nextLine(); // Czyszczenie bufora wejścia

                if (size >= 5 && size <= 10) {
                    break;
                } else {
                    System.out.println("Nieprawidłowy rozmiar! Wprowadź wartość w zakresie 5-10.");
                }
            } catch (Exception e) {
                System.out.println("Nieprawidłowe dane! Wprowadź liczbę całkowitą od 5 do 10.");
                scanner.nextLine(); // Czyszczenie bufora wejścia
            }
        }

        // Wybór poziomu trudności
        System.out.println("Wybierz poziom trudności:");
        System.out.println("1. Łatwy");
        System.out.println("2. Średni");
        System.out.println("3. Trudny");
        int difficulty = scanner.nextInt();
        scanner.nextLine(); // Czyszczenie bufora wejścia
        System.out.println("Wybrano poziom: " + difficulty);

        DifficultySettings settings = DifficultySettings.getSettings(difficulty);
        this.mapGenerator = new MapGenerator();
        this.playerManager = new PlayerManager();
        this.encounterManager = new EncounterManager(settings);

        // Generowanie mapy o podanych wymiarach (np. 7x7)
        this.map = mapGenerator.generateMap(size, size);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Instrukcje:");
        System.out.println("- Poruszanie: 'w', 'a', 's', 'd'");
        System.out.println("- Ruch o wiele pól: np. '2a' (dwa pola w lewo)");
        System.out.println("- Używanie przedmiotu: 'u <nazwa>'");
        System.out.println("- Sprawdzenie ekwipunku: 'i'");

        while (!playerManager.isGameOver()) {
            displayMap();
            System.out.println("Zdrowie: " + playerManager.getHealth() + " | XP: " + playerManager.getXp());
            System.out.println("Podaj swoją akcję:");
            String action = scanner.nextLine();

            if (action.startsWith("u ")) {
                String itemName = action.substring(2);
                playerManager.useItem(itemName);
            } else if (action.equals("i")) {
                playerManager.showInventory();
            } else {
                processMove(action);
            }
        }
        System.out.println("Koniec gry! Zdobyte XP: " + playerManager.getXp());
        scanner.close();
    }

    private void displayMap() {
        for (char[] row : map) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private void processMove(String action) {
        // Ustal liczbę kroków oraz kierunek ruchu
        int steps = 1;
        char direction;
        if (action.length() > 1 && Character.isDigit(action.charAt(0))) {
            steps = Character.getNumericValue(action.charAt(0));
            direction = action.charAt(1);
        } else {
            direction = action.charAt(0);
        }

        int startX = playerManager.getX();
        int startY = playerManager.getY();
        boolean interrupted = false;
        int currentX = startX;
        int currentY = startY;

        for (int step = 1; step <= steps; step++) {
            int newX = startX;
            int newY = startY;

            switch (direction) {
                case 'w':
                    newY = startY - step;
                    break;
                case 's':
                    newY = startY + step;
                    break;
                case 'a':
                    newX = startX - step;
                    break;
                case 'd':
                    newX = startX + step;
                    break;
                default:
                    System.out.println("Nieprawidłowy kierunek!");
                    return;
            }

            if (newX < 0 || newX >= map[0].length || newY < 0 || newY >= map.length) {
                System.out.println("Nie możesz się tam ruszyć!");
                return;
            }

            char cell = map[newY][newX];
            if (cell != '.' && cell != 'P') {
                encounterManager.handleEncounter(cell, playerManager, map, newX, newY);
                interrupted = true;
                break;
            }

            currentX = newX;
            currentY = newY;
        }

        int finalX = currentX; // Poprawiamy ostatni krok ruchu
        int finalY = currentY; // Poprawiamy ostatni krok ruchu

        map[startY][startX] = '.';
        map[finalY][finalX] = 'P';
        playerManager.move(finalX, finalY);
    }
}

