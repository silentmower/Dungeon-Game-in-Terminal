package game;

import character.Player;
import item.potion.SmallHealthPotion;
import item.potion.MediumHealthPotion;
import item.potion.LargeHealthPotion;
import item.armor.RustyArmor;
import item.armor.GoldArmor;
import item.armor.GodnessArmor;
import java.util.Random;
import java.util.Scanner;


public class EncounterManager {
    private DifficultySettings settings;

    public EncounterManager(DifficultySettings settings) {
        this.settings = settings;
    }

    public void handleEncounter(char cell, Player player, char[][] map, int newX, int newY) {
        switch (cell) {
            case 'E': // Przeciwnik
                fightEnemy(player);
                System.out.println("Pokonałeś przeciwnika!");
                map[newY][newX] = '.';
                break;
            case 'I':  // Przedmiot (drop)
                System.out.println("Znalazłeś przedmiot!");
                Random random = new Random();
                double dropChance = random.nextDouble();  // losuje wartość z przedziału [0,1)
                if (dropChance < 0.5) {
                    // 50% szans na otrzymanie mikstury zdrowia
                    int potionType = random.nextInt(3);  // losuje 0, 1 lub 2
                    if (potionType == 0) {
                        player.addItem(new SmallHealthPotion());
                        System.out.println("Otrzymałeś Small Health Potion.");
                    } else if (potionType == 1) {
                        player.addItem(new MediumHealthPotion());
                        System.out.println("Otrzymałeś Medium Health Potion.");
                    } else {
                        player.addItem(new LargeHealthPotion());
                        System.out.println("Otrzymałeś Large Health Potion.");
                    }
                } else {
                    // 50% szans na otrzymanie zbroi
                    int armorType = random.nextInt(3);  // losuje 0, 1 lub 2
                    if (armorType == 0) {
                        player.equipArmor(new RustyArmor());
                        System.out.println("Otrzymałeś zbroję: Rusty Armor.");
                    } else if (armorType == 1) {
                        player.equipArmor(new GoldArmor());
                        System.out.println("Otrzymałeś zbroję: Gold Armor.");
                    } else {
                        player.equipArmor(new GodnessArmor());
                        System.out.println("Otrzymałeś zbroję: Godness Armor.");
                    }
                }
                map[newY][newX] = '.';
                break;
            case '?':  // Zagadka
                solvePuzzle(player);
                System.out.println("Rozwiązałeś zagadkę!");
                map[newY][newX] = '.';
                break;
            case 'X':  // Wyjście
                System.out.println("Gratulacje! Udało ci się wyjść z lochu!");
                player.takeDamage(player.getHealth());
                break;
            default:
                System.out.println("Brak interakcji dla tego elementu.");
                break;
        }
    }

    // Metoda symulująca walkę z przeciwnikiem
    private void fightEnemy(Player player) {
        Random random = new Random();
        int damage = random.nextInt(settings.getMaxDamage() - settings.getMinDamage() + 1) + settings.getMinDamage();
        System.out.println("Przeciwnik zadaje Ci " + damage + " obrażeń!");
        player.takeDamage(damage);
        if (player.getHealth() > 0) {
            player.gainXp(settings.getEnemyXp());
        }
    }

    // Metoda rozwiązująca zagadkę – prosta matematyczna zagadka
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
}
