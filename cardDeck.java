package cangkulgame;

import java.util.Stack; 
import java.util.Random; 

public class cardDeck {
  //make this private again
  protected Stack<cardObj> deckOfCards = new Stack<cardObj>(); 

  //make the stack of cards with 52 cards 
  public cardDeck(){
    makeStackOfCards(deckOfCards); 
  }

  //add cards to deckOfCards repeating 4 times according to the suit array 
  private void makeStackOfCards(Stack<cardObj> collectionObject){
    String[] suits = {"Diamonds", "Spades", "Clubs", "Hearts"};
    for (String suit: suits){
      addCardsToDeck(suit, collectionObject); 
    }
  } 

  //add cards to deckOfCards repeating 14 times according to how many ranks there are 
  private void addCardsToDeck(String suit, Stack<cardObj> collectionObject){
    int[] rank = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14}; 
    for (int i: rank){
      collectionObject.add(new cardObj(i, suit));
    }
  } 

  //shuffle the deck of cards, 
  //divide the card by two, and stacking them on top of each other one by one, 
  //simulating one of the real life card shuffle techniques 
  public void shuffleDeck(){
    Random randomObject = new Random(); 
    int halfOfDeck = deckOfCards.size() / 2; 
    Stack<cardObj> deckStack = new Stack<cardObj>(); 
    Stack<cardObj> deckStackTwo = new Stack<cardObj>(); 
    for (int i = 0; i < halfOfDeck; i ++){
      deckStackTwo.add(deckOfCards.pop()); 
    }
    for(int i = 0; i < halfOfDeck; i ++){
      deckStack.add(deckOfCards.pop()); 
    }
    while(!deckStackTwo.isEmpty() || !deckStack.isEmpty()){ 
      int randomNumber = randomObject.nextInt(2) + 1; 
      for (int i = 0; i < randomNumber; i ++){
        if (!deckStackTwo.isEmpty()) deckOfCards.add(deckStackTwo.pop()); 
      } 
      randomNumber = randomObject.nextInt(2) + 1; 
      for(int i = 0; i< randomNumber; i ++){
        if (!deckStack.isEmpty()) deckOfCards.add(deckStack.pop()); 
      }
     }
  } 

  //get the top card from the deck 
  protected cardObj getCardFromDeck(){
    return deckOfCards.pop(); 
  }
  //returns the string format of the stack of cards 
  public String toString(){
    return deckOfCards.toString(); 
  }
}
