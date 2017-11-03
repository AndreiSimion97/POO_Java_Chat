/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import seriac.poo.server.config.ServerConfig;
import seriac.poo.server.exceptions.InvalidFormatException;
import seriac.poo.server.exceptions.MissingKeyException;
import seriac.poo.server.exceptions.UnknownKeyException;

/**
 *
 * @author asimion
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        // TODO code application logic here
        //PrivateMessage ob1 = new PrivateMessage("Matache","Simion","Ce faci?");
        //System.out.println(ob1.toString());
        //System.out.println(ob1.getRecipent());
        try {
            ServerConfig s = new ServerConfig();
            System.out.println("Portul TCP: " + s.getTcpPort());
            System.out.println("Numarul maxim de utilizatori: " + s.getMaxClients());
        } catch (IOException e){
            System.out.println("Fisierul nu exista/ nu poate fi deschis!");
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        } catch (UnknownKeyException e) {
            System.out.println(e.getMessage());
        } catch (MissingKeyException e) {
            System.out.println(e.getMessage());
        }

    }

}
