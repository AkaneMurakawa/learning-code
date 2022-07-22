package base;

public class User {

    public String name;

    public String bio;

    public User() {
    }

    public User(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "base.User{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
