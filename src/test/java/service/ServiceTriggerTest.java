package service;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTriggerTest {

    @org.junit.jupiter.api.Test
    void getDataChanges() {
        SQLRequest request = new SQLRequest();
        ServiceTrigger trigger = new ServiceTrigger();

        int countOne = request.insert("A", 1000, "B");
        assertEquals(1, countOne);

        int countTwo = request.update(1, "none", 0, "none");
        assertEquals(1, countTwo);

        int countThree = request.delete(1);
        assertEquals(1,countThree);

        int sizeArray = trigger.getDataChanges("2020-01-27 00:00:00").size();
        assertEquals(3, sizeArray);

        request.close();
    }
}