package character;

import item.armor.Armor;
import item.Item;
import java.util.ArrayList;
import game.Entity;

public class Player extends Character {
    private Armor equippedArmor;
    private ArrayList<Item> inventory;
    private int xp; // Pole na zdobyte punkty XP

    public Player(String id, String name, int health) {
        super(id, name, health);
        this.inventory = new ArrayList<>(); // Inicjalizacja ekwipunku
        this.xp = 0; // Początkowa ilość XP
    }

    public void equipArmor(Armor armor) {
        equippedArmor = armor;
        System.out.println(name + " wyposażył " + armor.getName());
    }

    public void heal(int amount) {
        health += amount;
        if (health > 100) health = 100;
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Dodano do ekwipunku: " + item.getName());
    }

    public void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Ekwipunek jest pusty.");
        } else {
            System.out.println("Ekwipunek:");
            for (Item item : inventory) {
                System.out.println("- " + item.getName());
            }
        }
    }

    // Dodana metoda gainXp, która zwiększa ilość XP gracza
    public void gainXp(int amount) {
        xp += amount;
        System.out.println(name + " zdobył " + amount + " punktów XP. Teraz ma " + xp + " XP.");
    }

    public int getXp() {
        return xp;
    }

    // Dodana metoda isGameOver sprawdzająca, czy gracz przegrał (np. zdrowie <= 0)
    public boolean isGameOver() {
        return health <= 0;
    }

    @Override
    public void interact(Entity other) {
        if (other != null) {
            System.out.println(name + " wchodzi w interakcję z " + other.getName());
        } else {
            System.out.println(name + " nie znajduje nic do interakcji.");
        }
    }
}
