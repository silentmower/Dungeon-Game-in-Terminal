import java.util.Random;
import java.util.Scanner;

public class EncounterManager {
    private DifficultySettings settings;

    public EncounterManager(DifficultySettings settings) {
        this.settings = settings;
    }

    public void handleEncounter(char cell, PlayerManager player, char[][] map, int newX, int newY) {
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
                System.out.println("Gratulacje! Znalazłeś wyjście!");
                player.setGameOver(true);
                break;
            default:
                // W przypadku pustego pola lub innych znaków nic nie zmieniamy
                break;
        }
        // Aktualizacja mapy
        map[player.getY()][player.getX()] = 'P'; // Ustaw gracza na nowej pozycji
        player.move(newX, newY);
    }


    private void fightEnemy(PlayerManager player) {
        Random random = new Random();
        int damage = random.nextInt(settings.getMaxDamage() - settings.getMinDamage() + 1) + settings.getMinDamage();
        System.out.println("Wróg zadaje Ci " + damage + " obrażeń!");
        player.takeDamage(damage);
        if (player.getHealth() > 0) {
            System.out.println("Pokonałeś wroga! Zdobywasz " + settings.getEnemyXp() + " punktów XP.");
            player.gainXp(settings.getEnemyXp());
        }
    }

    private void solvePuzzle(PlayerManager player) {
        Random random = new Random();
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(10) + 1;
        System.out.println("Rozwiąż zagadkę: Ile wynosi " + a + " + " + b + " ?");
        Scanner scanner = new Scanner(System.in);
        try {
            int answer = scanner.nextInt();
            if (answer == a + b) {
                System.out.println("Brawo! Zdobywasz " + settings.getPuzzleXp() + " punktów XP.");
                player.gainXp(settings.getPuzzleXp());
            } else {
                System.out.println("Źle! Tracisz 10 punktów zdrowia.");
                player.takeDamage(10);
            }
        } catch (Exception e) {
            System.out.println("Nieprawidłowy input! Tracisz 10 punktów zdrowia.");
            player.takeDamage(10);
        }
    }

    public void triggerAmbush(PlayerManager player) {
        fightEnemy(player);
    }
}

