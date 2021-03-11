package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IConnect extends Remote {

    String getUserIdFromPhonNumber(String phoneNumber) throws RemoteException;

    String[] getContacts(String phoneNumber) throws RemoteException;

    public HashMap<String, String> getChats(String userFromId, String userToId) throws Exception;

    String sendMessage(String userFromNumber, String userToNumber, String message) throws RemoteException;

    String getUserNumberFromName(String username) throws Exception;

}
