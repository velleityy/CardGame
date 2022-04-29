package cangkulgame;

import java.util.ArrayList; 
public class playerObj {
  private ArrayList<cardObj> playerHand = new ArrayList<cardObj>();  
  private cardDeck deckOnTable;
  private int playerNumber; 

  public playerObj(int playerNumber, cardDeck deckOfCards){
    this.playerNumber = playerNumber; 
    this.deckOnTable = deckOfCards; 
  } 

  //initially add cards to the hand, gets used in cangkulCardGame to add 7 cards 
  protected void initializeHand(){
    playerHand.add(deckOnTable.getCardFromDeck());
  }

  //player takes a card from the deck, 
  protected boolean playerTakeCard(String suit){
    if (!deckOnTable.deckOfCards.isEmpty()){
      if (suit.equals("none")){//if its the beginning of the round, player cant take card 
        System.out.println("You can't draw in the beginning of the round");
        return false;
      }else if (weHaveSuit(suit)){//if player already has card, they cant take 
        System.out.println("you have the card with the suit, you can't draw more");
        return false;
      }else{
        playerHand.add(deckOnTable.getCardFromDeck()); //take card from deck if conditions are met 
        return true; 
      }
    }else{
      System.out.println("You can no longer draw from the deck since it's finished");
      return false; 
    }
  }

  // check if we have the chosen suit for the round in our hand. 
  private boolean weHaveSuit(String suit){
    ArrayList<String> listOfSuits = new ArrayList<String>();
    for (cardObj card: playerHand){
      listOfSuits.add(card.getSuit());
    }
    if (listOfSuits.contains(suit)){
      return true; 
    }
    return false; 
  } 

  //player takes back the card they put forth 
  protected void playerTakeBackCard(cardObj card){
    playerHand.add(card); 
  }

  //player displays their hand 
  protected String displayHand(){
    return playerHand.toString(); 
  } 

  //player displays the size of their hand 
  protected int sizeOfHand(){
    return playerHand.size(); 
  }

  //player displays the last card they took 
  protected String takenCard(){
    return playerHand.get(playerHand.size()-1).toString(); 
  }

  //player puts forth their card 
  protected cardObj launchCard(int index){
    return playerHand.remove(index); 
  } 

  //displays the player number as a string 
  public String toString(){
    return "" + playerNumber; 
  }



}
