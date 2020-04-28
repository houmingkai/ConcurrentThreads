package ch3;

import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicInReference {

    static AtomicReference<UserInfo> reference = new AtomicReference<>();

    public static void main(String[] args) {
        UserInfo userInfo  = new UserInfo("hmk",18);
        reference.set(userInfo);
        System.out.println(reference.get().getName());
        System.out.println(reference.get().getAge());

        UserInfo user  = new UserInfo("lyx",24);
        System.out.println(user.getName());
        System.out.println(user.getAge());

    }


    public static class UserInfo{

        private String name;
        private Integer age;

        public UserInfo() {}

        public UserInfo(String name, Integer age) {
            this.name = name;
            this.age = age;
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
    }
}
