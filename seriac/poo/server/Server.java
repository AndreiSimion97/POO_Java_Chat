/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import seriac.poo.server.config.ServerConfig;
import seriac.poo.server.exceptions.InvalidFormatException;
import seriac.poo.server.exceptions.MissingKeyException;
import seriac.poo.server.exceptions.UnknownKeyException;


/**
 *
 * @author asimion
 */
public class Server {
   /**
    * Method main - reprezinta de unde se porneste server-ul TCP 
    * @param args
    * @throws IOException - arunca exceptia daca a esuat conexiunea
    * @throws UnknownKeyException - arunca exceptie daca in fisierul de configurare exista o cheie necunoscta
    * @throws MissingKeyException - arunca exceptie daca lipsete o cheie in fisierul de configurare
    * @throws InvalidFormatException - arunca excetie daca formatul datelor din fisier e invalid
    * @throws ClassNotFoundException  - arunca exceptie daca clasa nu exista
    */
    public static void main (String args[]) throws IOException, UnknownKeyException, MissingKeyException, InvalidFormatException, ClassNotFoundException{
     try {
            ServerConfig objectConfig = new ServerConfig();
            ServerSocket tcpSocket = new ServerSocket(objectConfig.getTcpPort());
            Socket socket =  tcpSocket.accept();
            if(socket.isConnected()){
                ServerPeer srvPeer = new ServerPeer(socket);
                srvPeer.run();
            }
            
        } catch (IOException e){
            System.out.println("Conexiunea s-a oprit!");
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        } catch (UnknownKeyException e) {
            System.out.println(e.getMessage());
        } catch (MissingKeyException e) {
            System.out.println(e.getMessage());
        }

    }
    
}
