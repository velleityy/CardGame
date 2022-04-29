package cangkulgame;
import java.util.Queue;
import java.util.LinkedList; 
import java.util.Random; 
import java.util.Scanner; 
import java.util.ArrayList; 

//cangkulCardGame class 
public class cangkulCardGame {
  private int numberOfPlayers = 1; 
  private cardDeck deckOnTable; 
  private Queue<playerObj> playersList = new LinkedList<playerObj>(); 

  //constructor method for cangkulCardGame 
  public cangkulCardGame(int numberOfPlayers){
    //initialize number of players 
    this.numberOfPlayers = numberOfPlayers; 
    //initialize card deck 
    this.deckOnTable = new cardDeck(); 
    //add players to the queue 
    addPlayersToList(); 
  }

  //add players to the queue 
  private void addPlayersToList(){
    for (int i = 1; i <= numberOfPlayers; i++){
      playersList.add(new playerObj(i, deckOnTable));  
    }
  }

  //shuffle the deck random times 
  private void shuffleDeckMultipleTimes(){
    Random randomObj = new Random(); 
    int randomNumber = randomObj.nextInt(4) + 3; 
    for (int i = 0; i< randomNumber ; i ++){
      deckOnTable.shuffleDeck(); 
    }
  } 

  //play the cangkul program 
  public void play(){
    System.out.println(); 
    Scanner input = new Scanner(System.in); 
    //shuffle the deck 
    shuffleDeckMultipleTimes(); 
    //all players initially take 7 cards 
    playersTakeSeven(); 
    //print out the instructions 
    instructions(); 
    boolean thereIsWinner = false; 
    //keep playing while there is no winner 
    while(!thereIsWinner){
      thereIsWinner = playOneRound(); 
    }

  }

  //prints out the instructions 
  private void instructions(){
    System.out.println("This game is a local game from Indonesia, It's played much like UNO" 
    + " in that, the suits need to match with the round's chosen suit." 
    + " If one does not have said suit, they will have to take from the deck until they get one.");
    System.out.println("In order to win the round, you must put forth the card with the highest rank: " 
    + "jack<queen<king<ace");
    System.out.println("When one person has run out of cards, they win the game");
    System.out.println("If no one has yet won and the deck has already ran out, " 
    + "The person with the least cards will win by default");
    System.out.println("It's a tie if more than one person have the least number of cards"); 
    System.out.println("Enjoy!"); 
    System.out.println(); 
  } 

  //plays one round 
  private boolean playOneRound(){
    cardObj biggestRank = new cardObj(0,"placeholder"); //biggest rank to determine the round's largest card value 
    String tableSuit = "none"; // to determine the round's chosen suit 
    playerObj roundWinner = new playerObj(0, deckOnTable); //the player object who won the round 
    boolean gameWon = false; //false if no winner yet, true if there is winner 
    for (playerObj player: playersList){ // iterate through the player list 
      playerObj currentPlayer = player; //current player 
      if (!tableSuit.equals("none")){ //if its not the first round, show the card to beat in the round 
        System.out.println("Card to beat is: " + biggestRank.toString()); 
      }
      System.out.println("It is player " + currentPlayer + "'s turn");
      int index = processPlayerInput(currentPlayer, tableSuit); //check if input is valid and if we can translate it 
      //into an integer 
      if(index == 1000){ //if the card deck is empty, we see who has the least amount of cards, and they will win 
        //if there are two with the least amount, its a tie. 
        playerObj leastAmountOfCards = currentPlayer;
        ArrayList<playerObj> leastAmountOfCardsPlayers = new ArrayList<playerObj>(); 
        for (playerObj thisPlayer: playersList){
          if (thisPlayer.sizeOfHand() <= leastAmountOfCards.sizeOfHand()){
            leastAmountOfCards = thisPlayer; 
            leastAmountOfCardsPlayers.add(thisPlayer); 
          } 
        }
        if (leastAmountOfCardsPlayers.size() > 1){
          System.out.println("No one wins, its a tie!");
        }else{
          System.out.println("Player " + leastAmountOfCards + " wins by default since deck is" 
          + " finished and the player has the least amount of cards"); 
        }
        gameWon = true;
        break; 
      } 
      //put forth the player's chosen card 
      cardObj chosenCard = currentPlayer.launchCard(index); 
      chosenCard = chooseValidCard(tableSuit, chosenCard, currentPlayer); 
      //if currentplayer runs out of cards, they win 
      if (winGame(currentPlayer)){
        System.out.println("Player " + currentPlayer + " WINS!!!!"); 
        gameWon = true; 
        break; 
      } 
      System.out.println("Player " + currentPlayer + " chose: " + chosenCard); 
      System.out.println();//prints out chosen card 
      tableSuit = chosenCard.getSuit(); //the round's suit is the card's suit 
      if (chosenCard.getRank() > biggestRank.getRank()){
        roundWinner = player; 
        biggestRank = chosenCard; 
      }//if the rank is bigger than the previous rank, biggest rank is set to current rank 
    }
    if (gameWon) return true; 
    revolvePlayersList(roundWinner); //the player who wins will start the next round first
    System.out.print("\033[H\033[2J");//clears the console 
    System.out.println("Player " + roundWinner + " won the round"); 
    return false; 
  } 


