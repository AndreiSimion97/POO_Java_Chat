/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.client;

import java.net.Socket;
import chat.*;//importez de unde am creat clasele Message si PrivateMessage
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 *
 * @author asimion
 */
public class ClientPeer {
       /**
        * Campuri privte ce definesc clasa ClientPeer
        */
    private Socket mConnectedClient;
    private String mSender;   
       
        /**
         * Constructor class ClientPeer
         * @param sender String care reprezinta expeditorul
         * @param connectedClient Socket pe care este coneatat un client
        */
        public ClientPeer(String sender,Socket connectedClient){
               mSender = sender;
               mConnectedClient = connectedClient;
        }
        /**
         * Method sendMessage
         * Serializeaza pe Socket obiecte de tip Message
         * @param message String ce reprezinta mesajul trimis
         * @throws  IOException atunci cand nu poate/are permisiunea de a scrie pe socket
         */
        public void sendMessage(String message) throws IOException{
            Message _message = new Message(mSender, message);
            DataOutputStream outToServer = new DataOutputStream(mConnectedClient.getOutputStream());
            ObjectOutputStream _objServerStream = new ObjectOutputStream(outToServer);
           _objServerStream.writeObject(_message);
        }
        /**
         * Method sendMessage 
         * Serializeaza pe Socket obiecte de tip PrivateMessage
         * @param message String ce reprezinta mesajul trimis
         * @param recipient String ce reprezinta destinatarul
         * @throws IOException atunci cand nu poate/are permisiunea de a scrie pe socket
         */
        public void sendMessage(String message, String recipient) throws IOException{
            PrivateMessage _privateMessage = new PrivateMessage(recipient, mSender, message);
            DataOutputStream outToServer = new DataOutputStream(mConnectedClient.getOutputStream());
            ObjectOutputStream _objServerStream = new ObjectOutputStream(outToServer);
            _objServerStream.writeObject(_privateMessage);
        }
        
}
