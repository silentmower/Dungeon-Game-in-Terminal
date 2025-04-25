package item.armor;

import item.Item;
import character.Player;

public abstract class Armor extends Item {
    protected int damageBlockPercentage;

    public Armor(String id, String name, int value, int damageBlockPercentage) {
        super(id, name, value);
        this.damageBlockPercentage = damageBlockPercentage;
    }

    public int getDamageBlockPercentage() {
        return damageBlockPercentage;
    }

    @Override
    public void use(Player player) {
        player.equipArmor(this);
        System.out.println(player.getName() + " wyposażył " + name + " i zyskał ochronę blokującą " + damageBlockPercentage + "% obrażeń.");
    }
}
