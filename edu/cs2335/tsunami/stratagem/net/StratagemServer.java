/*
 * Created on Nov 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.cs2335.tsunami.stratagem.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

import edu.cs2335.tsunami.stratagem.kernel.Player;


/**
 * @author Tim Liu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class StratagemServer extends Thread {

    /** Public constant port number */
    public static final int PORT = 1234;

    /** Constant Maximum Users */
    public static final int MAXUSERS = 4;

    /** ServerSocket */
    private ServerSocket serverSocket;

    /**object out*/
    private ObjectOutputStream objOut;

    /** User connection Threads */
    private static LinkedList userThreads;

    /** Commands Executed */
    private LinkedList savedCommands;

    /** Game State */
    private ServerState gameState;

    /** Server Command */
    private ServerCommandProcessor serverCommand;

    /** ServerAddress */
    private String myAddress;

    /** Time Game Began */
    private static long initTime;

    /** Boolean Finished waiting for connections */
    private boolean finishedWaitingForConnection;

    /** Finished Program */
    private boolean finishedGame;

    /** pausing */
    private boolean paused;

    /** record mode */
    private boolean record;

    /**
     * Stratagem Server Constructor
     */
    public StratagemServer() {
        /** boolean flags */
        paused = false;
        record = false;

        /** init timer */
        initTime = 0;

        /** UserThreads */
        userThreads = new LinkedList();

        /** Commands */
        savedCommands = new LinkedList();

        gameState = new ServerState();

        /** Server command */
        serverCommand = new ServerCommandProcessor(this);

        /** Starting new Server */
        try {
            serverSocket = new ServerSocket(PORT);

            /** ServerAddress */
            myAddress = serverSocket.getInetAddress().getHostName();
        } catch (IOException e) {
            sendToAll("ERROR");
        }

        /** Starting the StratagemServer Thread */
        this.start();
    }

    /**
     * constructor
     * @param s state
     */
    public StratagemServer(ServerState s) {
        /** boolean flags */
        paused = false;
        record = false;

        /** init timer */
        initTime = 0;

        /** UserThreads */
        userThreads = new LinkedList();

        /** Commands */
        savedCommands = new LinkedList();

        gameState = s;

        /** Server command */
        serverCommand = new ServerCommandProcessor(this);

        /** Starting new Server */
        try {
            serverSocket = new ServerSocket(PORT);

            /** ServerAddress */
            myAddress = serverSocket.getInetAddress().getHostName();
        } catch (IOException e) {
            sendToAll("ERROR");
        }

        /** Starting the StratagemServer Thread */
        this.start();
    }

    /**
     * run method in the StratagemServer
     */
    public void run() {
        waitForPlayers();
        while (userThreads.size() > 0) {
            try {
                this.sleep(3000);
            } catch (Exception e) {
                sendToAll("ERROR");
            }
            if (!paused) {
                gameState.incrementDate();
                sendToAll("UPDATERESOURCES");
            }
        }
    } //run thread

    /**
     * waiting for players to connect
     */
    public void waitForPlayers() {
        Socket s;
        UserThread currentUser;
        try {
            while ((s = serverSocket.accept()) != null) {
                if (userThreads.size() < gameState.getPlayers().size()) {
                    currentUser = new UserThread(this, s, gameState.getPlayer(
                            userThreads.size()).getPlayerID());
                } else if (userThreads.size() == gameState.getPlayers().size()
                        && gameState.getOrdersProcessed().size() != 0) {
                    continue;
                } else {
                    currentUser = new UserThread(this, s, gameState
                            .getPlayerIdGen());
                }
                //System.out.println("Client " + currentUser.getUserId()
                        //+ " Address:" + currentUser.getUserAddressName()
                        //+ " Connected");
                currentUser.setDaemon(true);
                userThreads.add(currentUser);
                currentUser.start();
            }
        } catch (SocketException e) {
            initTime = System.currentTimeMillis();
        } catch (Exception e) {
            sendToAll("ERROR");
        }
    }

    /**
     * sendToAll function that send a command and user Id to all users
     * 
     * @param line
     *            is a tring command
     */
    public final void sendToAll(String line) {
        for (int i = 0; i < userThreads.size(); i++) {
            UserThread currentU = (UserThread) userThreads.get(i);
            //System.out.println("Sending |" + line + "| to Player" + i);
            currentU.printline(line);
        }
    }

    /**
     * send to all except one user
     * @param usrId id
     * @param line command
     */
    public void sendToAllExcept(int usrId, String line) {
        for (int i = 0; i < userThreads.size(); i++) {
            UserThread currentU = (UserThread) userThreads.get(i);
            if (currentU.getUserId() != usrId) {
                currentU.printline(line);
            }
        }
    }

    /**
     * finds user by Id
     * 
     * @param id
     *            is an int that is a user's id
     * @return a userThread
     */
    public UserThread getUser(int id) {
        for (int i = 0; i < userThreads.size(); i++) {
            if (((UserThread) userThreads.get(i)).getUserId() == id) {
                return ((UserThread) userThreads.get(i));
            }
        }
        return null;
    }

    /**
     * getting user threads
     * 
     * @return userThreads which are the user thread
     */
    public LinkedList getUserThreads() {
        return userThreads;
    }

    /**
     * set finished
     * 
     * @param f
     *            is a boolean
     */
    public void setFinished(boolean f) {
        finishedGame = f;
    }

    /**
     * return finishedGame
     * 
     * @return finishedGame
     */
    public boolean getFinishedGame() {
        return finishedGame;
    }

    /**
     * returns gameState
     * 
     * @return gamstate
     */
    public ServerState getState() {
        return gameState;
    }

    /**
     * set gameState
     * 
     * @param s
     *            is the gamestate
     */
    public void setState(ServerState s) {
        gameState = s;
    }

    /**
     * addsaved command
     * @param s string
     */
    public void addSavedCommands(String s) {
        savedCommands.add(s);
    }

    /**
     * setFinished waiting sets a boolean flag
     * 
     * @param w
     *            is a passed in boolean
     */
    public void setFinishedWaitingForConnection(boolean w) {
        finishedWaitingForConnection = w;
    }

    /** 
     * get socket
     * @return socket
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * getInittime
     * @return time
     */
    public long getInitTime() {
        return initTime;
    }

    /**
     * return address
     * @return address string
     */
    public String getAddress() {
        return myAddress;
    }

    /**
     * started?
     * @return bool
     */
    public boolean started() {
        if (initTime == 0) {
            return false;
        }
        return true;
    }

    /**
     * Proccess a command
     * 
     * @param cmd
     *            is a command String
     * @param currentId
     *            is a userString
     */
    public void processCommand(String cmd, int currentId) {
        serverCommand.processCommand(cmd, currentId);
    }

    /**
     * sendgameState
     * @param u to user
     */
    public void sendGameState(UserThread u) {
        try {
            this.sleep(100);
            u.getUserSocket().getOutputStream().flush();
            objOut = new ObjectOutputStream
            (u.getUserSocket().getOutputStream());
            objOut.writeObject(gameState);
            objOut.flush();
        } catch (Exception e) {
            u.printline("ERROR");
        }

    }

    /**
     * serverDisconnect
     *
     */
    public void serverDisconnect() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            sendToAll("ERROR");
        }
        userThreads.clear();
    }

    /**
     * pause server
     *
     */
    public void pause() {
        paused = !paused;
    }

    /**
     * update Resources
     *
     */
    public void updateResources() {
        sendToAll("UPDATERESOURCES ");
    }

    /**
     * Saves the CurrentState
     * @param filename is file
     * @return bool
     */
    public boolean saveState(String filename) {
        try {
            FileOutputStream out = new FileOutputStream(filename);
            ObjectOutputStream s = new ObjectOutputStream(out);
            s.writeObject(gameState);
            s.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * return object out
     * @return out stream
     */
    public ObjectOutputStream getObjStream() {
        return objOut;
    }
    
    /**
     * is ready
     * @return bool
     */
    public boolean isReady() {
        for (int i = 1; i < gameState.getPlayers().size(); i++) {
            Player p = (Player) gameState.getPlayer(i);
                if (!p.getReady()) {
                    return false;
                }
        }
        return true;
    }
    
    /**
     * server command
     * @return server command
     */
    public ServerCommandProcessor getCommand() {
        return serverCommand;
    }

}

