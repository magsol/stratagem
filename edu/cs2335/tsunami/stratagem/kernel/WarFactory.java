package edu.cs2335.tsunami.stratagem.kernel;

/**
 * War Factory, allows for production of frigates
 *
 * @author Shannon Quinn
 * @version 1.0
 */
public class WarFactory extends Building {

   /** cost for this building */
   public static final int COSTMONEY = 300;

   /**
    * Constructor
    *
    */
   public WarFactory() {
     this.setName("WARFACTORY");
     setCost(COSTMONEY);
   }
}
