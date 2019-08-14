package CO2017.exercise3.ab817;

/**
 * Created by aspol on 06/05/2017.
 */

        import java.net.*;
        import java.io.*;
        import java.util.*;

public class MessageServer
{


//number representing a-z for client assignment
public static int alphaid =65;


    public static void main(String[] args) throws IOException
    {




        // message board for every client to use
        MessageBoard messageBoard = new MessageBoard();
        // converts arg into int for connection
        int portnum = Integer.parseInt(args[0]);
        ServerSocket serverSocket = null;
        //try catch for connection with args
        try {
            serverSocket = new ServerSocket(portnum);
            System.out.println ("Starting Message server on port" + " " + portnum);
            try {
                while (true)
                {

                    new MessageServerHandler(messageBoard,serverSocket.accept(),alphaid);
                    alphaid++;
                }
            }
            catch (IOException e)
            {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: " + portnum);
            System.exit(1);
        }
        finally
        {
            try {
                serverSocket.close();
            }
            catch (IOException e)
            {
                System.err.println("Could not close port: " + portnum);
                System.exit(1);
            }
        }
    }


}