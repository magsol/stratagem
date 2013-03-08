/*
 * Created on Oct 21, 2004
 *
 */
package edu.cs2335.tsunami.stratagem.gui;

/*  java.applet.Applet
 java.applet.AudioClip */
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import edu.cs2335.tsunami.stratagem.StratagemMain;
import edu.cs2335.tsunami.stratagem.kernel.Army;
import edu.cs2335.tsunami.stratagem.kernel.Military;
import edu.cs2335.tsunami.stratagem.kernel.Navy;
import edu.cs2335.tsunami.stratagem.kernel.Planet;
import edu.cs2335.tsunami.stratagem.kernel.Player;
import edu.cs2335.tsunami.stratagem.net.ClientCommandProcessor;
import edu.cs2335.tsunami.stratagem.net.ClientState;
import edu.cs2335.tsunami.stratagem.net.StratagemClient;

/**
 * @author Chris Gray TODO Readying
 *  
 */
public class StratagemGUI implements Runnable, KeyListener, MouseListener {

    /** BufferedStrategy used */
    private BufferStrategy bufferStrategy;

    /** StratagemMain to pass back data to */
    private StratagemMain stratMain;

    /** StratagemClient to call commands on */
    private StratagemClient stratClient;

    /** ImageLoader used to load & create accelerated BufferedImages */
    private ImageLoader il;

    /** Holds predrawn components */
    private DrawComponent dc;

    /** The single component used */
    private Frame frame;

    /** Running boolean */
    private boolean running = true;

    /** Thread */
    private Thread thread;
    
    /** Scroll up image */
    private BufferedImage scrollUp;
    
    /** Scroll down image */
    private BufferedImage scrollDown;

    /** State of the game. Needed for drawing & interaction. */
    private ClientState state;

    /** Player */
    private Player player;

    /** Battle image */
    private BufferedImage battleImage;

    /** Image storage */
    private StoredImages store;

    /** Color of the chat text */
    private Color chatColor = new Color(120, 100, 180);

    /** Panel color */
    private Color panelColor = new Color(60, 60, 80);

    /** Title font */
    private Font titleFont = new Font("Arial", Font.BOLD, 26);

    /** Large font */
    private Font largeFont = new Font("Arial", Font.BOLD, 18);

    /** Small font */
    private Font smallFont = new Font("Arial", Font.BOLD, 12);

    /** Current frame we're drawing too */
    private Graphics drawG;

    /** Graphics Device */
    private GraphicsDevice device;

    /** Boolean for whether or not menu is displayed */
    private boolean showMenu = false;

    /** Boolean for whether or not confirmation is displayed */
    private boolean showConfirm = false;

    /** Boolean for whether or not pause is dispalyed */
    private boolean showPause = false;

    /** Current player chat input */
    private String playerChat = "";

    /** Player is currently using chat function */
    private boolean chat = false;

    /** Draws map with cities, armies, etc on it */
    private MapImage map;

    /** Start viewable map at this X */
    private int startX = 0;

    /** Start viewable map at this Y */
    private int startY = 0;

    /** X increment to scroll by */
    private int incX = 0;

    /** Y increment to scroll by */
    private int incY = 0;

    /** Planet to manipulate with planet box */
    private Planet selectedPlanet;

    /** Clicked planet */
    private Planet clickedPlanet;

    /** Military to manipulate in military box */
    private Military selectedUnit;
    
    /** Old unit for merge purposes */
    private Military oldUnit;

    /** Player has selected move */
    private boolean selectMove;

    /** Selected recruit/build */
    private boolean selectRecruit;

    /** Player has selected Shuttle */
    private boolean selectShuttle;

    /** Player has selected Raid */
    private boolean selectRaid;

    /** Player has selected Bombard */
    private boolean selectBombard;

    /** Display startup menu */
    private boolean startup = true;

    /** Display matchmaker screen */
    private boolean matchmaking;

    /** Display joinGame box */
    private boolean joinGame;

    /** Display color selection */
    private boolean selectColor;

    /** Show battle screen */
    private boolean showBattle;

    /** Player entering name */
    private boolean name;

    /** Display credits */
    private boolean showCredits;

    /** Make player enter name */
    private boolean enterName;

    /** Player name to be sent to server */
    private String playerName = "StratPlayer";

    /** Side name entered on matchmaking screen */
    private String sideName = "Trekks";

    /** Entry box string */
    private String entryString = "";

    /** Amount chat has been scrolled */
    private int scrollChat = 0;

    /** Show load game */
    private boolean loadGame;

    /** Show save game */
    private boolean saveGame;

    /** Show raid box */
    private boolean showRaid;

    /** Player has selected merge */
    private boolean selectMerge;
    
    /** Lock player action while connecting */
    private boolean lock;

    /**
     * Constructor
     * 
     * @param strat
     *            Main game
     */
    public StratagemGUI(StratagemMain strat) {
        stratMain = strat;
        stratClient = strat.getClient();
        frame = new Frame("Stratagem");
        frame.setSize(800, 600);
        
        device = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice();
//        if (device.isFullScreenSupported()) {
//            device.setFullScreenWindow(frame);
//               frame.setUndecorated(true);
//        }
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);
        frame.addWindowListener(new WindowAdapter() {

            /** embedded window listener */
            public void windowClosing(WindowEvent we) {
                running = false;
            }
        });
        frame.addKeyListener(this);
        frame.addMouseListener(this);

