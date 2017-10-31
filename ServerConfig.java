/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriac.poo.server.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import seriac.poo.server.exceptions.*;
//import java.util.Properties;
//import java.io.InputStream;

/**
 *
 * @author asimion
 */
final public class ServerConfig {

    private int mTcp_Port;
    private int mMax_Clients;
    private String mConfFile;
    private String[] properties = {"TCP_PORT", "MAX_CLIENTS"};

    public ServerConfig(String confFileServer) throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        mConfFile = confFileServer;
        BufferedReader br = null;
        FileReader fr = new FileReader(mConfFile);
        br = new BufferedReader(fr);
        String line;
        String[] keysArray = new String[2];
        String[] valueArray = new String[2];
        int i = 0;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            } else {

                if (!line.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[0-9]+")) {
                    throw new InvalidFormatException("IvalidFormatException please check " + mConfFile);
                } else {
                    String[] arr = line.split("=");
                    String keyFound = arr[0].trim();
                    if (checkKey(keyFound)) {
                        keysArray[i] = keyFound;
                        valueArray[i] = arr[1].trim();
                        i++;
                    } else {
                        throw new UnknownKeyException("UnknownKeyException please check " + mConfFile);
                    }
                }
            }

        }

        if (!Arrays.asList(keysArray).containsAll(Arrays.asList(properties))) {
            throw new MissingKeyException("MissingKeyException please check " + mConfFile);
        }

        mTcp_Port = Integer.parseInt(valueArray[Arrays.asList(keysArray).indexOf("TCP_PORT")]);
        mMax_Clients = Integer.parseInt(valueArray[Arrays.asList(keysArray).indexOf("MAX_CLIENTS")]);

        br.close();
    }

    public ServerConfig() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
       this("/home/asimion/NetBeansProjects/Chat/src/seriac/poo/server/config/server.conf");
    }

    private boolean checkKey(String key) {
        boolean ans = false;
        for (String k : properties) {
            if (key.equals(k)) {
                ans = true;
            }
        }
        return ans;
    }

    public int getTcpPort() {
        return mTcp_Port;
    }

    public int getMaxClients() {
        return mMax_Clients;
    }

}
