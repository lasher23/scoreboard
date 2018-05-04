/**
 * File Name: GameSettingsProvider.java
 * 
 * Copyright (c) 2018 BISON Schweiz AG, All Rights Reserved.
 */

package ch.uhc_yetis.view.settings.game;

import javafx.beans.property.SimpleLongProperty;

/**
 *
 * @author nicolas.schmid
 */
public class GameSettingsProvider {
  public static final SimpleLongProperty PAUSE_DURATION = new SimpleLongProperty(300000);
  public static final SimpleLongProperty THIRD_DURATION = new SimpleLongProperty(1200000);
}
