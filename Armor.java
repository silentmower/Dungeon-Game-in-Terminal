// Abstrakcyjna klasa Armor reprezentuje rodzaje zbroi, które blokują część obrażeń
public abstract class Armor extends Item {
    protected int damageBlockPercentage; // Procent obrażeń, które zbroja blokuje (np. 10, 25, 50)

    // Konstruktor ustawia id, nazwę, wartość (może być np. kosztem lub inną cechą) i procent blokowania
    public Armor(String id, String name, int value, int damageBlockPercentage) {
        super(id, name, value);                  // Wywołanie konstruktora klasy Item
        this.damageBlockPercentage = damageBlockPercentage; // Ustawienie procentu blokowania obrażeń
    }

    // Getter zwracający procent blokowania obrażeń
    public int getDamageBlockPercentage() {
        return damageBlockPercentage;
    }

    // Metoda use umożliwia graczowi wyposażenie zbroi
    @Override
    public void use(Player player) {
        player.equipArmor(this);  // Wyposażenie gracza w tę zbroję
        System.out.println(player.getName() + " wyposażył " + name + ", która blokuje " + damageBlockPercentage + "% obrażeń.");
    }
}
