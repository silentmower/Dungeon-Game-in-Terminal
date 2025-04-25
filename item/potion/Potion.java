package item.potion;

import item.Item; // Import klasy Item z pakietu items
import character.Player; // Import klasy Player z pakietu character

public abstract class Potion extends Item {
    public Potion(String id, String name, int value) {
        super(id, name, value); // Wywołanie konstruktora Item
    }

    @Override
    public void use(Player player) {
        player.heal(value); // Leczy gracza
        System.out.println(player.getName() + " użył " + getName() + " i odzyskał " + value + " punktów zdrowia.");
    }
}
