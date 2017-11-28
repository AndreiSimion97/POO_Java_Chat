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
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.ObjectOutputStream;

/**
 *
 * @author asimion
 */
public class ServerPeer implements Runnable {

    private final Socket mSocketConnected;
    private  Server mServer;
    private  String mUsername;
    //private  DataInputStream mDIS;
    //private ObjectInputStream mOIS;
    private  DataOutputStream mDOS;
    private ObjectOutputStream mOOS;
   
    /**
     * Constructor class ServerPeer
     *
     * @param connected reprezinta Socket-ul pe care e connectat clientul
     */
    public ServerPeer(Server server, Socket socket) throws IOException {
        mSocketConnected = socket;
        mServer = server;
        mDOS = new DataOutputStream(mSocketConnected.getOutputStream());
        mOOS = new ObjectOutputStream(mDOS);
    }

    /**
     * Method run() - deserializeaza obiecte de tip Message
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */

    @Override
    public void run() {
       try{
            Message message;//creez o referinta la un obiect de tip Messae
            DataInputStream _dis = new DataInputStream(mSocketConnected.getInputStream());
            ObjectInputStream _oos = new ObjectInputStream(_dis);
            while(true){
                message = (Message) _oos.readObject();//citesc de pe strimul de iersire a serverului un mesaj
                mUsername = message.getSender();//extrag expeditorul
                System.out.println(message.toString());//afisez mesajul
                if(message instanceof PrivateMessage){//verific daca este mesaj privat sau nu
                    sendMessage(message);//serializez mesajul
                    mServer.dispatch(message);//trimit unde trebuie mesajul
                }
                else{
                    mServer.dispatch(message);//trimit mai departe mesajul
                }
            }
        } catch (EOFException e){
            mServer.removeClient(this);//sterg clientul daca nu mai e loc pe server
        } catch (IOException e){
            mServer.removeClient(this);//sterg clientul daca nu s-a putut conectat
            System.err.println("Conexiunea clientului resetata: " + e.getMessage());
        } catch (ClassNotFoundException e){
            System.err.println("Obiect necunoscut primit!");
        }
    }

    public void sendMessage(Message message) throws IOException {
        mOOS.writeObject(message);
    }

    public Socket getSocket(){
        return this.mSocketConnected;
    }   
 
    public ObjectOutputStream getOOS(){
        return this.mOOS;
    }
    
    public String getUsername() {
        return this.mUsername;
    }
}
