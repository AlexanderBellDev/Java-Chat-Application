
package CO2017.exercise3.ab817;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class MessageClient {
    public static void main(String[] args) throws IOException {

        //taking args and assigning to variables
        String serverHostname = args[0];
        int portnumber = Integer.parseInt(args[1]);

        //error message
        if (args.length != 2) {
            System.err.println
                    ("Usage: java MessageClient <host> <port>");
            System.exit(1);
        }

        //connect to server
        System.out.println ("Attempting to connect to host " +
                serverHostname + " on port " + portnumber);


        //initialize reader/writer
        Socket messageSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        //try catch for connection to message server

        try {
            messageSocket = new Socket(serverHostname,portnumber);
            out = new PrintWriter(messageSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    messageSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Couldn't find host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;
        String example;


        System.out.print("?" + " ");
        try {


            while ((userInput = stdIn.readLine()) != null) {


                //list method
                if (userInput.equals("LIST")) {


                    out.println(userInput);

                    //for loop parameter passed by server
                    int g = Integer.parseInt(in.readLine());

                    //LOOP as many times as there is message headers
                    for (int x = 0; x < g; x++) {
                        System.out.println(in.readLine());
                    }


                }


                //bye method
                if (userInput.equals("BYE")) {
                    out.println(userInput);
                    break;
                }

                //send method
                if (userInput.contains("SEND:")) {
                    out.println(userInput);

                }

                //get method
                if (userInput.contains("GET:")) {
                    out.println(userInput);

                    System.out.println(in.readLine());

                }


                //print ? every time that it waits for user input
                System.out.print("?" + " ");
            }
        }
        catch (SocketException e){
            System.out.println("Server closed connection");
        }

        out.close();
        in.close();
        stdIn.close();
        messageSocket.close();
    }
}
