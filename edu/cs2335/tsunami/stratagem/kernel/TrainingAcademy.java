package edu.cs2335.tsunami.stratagem.kernel;

/**
 * Training Academy, for building Fighters
 *
 * @author Shannon Quinn
 * @version 1.0
 */
public class TrainingAcademy extends Building {

  /** cost for this building */
  public static final int COSTMONEY = 200;
  
  /**
   * Constructor
   *
   */
  public TrainingAcademy() {
      this.setName("TRAININGACADEMY");
      setCost(COSTMONEY);
  }

}
