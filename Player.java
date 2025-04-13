import java.util.ArrayList;

public class Player {
    private String name;
    private int health;
    private int xp;
    private ArrayList<Item> inventory;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.xp = 0;
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getXp() {
        return xp;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public void heal(int amount) {
        health += amount;
        if (health > 100) {
            health = 100;
        }
    }

    public void gainXp(int amount) {
        xp += amount;
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Dodano do ekwipunku: " + item.getName());
    }

    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equals(itemName)) {
                if (itemName.equals("Health Potion")) {
                    heal(item.getValue());
                    System.out.println("Użyto mikstury zdrowia! Odzyskałeś 25 punktów zdrowia.");
                }
                inventory.remove(item);
                return;
            }
        }
        System.out.println("Nie znaleziono przedmiotu o nazwie: " + itemName);
    }

    public void showInventory() {
        System.out.println("Ekwipunek:");
        for (Item item : inventory) {
            System.out.println("- " + item.getName() + " (Wartość: " + item.getValue() + ")");
        }
    }
}
