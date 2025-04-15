public abstract class Entity {
    protected String id; // Unikalny identyfikator dla każdego bytu
    protected String name; // Nazwa bytu

    public Entity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Możliwa metoda abstrakcyjna definiująca interakcję między bytami
    public abstract void interact(Entity other);
}
