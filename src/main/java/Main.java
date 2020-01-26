import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        SQLRequest service = new SQLRequest();
        ServiceTrigger serviceTrigger = new ServiceTrigger();

        service.insert("Mike", 18, "Moscow");
        service.insert("Musk", 22, "USA");
        service.insert("Ola", 33, "GB");
        Thread.sleep(1000);
        service.update(1, "none", 0, "none");
        service.delete(2);

        List<User> userList = serviceTrigger.getChanged("2020-01-25");
        for (User user : userList) {
            System.out.println("name " + user.getName());
            System.out.println("age " + user.getAge());
            System.out.println("address " + user.getAddress());
            System.out.println("date " + user.getDate());
            System.out.println();
        }


    }
}
