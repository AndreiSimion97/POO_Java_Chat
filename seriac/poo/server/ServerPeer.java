/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import chat.*;
/**
 *
 * @author asimion
 */
public class ServerPeer {
    private Socket mConnected ;
    private DataInputStream mDIS;
    private ObjectInputStream mOIS;
    /**
     * Constructor class ServerPeer
     * 
     * @param connected reprezinta Socket-ul pe care e connectat clientul 
     */
    public ServerPeer(Socket connected) throws IOException{
        mConnected = connected;
        mDIS = new DataInputStream(mConnected.getInputStream());
        mOIS = new ObjectInputStream(mDIS);
    }
    /**
     * Method run() - deserializeaza obiecte de tip Message
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void run() throws IOException, ClassNotFoundException{
        while(mConnected.isConnected()){
           // DataInputStream _in = new DataInputStream(mConnected.getInputStream());
            //ObjectInputStream _objIn = new ObjectInputStream(_in);
            Message _mObj = (Message)mOIS.readObject();
            System.out.println(_mObj.toString());   
        }
    }
    
}
