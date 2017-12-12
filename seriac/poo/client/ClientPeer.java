/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.client;

import java.net.Socket;
import chat.*;//importez de unde am creat clasele Message si PrivateMessage
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author asimion
 */
public class ClientPeer implements Runnable {

    /**
     * Campuri privte ce definesc clasa ClientPeer
     */
    private final Socket mSocketClient;
    private  String mSender;
    private static DataOutputStream mDOS;
   // private static DataInputStream mDIS;
    private  ObjectOutputStream mOOS;
    //private  ObjectInputStream mOIS;
      DataInputStream dis ;
            ObjectInputStream ois ;
            JTextArea JTA;
    /**
     * Constructor class ClientPeer
     *
     * @param sender String care reprezinta expeditorul
     * @param connectedClient Socket pe care este coneatat un client
     */
    public ClientPeer(String sender, Socket socket) throws IOException {
        mSender = sender;
        mSocketClient = socket;
        mDOS = new DataOutputStream(mSocketClient.getOutputStream());
        mOOS = new ObjectOutputStream(mDOS);
        //mDIS = new DataInputStream(mConnectedClient.getInputStream());
        dis = new DataInputStream(mSocketClient.getInputStream());
        ois = new ObjectInputStream(dis);
        //mOIS = new ObjectInputStream(mDIS);
    }

    @Override
    public void run() {
        try{    
            while(true){
                //deserialozarea mesaj
               // System.out.println(ois.readObject().toString().trim());
               setOutputPane(JTA);
               JTA.append(ois.readObject().toString().trim() + "\n");
            }
        }catch(IOException | ClassNotFoundException e){
            
        }
    }

    /**
     * Method sendMessage Serializeaza pe Socket obiecte de tip Message
     *
     * @param message String ce reprezinta mesajul trimis
     * @throws IOException atunci cand nu poate/are permisiunea de a scrie pe
     * socket
     */
    public void sendMessage(String message) throws IOException {
        Message _message = new Message(mSender, message);
        
        mOOS.writeObject(_message);
    }

    /**
     * Method sendMessage Serializeaza pe Socket obiecte de tip PrivateMessage
     *
     * @param message String ce reprezinta mesajul trimis
     * @param recipient String ce reprezinta destinatarul
     * @throws IOException atunci cand nu poate/are permisiunea de a scrie pe
     * socket
     */
    public  void sendMessage(String message, String recipient) throws IOException {
        PrivateMessage _privateMessage = new PrivateMessage(recipient, mSender, message);
        
        mOOS.writeObject(_privateMessage);
    }

   
    public void setUsername(String usr){
        mSender = usr;
    }
    
    public void setOutputPane(JTextArea jta) throws IOException, ClassNotFoundException{
        JTA = jta;
    }
   
}
