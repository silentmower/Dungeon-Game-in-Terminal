import java.util.ArrayList;

public class Player extends Character {
    private ArrayList<Item> inventory;
    private int xp; // Pole dla doświadczenia

    public Player(String id, String name, int health, int attackPower) {
        super(id, name, health, attackPower);
        this.inventory = new ArrayList<>();
        this.xp = 0;
    }

    public int getXp() {
        return xp;
    }

    public void gainXp(int amount) {
        xp += amount;
        System.out.println(name + " zdobywa " + amount + " punktów doświadczenia!");
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println(name + " dodaje do ekwipunku: " + item.getName());
    }

    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                this.health += item.getValue();
                if (this.health > 100) {
                    this.health = 100;
                }
                inventory.remove(item);
                System.out.println(name + " użył przedmiotu: " + item.getName());
                return;
            }
        }
        System.out.println("Nie znaleziono przedmiotu: " + itemName);
    }

    public void showInventory() {
        System.out.println("Ekwipunek " + name + ":");
        if (inventory.isEmpty()) {
            System.out.println("- Pusty");
        } else {
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + " (Wartość: " + item.getValue() + ")");
            }
        }
    }

    @Override
    public void attack(Character target) {
        System.out.println(name + " atakuje " + target.getName() + " z siłą: " + attackPower);
        target.takeDamage(attackPower);
    }

    @Override
    public void interact(Entity other) {
        if (other instanceof Enemy) {
            attack((Enemy) other);
        } else {
            System.out.println("Nie można wejść w interakcję z " + other.getName());
        }
    }

    public boolean isGameOver() {
        return this.health <= 0;
    }
}
