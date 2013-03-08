/*
 * Created on Oct 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
//import java.util.LinkedList;

import edu.cs2335.tsunami.stratagem.StratagemMain;

/**
 * @author gtg835p TODO To change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class StratagemClient extends Thread {

    /** Client Socket Side */
    private Socket clientSocket;

    /** Client command */
    private ClientCommandProcessor clientCommand;

    /** Server Port number */
    private int serverPort;

    /** server address */
    private String address;

    /** Output Stream */
    private PrintWriter out;

    /** object out stream */
    private ObjectInputStream objIn;

    /** Input Stream */
    private BufferedReader in;

    /** Server name */
    private String fromName;

    /** Connection */
    private boolean connected;

    /**state*/
    private ClientState gameState;

    /** main*/
    private StratagemMain stratagemMain;

    /**
     * This is the cmdlinman constructor
     * 
     * @param add
     *            is a string
     * @param sm is main
     */
    public StratagemClient(final String add, StratagemMain sm) {
        stratagemMain = sm;
        this.address = add;
        this.serverPort = StratagemServer.PORT;
        connected = false;
        clientCommand = new ClientCommandProcessor(this, stratagemMain);

        try {

            clientSocket = new Socket(this.address, this.serverPort);
            connected = true;
        } catch (Exception e) {
            connected = false;
        }

        if (connected) {
            try {
                InetAddress source = clientSocket.getInetAddress();
                fromName = source.getHostName();

                out = new PrintWriter(new OutputStreamWriter(clientSocket
                        .getOutputStream()));
                in = new BufferedReader(new InputStreamReader(clientSocket
                        .getInputStream()));
                this.start();
            } catch (Exception e) {
                connected = false;
            }
        }

    } //end CommandLineManager constructor

    /**
     * This encodes a string and send it to a server
     * 
     * @param s
     *            is a string to encode
     */
    public final void sendToServer(final String s) {
      //if (stratagemMain.getState() != null) {
        //System.out.println("Client " + stratagemMain.getState().getPlayerId()
         //+ " sends: " + s);
      //}
        out.println(s);
        out.flush();

    }

    /** This is the run function */
    public final void run() {
        String line = null;

        while (connected) {
            if (line == null) {
                try {
                    line = in.readLine();
                    clientCommand.processCommand(line);
                    this.sleep(50);
                } catch (Exception e) {
                    //System.out.println(e);
                    connected = false;
                }
            }
            line = null;

        } //end while
        //System.out.println("Client: Client Thread died");
    } //end run

    /**
     *receive state
     */
    public void receiveGameState() {
        try {
            objIn = new ObjectInputStream(clientSocket.getInputStream());
            ServerState s = (ServerState) objIn.readObject();
            stratagemMain.setState(s);
            gameState.setPlayerId(stratagemMain.getPlayerId());
            sendToServer("STATERECEIVED");
        } catch (Exception e) {
            if (stratagemMain.isServer()) {
                stratagemMain.getServer().serverDisconnect();
            }
            disconnect();
        }
    }

    /**
     * get State
     * @return state
     */
    public ClientState getState() {
        return gameState;
    }

    /** 
     * set state
     * @param s state
     */
    public void setState(ClientState s) {
        gameState = s;
        clientCommand.setState(s);
    }

    /**
     * disconnect
     *
     */
    public void disconnect() {
        try {
            out.close();
            in.close();
            clientSocket.close();
            connected = false;
        } catch (Exception e) {
            sendToServer("ERROR");
        }
    }

    /**
     * get Command
     * @return processor
     */
    public ClientCommandProcessor getCommand() {
        return clientCommand;
    }

}

