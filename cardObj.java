package cangkulgame;


public class cardObj {
  private int rank; 
  private String suit; 

  //constructor for cardObj, initializes the card's rank and suit 
  public cardObj(int rank, String suit){
    this.rank = rank;
    this.suit = suit;  
  }

  //displays the card's suit 
  public String getSuit(){
    return suit; 
  }

  //displays the card's rank 
  public int getRank(){
    return rank; 
  } 

  //check which rank corresponds to each value 
  private String checkRank(int rank){
    switch(rank){
      case 11: 
        return "Jack"; 
      case 12: 
        return "Queen";
      case 13: 
        return "King";
      case 14: 
        return "Ace"; 
      default: 
        return "" + rank; 
    }
  } 

  //displays the card in string format 
  public String toString(){
    String cardInStringFormat = checkRank(rank) + " of " + suit; 
    return cardInStringFormat; 
  }
}
