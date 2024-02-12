package com.driver.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.driver.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases
{
    WhatsappController whatsappController=new WhatsappController();

    @Test
    public void testCreateGroup() throws Exception
    {
        User user=new User("Rohan","1234");
        User admin=new User("Suba","456");

        List<User>users=new ArrayList<>();

        users.add(admin);
        users.add(user);


        Group group=whatsappController.createGroup(users);
        String name=group.getName();
        int size=group.getNumberOfParticipants();

        assertEquals(2,size);
        assertEquals(name,user.getName());
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("Rohan", "123456");
        assertEquals(user.getName(), "Rohan");
    }

    public void testSendMessage() throws Exception
    {

    }


}
