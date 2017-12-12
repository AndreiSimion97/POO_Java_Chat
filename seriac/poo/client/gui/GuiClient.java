/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seriaf.poo.client.gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import seriac.poo.client.ClientPeer;

/**
 *
 * @author asimion
 */
public class GuiClient extends JFrame {

    private ClientPeer mCurrentUser;
    private JPanel mainPanel;
    private JLabel mSenderNameLabel;
    private JTextField mSenderTextField;
    private JButton mChangeSenderButton;
    private JTextArea mMessageDisplayArea;
    private JTextField mMessageTextField;
    private JButton mSendMessageButton;
    private JScrollPane mMessageAreaScrollPane;
    private BoxLayout mLayout;
    private Socket mSocket;
    public GuiClient(Socket socket, String user) throws IOException, ClassNotFoundException {
        super("Chat Client");
        mSocket = socket;
        initComponent();
        mCurrentUser = new ClientPeer(user, mSocket);
        mCurrentUser.setOutputPane(mMessageDisplayArea);
    }

    private void initComponent() {

        JPanel mainPanel = new JPanel();
        mLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(mLayout);
        add(mainPanel);
        
        ///top panel
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        mSenderNameLabel = new JLabel("Name:");
        mSenderTextField = new JTextField(20);
        mChangeSenderButton = new JButton("Set Name");
        mChangeSenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               
                mCurrentUser.setUsername(mSenderTextField.getText());
            }
        });
        
        top.add(mSenderNameLabel);
        top.add(mSenderTextField);
        top.add(mChangeSenderButton);
        top.setVisible(true);
        
        ///mid panel
        JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        mMessageDisplayArea = new JTextArea(20, 51);
        mMessageDisplayArea.setEditable(false);
        mMessageAreaScrollPane = new JScrollPane(mMessageDisplayArea);
       
        mid.add(mMessageAreaScrollPane);
        mid.setVisible(true);
        
       //bottom panel
       JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
       
       mMessageTextField = new JTextField(40);
       mSendMessageButton = new JButton("Send");
       mSendMessageButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String line = mMessageTextField.getText();
                if (line.startsWith("/w")) {
                    try {
                        String[] information = line.split(" ");
                        
                        mCurrentUser.sendMessage(Concatenate(information, 2), information[1]);
                    } catch (IOException ex) {
                        Logger.getLogger(GuiClient.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    if (!line.equals("/q")) {

                        try {
                            mCurrentUser.sendMessage(line);
                        } catch (IOException ex) {
                            Logger.getLogger(GuiClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            mSocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(GuiClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       System.exit(0);
                    }
                }
            }
       });
       bottom.add(mMessageTextField);
       bottom.add(mSendMessageButton);
       bottom.setVisible(true);
       
        mainPanel.add(top);
        mainPanel.add(mid);
        mainPanel.add(bottom);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket clientSocket = new Socket("127.0.0.1", 9000);
        GuiClient gc = new GuiClient(clientSocket, "guest");
        gc.display();
    }

    void display() {
        (new Thread(mCurrentUser)).start();
        setSize(640, 480);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private String Concatenate(String[] arr, int index) {
        String message = "";
        for (int i = index; i < arr.length; i++) {
            message = message + (arr[i] + " ");
        }
        return message;
    }
}
