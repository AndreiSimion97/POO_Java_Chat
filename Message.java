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
 public class Message {
    final private String mNumeExpeditor;
    final private String mContinut;
    /**
    *Constructorul clasei Message
    * 
    * @param expeditor  numele expeditorului mesajului
    * @param continut  continutul mesajului
    */
    public Message(String expeditor, String continut){
        mNumeExpeditor = expeditor;
        mContinut = continut;
    }
    
    /**
    *
    *Produce si intoarce un obiect de tip String ce va fi afisat in fereastra de chat al utililizatorului 
    * 
    * @return mesajul formatat sub forma "expeditor: continut"
    */
    @Override
    public String toString(){
        return this.mNumeExpeditor + ": " + this.mContinut; 
    }
    
    /**
     *Metoda ce permite accesul la numele expeditorului
     * @return nume expeditor
     */
    public String getExpeditorName(){
        return mNumeExpeditor;
    }
    
    /**
     *Metoda ce permite accesul la continutul mesajului
     * @return continut
     */
    public String getMessage(){
        return mContinut;
    }
    
}
