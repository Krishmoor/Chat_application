package chat_application;

import java.util.ArrayList;

public class Group {
 
    private int groupId;
    private String groupName;
    private String groupAdmin;
    private ArrayList<String> usernames;
    private ArrayList<Chat> groupChat;

    Group(int groupId,String groupName,String groupAdmin,ArrayList<String> usernames,ArrayList<Chat> groupChat)
    {
        this.groupId=groupId;
        this.groupName=groupName;
        this.groupAdmin=groupAdmin;
        this.usernames=usernames;
        this.groupChat=groupChat;
    }
    public int getGroupId()
    {
        return this.groupId;
    }
    public String getGroupName()
    {
        return this.groupName;
    }
    public String getGroupAdmin()
    {
        return this.groupAdmin;
    }
    public ArrayList<String> getUserNames()
    {
        return this.usernames;
    }
    public ArrayList<Chat> getGroupChat()
    {
        return this.groupChat;
    }
}
