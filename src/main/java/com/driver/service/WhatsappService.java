package com.driver.service;


import com.driver.Group;
import com.driver.Message;
import com.driver.User;
import com.driver.WhatsappRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class WhatsappService
{
    WhatsappRepository whatsappRepository=new WhatsappRepository();
    public String createUser(String name, String mobile) throws Exception
    {
        return whatsappRepository.createUser(name,mobile);
    }

    public Group createGroup(List<User> users)
    {
        return whatsappRepository.createGroup(users);
    }

    public int createMessage(String content)
    {
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception
    {
        return whatsappRepository.sendMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception
    {
        return whatsappRepository.changeAdmin(approver,user,group);
    }
}
