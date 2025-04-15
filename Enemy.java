import java.util.Random;

public class Enemy extends Character {
    public Enemy(String id, String name, int health, int attackPower) {
        super(id, name, health, attackPower);
    }

    @Override
    public void attack(Character target) {
        Random random = new Random();
        int damage = random.nextInt(attackPower) + 1; // Losowa siła ataku od 1 do attackPower
        System.out.println(name + " atakuje " + target.getName() + " z siłą: " + damage);
        target.takeDamage(damage);
    }

    @Override
    public void interact(Entity other) {
        if (other instanceof Player) {
            attack((Player) other); // Przykładowa interakcja: atak na gracza
        } else {
            System.out.println("Wrogowie nie wchodzą w interakcje z " + other.getName());
        }
    }
}