  //prompts the player for a choice of card 
  private void promptPlayerChoice(playerObj currentPlayer){
    System.out.println();
    System.out.println("This is your hand: ");
    System.out.println(currentPlayer.displayHand());
    System.out.println("which one do you want to choose? 0-" + (currentPlayer.sizeOfHand()-1)); 
    System.out.println("press -1 if you want to take a card from the deck"); 
  } 

  //checks if the player input is valid 
  private int processPlayerInput(playerObj currentPlayer, String tableSuit){
    promptPlayerChoice(currentPlayer); 
    Scanner input = new Scanner(System.in); 
    String index = input.nextLine(); 
    index = whileNumberIsntValid(index, currentPlayer); 
    index = whileTakeFromDeck(index, currentPlayer, tableSuit); 
    return Integer.parseInt(index); 
  } 

  //if player input isnt valid, we keep on asking until they give valid input 
  private String whileNumberIsntValid(String index, playerObj currentPlayer){
    Scanner input = new Scanner(System.in);  
    while(!checkIfNumberValid(index, currentPlayer)){
      System.out.println("Please input a valid number between 0-" + (currentPlayer.sizeOfHand()-1));
      promptPlayerChoice(currentPlayer);
      index = input.nextLine(); 
    }
    return index; 
  } 

  //if player input wants to take from deck, we give 
  //if deck is finished a player will win by default 
  private String whileTakeFromDeck(String index, playerObj currentPlayer, String tableSuit){
    Scanner input = new Scanner(System.in); 
    while(Integer.parseInt(index) == -1){
      if(currentPlayer.playerTakeCard(tableSuit)){
        System.out.println("You took "+ currentPlayer.takenCard());
      }
      if (winGameByDefault()){
        return "1000"; 
      }
      promptPlayerChoice(currentPlayer);
      index = input.nextLine(); 
      index = whileNumberIsntValid(index, currentPlayer); 
    } 
    return index; 
  } 

  //check if user input for index is a number 
  private boolean checkIfNumberValid(String index, playerObj currentPlayer){
    try{
      if (Integer.parseInt(index) == -1){
        return true; 
      }
      if (Integer.parseInt(index) < currentPlayer.sizeOfHand() && Integer.parseInt(index)>=0){
        return true; 
      }
    }catch(Exception e){
    }
    return false; 
  } 

  //check if the card chosen has the same suit 
  //take back the card put forth if the card doesn't match the suit 
  private cardObj chooseValidCard(String tableSuit, cardObj chosenCard, playerObj currentPlayer){
    cardObj validCard = chosenCard; 
    while(!checkChoice(tableSuit, validCard.getSuit())){
      currentPlayer.playerTakeBackCard(validCard);
      System.out.println(); 
      System.out.println("The chosen card doesn't match the suit, please choose another one or retrieve another card from the deck. Press -1 to take card from deck"); 
      int index = processPlayerInput(currentPlayer, tableSuit); 
      validCard = currentPlayer.launchCard(index); 
    }
    return validCard; 
  } 

  //check if the card matches the suit 
  private boolean checkChoice(String tableSuit, String chosenSuit){
    if (!tableSuit.equals("none")){
      if (tableSuit.equals(chosenSuit)){
        return true; 
      }else{
        return false; 
      }
    }
    return true; 
  }

  //the winner of the round will go first the next round, still keeping the same order of play. 
  private void revolvePlayersList(playerObj player){
    Queue<playerObj> queuePlaceholder = new LinkedList<playerObj>(); 
    while(playersList.peek() != player){
      queuePlaceholder.add(playersList.remove()); 
    } 
    playersList.addAll(queuePlaceholder); 
  }

  //each player takes seven cards initially 
  private void playersTakeSeven(){
    for (playerObj players: playersList){
      for (int i =0; i< 7; i++){
        players.initializeHand(); 
      }
    }
  }  

  //if the deck of cards is empty, a player will win by default 
  private boolean winGameByDefault(){
    if (deckOnTable.deckOfCards.isEmpty()){
      return true; 
    }
    return false; 
  }

  //if player has no more cards in their hand, they win. 
  private boolean winGame(playerObj player){ 
    if (player.sizeOfHand() == 0){
      return true; 
    }
    return false; 
  }
}
