package jvm;

import base.User;

public class EscapeAnalysisTest {

    User myUser;

    public void newUser1() {
        new User("1", "1");
    }

    public void newUser2() {
        // 逃逸对象，新指向
        myUser = new User("1", "1");
    }
}
