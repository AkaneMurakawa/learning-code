package spring.ioc.dependency.domain;

public class User {

    private String name;

    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }

    public static User createUser(){
        User user = new User();
        user.setName("Akane");
        return user;
    }
}