        il = new ImageLoader(device.getDefaultConfiguration());

        frame.setVisible(true);
        store = new StoredImages(il);
        dc = new DrawComponent(store, il, Color.blue);
        scrollUp = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/up.jpg");
        scrollDown = il.loadImage("edu/cs2335/tsunami/"
                + "stratagem/util/down.jpg");
        frame.createBufferStrategy(2);
        bufferStrategy = frame.getBufferStrategy();
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Handles key presses
     * 
     * @param e
     *            KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
      if (key == KeyEvent.VK_ENTER) {
            if (matchmaking || !startup || !showMenu || !showPause) {
                if (!chat && !name) {
                    chat = true;
                } else if (name) {
                    name = false;
                    stratClient.getCommand().sendSide(sideName);
                } else if (chat && !joinGame) {
                    if (playerChat.equals("")) {
                        chat = false;
                    } else {
                    stratClient.getCommand().chat(
                            player.getName() + ": " + playerChat);
                    chat = false;
                    playerChat = "";
                    }
                }
            }
      } else if (key == KeyEvent.VK_ESCAPE) {
            if (startup) {
                if (matchmaking && !chat) {
                    matchmaking = false;
                    chat = false;
                    playerChat = "";
                    stratClient.disconnect();
                    if (stratMain.isServer()) {
                        stratMain.getServer().serverDisconnect();
                    }
                } else {
                    joinGame = false;
                    showCredits = false;
                    playerChat = "";
                    enterName = false;
                    loadGame = false;
                }
            } else if (showMenu) {
                showMenu = false;
            } else {
                if (showConfirm) {
                    showConfirm = false;
                    showMenu = true;
                } else if (saveGame || showRaid) {
                    saveGame = false;
                    showRaid = false;
                    entryString = "";
                } else {
                    showMenu = true;
                    incY = 0;
                    incX = 0;
                }
            }
      } else if (key == KeyEvent.VK_PAUSE) {
            if (!startup && showPause) {
                stratClient.getCommand().pause();
            } else if (!startup) {
                stratClient.getCommand().pause();
            }
      } else if (key == KeyEvent.VK_DELETE 
              || key == KeyEvent.VK_BACK_SPACE) {
            if (!playerChat.equals("") && chat) {
                playerChat = playerChat.substring(0, (playerChat.length() - 1));
            } else if (!sideName.equals("") && name && matchmaking) {
                sideName = sideName.substring(0, (sideName.length() - 1));
            } else if (!playerName.equals("") && (name || enterName)) {
                playerName = playerName.substring(0, (playerName.length() - 1));
            } else if (!entryString.equals("")
                    && (loadGame || saveGame || showRaid)) {
                entryString = entryString.substring(0,
                        (entryString.length() - 1));
            } 
      } else if (key == KeyEvent.VK_SHIFT) {
        return;
      } else {
            if (chat && (playerChat.length() < 50)) {
                playerChat = playerChat + e.getKeyChar();
            } else if (name && matchmaking && (sideName.length() < 20)) {
                sideName = sideName + e.getKeyChar();
            } else if (name && joinGame && (playerName.length() < 20)) {
                playerName = playerName + e.getKeyChar();
            } else if (enterName && (playerName.length() < 20)) {
                playerName = playerName + e.getKeyChar();
            } else if ((loadGame || saveGame || showRaid)
                    && (entryString.length() < 20)) {
                entryString = entryString + e.getKeyChar();
            }
        }
    }

    /**
     * Handles key releases
     * 
     * @param e
     *            KeyEvent
     */
    public void keyReleased(KeyEvent e) {
        // Needed for implementation, not for game.
    }

    /**
     * Handles key types
     * 
     * @param e
     *            KeyEvent
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Handles mouse clicked events
     * 
     * @param e
     *            MouseEvent
     */
    public void mouseClicked(MouseEvent e) {
        //Unused
    }

    /**
     * Handles mouse entered events
     * 
     * @param e
     *            MouseEvent
     */
    public void mouseEntered(MouseEvent e) {
        incX = 0;
        incY = 0;
    }

    /**
     * Handles mouse exit events
     * 
     * @param e
     *            MouseEvent
     */
    public void mouseExited(MouseEvent e) {
        if (!(showMenu || showConfirm)) {
            if (e.getX() > 600) {
                incX = 20;
            } else if (e.getX() < 200) {
                incX = -20;
            }
            if (e.getY() > 450) {
                incY = 20;
            } else if (e.getY() < 150) {
                incY = -20;
            }
        }
    }

    /**
     * Handles mouse pressed events
     * 
     * @param e
     *            MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //System.out.println("Clicked X: " + x + " Y: " + y);
         if (chat && (x > 127) && (x < 139)) {
          if ((y > 499) && (y < 531)) {
            scrollChat++;
          } else if ((y > 569) && (y < 600)) {
            scrollChat--;
          }
          if (scrollChat == state.getChat().size()) {
            scrollChat = state.getChat().size() - 1;
            }
            if (scrollChat < 0) {
            scrollChat = 0;
            }
        } else if (startup) {
            checkStartupClick(x, y);
        } else if (showBattle) {
            if ((x > 380) && (x < 420) && (y > 350) && (y < 380)) {
                showBattle = false;
            }
        } else if (showMenu) {
            if ((x > 330) && (x < 470)) {
                if ((y > 220) && (y < 270)) {
                    showMenu = false;
                } else if ((y > 270) && (y < 320)) {
                    saveGame = true;
                    showMenu = false;
                } else if ((y > 320) && (y < 370)) {
                    //TODO New game
                    showMenu = false;
                    showConfirm = true;
                } else if ((y > 370) && (y < 420)) {
                    showMenu = false;
                    showConfirm = true;
                }
            }
        } else if (showConfirm) {
            if ((y > 370) && (y < 430)) {
                if ((x > 140) && (x < 340)) {
                    startup = true;
                    showRaid = false;
                    selectedPlanet = null;
                    selectShuttle = false;
                    showConfirm = false;
                    selectRaid = false;
                    selectMove = false;
                    chat = false;
                    lock = false;
                    playerChat = "";
                    stratClient.disconnect();
                    if (stratMain.isServer()) {
                        stratMain.getServer().serverDisconnect();
                    }
                } else if ((x > 420) && (x < 660)) {
                    showConfirm = false;
                    showMenu = true;
                }
            }
        } else if (saveGame || showRaid) {
            //TODO Save/Raid network
            if (saveGame) {
                if ((entryString != null) && (x > 370) && (x < 430)
                        && (y > 330) && (y < 360)) {
                    stratClient.getCommand().saveGame(entryString);
                    entryString = "";
                    saveGame = false;
                }
            } else if ((entryString != null) && (x > 370) && (x < 430)
                    && (y > 330) && (y < 360)) {
                showRaid = false;
                selectRaid = true;
            }
        } else if ((selectRecruit) && (x > 500) && (x < 670) && (y > 100)
                && (y < 318)) {
            selectRecruit = false;
            if ((y > 110) && (y < 140)) {
                if (selectedPlanet.hasBuilding("TRAININGACADEMY")) {
                    stratClient.getCommand().recruit(
                            selectedPlanet.getPlanetID(), "RAPTOR", 
                            selectedPlanet.getPopulation() / 1500);
                } else {
                    stratClient.getCommand().build(
                            selectedPlanet.getPlanetID(), "TRAININGACADEMY");
                }
            } else if ((y > 150) && (y < 210)) {
                if (selectedPlanet.hasBuilding("WARFACTORY")) {
                    if (y < 180) {
                        stratClient.getCommand().recruit(
                                selectedPlanet.getPlanetID(), "FRIGATE", 
                                selectedPlanet.getPopulation() / 3000);
                    } else {
                        stratClient.getCommand().recruit(
                                selectedPlanet.getPlanetID(), "RAGNAROK", 
                                selectedPlanet.getPopulation() / 3000);
                    }
                } else {
                    stratClient.getCommand().build(
                            selectedPlanet.getPlanetID(), "WARFACTORY");
                }
            }
//            if ((y > 220) && (y < 318)) {
//                if (selectedPlanet.hasBuilding("SHIPYARD")) {
//                    if (y < 250) {
//                        stratClient.getCommand().recruit(
//                                selectedPlanet.getPlanetID(), "SHADE", 
//                                selectedPlanet.getPopulation() / 2000);
//                    } else if (y < 280) {
//                        stratClient.getCommand().recruit(
//                                selectedPlanet.getPlanetID(), "DREADNAUGHT", 
//                                selectedPlanet.getPopulation() / 3000);
//                    } else {
//                        stratClient.getCommand().recruit(
//                                selectedPlanet.getPlanetID(), "OVERLORD", 
//                                selectedPlanet.getPopulation() / 4000);
//                    }
//                } else {
//                    stratClient.getCommand().build(
//                            selectedPlanet.getPlanetID(), "SHIPYARD");
//                }
//            }

        } else if (x > 670 && !showPause) {
            // Panel click. Check location and desired action.
            checkPanelClick(x, y);
        } else if (!showPause) {
            //User clicked on map area. Check click against planet locations
            clickedPlanet = checkPlanetHit(x + startX, y + startY);
            if (clickedPlanet != null) {
                if (selectMove) {
                    //TODO Shuttle commands.
                    if (selectedUnit instanceof Army) {
                        Army r = (Army) selectedUnit;
                        stratClient.getCommand().moveFlight(r.getMilitaryID(),
                                clickedPlanet.getPlanetID());
                    } else {
                        Navy r = (Navy) selectedUnit;
                        stratClient.getCommand().moveWormHole(
                                r.getMilitaryID(), clickedPlanet.getPlanetID());
                    }
                    selectMove = false;
                    selectedUnit = null;

                } else if (selectShuttle) {
                    //TODO Shuttle commands.
                    Army r = (Army) selectedUnit;
                    stratClient.getCommand().moveShuttle(r.getMilitaryID(),
                            clickedPlanet.getPlanetID());
                    selectShuttle = false;
                    selectedUnit = null;

                } else if (selectRaid) {
                    //TODO Raid commands.
                    if (clickedPlanet.getOwner().getPlayerID()
                            != player.getPlayerID()) {
                    stratClient.getCommand().isRaiding(
                            selectedUnit.getMilitaryID(),
                            clickedPlanet.getPlanetID(), "TRUE", entryString);
                    entryString = "";
                    selectRaid = false;
                    selectedUnit = null;
                    }
                } else if (selectBombard) {
                    //TODO Bombard commands.
                    stratClient.getCommand().isBombarding(
                            selectedUnit.getMilitaryID(),
                            clickedPlanet.getPlanetID(), "TRUE");
                    selectBombard = false;
                    selectedUnit = null;
                } else if (clickedPlanet.getOwner() != null) {
                    if (clickedPlanet.getOwner().getPlayerID()
                            == player.getPlayerID()) {
                    dc.setSelectedPlanet(clickedPlanet);
                    selectedPlanet = clickedPlanet;
                    selectedUnit = null;
                    }
                }
            }
        }
    }

    /**
     * Handles mouse clicks in start area
     * 
     * @param x
     *            x coordinate of mouse click
     * @param y
     *            y coordinate of mouse click
     */
    private void checkStartupClick(int x, int y) {
        if (joinGame) {
            if ((x > 290) && (x < 510)) {
                if ((y > 250) && (y < 280)) {
                    name = true;
                    chat = false;
                } else if ((y > 295) && (y < 325)) {
                    name = false;
                    chat = true;
                } else if ((x > 360) && (x < 420) && (y > 350) && (y < 380)) {
                  if (!lock) {
                    stratMain.joinGame(playerChat, playerName);
                    stratClient = stratMain.getClient();
                    lock = true;
                    }
                }
            }
        } else if (matchmaking) {
            if (x > 440) {
                if ((x > 640) && (x < 780)) {
                    if ((y > 480) && (y < 520)) {
                        //TODO QUIT code here
                        matchmaking = false;
                        chat = false;
                        playerChat = "";
                        sideName = "";
                        stratClient.disconnect();
                        if (stratMain.isServer()) {
                            stratMain.getServer().serverDisconnect();
                        }
                    } else if ((y > 540) && (y < 580) 
                      && stratMain.isServer()
                      && stratMain.getServer().isReady()
                      && !sideName.equals("")
                      && (selectedPlanet != null)) {
                        stratClient.getCommand().beginGame();
                        dc.setPlayerColor(player.getColor());
                    } else if ((y > 540) && (y < 580) 
                      && !stratMain.isServer()
                      && !sideName.equals("")
                      && (selectedPlanet != null)) {
                        stratClient.getCommand().ready();
                    }
                } else if ((x < 520) && (y > 120) && (y < 280)) {
                    if (!selectColor && (y < 160)) {
                        selectColor = true;
                    } else if (selectColor) {
                        setColor(y);
                    }
                } else if ((x > 540) && (x < 720) && (y > 120) && (y < 160)) {
                    name = true;
                } else if ((x > 410) && (x < 730) && (y > 180) && (y < 436)) {
                    checkMiniClick(x - 410, y - 180);
                }
            }
        } else if (enterName) {
            if ((playerName != null) && (x > 370) && (x < 430) && (y > 330)
                    && (y < 360)) {
                //TODO Host game code goes here.
              if (!lock) {
                stratMain.hostGame();
                stratClient = stratMain.getClient();
                lock = true;
                }
            }
        } else if (loadGame) {
            if ((entryString != null) && (x > 370) && (x < 430) && (y > 330)
                    && (y < 360)) {
                stratMain.loadGame(entryString);
                stratClient = stratMain.getClient();
            }
        } else if ((x > 330) && (x < 470)) {
            if ((y > 170) && (y < 220)) {
                //Name entry before host game
                enterName = true;
            } else if ((y > 220) && (y < 270)) {
                joinGame = true;
                name = true;
            } else if ((y > 270) && (y < 320)) {
                //TODO Load game handling
                loadGame = true;
            } else if ((y > 320) && (y < 370)) {
                showCredits = true;
            } else if ((y > 370) && (y < 420)) {
                running = false;
            }
        }
    }

    /**
     * Checks to see if player clicked a planet and sets that planet as new
     * capital if so.
     * 
     * @param x
     *            X parameter
     * @param y
     *            Y parameter
     */
    private void checkMiniClick(int x, int y) {
        Planet temp = checkPlanetHit(x * 4, y * 4);
        if (temp != null) {
            selectedPlanet = temp;
            stratClient.getCommand().startCity(temp);
        }
    }

    /**
     * Draws planet on panel
     *  
     */
    private void drawPlanet() {

        if (selectedPlanet != null) {
            switch (selectedPlanet.getPlanetType()) {
            case 0:
                drawG.drawImage(store.getPlanet1(selectedPlanet.getOwner()
                        .getColor()), 682, 77, 100, 100, frame);
                break;
            case 1:
                drawG.drawImage(store.getPlanet2(selectedPlanet.getOwner()
                        .getColor()), 682, 77, 100, 100, frame);
                break;
            case 2:
                drawG.drawImage(store.getPlanet3(selectedPlanet.getOwner()
                        .getColor()), 682, 77, 100, 100, frame);
                break;
            case 3:
                drawG.drawImage(store.getPlanet4(selectedPlanet.getOwner()
                        .getColor()), 682, 77, 100, 100, frame);
                break;
            default:
                drawG.drawImage(store.getPlanet5(selectedPlanet.getOwner()
                        .getColor()), 682, 77, 100, 100, frame);
            }
        }
    }

    /**
     * Sets player color
     * 
     * @param y
     *            Color clicked
     */
    private void setColor(int y) {
        if (y < 160) {
            selectColor = false;
            player.setColor(Color.red);
            stratClient.getCommand().sendColor("RED");
        } else if (y < 200) {
            selectColor = false;
            player.setColor(Color.blue);
            stratClient.getCommand().sendColor("BLUE");
        } else if (y < 240) {
            selectColor = false;
            player.setColor(Color.orange);
            stratClient.getCommand().sendColor("ORANGE");
        } else {
            selectColor = false;
            player.setColor(Color.cyan);
            stratClient.getCommand().sendColor("CYAN");
        }
    }

    /**
     * If planet clicked, return planet. Else return null.
     * 
     * @param x
     *            X coordinate of click
     * @param y
     *            Y coordinate of click
     * @return Planet if match, else returns null.
     */
    private Planet checkPlanetHit(int x, int y) {
        int num = state.getNumPlanets();
        int i = 0;
        Planet temp;

        while (i < num) {
            temp = state.getPlanet(i);
            if ((x < (temp.getXPosition() + 110))
                    && (x > (temp.getXPosition() - 110))) {
                if ((y < (temp.getYPosition() + 110))
                        && (y > (temp.getYPosition() - 110))) {
                    return temp;
                }
            }
            i++;
        }
        return null;
    }

    /**
     * Draws battle window
     * 
     * @param winner
     *            Winning unit
     * @param loser
     *            Losing unit
     */
    public void drawBattle(Military winner, Military loser) {
        battleImage = dc.drawBattle(winner, loser);
        showBattle = true;

            /** loser Dies */
            if (loser.getSize() == 0) {
                /** If loser is defendeding a planet */
                if (loser.getCurrentPlanet().getPlanetID() == loser
                        .getNextPlanet().getPlanetID()) {
                    if (!stratClient.getState().stillProtected
                            (loser.getCurrentPlanet(), loser)) {
                        loser.getCurrentPlanet().setFriendlyUnits(false);
                    }
                }
                if (stratMain.isServer()) {
                  stratClient.getCommand().removeUnit
                      (loser.getMilitaryID());
                }
            } else if ((loser.getSize() > 0) && !loser.getOrbiting()) {
                loser.setNextPlanet(loser.getCurrentPlanet());
            } if (winner.getSize() == 0) {
                /** If loser is defendeding a planet */
                if (winner.getCurrentPlanet().getPlanetID() == winner
                        .getNextPlanet().getPlanetID()) {
                    if (!stratClient.getState().stillProtected
                            (winner.getCurrentPlanet(), winner)) {
                        winner.getCurrentPlanet().setFriendlyUnits(false);
                    }
                }
                if (stratMain.isServer()) {
                  stratClient.getCommand().removeUnit
                      (winner.getMilitaryID());
                }
            }
        winner.setIsBattling(false);
    }

    /**
     * Handle panel clicks
     * 
     * @param x
     *            X coordinate of click
     * @param y
     *            Y coordinate of click
     */
    private void checkPanelClick(int x, int y) {
        if (y < 39) {
            map.setPlanetStats();
        } else if (y < 77) {
            map.setUnitStats();
        } else if ((y > 178) && (y < 377)) {
            if (y < 216) {
                if (x < 735) {
                    if (!selectRecruit) {
                        if (selectedPlanet != null) {
                            if (selectedPlanet.getOwner().getPlayerID()
                                    == player.getPlayerID()) {
                                selectRecruit = true;
                            }
                        }
                    } else {
                        selectRecruit = false;
                    }
                } else if (selectedUnit != null) {
                    //TODO Train commands here
                    stratClient.getCommand()
                            .train(selectedUnit.getMilitaryID());
                }
            } else if (y < 256) {
                if (x < 735) {
                    //TODO Fortify commands go here
                    if (selectedPlanet != null) {
                        stratClient.getCommand().fortify(
                                selectedPlanet.getPlanetID());
                    }
                } else {
                    //TODO SetCapital here
                    if (selectedPlanet != null) {
                        stratClient.getCommand().makeCapital(
                                selectedPlanet.getPlanetID());
                    }
                }
            } else if ((y < 297) && (selectedUnit != null)) {
                if (x < 735) {
                    showRaid = true;
                    selectMove = false;
                    selectShuttle = false;
                    selectBombard = false;
                    selectMerge = false;
                } else {
                    selectBombard = true;
                    selectMove = false;
                    selectShuttle = false;
                    selectRaid = false;
                    selectMerge = false;
                }
            } else if ((y < 336) && (selectedUnit != null)) {
                if (x < 735) {
                    selectShuttle = true;
                    selectMove = false;
                    selectRaid = false;
                    selectBombard = false;
                    selectMerge = false;
                } else {
                    selectMerge = true;
                    selectShuttle = false;
                    selectMove = false;
                    selectRaid = false;
                    selectBombard = false;
                }
            } else if ((y < 370) && (selectedUnit != null)) {
                selectMove = true;
                selectShuttle = false;
                selectMerge = false;
                selectRaid = false;
                selectBombard = false;
            }
        } else if ((y > 379) && (y < 498)) {
            Military temp;
            //Scrolling
            if (x < 688) {
                if (y < 395) {
                    dc.scrollUnits(-1);
                } else if (y > 477) {
                    dc.scrollUnits(1);
                }
            } else if (y < 433) {
                //select first visible unit on list
                temp = dc.getUnit(0);
                if (temp != null) {
                    oldUnit = selectedUnit;
                    selectedUnit = temp;
                }
            } else {
                //second
                temp = dc.getUnit(1);
                if (temp != null) {
                    oldUnit = selectedUnit;
                    selectedUnit = temp;
                }
            }
            if (selectMerge && (oldUnit != null)
                && (selectedUnit != oldUnit)) {
                stratClient.getCommand().sendMerge(oldUnit.getMilitaryID(),
                        selectedUnit.getMilitaryID());
                selectMerge = false;
            }
        } else if (y > 497) {
            startX = ((x - 672) - 37) * 10;
            startY = ((y - 498) - 30) * 10;

            if (startX < 0) {
                startX = 0;
            } else if (startX > 610) {
                startX = 610;
            }
            if (startY < 0) {
                startY = 0;
            } else if (startY > 424) {
                startY = 424;
            }
        }
    }

    /**
     * Handles mouse release events
     * 
     * @param e
     *            MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        //Unused
    }

    /**
     * Go to matchmaking
     *  
     */
    public void startMatchmaking() {
        matchmaking = true;
    }

    /** Draws pause screen */
    private void drawPause() {
        drawG.setFont(largeFont);
        drawG.setColor(Color.white);
        drawG.drawString("PAUSED", 350, 300);
    }

    /**
     * Draws menu when Esc is pressed
     */
    private void drawGameMenu() {
        drawG.setColor(Color.black);
        drawG.fillRect(0, 0, 800, 600);
        drawG.setFont(largeFont);
        drawG.setColor(Color.white);
        drawG.drawRect(330, 220, 140, 50);
        drawG.drawString("Continue", 360, 250);
        drawG.drawRect(330, 270, 140, 50);
        drawG.drawString("Save Game", 350, 300);
        drawG.drawRect(330, 320, 140, 50);
        drawG.drawString("New Game", 350, 350);
        drawG.drawRect(330, 370, 140, 50);
        drawG.drawString("Quit", 375, 400);
    }

    /**
     * Draws quit confirmation screen.
     *  
     */
    private void drawConfirm() {
        if (showConfirm) {
            drawG.setColor(Color.black);
            drawG.fillRect(0, 0, 800, 600);
            drawG.setFont(titleFont);
            drawG.setColor(Color.white);
            drawG.drawString("Are you sure?", 300, 280);
            drawG.drawRect(120, 370, 220, 60);
            drawG.drawString("Of course I am!", 130, 410);
            drawG.drawRect(400, 370, 240, 60);
            drawG.drawString("I made a mistake", 410, 410);
        }
    }

    /**
     * Sets state and initiates MapImage
     * 
     * @param s
     *            Gamestate
     */
    public void setServerState(ClientState s) {
        this.state = s;
        map = new MapImage(state, il, store, this);
        joinGame = false;
        playerChat = "";
        player = state.getPlayerFromID(stratMain.getPlayerId());
        dc.setState(s);
        chat = false;
        enterName = false;
    }

    /**
     * Begins game
     *  
     */
    public void beginGame() {
        matchmaking = false;
        startup = false;
    }

    /**
     * Draws minimap
     *  
     */
    private void drawMinimap() {
        drawG.drawImage(dc.getMinimap(), 672, 498, frame);
        drawG.setColor(Color.yellow);
        drawG.drawRect(671 + (startX / 10), 497 + (startY / 10), 67, 60);

    }

    /**
     * Draws city box
     *  
     */
    private void drawPanel() {
        drawG.drawImage(dc.getPanel(), 670, 0, frame);
        drawPlanet();
        //Popup
        if (selectRecruit) {
            drawRecruit();
        }
    }

    /**
     * Draws chat TODO change background, need faster transparency
     */
    private void drawChat() {
        drawG.setFont(smallFont);
        drawG.setColor(Color.white);
        LinkedList temp = state.getChat();
        int size = temp.size();
        if (chat) {
            drawG.drawString("Say: " + playerChat, 140, 591);
            drawG.drawImage(scrollUp, 128, 500, frame);
            drawG.drawImage(scrollDown, 128, 570, frame);
        }
        drawG.setColor(chatColor);
        if (!temp.isEmpty()) {
            drawG.drawString((String) temp.get(scrollChat), 140, 578);
        }
        if (size > (scrollChat + 1)) {
            drawG.drawString((String) temp.get(scrollChat + 1), 140, 566);
        }
        if (size > (scrollChat + 2)) {
            drawG.drawString((String) temp.get(scrollChat + 2), 140, 554);
        }
        if (size > (scrollChat + 3)) {
            drawG.drawString((String) temp.get(scrollChat + 3), 140, 542);
        }
        if (size > (scrollChat + 4)) {
            drawG.drawString((String) temp.get(scrollChat + 4), 140, 530);
        }
        if (size > (scrollChat + 5)) {
            drawG.drawString((String) temp.get(scrollChat + 5), 140, 518);
        }
        if (size > (scrollChat + 6)) {
            drawG.drawString((String) temp.get(scrollChat + 6), 140, 506);
        }
    }

    /**
     * Draws resources
     *  
     */
    private void drawResources() {
        drawG.setFont(smallFont);
        drawG.setColor(Color.white);
        //Moola = Money; Caffeine = Oil; Correlium = Steel
        drawG.drawString("Food: " + player.getFood() + "  Dinero: "
                + player.getMoney() + "  Caffeine: " + player.getOil()
                + "  Correlium: " + player.getSteel(), 10, 35);
    }

    /**
     * Draw join game screen
     *  
     */
    private void drawJoin() {
        //Draw join box
        drawG.setColor(panelColor);
        drawG.fillRect(210, 190, 390, 215);
        drawG.setColor(Color.black);
        drawG.drawRect(210, 190, 390, 215);
        drawG.setFont(largeFont);
        drawG.drawString("Join Game", 360, 215);

        //Player name box
        drawG.setColor(Color.lightGray);
        drawG.fillRect(290, 250, 300, 30);
        //Server address box
        drawG.fillRect(290, 295, 300, 30);
        drawG.setColor(Color.black);
        drawG.drawRect(290, 295, 300, 30);
        drawG.drawRect(290, 250, 300, 30);

        //Join button
        drawG.drawRect(370, 350, 60, 30);

        //Box title
        drawG.setFont(smallFont);
        drawG.drawString("Join", 385, 370);
        drawG.drawString("Your Name:", 213, 265);
        drawG.drawString("Host IP:", 230, 314);
        drawG.drawString(playerName, 302, 270);
        drawG.drawString(playerChat, 302, 315);
    }

    /** Draws name entry */
    private void drawEnterName() {
        drawG.drawImage(dc.getEntryBox(), 260, 190, frame);
        drawG.setFont(largeFont);
        drawG.drawString("Enter Name", 360, 220);
        //Box title
        drawG.setFont(smallFont);
        drawG.drawString("Go", 385, 350);
        drawG.drawString(playerName, 302, 270);
    }

    /**
     * draw entry
     *
     */
    private void drawEntry() {
        drawG.drawImage(dc.getEntryBox(), 260, 190, frame);
        drawG.setFont(largeFont);
        drawG.setColor(Color.black);
        if (loadGame) {
            drawG.setFont(largeFont);
            drawG.drawString("Load Game", 360, 220);
            //Box title
            drawG.setFont(smallFont);
            drawG.drawString("Load", 385, 350);
        } else if (saveGame) {
            drawG.setFont(largeFont);
            drawG.drawString("Save Game", 360, 220);
            //Box title
            drawG.setFont(smallFont);
            drawG.drawString("Save", 385, 350);
        } else if (showRaid) {
            drawG.setFont(largeFont);
            drawG.drawString("Enter How Many Days To Raid", 280, 220);
            //Box title
            drawG.setFont(smallFont);
            drawG.drawString("Raid", 385, 350);
        }
        drawG.drawString(entryString, 302, 270);
    }

    /**
     * Draws matchmaking screen
     *  
     */
    private void drawMatchmaking() {
        drawG.drawImage(dc.getMatchmaking(), 0, 0, frame);
        drawG.setColor(player.getColor());
        drawG.fillRect(440, 120, 80, 40);
        Player temp;
        int i = 0;
        int drawY = 120;
        drawG.setFont(largeFont);
        while (state.getPlayers().size() > i) {
            temp = (Player) state.getPlayers().get(i);
            if ((temp.getPlayerID() != player.getPlayerID())
                    && !temp.getName().equals("Pie")) {
                drawG.setColor(temp.getColor());
                drawG.drawString(temp.getName(), 120, drawY);
                drawG.fillRect(90, drawY + 10, 40, 40);
                drawG.drawString(temp.getSide(), 160, drawY + 35);
                drawY += 80;
            }
            i++;
        }
        drawG.drawImage(map.drawMap(), 410, 180, 730, 436, 0, 0, 1280, 1024,
                frame);
        
        //Start button
        drawG.setColor(Color.black);
        if (stratMain.isServer() && stratMain.getServer().isReady()
              && !sideName.equals("") && (selectedPlanet != null)) {
            drawG.setColor(panelColor);
            drawG.fillRect(640, 540, 120, 40);
            drawG.setColor(Color.black);
            drawG.drawRect(640, 540, 120, 40);
            drawG.drawString("Start Game", 650, 565);
        } else if (!stratMain.isServer() && !sideName.equals("")
                   && (selectedPlanet != null)) {
            drawG.setColor(panelColor);
            drawG.fillRect(640, 540, 120, 40);
            drawG.setColor(Color.black);
            drawG.drawRect(640, 540, 120, 40);
            drawG.drawString("Ready!", 660, 565);
        }
        drawG.drawString(sideName, 550, 145);
        drawChat();

        //Color selection
        if (!selectColor) {
            drawG.drawRect(440, 120, 80, 40);
        } else {
            drawG.drawRect(440, 120, 80, 160);
            drawG.setColor(Color.red);
            drawG.fillRect(440, 120, 80, 40);
            drawG.setColor(Color.blue);
            drawG.fillRect(440, 160, 80, 40);
            drawG.setColor(Color.ORANGE);
            drawG.fillRect(440, 200, 80, 40);
            drawG.setColor(Color.cyan);
            drawG.fillRect(440, 240, 80, 40);
        }
    }

    /**
     * Scrolls the map by updating the viewable area.
     *  
     */
    private void scrollMap() {
        if (((startX > 19) && (incX < 0)) || ((startX < 583) && (incX > 0))) {
            startX += incX;
        }
        if (((startY > 19) && (incY < 0)) || ((startY < 403) && (incY > 0))) {
            startY += incY;
        }
    }

    /** Draws game stats */
    public void drawStats() {
        drawG.drawImage(dc.getStats(), 670, 0, frame);
    }

    /**
     * Handles drawing of recruit/build menu
     *  
     */
    private void drawRecruit() {
        drawG.setColor(panelColor);
        drawG.fillRect(500, 100, 170, 218);
        drawG.setColor(Color.black);
        drawG.setFont(smallFont);
        //Infantry or building
        drawG.drawRect(510, 110, 150, 30);
        if (selectedPlanet != null) {
            if (selectedPlanet.hasBuilding("TRAININGACADEMY")) {
                drawG.drawString("Build Raptor", 532, 127);
            } else {
                drawG.drawString("Build Acadamy", 532, 127);
            }
            //Factory units or building
            drawG.drawRect(510, 150, 150, 60);
            if (selectedPlanet.hasBuilding("WARFACTORY")) {
                drawG.drawString("Build Frigate", 532, 167);
                drawG.drawString("Build Ragnarok", 532, 197);
            } else {
                drawG.drawString("Build WarFactory", 532, 187);
            }
            //Shipyard units or building
            drawG.drawRect(510, 218, 150, 90);
            if (selectedPlanet.hasBuilding("SHIPYARD")) {
                drawG.drawString("Build Shade", 542, 235);
                drawG.drawString("Build Dreadnaught", 522, 265);
                drawG.drawString("Build Overlord", 532, 295);
            } else {
                drawG.drawString("Build Shipyard", 522, 267);
            }
        }
    }

    /**
     * Handles drawing of start menu, join and matchmaking screens
     *  
     */
    private void drawStartup() {
        drawG.drawImage(store.getMoonImage(), 0, 0, 800, 600, 0, 20, 800, 620,
                frame);
        if (showCredits) {
            drawG.drawImage(store.getCredits(), 0, 0, frame);
        } else if (matchmaking) {
            drawMatchmaking();
        } else if (joinGame) {
            drawJoin();
        } else if (enterName) {
            drawEnterName();
        } else if (loadGame) {
            drawEntry();
        } else {
            //Draw startup menu
            drawG.drawImage(store.getTitleImage(), 270, 60, frame);

            //Menu images
            drawG.drawImage(store.getHostImage(), 290, 170, frame);
            drawG.drawImage(store.getJoinImage(), 283, 220, frame);
            drawG.drawImage(store.getLoadImage(), 280, 270, frame);
            drawG.drawImage(store.getCreditsImage(), 332, 320, frame);
            drawG.drawImage(store.getQuitImage(), 355, 370, frame);
        }
    }

    /**
     * Setter for pause
     */
    public void setPause() {
        showPause = !showPause;
    }

    /**
     * starts drawing the GUI
     */
    public void run() {

        while (running) {

            /** gets the frame we are drawing on */
            drawG = bufferStrategy.getDrawGraphics();

            if (startup) {
                drawStartup();
            } else {
                if (showMenu) {
                    drawGameMenu();
                } else if (showConfirm) {
                    drawConfirm();
                } else if (showPause) {
                    drawPause();
                } else if (showBattle) {
                    drawG.drawImage(battleImage, 250, 200, frame);
                } else {
                    scrollMap();
                    drawG.drawImage(map.drawMap(), 0, 0, 670, 600, startX,
                            startY, startX + 670, startY + 600, frame);
                    // Draw interface
                    drawPanel();
                    drawMinimap();
                    drawChat();
                    drawResources();
                    drawG.setColor(Color.white);
                    drawG.setFont(smallFont);
                    drawG.drawString(
                            "Year: " + (state.getDay() + 82) + " A.W.", 5, 580);
                    
                    if (saveGame || showRaid) {
                        drawEntry();
                    }

                }
            }

            drawG.dispose();
            bufferStrategy.show();

            try {
                thread.sleep(33);
            } catch (Exception e) {
                entryString = "";
            }

        } //end while

        System.exit(0);
    } //end start

    /**
     * get command
     * @return processor
     */
    public ClientCommandProcessor getCommand() {
        return stratClient.getCommand();
    }

    /**
     * get Player
     * @return player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * return strat main
     * @return main
     */
    public StratagemMain getStratMain() {
        return stratMain;
    }

    /**
     * get player name
     * @return name string
     */
    public String getPlayerName() {
        return playerName;
    }

}