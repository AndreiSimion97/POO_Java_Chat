/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.server;

import chat.Message;
import chat.PrivateMessage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import seriac.poo.server.config.ServerConfig;
import seriac.poo.server.exceptions.InvalidFormatException;
import seriac.poo.server.exceptions.MissingKeyException;
import seriac.poo.server.exceptions.UnknownKeyException;

/**
 *
 * @author asimion
 */
public class Server {

    ArrayList<ServerPeer> clientList = new ArrayList();
    private ServerSocket mServerSocket;
    private final int MAX_CLIENTS;

    public Server(int tcpPort, int maxClients) throws IOException {
        try {
            this.mServerSocket = new ServerSocket(tcpPort);
        } catch (IOException e) {

        }
        this.MAX_CLIENTS = maxClients;
    }

    /**
     * Method main - reprezinta de unde se porneste server-ul TCP
     *
     * @param args
     * @throws IOException - arunca exceptia daca a esuat conexiunea
     * @throws UnknownKeyException - arunca exceptie daca in fisierul de
     * configurare exista o cheie necunoscta
     * @throws MissingKeyException - arunca exceptie daca lipsete o cheie in
     * fisierul de configurare
     * @throws InvalidFormatException - arunca excetie daca formatul datelor din
     * fisier e invalid
     * @throws ClassNotFoundException - arunca exceptie daca clasa nu exista
     */
    public static void main(String args[]) throws IOException, UnknownKeyException, MissingKeyException, InvalidFormatException, ClassNotFoundException {

        try {
            ServerConfig objectConfig = new ServerConfig();
            Server serverMain = new Server(objectConfig.getTcpPort(), objectConfig.getMaxClients());
            serverMain.listen(serverMain);
        } catch (IOException e) {
            System.out.println("Conexiunea s-a oprit!");
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        } catch (UnknownKeyException e) {
            System.out.println(e.getMessage());
        } catch (MissingKeyException e) {
            System.out.println(e.getMessage());
        }

    }

    public void listen(Server server) throws IOException {
        while (true) {
            if (clientList.size() < this.MAX_CLIENTS) {
                ServerPeer client = new ServerPeer(server, mServerSocket.accept());
                clientList.add(client);
                (new Thread(client)).start();
            }
        }
    }

    public synchronized void dispatch(Message mess) {
        for (ServerPeer client : clientList) {
            if (mess instanceof PrivateMessage) {
                if (client.getUsername().equals(((PrivateMessage) mess).getRecipient())) {
                    try {
                        ObjectOutputStream objectStream = client.getOOS();
                        objectStream.writeObject(mess);
                    } catch (Exception e) {

                    }
                }
            } else {
                try {
                    ObjectOutputStream objectStream = client.getOOS();
                    objectStream.writeObject(mess);
                } catch (Exception e) {

                }
            }
        }
    }

    
    public synchronized void removeClient(ServerPeer serverPeer) {
       clientList.remove(serverPeer);
    }
    
}
