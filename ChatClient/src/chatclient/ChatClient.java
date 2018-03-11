/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author RMS
 */
public class ChatClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Getting IP address of a remote host
      try{
        InetAddress RemoteIPAddress = InetAddress.getLocalHost();
        //RemoteIPAddress=InetAddress.getByName("google.com");

        //Creating Socket Address Remote Host + Rempte Listener Port
        InetSocketAddress serverSockAddress = new InetSocketAddress(RemoteIPAddress, 4444);

        //Creating a Socket
        Socket clientSock = new Socket();
        //Connecting the remote server
        clientSock.connect(serverSockAddress);

        // preparing for data sender
        String end = "";
        DataInputStream input = new DataInputStream(clientSock.getInputStream());
        DataOutputStream output = new DataOutputStream(clientSock.getOutputStream());
        Scanner in = new Scanner(System.in);
        while (!end.equalsIgnoreCase("quit")) {
            String message = in.nextLine();

            if (message.equalsIgnoreCase("quit")) {
                end = message;
                continue;
            }
            System.out.println("sending data (Client) : " + message);
            output.writeUTF(message);

            // preparing for data receiver
            message = input.readUTF();
            System.out.println("data received  (Client ): " + message);
        }
        //closing objects
        input.close();
        output.close();
        clientSock.close();
      }
      catch(Exception e){}

    }

}
