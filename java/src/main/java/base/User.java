package base;

public class User {

    private String name;

    private Integer age;

    private String bio;

    public User() {
    }

    public User(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public User(String name, Integer age, String bio) {
        this.name = name;
        this.age = age;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
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
