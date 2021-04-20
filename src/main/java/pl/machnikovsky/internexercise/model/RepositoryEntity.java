package pl.machnikovsky.internexercise.model;

public class RepositoryEntity {

    private String name;
    private int stars;

    public RepositoryEntity(String name, int stars) {
        this.name = name;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
