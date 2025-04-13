import java.util.ArrayList;

public class PlayerManager {
    private int health;
    private int xp;
    private int x;
    private int y;
    private boolean gameOver;
    private ArrayList<Item> inventory;

    public PlayerManager() {
        this.health = 100;
        this.xp = 0;
        this.x = 0;
        this.y = 0;
        this.gameOver = false;
        this.inventory = new ArrayList<>();
    }

    public int getHealth() {
        return health;
    }

    public int getXp() {
        return xp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isGameOver() {
        return gameOver || health <= 0;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public void gainXp(int amount) {
        xp += amount;
    }

    public void heal(int amount) {
        health += amount;
        if (health > 100) {
            health = 100;
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Dodano do ekwipunku: " + item.getName());
    }

    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                heal(item.getValue());
                inventory.remove(item);
                System.out.println("Użyto przedmiotu: " + item.getName());
                return;
            }
        }
        System.out.println("Nie znaleziono przedmiotu: " + itemName);
    }

    public void showInventory() {
        System.out.println("Ekwipunek:");
        if (inventory.isEmpty()) {
            System.out.println("- Pusty -");
        } else {
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + " (Wartość: " + item.getValue() + ")");
            }
        }
    }

    public int[] calculateMove(String action) {
        int dx = 0, dy = 0;
        if (action.length() > 1 && Character.isDigit(action.charAt(0))) {
            int steps = Character.getNumericValue(action.charAt(0));
            char direction = action.charAt(1);
            switch (direction) {
                case 'w': dy = -steps; break;
                case 's': dy = steps; break;
                case 'a': dx = -steps; break;
                case 'd': dx = steps; break;
                default:
                    System.out.println("Ruch nieznany.");
                    break;
            }
        } else {
            switch (action) {
                case "w": dy = -1; break;
                case "s": dy = 1; break;
                case "a": dx = -1; break;
                case "d": dx = 1; break;
                default:
                    System.out.println("Ruch nieznany.");
                    break;
            }
        }
        return new int[] { x + dx, y + dy };
    }

    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}
