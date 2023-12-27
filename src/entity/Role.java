package entity;

public class Role {
    private int id;
    private String name;

    //Constructeurs
    public Role() {

    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters ou Accesseurs
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //Setters ou Modificateurs
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Methodes
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
