import java.util.Random;
import java.util.Scanner;

public class EncounterManager {
    private DifficultySettings settings;

    public EncounterManager(DifficultySettings settings) {
        this.settings = settings;
    }

    // Zmodyfikowana metoda: teraz drugi parametr ma typ Player
    public void handleEncounter(char cell, Player player, char[][] map, int newX, int newY) {
        switch (cell) {
            case 'E':
                fightEnemy(player);
                System.out.println("Pokonałeś przeciwnika!");
                map[newY][newX] = '.'; // Usuwamy przeciwnika z mapy
                break;
            case 'I':
                System.out.println("Znalazłeś przedmiot! Mikstura zdrowia.");
                player.addItem(new Item("Health Potion", 25));
                map[newY][newX] = '.'; // Usuwamy przedmiot z mapy
                break;
            case '?':
                solvePuzzle(player);
                System.out.println("Rozwiązałeś zagadkę!");
                map[newY][newX] = '.'; // Usuwamy zagadkę z mapy
                break;
            case 'X':
                System.out.println("Gratulacje! Wyszedłeś z lochu!");
                player.takeDamage(player.getHealth()); // na przykład: kończymy grę
                break;
            default:
                System.out.println("Brak interakcji dla tego elementu.");
                break;
        }
        // Obecnie nie aktualizujemy automatycznie pozycji gracza, zakładamy, że gra to obsługuje osobno.
    }

    private void fightEnemy(Player player) {
        Random random = new Random();
        int damage = random.nextInt(settings.getMaxDamage() - settings.getMinDamage() + 1) + settings.getMinDamage();
        System.out.println("Przeciwnik zadaje Ci " + damage + " obrażeń!");
        player.takeDamage(damage);
        if (player.getHealth() > 0) {
            System.out.println("Pokonałeś przeciwnika! Zdobywasz " + settings.getEnemyXp() + " punktów XP.");
            // Jeżeli masz metodę do zdobywania XP, możesz ją tutaj wywołać
        }
    }

    private void solvePuzzle(Player player) {
        Random random = new Random();
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(10) + 1;
        System.out.println("Rozwiąż zagadkę: Ile wynosi " + a + " + " + b + " ?");
        Scanner scanner = new Scanner(System.in);
        try {
            int answer = scanner.nextInt();
            if (answer == a + b) {
                System.out.println("Brawo! Zdobywasz " + settings.getPuzzleXp() + " punktów XP.");
                // player.gainXp(settings.getPuzzleXp());
            } else {
                System.out.println("Źle! Tracisz 10 punktów zdrowia.");
                player.takeDamage(10);
            }
        } catch (Exception e) {
            System.out.println("Nieprawidłowy input! Tracisz 10 punktów zdrowia.");
            player.takeDamage(10);
        }
    }
}
