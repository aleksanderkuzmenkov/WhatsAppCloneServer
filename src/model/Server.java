package model;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) throws Exception {
        try{
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Remote remote = new Connect();

        Naming.rebind("//localhost:1099/echo1", remote);

        System.out.println("=================Chatserver started=================");
    }
}
