package game; // Deklaracja pakietu

public abstract class Entity {
    protected String id;
    protected String name;

    public Entity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void interact(Entity other);
}
