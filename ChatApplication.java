package chat_application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class ChatApplication
{
    static HashMap<String,User> userMap=new HashMap<>();
    static HashMap<Integer,ArrayList<Chat>> chatMap=new HashMap<>();
    static HashMap<String,Group> groupMap=new HashMap<>();
    static int groupId=0;
    public static void main(String[] args) {
        initailize();
        System.out.println("________________________________________________________________________");
        System.out.println();
        System.out.println("                              TEXTCOM                                   ");
        System.out.println("________________________________________________________________________");
        int ch;
        Scanner ob=new Scanner(System.in);
        do
        {
            System.out.println("Welcome to TEXTGRAM");
            System.out.println("1.Login\n2.Exit\nEnter ur choice : ");
            ch=ob.nextInt();
            if(ch==1)
                login();
        }while(ch!=2);
    }

    static void login()
    {
        Scanner ob=new Scanner(System.in);
        System.out.println("Enter username : ");
        String username=ob.next();
        if(userMap.containsKey(username))
        {
            System.out.println("Enter password : ");
            String password=ob.next();
            if(password.equals(userMap.get(username).getPassword()))
                homePage(userMap.get(username));
            else
                System.out.println("Wrong Password!!! try again....");
        }
        else
            System.out.println("Username not found!!! try again....");
    }
    private static void homePage(User user) 
    {
        System.out.println("Welcome to home page "+user.getUserName());
        int ch;
        Scanner ob=new Scanner(System.in);
        do
        {
            System.out.println("Current User : "+user.getUserName()+"\n");
            System.out.println("1.create new contact\n2.create new group\n3.choose user\n4.choose group\n5.Logout\nEnter ur choice : ");
            ch=ob.nextInt();
            switch(ch)
            {
                case 1:
                    createNewContact(user);
                    break;
                case 2:
                    createNewGroup(user);
                    break;
                case 3:
                    openUser(user);
                    break;
                case 4:
                    openGroup(user);
                    break;
                default:
                    System.out.println("Please Enter correct option...");
            }
            System.out.println("\033[H\033[2J");
            System.out.flush();
        }while(ch!=5);
    }

    private static void openGroup(User user1) 
    {
        Scanner ob=new Scanner(System.in);
        System.out.println("You choosed : Open Group");
        if(user1.getGroupList().size()==0)
        {   
            System.out.println("There is no group in your group list...");
            ob.next();
        }   
        else
        {
            for(int i=0;i<user1.getGroupList().size();i++)
            {
                System.out.println(i+1+" "+user1.getGroupList().get(i));
            }
            int ch=ob.nextInt();
            Group group=groupMap.get(user1.getGroupList().get(ch-1));
            do
            {
                System.out.println("current user : "+user1.getUserName()+"\nGroup name : "+group.getGroupName()+"\n\n1.Open group chat\n2.Send Message\n3.Delete Message\n4.Remove User\n5.Group info\n6.close\nEnter ur choice : ");
                ch=ob.nextInt();
                switch (ch) {
                    case 1:
                        for(int i=0;i<group.getGroupChat().size();i++)
                        {
                            System.out.println(group.getGroupChat().get(i).getUserName()+" : "+group.getGroupChat().get(i).getMessage());
                        }
                        ob.next();
                        break;
                    case 2:
                        Scanner ob1=new Scanner(System.in);
                        System.out.println("Enter the message : ");
                        String message=ob1.nextLine();
                        Chat chat=new Chat(user1.getUserName(),message);
                        group.getGroupChat().add(chat);
                        System.out.println("Message send Successfully...");
                        ob.next();
                        break;
                    case 3:
                        for(int i=0;i<group.getGroupChat().size();i++)
                        {
                            if(group.getGroupChat().get(i).getUserName().equals(user1.getUserName()))
                                System.out.println(i+1+" "+group.getGroupChat().get(i).getMessage());
                        }
                        System.out.println("Enter number to delete");
                        ch=ob.nextInt();
                        if(group.getGroupChat().get(ch-1).getUserName().equals(user1.getUserName()))
                        {
                            group.getGroupChat().get(ch-1).setMessage("deleted");
                            System.out.println("Message deleted successfully...");
                        }
                        else
                            System.out.println("please enter valid number...");
                        ob.next();
                        break;
                    case 4:
                        for(int i=0;i<group.getUserNames().size();i++)
                        {
                            System.out.println(i+1+" "+group.getUserNames().get(i));
                        }
                        System.out.println("Enter correct number to remove : ");
                        ch=ob.nextInt();
                        if(ch<1 || ch>group.getUserNames().size())
                            System.out.println("Please enter valid number...");
                        else
                        {
                            String username=group.getUserNames().remove(ch-1);
                            userMap.get(username).getGroupList().remove(group.getGroupName());
                            message="( "+user1.getUserName()+" removed "+username+" )";
                            group.getGroupChat().add(new Chat(user1.getUserName(),message));
                            System.out.println("User removed successfully...");
                        }
                        ob.next();
                        break;
                    case 5:
                        System.out.println("Group name : "+group.getGroupName());
                        System.out.println("Group admin : "+group.getGroupAdmin());
                        System.out.println("Group members : ");
                        for(int i=0;i<group.getUserNames().size();i++)
                        {
                            System.out.println(i+1+" "+group.getUserNames().get(i));
                        }
                        ob.next();
                        break;
                    default:
                        System.out.println("Please Enter valid option...");
                        break;
                }
                System.out.println("\033[H\033[2J");
                System.out.flush();
            }while(ch!=6);
        }
    }

    private static void openUser(User user1) 
    {
        System.out.println("You choosed : Open Chat");
        if(user1.getFriendList().size()==0)
            System.out.println("Friendlist is Empty...");
        else
        {
            int i;
            for(i=0;i<user1.getFriendList().size();i++)
            {
                System.out.println(i+1+" "+user1.getFriendList().get(i));
            }
            Scanner ob=new Scanner(System.in);
            System.out.println("Enter correct number to open : ");
            int ch=ob.nextInt();
            if(ch>i)
            { 
                System.out.println("Entered Wrong number...");
                ob.next();
            }
            else
                openPersonalChat(user1,ch-1);
        }
    }

    private static void openPersonalChat(User user1, int number) 
    {
        User user2=userMap.get(user1.getFriendList().get(number));
        int chatId=user1.getUserId()*user2.getUserId();
        Scanner ob=new Scanner(System.in);
        int ch=0;
        do{
            System.out.println("Current User : "+user1.getUserName());
            System.out.println("Choosed User : "+user2.getUserName());
            System.out.println("Chat id : "+chatId+"\n");
            System.out.println("1.open chat\n2.send message\n3.delete message\n4.close chat\nEnter ur choice : ");
            ch=ob.nextInt();
            switch (ch) {
                case 1:
                    printChat(chatId);
                    break;
                case 2:
                    sendMessage(user1,user2,chatId);
                    break;
                case 3:
                    deleteMessage(user1,chatId);
                    break;
                default:
                    System.out.println("Please enter correct option...");
                    break;
            }
            System.out.println("\033[H\033[2J");
            System.out.flush();
        }while(ch!=4);
    }

    private static void deleteMessage(User user1, int chatId)
    {
        ArrayList<Chat> chats=chatMap.get(chatId);
        Scanner ob=new Scanner(System.in);
        if(chats.size()==0)
            System.out.println("There is no message to delete...");
        else
        {
            for(int i=0;i<chats.size();i++)
            {
                if(chats.get(i).getUserName().equals(user1.getUserName()))
                    System.out.println(i+1+" "+chats.get(i).getMessage());
            }
            System.out.println("Enter number to delete chat : ");
            int ch=ob.nextInt();
            if(chats.get(ch-1).getUserName().equals(user1.getUserName()))
            {
                chatMap.get(chatId).get(ch-1).setMessage("deleted");
                System.out.println("Message deleted Successfully...");
            }
            else
                System.out.println("Entered Wrong number...");
        }
        ob.next();    
    }

    private static void sendMessage(User user1, User user2, int chatId) 
    {
        Scanner ob=new Scanner(System.in);
        System.out.println("Enter the message : ");
        String message=ob.nextLine();
        Chat chat=new Chat(user1.getUserName(),message);
        chatMap.get(chatId).add(chat);
        user1.getFriendList().remove(user2.getUserName());
        user1.getFriendList().add(0,user2.getUserName());
        user2.getFriendList().remove(user1.getUserName());
        user2.getFriendList().add(0,user1.getUserName());
        System.out.println("Message sent Successfully...");
        ob.next();
    }

    private static void printChat(int chatId) 
    {
        ArrayList<Chat> chats=chatMap.get(chatId);
        for(int i=0;i<chats.size();i++)
        {
            System.out.println(chats.get(i).getUserName()+" : "+chats.get(i).getMessage());
        }
        Scanner ob=new Scanner(System.in);
        ob.next();
    }

    private static void createNewGroup(User user1) 
    {
        System.out.println("You choosed : Create New Group");
        Scanner ob=new Scanner(System.in);
        System.out.println("Enter Group Name : ");
        String groupName=ob.nextLine();
        ArrayList<String> users=new ArrayList<>();
        char ch;
        do
        {
            System.out.println("Enter name to add : ");
            String userName=ob.next();
            if(userMap.containsKey(userName)&&(!users.contains(userName)))
            {
                users.add(userName);
                userMap.get(userName).getGroupList().add(groupName);
            }
            else
                System.out.println("Please enter valid username...");
            System.out.println("Do you want to add another one (y/n):");
            ch=ob.next().charAt(0);
        }while(ch!='n');
        if(users.size()>0)
        {
            Group group=new Group(groupId++,groupName,user1.getUserName(),users,new ArrayList<Chat>());
            userMap.get(user1.getUserName()).getGroupList().add(groupName);
            groupMap.put(groupName,group);
            System.out.println("Group created Successfully...");
        }
        else
            System.out.println("Group not created...");
        ob.next();
    }

    private static void createNewContact(User user1) 
    {
        System.out.println("You choosed : Create New Contact");
        System.out.println("\nEnter Friend name to add : ");
        Scanner ob=new Scanner(System.in);
        String friendName=ob.next();
        if(userMap.containsKey(friendName)&&(!user1.getFriendList().contains(friendName)))
        {
            User user2=userMap.get(friendName);
            int chatId=user1.getUserId()*user2.getUserId();
            user1.getFriendList().add(friendName);
            user2.getFriendList().add(user1.getUserName());
            chatMap.put(chatId,new ArrayList<Chat>());
            System.out.print("\nContact created successfully...");
        }
        else
            System.out.print("\nNo such user found...");
        ob.next();
    }

    static void initailize()
    {
        int id=100;
        User user1=new User(id++,"Mani","Man123",new ArrayList<String>(),new ArrayList<String>());
        userMap.put(user1.getUserName(),user1);
        User user2=new User(id++,"Suresh","Sur123",new ArrayList<String>(),new ArrayList<String>());
        userMap.put(user2.getUserName(),user2);
        User user3=new User(id++,"Naresh","Nar123",new ArrayList<String>(),new ArrayList<String>());
        userMap.put(user3.getUserName(),user3);
        User user4=new User(id++,"Ram","Ram123",new ArrayList<String>(),new ArrayList<String>());
        userMap.put(user4.getUserName(),user4);
        User user5=new User(id++,"Krish","Kri123",new ArrayList<String>(),new ArrayList<String>());
        userMap.put(user5.getUserName(),user5);
    }
}