package game; // Deklaracja pakietu

import character.Player; // Import wymaganych klas
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
        player = new Player("player1", "Filip", 100);
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
            displayMap(); // Wyświetla aktualną mapę
            System.out.println("Zdrowie gracza: " + player.getHealth() + " | XP: " + player.getXp());
            System.out.println("Podaj komendę: (np. w, s, a, d – ruch; 2d – ruch o 2 pola; i - ekwipunek; q - wyjście)");
            String command = scanner.nextLine().trim().toLowerCase(); // Pobiera i przetwarza komendę od gracza

            if (command.equals("i")) {
                player.showInventory(); // Wyświetla ekwipunek
                continue;
            }
            if (command.equals("q")) {
                System.out.println("Gra zakończona!");
                scanner.close(); // Zamyka Scanner
                return;
            }

            int steps = 1; // Domyślna liczba kroków (ruch o jedno pole)
            char moveDir = ' '; // Kierunek ruchu
            if (command.length() > 1 && java.lang.Character.isDigit(command.charAt(0))) {
                steps = java.lang.Character.getNumericValue(command.charAt(0)); // Pobiera liczbę kroków
                moveDir = command.charAt(1); // Pobiera kierunek z komendy
            } else if (command.length() == 1 && "wasd".indexOf(command.charAt(0)) != -1) {
                moveDir = command.charAt(0); // Ustawia kierunek ruchu dla jednoliterowej komendy
            } else {
                System.out.println("Nieznana komenda!");
                continue;
            }

            int currentX = getPlayerX(); // Pobiera aktualną pozycję X gracza
            int currentY = getPlayerY(); // Pobiera aktualną pozycję Y gracza
            int newX = currentX;         // Inicjalizuje nowe współrzędne X
            int newY = currentY;         // Inicjalizuje nowe współrzędne Y

            boolean interrupted = false; // Flaga do oznaczenia przerwania ruchu/interakcji

            for (int i = 0; i < steps; i++) { // Iteruje przez liczbę kroków
                switch (moveDir) {
                    case 'w': newY--; break; // Ruch w górę
                    case 's': newY++; break; // Ruch w dół
                    case 'a': newX--; break; // Ruch w lewo
                    case 'd': newX++; break; // Ruch w prawo
                    default:
                        System.out.println("Nieznany kierunek!");
                        interrupted = true; // Ustawia przerwanie, gdy kierunek jest nieznany
                        break;
                }
                if (interrupted) break; // Przerywa, jeśli wystąpił problem z kierunkiem

                // Sprawdzanie granic mapy
                if (newX < 0 || newX >= mapWidth || newY < 0 || newY >= mapHeight) {
                    System.out.println("Nie możesz się tam ruszyć! Granica mapy.");
                    interrupted = true; // Przerywa ruch, jeśli wyszliśmy poza mapę
                    break;
                }

                // Jeśli gracz wchodzi na wyjście 'X'
                if (map[newY][newX] == 'X') {
                    System.out.println("Gratulacje! Wyszedłeś z lochu!");
                    scanner.close();
                    return;
                }

                // Jeśli na nowym polu znajduje się interaktywny element
                                   // Jeśli na nowym polu znajduje się interaktywny element
                if (map[newY][newX] == 'E' || map[newY][newX] == 'I' || map[newY][newX] == '?') {
                    encounterManager.handleEncounter(map[newY][newX], player, map, newX, newY); // Obsługa interakcji
                    map[newY][newX] = 'P'; // Gracz przejmuje miejsce interakcji
                    map[currentY][currentX] = '.'; // Poprzednia pozycja gracza staje się pusta
                    currentX = newX; // Aktualizuje bieżące X gracza na nowe
                    currentY = newY; // Aktualizuje bieżące Y gracza na nowe
                    interrupted = true; // Przerywa dalszy ruch po interakcji
                    break; // Kończy pętlę kroków po interakcji
                }
            }

            // Jeśli ruch nie został przerwany, gracz przechodzi na nowe pole
            if (!interrupted) {
                map[currentY][currentX] = '.'; // Oznacza stare pole jako puste
                map[newY][newX] = 'P';         // Ustawia gracza na nowej pozycji
            }

            // Sprawdzanie, czy gracz przegrał (zdrowie <= 0)
            if (player.isGameOver()) {
                System.out.println("Przegrałeś!");
                scanner.close();
                return;
            }
        }
    } // Zamknięcie metody startGame

} // Zamknięcie klasy GameManager
