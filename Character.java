public abstract class Character extends Entity {
    protected int health;   // Wspólny atrybut: zdrowie
    protected int attackPower; // Wspólny atrybut: siła ataku

    public Character(String id, String name, int health, int attackPower) {
        super(id, name);
        this.health = health;
        this.attackPower = attackPower;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;  // Zapewniamy, że zdrowie nie spada poniżej 0
        }
    }

    public int getAttackPower() {
        return attackPower;
    }

    public abstract void attack(Character target); // Metoda abstrakcyjna do zaimplementowania w klasach dziedziczących
}
