package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Connect extends UnicastRemoteObject implements IConnect{

    Connection con = null;
    private String url = "jdbc:oracle:thin:@dbserver2.bg.bib.de:1521:ora10";
    private String login = "bbm3h17mku";
    private String password = "Kukuruza456";
    private Statement stmt = null;
    private ResultSet resultSet =  null;
    private Map<String, String> dbResult = new HashMap<>();

    private String userId;

    public Connect() throws RemoteException{
        try {
            con = DriverManager.getConnection(url, login, password);
            stmt = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String getUserIdFromPhonNumber(String phoneNumber) throws RemoteException {

        String query = "SELECT id FROM CHAT_USERS WHERE phone_number = " + phoneNumber;


        String userID = null;
        try {
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                userId = resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userId;
    }

    @Override
    public String[] getContacts(String phoneNumber) throws RemoteException {

        String query = "SELECT CHAT_USERS.NAME, CHAT_USERS_RELATIONS.USER_TO_ID\n" +
                "FROM CHAT_USERS\n" +
                "INNER JOIN CHAT_USERS_RELATIONS\n" +
                "ON CHAT_USERS.ID = CHAT_USERS_RELATIONS.USER_TO_ID\n" +
                "WHERE CHAT_USERS_RELATIONS.USER_FROM_ID = " + this.userId;

        ArrayList<String> contactsList = new ArrayList<>();
        try{
            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                contactsList.add(resultSet.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String[] contacts = new String[contactsList.size()];
        for (int i = 0; i < contactsList.size() ; i++) {
            contacts[i] = contactsList.get(i);
        }

        return contacts;
    }

    @Override
    public String getChats(String phoneNumber) throws RemoteException {

        return "LOH";
    }

    @Override
    public String sendMessage(String userFromNumber, String userToNumber, String message) throws RemoteException {

        // --- TODO Exception ---
        String query = "INSERT INTO CHAT_MESSAGING " +
                "(USER_FROM_ID, USER_TO_ID, MESSAGE)" +
                "VALUES" +
                "(" +getUserIdFromPhonNumber(userFromNumber) + ", " +  getUserIdFromPhonNumber(userToNumber) + ", " + "'" + message + "'" + ")";

        try {
            stmt.executeUpdate(query);

            System.out.println("Nachrincht wurde verschickt");
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
        return null;
    }

    public String getUserNumberFromName(String username) throws Exception {

        String query = "SELECT PHONE_NUMBER FROM CHAT_USERS WHERE NAME = " + "'" +username +"'";

        resultSet = stmt.executeQuery(query);
        String phoneNumber = null;
        while (resultSet.next()){
            phoneNumber = resultSet.getString(1);
        }

        System.out.println(phoneNumber);

        return phoneNumber;
    }


}
