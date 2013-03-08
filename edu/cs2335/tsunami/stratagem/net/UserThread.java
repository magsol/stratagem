/*
 * Created on Oct 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.net;

import java.net.Socket;
//import java.net.*;
//import java.io.*;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//import java.util.*;

/**
 * @author gtg835p
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class UserThread extends Thread {

    /** User's socket */
    private Socket userSocket;

    /** Out stream */
    private PrintWriter out;

    /** In Stream */
    private BufferedReader in;

    /** reference to the main server */
    private StratagemServer serverMain;

    /** User's ip address name */
    private String userAddressName;

    /** User's Id */
    private int userId;

    /** Boolean to tell when people start connecting */
    private boolean connected;

    /**
     * UserThread constructor
     * 
     * @param sm
     *            is an instance of the main server
     * @param s
     *            is the User's socket
     * @param id
     *            is the user's id
     */
    public UserThread(final StratagemServer sm, final Socket s, final int id) {
        serverMain = sm;
        userSocket = s;
        userId = id;
        userAddressName = userSocket.getInetAddress().getHostName();

        try {
            out = new PrintWriter(new OutputStreamWriter(userSocket
                    .getOutputStream()));
            in = new BufferedReader(new InputStreamReader(userSocket
                    .getInputStream()));
            connected = true;
        } catch (Exception e) {
            printline("ERROR");
        }
        printline("PLAYERID " + userId);
    }

    /** Thread's run method */
    public final void run() {
        String line = null;

        while (connected) {
            try {
                if ((line = in.readLine()) != null) {
                    //System.out.println("Server: waiting to read lines");
                    serverMain.processCommand(line, userId);
                    this.sleep(100);
                }
            } catch (Exception e) {
                //System.out.println("Problem with readline");
                //e.printStackTrace();
                connected = false;
            }

            if (!connected) {
                closeConnection();
            }
            line = null;
        } //end while
        //System.out.println("Server-side client thread died");
    } //end run

    /**
     * closes connection from Server to User
     */
    public void closeConnection() {
        try {
            //System.out.println("User " + userId + " is being disconnected");
            out.close();
            in.close();
            userSocket.close();
            connected = false;
        } catch (Exception e) {
            //System.out.println("Connection could not be disconnected");
            connected = true;
        }
    }

    /**
     * print a line to the user
     * 
     * @param l
     *            is a string
     */
    public final void printline(final String l) {
        //System.out.println("Server Sending: " + l);
        out.println(l);
        out.flush();
    }

    /**
     * get the current conetion status
     * 
     * @return connected is the connection status
     */
    public final boolean getconnected() {
        return connected;
    }

    /**
     * set the connected boolean flag
     * 
     * @param c
     *            is the boolean being passed in
     */
    public void setConnected(boolean c) {
        connected = c;
        //System.out.println("Connected = " + connected);
    }

    /**
     * get the current user id
     * 
     * @return userId is the uer's id
     */
    public final int getUserId() {
        return userId;
    }

    /**
     * get user's computer name
     * 
     * @return name is the user's address name
     */
    public String getUserAddressName() {
        return userAddressName;
    }

    /**
     * get user socket
     * @return socket
     */
    public Socket getUserSocket() {
        return userSocket;
    }

}