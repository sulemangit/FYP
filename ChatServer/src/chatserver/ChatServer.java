/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author RMS
 */
public class ChatServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       MarkovChain markovChain = new MarkovChain();
      
        try{
        //Getting IP address of local machine
        InetAddress LocalAddress = InetAddress.getLocalHost();
        //Creating Socket Address Local Host + Local Listener Port
        InetSocketAddress serverSockAddress = new InetSocketAddress(LocalAddress, 4444);
        int concurrent_clients = 5;

        //creating a socket for Server/Listener
        ServerSocket serverSocket = new ServerSocket();
        //binding a Server socket
        serverSocket.bind(serverSockAddress, concurrent_clients);
        System.out.println("Server ready to Listen at : "
                + serverSocket.getLocalSocketAddress());

        //client socket
        Socket clientSocket = null;
        // waiting a client and then accept the connection
        clientSocket = serverSocket.accept();
        System.out.println("Request Received From client : "
                + clientSocket.getRemoteSocketAddress());
        String end = "";
        DataOutputStream output;
        output = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream input;
        input = new DataInputStream(clientSocket.getInputStream());
        String training_data = FilesUtil.readTextFile("hello.txt");
        String message= "";
        while(!end.equalsIgnoreCase("quit")){
        // preparing for data receiver
        message = input.readUTF();
        
        System.out.println("Data Received (Server): " + message);
        if(message.equalsIgnoreCase("quit")){
        end = "quit";
        continue;
        }
        // preparing for data sender
        
        markovChain.setText(training_data);
        markovChain.generate_words_list();
        markovChain.gram_by_word();
        message = markovChain.markov_by_word(message);
//        System.out.println("sending data (Server): " + message);
        output.writeUTF(message);
        
        }
        //closing objects
        input.close();
        output.close();
        clientSocket.close();
        serverSocket.close();
       }
       catch(Exception e){}

    }


}
