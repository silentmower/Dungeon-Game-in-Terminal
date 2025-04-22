import java.util.ArrayList;

// Klasa Player reprezentuje gracza; dziedziczy po Character
public class Player extends Character {
    private ArrayList<Item> inventory; // Lista przedmiotów posiadanych przez gracza
    private int xp;                    // Doświadczenie gracza
    private Armor equippedArmor;       // Aktualnie wyposażona zbroja

    // Konstruktor tworzy gracza z id, nazwą, zdrowiem i siłą ataku
    public Player(String id, String name, int health, int attackPower) {
        super(id, name, health, attackPower); // Wywołanie konstruktora klasy Character
        this.inventory = new ArrayList<>();    // Inicjalizacja ekwipunku jako pusta lista
        this.xp = 0;                           // Ustawienie domyślnego doświadczenia na 0
        this.equippedArmor = null;             // Początkowo gracz nie ma wyposażonej żadnej zbroi
    }

    // Zwraca aktualne doświadczenie gracza
    public int getXp() {
        return xp;
    }

    
    // Dodaje zdobyte doświadczenie i wypisuje informację o zdobyciu XP
    public void gainXp(int amount) {
        xp += amount;
        System.out.println(name + " zdobywa " + amount + " punktów doświadczenia!");
    }

    // Dodaje przedmiot do ekwipunku
    public void addItem(Item item) {
        inventory.add(item);
        System.out.println(name + " dodaje do ekwipunku: " + item.getName());
    }

    // Używa przedmiotu z ekwipunku – np. mikstury, a następnie usuwa go z listy
    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.use(this);           // Używa przedmiotu (wywołanie abstrakcyjnej metody use)
                inventory.remove(item);     // Usuwa przedmiot z ekwipunku po użyciu
                return;
            }
        }
        System.out.println("Nie znaleziono przedmiotu: " + itemName);
    }

    // Wyświetla zawartość ekwipunku
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

    // Metoda umożliwiająca graczowi wyposażenie zbroi
    public void equipArmor(Armor armor) {
        this.equippedArmor = armor;
        System.out.println(name + " wyposażono w " + armor.getName() + ", która blokuje " +
                armor.getDamageBlockPercentage() + "% obrażeń.");
    }

    // Nadpisana metoda ataku, w której gracz atakuje przeciwnika
    @Override
    public void attack(Character target) {
        System.out.println(name + " atakuje " + target.getName() + " z siłą: " + attackPower);
        target.takeDamage(attackPower);
    }

    // Nadpisana metoda interakcji – gracz wchodzi w interakcję, np. atakuje przeciwnika
    @Override
    public void interact(Entity other) {
        if (other instanceof Enemy) {
            attack((Enemy) other);
        } else {
            System.out.println("Nie można wejść w interakcję z " + other.getName());
        }
    }

    // Nadpisana metoda takeDamage, aby uwzględnić redukcję obrażeń z wyekwipowanej zbroi
    @Override
    public void takeDamage(int damage) {
        if (equippedArmor != null) {  // Jeśli gracz ma wyposażoną zbroję
            int reducedDamage = damage * (100 - equippedArmor.getDamageBlockPercentage()) / 100; // Oblicza obrażenia po redukcji
            System.out.println(name + " dzięki " + equippedArmor.getName() + " otrzymał tylko " + reducedDamage +
                    " obrażeń (zamiast " + damage + ").");
            super.takeDamage(reducedDamage); // Wywołuje oryginalną metodę takeDamage z klasy Character z pomniejszonymi obrażeniami
        } else {
            super.takeDamage(damage);  // Jeśli brak zbroi, zadaje pełne obrażenia
        }
    }

    // Metoda sprawdzająca, czy gra zakończyła się (czy zdrowie gracza spadło do 0)
    public boolean isGameOver() {
        return this.health <= 0;
    }
    public void heal(int amount) {
        this.health += amount;                    // Zwiększa stan zdrowia o podaną wartość
        if(this.health > 100) {                     // Sprawdza, czy zdrowie przekracza maksymalny limit
            this.health = 100;                      // Ustawia zdrowie na 100, jeśli wartość jest większa
        }
        System.out.println(name + " odzyskał " + amount + " punktów zdrowia."); // Informacja dla gracza
    }
}
