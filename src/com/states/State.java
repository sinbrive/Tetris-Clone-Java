package com.states;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class State {

  private static State currentState = null;
  
  public static void setState(State state){
    currentState = state;
  }
  
  public static State getState(){
    return currentState;
  }
  
  //CLASS
  public abstract void setup();
  
  public abstract void update();
  
  public abstract void draw(Graphics2D g);
  
}