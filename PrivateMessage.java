/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author asimion
 */
 final public class PrivateMessage extends Message {
     
    //final private String mExpeditor;
    //final private String mMesaj;
    final private String mDestinatar;
    public PrivateMessage(String destinatar, String expeditor, String mesaj){
       super(expeditor, mesaj);
       mDestinatar = destinatar;
     }
 
    @Override
    public String toString(){
        return "(priv)" + super.getExpeditorName() + ": " + super.getMessage();
    }
    
    public String getRecipent(){
        return mDestinatar;
    }
}
