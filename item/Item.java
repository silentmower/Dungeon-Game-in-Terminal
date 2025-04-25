package item;

import character.Player; // Import klasy Player z pakietu character
import game.Entity; // Import klasy Entity z pakietu game

public abstract class Item extends Entity {
    protected int value;

    public Item(String id, String name, int value) {
        super(id, name);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public abstract void use(Player player); // Użycie klasy Player

    @Override
    public void interact(Entity other) {
        if (other instanceof Player) {
            use((Player) other);
        } else {
            System.out.println("Ten przedmiot może być użyty tylko przez gracza.");
        }
    }
}
