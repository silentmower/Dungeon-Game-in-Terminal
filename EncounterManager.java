import java.util.Random;
import java.util.Scanner;

// Klasa EncounterManager zarządza interakcjami gracza z elementami mapy (przeciwnicy, przedmioty, zagadki)
public class EncounterManager {
    private DifficultySettings settings;  // Ustawienia trudności wpływające na interakcje, np. obrażenia, XP

    // Konstruktor inicjalizujący EncounterManager z podanymi ustawieniami trudności
    public EncounterManager(DifficultySettings settings) {
        this.settings = settings;         // Ustawia konfigurację trudności
    }

    /* Metoda handleEncounter obsługuje interakcje gracza z elementami mapy.
       Przyjmuje:
         - 'cell': symbol napotkanego elementu (np. 'E', 'I', '?', 'X'),
         - 'player': obiekt gracza,
         - 'map': mapę gry (tablica znaków),
         - 'newX', 'newY': współrzędne, gdzie nastąpiła interakcja. */
    public void handleEncounter(char cell, Player player, char[][] map, int newX, int newY) {
        switch (cell) {
            case 'E':  // Jeśli napotkano przeciwnika
                fightEnemy(player);  // Używa metody walki z przeciwnikiem
                System.out.println("Pokonałeś przeciwnika!");
                map[newY][newX] = '.';  // Usuwa przeciwnika z mapy, zamieniając symbol na pusty
                break;
            case 'I':  // Jeśli napotkano przedmiot (tutaj miksturę zdrowia)
                System.out.println("Znalazłeś przedmiot! Mikstura zdrowia.");
                // Instancja konkretnej mikstury, np. małej mikstury zdrowia (SmallHealthPotion)
                player.addItem(new SmallHealthPotion());
                map[newY][newX] = '.';  // Usuwa przedmiot z mapy
                break;
            case '?':  // Jeśli napotkano zagadkę
                solvePuzzle(player);  // Wywołuje metodę rozwiązującą zagadkę
                System.out.println("Rozwiązałeś zagadkę!");
                map[newY][newX] = '.';  // Usuwa zagadkę z mapy
                break;
            case 'X':  // Jeśli napotkano wyjście (choć zwykle obsługiwane oddzielnie)
                System.out.println("Gratulacje! Udało ci się wyjść z lochu!");
                // Przykładowo: kończymy grę poprzez ustalenie, że gracz traci całe zdrowie
                player.takeDamage(player.getHealth());
                break;
            default:  // Obsługa sytuacji, gdy nie ma zdefiniowanej interakcji dla danego elementu
                System.out.println("Brak interakcji dla tego elementu.");
                break;
        }
    }

    // Metoda symulująca walkę z przeciwnikiem
    private void fightEnemy(Player player) {
        Random random = new Random(); // Tworzy obiekt Random do losowania obrażeń
        int damage = random.nextInt(settings.getMaxDamage() - settings.getMinDamage() + 1) + settings.getMinDamage();
        System.out.println("Przeciwnik zadaje Ci " + damage + " obrażeń!"); // Informacja o zadanych obrażeniach
        player.takeDamage(damage); // Zadaje obrażenia graczowi

        // Jeśli gracz przeżyje, przyznaje XP i wyświetla tylko jeden komunikat
        if (player.getHealth() > 0) {
            player.gainXp(settings.getEnemyXp()); // Przyznaje doświadczenie graczowi
        }
    }


    // Metoda rozwiązująca zagadkę – prosta matematyczna zagadka
    private void solvePuzzle(Player player) {
        Random random = new Random();  // Tworzy obiekt Random do generacji liczb
        int a = random.nextInt(10) + 1;  // Losuje pierwszą liczbę z zakresu 1-10
        int b = random.nextInt(10) + 1;  // Losuje drugą liczbę z zakresu 1-10
        System.out.println("Rozwiąż zagadkę: Ile wynosi " + a + " + " + b + " ?");
        Scanner scanner = new Scanner(System.in); // Tworzy Scanner do pobierania odpowiedzi gracza
        try {
            int answer = scanner.nextInt();  // Odczytuje odpowiedź gracza
            if (answer == a + b) {           // Sprawdza poprawność odpowiedzi
                System.out.println("Brawo! Zdobywasz " + settings.getPuzzleXp() + " punktów XP.");
                player.gainXp(settings.getPuzzleXp());  // Przyznaje graczowi XP
            } else {
                System.out.println("Źle! Tracisz 10 punktów zdrowia.");
                player.takeDamage(10);       // Kara w postaci utraty zdrowia za błędną odpowiedź
            }
        } catch (Exception e) {              // Obsługuje błędy podczas pobierania odpowiedzi
            System.out.println("Nieprawidłowy input! Tracisz 10 punktów zdrowia.");
            player.takeDamage(10);           // Kara w postaci obrażeń
        }
    }
}
