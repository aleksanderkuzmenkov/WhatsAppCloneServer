package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConnect extends Remote {

    String getUserIdFromPhonNumber(String phoneNumber) throws RemoteException;

    String[] getContacts(String phoneNumber) throws RemoteException;

    String getChats(String phoneNumber) throws RemoteException;

    String sendMessage(String userFromNumber, String userToNumber, String message) throws RemoteException;

    String getUserNumberFromName(String username) throws Exception;

}
