package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;

    private HashSet<User>users;

    private ArrayList<Message>messages;
    private int customGroupCount;
    private int messageId;


    public String changeAdmin(User approver, User user, Group group) throws Exception
    {
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        if(!adminMap.containsKey(group))
        {
            throw new Exception("Group does not exist");
        }

        if(adminMap.get(group)!=approver)
        {
            throw new Exception("Approver does not have rights");
        }

        boolean isPresent=false;

        for(User user1:groupUserMap.get(group))
        {
            if(user1==user)
            {
                isPresent=true;
                break;
            }
        }

        if(!isPresent) //user is not the part of the given group
        {
            throw new Exception("User is not a participant");
        }

        adminMap.put(group,user); //changed the admin to user

        return "SUCCESS";
    }
    public int sendMessage(Message message, User sender, Group group) throws Exception
    {
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

       if(!groupUserMap.containsKey(group) || group==null) //if group does not exist
       {
           throw new Exception("Group does not exist");
       }

        List<User>userList=groupUserMap.get(group); //got all the users
        boolean isPresent=false;

        for(User user:userList)
        {
            if(user==sender)
            {
                isPresent=true;
                break;
            }
        }

        //if not present that means sender is not a member of the given group

        if(!isPresent)
        {
            throw new Exception("You are not allowed to send message");
        }

        groupMessageMap.get(group).add(message);

        return groupMessageMap.get(group).size();

    }
    public int createMessage(String content)
    {
        this.messageId+=1;
        Message message=new Message(this.messageId,content);
        messages.add(message);

        return this.messageId;
    }
    public Group createGroup(List<User> users)
    {

        // The list contains at least 2 users where the first user is the admin. A group has exactly one admin.
        // If there are only 2 users, the group is a personal chat and the group name should be kept as the name of the second user(other than admin)
        // If there are 2+ users, the name of group should be "Group count". For example, the name of first group would be "Group 1", second would be "Group 2" and so on.
        // Note that a personal chat is not considered a group and the count is not updated for personal chats.
        // If group is successfully created, return group.

        //For example: Consider userList1 = {Alex, Bob, Charlie}, userList2 = {Dan, Evan}, userList3 = {Felix, Graham, Hugh}.
        //If createGroup is called for these userLists in the same order, their group names would be "Group 1", "Evan", and "Group 2" respectively.


        //at least 2 user
        if(users==null || users.size()<2)
        {
            throw new IllegalArgumentException("user is less than 2");
        }



        //first user will be admin
        User admin=users.get(0);

        String groupName="";

        if(users.size()==2)  //if only 2 users are there and  group name should be name of 2nd user
        {
            //it is personal chat

            groupName=users.get(1).getName();
            return new Group(groupName,2);
        }
        else //if 2+ users then group name will be "Group count"
        {
            int count=groupUserMap.size()+1;

            groupName="Group "+count;

            Group group=new Group(groupName,users.size());

            //add in groupUserMap
            groupUserMap.put(group,users);
            adminMap.put(group,admin);

            this.customGroupCount++;
            return group;
        }

    }
    public String createUser(String name, String mobile) throws Exception
    {

        if(userMobile.contains(mobile))
        {
            throw new Exception("User already exists");
        }

        User user=new User(name,mobile);
        userMobile.add(mobile);
        this.users.add(user);
        return "SUCCESS";
    }

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.users=new HashSet<>();
        this.messages=new ArrayList<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public HashMap<Group, List<User>> getGroupUserMap() {
        return groupUserMap;
    }

    public void setGroupUserMap(HashMap<Group, List<User>> groupUserMap) {
        this.groupUserMap = groupUserMap;
    }

    public HashMap<Group, List<Message>> getGroupMessageMap() {
        return groupMessageMap;
    }

    public void setGroupMessageMap(HashMap<Group, List<Message>> groupMessageMap) {
        this.groupMessageMap = groupMessageMap;
    }

    public HashMap<Message, User> getSenderMap() {
        return senderMap;
    }

    public void setSenderMap(HashMap<Message, User> senderMap) {
        this.senderMap = senderMap;
    }

    public HashMap<Group, User> getAdminMap() {
        return adminMap;
    }

    public void setAdminMap(HashMap<Group, User> adminMap) {
        this.adminMap = adminMap;
    }

    public HashSet<String> getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(HashSet<String> userMobile) {
        this.userMobile = userMobile;
    }

    public int getCustomGroupCount() {
        return customGroupCount;
    }

    public void setCustomGroupCount(int customGroupCount) {
        this.customGroupCount = customGroupCount;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
