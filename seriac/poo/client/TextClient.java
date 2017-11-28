/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author asimion
 */
public class TextClient {

    /**
     * Metoda statica main care porneste TextClient ce se conecteaza pe portul
     * 9000 localhost '127.0.0.1' si serializeaza obiectele de tip Message si
     * PrivateMessage prin apeluri ale obictului de tip ClientPeer la metodele
     * sendMessage
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Username: ");
            String username = in.readLine();

            Socket clientSocket = new Socket("127.0.0.1", 9000);

            ClientPeer client = new ClientPeer(username, clientSocket);
           ( new Thread(client)).start();
            String line;
            do {

                line = in.readLine();

                if (line.contains("/w") && !line.equals("/w")) {
                    String[] information = line.split(" ");

                    client.sendMessage(Concatenate(information, 2), information[1]);

                } else {
                    if (!line.equals("/q")) {

                        client.sendMessage(line);
                    } else {
                        clientSocket.close();
                        break;
                    }
                }

            } while (!line.equals("/q"));
        } catch (IOException e) {

        }
    }

    /**
     * Method Concatenate - produce si intoarce un String reprezentat de mesajul
     * prin concatenarea cuvintelor ce au fost scrise
     *
     * @param arr reprezinta un array de String
     * @param index reprezinta indexul array-ului de unde se incepe concatenarea
     * @return message
     */
    private static String Concatenate(String[] arr, int index) {
        String message = "";
        for (int i = index; i < arr.length; i++) {
            message = message + (arr[i] + " ");
        }
        return message;
    }
}
