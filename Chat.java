package chat_application;

public class Chat 
{
    private String userName;
    private String message;
    
    Chat(String userName,String message)
    {
        this.userName=userName;
        this.message=message;
    }

    public String getUserName()
    {
        return this.userName;
    }
    public String getMessage()
    {
        return this.message;
    }
    public void setMessage(String message)
    {
        this.message=message;
    }
}
