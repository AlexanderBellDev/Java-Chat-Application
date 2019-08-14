package CO2017.exercise3.ab817;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by aspol on 06/05/2017.
 */
public class MessageServerHandler extends Thread {

    //declare variables to use
    protected Socket clientSocket;
    MessageBoard messageBoard;
    int lettervar;
    char threadid;
    int idnum =1;



    //default constructor
    public MessageServerHandler (MessageBoard b, Socket c1, int l1)
    {
    lettervar = l1;
       messageBoard = b;
        clientSocket = c1;
        start();
    }

    //thread run method
    public void run()
    {

        //ip address
        InetAddress clientAddress = clientSocket.getInetAddress();
        System.out.println ("connection :" + " " + clientAddress);

        //try catch for io exception

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( clientSocket.getInputStream()));

            String inputLine;

            //while loop to detect input

            while ((inputLine = in.readLine()) != null)
            {




                //list method
                if (inputLine.equals("LIST")) {
                    System.out.println(inputLine);
               //new set to contain messageheaders
                    Set<MessageHeader> messageHeaders = new HashSet<>();

                    messageHeaders =  messageBoard.ListHeaders();
                    //send size of message headers to client
                    out.println(messageHeaders.size());
                    //iterate message headers to client
                   Iterator<MessageHeader> itr = messageHeaders.iterator();



                    //send it to the client
                    for(int i = 0;i<messageHeaders.size();i++){
                    out.println(itr.next());
                    }




                }


                //ends the loop and disconnects
                if (inputLine.equals("BYE")) {
                    System.out.println(inputLine);
                    break;
                }






                //send method
                if (inputLine.contains("SEND:")) {


                    String messagefull = inputLine;
                    String messagebody;
                    messagebody = messagefull.substring(5);



                    // A - 65  Z - 90 - assigns letter to user
                    for(int i=lettervar;i<=lettervar;i++) {
                        threadid = (char)i;
                    }




                    MessageHeader messagehead = new MessageHeader(threadid,idnum);

                    messageBoard.SaveMessage(messagehead,messagebody);



                    String messageprotocol = messagefull.substring(0,4);

                    System.out.println(messageprotocol+":"+idnum+":"+messagebody);
                    idnum++;




                }

                //get message method
                if (inputLine.contains("GET:")){

                    String messagehead = inputLine;
                    // substring methods to extract each bit of data

                    System.out.println(messagehead);
                    messagehead  = messagehead.substring(4);


                    //extract letter
                    String messagethread = messagehead.substring(0,1);
                    char messagethreadchar = messagethread.charAt(0);
                    //extract id
                    String messageid = messagehead.substring(2);
                    int messageidint = Integer.parseInt(messageid);

                    // build message header according to those parameters
                    MessageHeader mh = new MessageHeader(messagethreadchar,messageidint);

                    //message board message extracted
                    if(messageBoard.getMessage(mh) == null){
                        out.println("No such message");
                    }

                        String message = messageBoard.getMessage(mh);


                    // messageboard contents has content
                        if(messageBoard.getMessage(mh) !=null){
                            out.println(message);
                            System.out.println(messagethread+"+"+messageid+"="+message);
                            System.out.println("OK");
                        }
                    //message board no content
                    if(messageBoard.getMessage(mh) == null){
                        System.out.println(messagethread+"+"+messageid+"="+message);
                        System.out.println("ERR");
                    }











                }








            }

            out.close();
            in.close();
            clientSocket.close();

        }
        catch (IOException e)
        {
            System.err.println("Connection dropped unexpectedly.");

        }


    }

}
