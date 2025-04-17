// Abstrakcyjna klasa Item – wszystkie przedmioty w grze będą dziedziczyć jej wspólne atrybuty i metody
public abstract class Item extends Entity {
    protected int value;  // Wspólny atrybut określający wartość przedmiotu (np. moc ulepszenia)

    // Konstruktor inicjalizujący przedmiot poprzez id, nazwę i wartość
    public Item(String id, String name, int value) {
        super(id, name);      // Wywołanie konstruktora klasy Entity
        this.value = value;   // Ustawienie wartości przedmiotu
    }

    // Getter zwracający wartość przedmiotu
    public int getValue() {
        return value;
    }

    // Abstrakcyjna metoda use – definiuje, co się stanie, gdy przedmiot zostanie użyty przez gracza
    public abstract void use(Player player);

    // Standardowa implementacja interakcji – jeśli interakcja następuje przez gracza, wywołujemy metodę use
    @Override
    public void interact(Entity other) {
        if (other instanceof Player) {
            use((Player) other);
        } else {
            System.out.println("Ten przedmiot może być użyty tylko przez gracza.");
        }
    }
}
