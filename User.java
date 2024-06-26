package chat_application;

import java.util.ArrayList;

public class User 
{
    private int userId;
    private String userName;
    private String password;
    private ArrayList<String> friendList=new ArrayList<>();
    private ArrayList<String> groupList=new ArrayList<>();
    
    User(int userId,String userName,String password,ArrayList<String> friendList,ArrayList<String> groupList)
    {
        this.userId=userId;
        this.userName=userName;
        this.password=password;
        this.friendList=friendList;
        this.groupList=groupList;
    }

    public int getUserId()
    {
        return this.userId;
    }

    public String getUserName()
    {
        return this.userName;
    }
    public String getPassword()
    {
        return this.password;
    }
    public ArrayList<String> getFriendList()
    {
        return this.friendList;
    }

    public ArrayList<String> getGroupList()
    {
        return this.groupList;
    }
}
