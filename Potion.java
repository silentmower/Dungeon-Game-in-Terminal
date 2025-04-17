// Abstrakcyjna klasa Potion reprezentuje mikstury, które są używane do leczenia (lub innych efektów)
public abstract class Potion extends Item {
    // Konstruktor ustawia miksturę poprzez id, nazwę i wartość (liczba punktów zdrowia, które przywraca)
    public Potion(String id, String name, int value) {
        super(id, name, value);   // Wywołuje konstruktor klasy Item
    }

    // Metoda use – użycie mikstury leczy gracza o określoną wartość
    @Override
    public void use(Player player) {
        player.heal(value);  // Przywraca graczowi punkty zdrowia równające się value
        System.out.println(player.getName() + " użył " + name + " i odzyskał " + value + " punktów zdrowia.");
    }
}

