

import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
   private static  FallingWord[] Hword; // list of hungry Word
   
	private static int noWords; //how many
	private static Score score; //user score
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}
   
   // this is for hungry Word to get it
   public static void setHWords(FallingWord[] _Hword) {
		Hword  = _Hword;	
		
	}
	
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	
	public void run() {
		int i=0;
      // i am getting the max y of the falling words 
      int initPreviousFalling = words[0].getMaxY();
      int prev = 0;
      int prevp = -1;
      boolean Break = false;
      
      
     
      
    
		while (i<noWords) {
         /*if(Break){
            break;
         }*/
         
         
         		
			while(pause.get()) {};
         
            
			if (words[i].matchWord(target)) {
            if (initPreviousFalling > prev){
               // set the initial prev
               initPreviousFalling = 0;
               // the previous y
               prev = words[i].getY(); 
               // holds the position of the word
               prevp = i;
               score.caughtWord(target.length());
            }
            
            if (words[i].getY() > prev){
               prev = words[i].getY(); 
               prevp = i;
            }
				//System.out.println( " score! '" + target); //for checking
				//score.caughtWord(target.length());	
				//FallingWord.increaseSpeed();
				//break;
			}
		   i++;
		}
      // there is a match
      if (prevp != -1){
         if(prev < 240 && Hword[0].matchWord(target)){
           score.caughtWord(target.length());
           Hword[0].hResetWord();
           Break = true;
         }
         else{ 
         words[prevp].resetWord();
         Break = true;
         
         }
      }
      if(!Break && Hword[0].matchWord(target)){
           score.caughtWord(target.length());
           Hword[0].hResetWord();
           Break = true;
      }
      
		
	}	
}
