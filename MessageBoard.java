package CO2017.exercise3.ab817;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by aspol on 05/05/2017.
 */



//message board class
public class MessageBoard {


    Map<MessageHeader,String> messageBoard = new ConcurrentHashMap<>();
   MessageBoard(){






   }


    synchronized void SaveMessage(MessageHeader mh,
                     java.lang.String msg){

        messageBoard.put(mh,msg);


    }


    String getMessage(MessageHeader mh){






        return messageBoard.get(mh);




    }

    Set<MessageHeader> ListHeaders(){


            return       messageBoard.keySet();




    }




    }






