package character;

import game.Entity;

public abstract class Character extends Entity {
    protected int health;

    public Character(String id, String name, int health) {
        super(id, name);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    @Override
    public abstract void interact(Entity other);
}
