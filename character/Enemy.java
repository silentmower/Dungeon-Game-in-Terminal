package character; // Deklaracja pakietu

import game.Entity;

public class Enemy extends Character {
    public Enemy(String id, String name, int health) {
        super(id, name, health);
    }

    @Override
    public void interact(Entity other) {
        System.out.println(name + " atakuje " + other.getName());
    }
}
