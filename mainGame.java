/*
Programmers: Tsaqif Insani, Gracia Winata, Elaine Hsu
Date: 26 April 2022;
Class: CS 145 
Assignment: Lab 4 deck of cards 
Purpose of this program: to play a game of 'cangkul' a local game from our home country Indonesia
notes: 
  We've separated the code for the cangkul game itself into a package of their own called 'cangkulgame'. 
  Since we thought it'd be nice to have experience utilizing java packages. 
  Plus, it makes the structure of the program cleaner and more organized. 
  We used vscode for our IDE and git to keep track of the program's progress. 
  Then, for extra credits: 
    - we used both stacks and queues in this program. Queues in cangkulCardGame.java for the playersList, 
      Stacks in cardDeck java for the deck of Cards. 
    - We used switch case in cardObj.java to check the rank of the cards 
    - We used try/catch in cangkulCardGame.java to check if the user's input is a number 
    - We made it so that the program is crash resistant, 
    - We made it so that after every round the terminal will clear up so the user interface is more user friendly 
      and less clustered in the terminal 
  We really wanted to make the code cleaner and less large especially in the cangkulCardGame.java but 
  we ran out of time, fortunately its only one method that's larger than the rest, but we hoped we could've 
  cleaned it up. 

*/
import cangkulgame.cangkulCardGame;
public class mainGame{
  public static void main(String[] args){
    //creating a new cangkulCardGame object, takes in the number of players that 
    //will be playing as its constructor parameter 
    cangkulCardGame game = new cangkulCardGame(2);
    game.play();  
  }
}
