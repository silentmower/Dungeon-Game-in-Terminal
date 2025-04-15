import java.util.Random;
import java.util.Scanner;

public class GameManager {
    private char[][] map;
    private int mapWidth;
    private int mapHeight;
    private Player player;
    private EncounterManager encounterManager;
    private DifficultySettings difficultySettings;

    public GameManager() {
        Scanner scanner = new Scanner(System.in);

        // WYBÓR ROZMIARU MAPY:
        System.out.println("Podaj rozmiar mapy (liczba z zakresu 5-10, mapa będzie kwadratowa):");
        int size = 7;
        while (true) {
            try {
                size = scanner.nextInt();
                if (size >= 5 && size <= 10) {
                    break;
                } else {
                    System.out.println("Niepoprawny rozmiar, podaj liczbę od 5 do 10:");
                }
            } catch (Exception e) {
                System.out.println("Należy podać liczbę całkowitą.");
                scanner.next(); // czyszczenie bufora
            }
        }
        mapWidth = size;
        mapHeight = size;

        // WYBÓR POZIOMU TRUDNOŚCI:
        System.out.println("Wybierz poziom trudności (1 – Łatwy, 2 – Średni, 3 – Trudny):");
        int diffChoice = 2;
        while (true) {
            try {
                diffChoice = scanner.nextInt();
                if (diffChoice >= 1 && diffChoice <= 3) {
                    break;
                } else {
                    System.out.println("Nieprawidłowy wybór, podaj 1, 2 lub 3:");
                }
            } catch (Exception e) {
                System.out.println("Należy podać liczbę całkowitą.");
                scanner.next(); // czyszczenie bufora
            }
        }
        scanner.nextLine(); // konsumujemy znak nowej linii
        difficultySettings = DifficultySettings.getSettings(diffChoice);

        // Inicjalizacja mapy
        map = new char[mapHeight][mapWidth];
        generateMap();

        // Inicjalizacja gracza – umieszczamy gracza na pozycji (0,0)
        player = new Player("player1", "Filip", 100, 15);
        map[0][0] = 'P';

        // Inicjalizacja EncounterManagera z wybranymi ustawieniami trudności
        encounterManager = new EncounterManager(difficultySettings);
    }

    // Generuje mapę losowo. Na każdym polu (poza pozycjami startowymi) losuje:
    // 10% - przedmiot ('I'), kolejne 10% - przeciwnik ('E'), następne 10% - zagadka ('?')
    // pozostałe 70% to puste pole ('.'). Wyjście ustalamy jako 'X' w prawym dolnym rogu.
    private void generateMap() {
        Random random = new Random();
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                // Rezerwacja pola startowego i wyjścia
                if ((i == 0 && j == 0) || (i == mapHeight - 1 && j == mapWidth - 1)) {
                    map[i][j] = '.';
                } else {
                    int chance = random.nextInt(100);
                    if (chance < 10) {
                        map[i][j] = 'I';
                    } else if (chance < 20) {
                        map[i][j] = 'E';
                    } else if (chance < 30) {
                        map[i][j] = '?';
                    } else {
                        map[i][j] = '.';
                    }
                }
            }
        }
        // Ustalenie wyjścia – dolny prawy róg
        map[mapHeight - 1][mapWidth - 1] = 'X';
    }

    // Wyświetla mapę w konsoli
    private void displayMap() {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Znajduje współrzędną X gracza (szukamy symbolu 'P')
    private int getPlayerX() {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if (map[i][j] == 'P') {
                    return j;
                }
            }
        }
        return -1;
    }

    // Znajduje współrzędną Y gracza
    private int getPlayerY() {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                if (map[i][j] == 'P') {
                    return i;
                }
            }
        }
        return -1;
    }

    // Główna pętla gry
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMap();
            System.out.println("Zdrowie gracza: " + player.getHealth() + " | XP: " + player.getXp());
            System.out.println("Podaj komendę: (np. w, s, a, d – ruch; np. 2d – ruch o 2 pola; i - ekwipunek; q - wyjście)");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("i")) {
                player.showInventory();
                continue;
            }
            if (command.equals("q")) {
                System.out.println("Gra zakończona!");
                scanner.close();
                return;
            }

            int steps = 1;
            char moveDir = ' ';
            // Jeśli komenda ma więcej niż jeden znak i początek to cyfra,
            // interpretujemy ją jako ruch wielokrokowy.
            if (command.length() > 1 && java.lang.Character.isDigit(command.charAt(0))) {
                steps = java.lang.Character.getNumericValue(command.charAt(0));
                moveDir = command.charAt(1);
            } else if (command.length() == 1 && "wasd".indexOf(command.charAt(0)) != -1) {
                moveDir = command.charAt(0);
            } else {
                System.out.println("Nieznana komenda!");
                continue;
            }

            int currentX = getPlayerX();
            int currentY = getPlayerY();
            int newX = currentX;
            int newY = currentY;
            boolean interrupted = false;

            // Wykonujemy ruch krok po kroku
            for (int i = 0; i < steps; i++) {
                switch (moveDir) {
                    case 'w': newY--; break;
                    case 's': newY++; break;
                    case 'a': newX--; break;
                    case 'd': newX++; break;
                    default:
                        System.out.println("Nieznany kierunek!");
                        interrupted = true;
                        break;
                }
                if (interrupted) break;

                // Sprawdzamy, czy nie wychodzimy poza granice mapy
                if (newX < 0 || newX >= mapWidth || newY < 0 || newY >= mapHeight) {
                    System.out.println("Nie możesz się tam ruszyć! Granica mapy.");
                    newX = currentX;
                    newY = currentY;
                    interrupted = true;
                    break;
                }

                // Jeśli trafiamy na wyjście 'X', kończymy grę
                if (map[newY][newX] == 'X') {
                    System.out.println("Gratulacje! Wyszedłeś z lochu!");
                    scanner.close();
                    return;
                }

                // Jeśli napotkamy interaktywny element (przeciwnik, przedmiot, zagadka)
                if (map[newY][newX] == 'E' || map[newY][newX] == 'I' || map[newY][newX] == '?') {
                    encounterManager.handleEncounter(map[newY][newX], player, map, newX, newY);
                    interrupted = true;
                    break;
                }
            }

            // Jeżeli ruch nie został przerwany, aktualizujemy pozycję gracza
            if (!interrupted) {
                map[currentY][currentX] = '.';
                map[newY][newX] = 'P';
            }

            // Jeżeli zdrowie gracza spadło do zera, kończymy grę
            if (player.isGameOver()) {
                System.out.println("Przegrałeś!");
                scanner.close();
                return;
            }
        }
    }
}
