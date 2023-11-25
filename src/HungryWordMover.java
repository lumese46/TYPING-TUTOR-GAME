

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HungryWordMover extends Thread {
	private FallingWord[] myWord;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
   CountDownLatch startLatch; //so all can start at once
	
	
	HungryWordMover( FallingWord[] word) {
		myWord = word;
	}
	
	HungryWordMover( FallingWord[] word,WordDictionary dict, Score score,CountDownLatch startLatch,
			 AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
	}
	
	
	
	public void run() {
      //System.out.println("  HUNGRY             "+ myWord.getWord() +"                " + myWord.getWord() + " falling speed = " + myWord.getSpeed());
      try {
			//System.out.println(myWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start
		
      while (!done.get()) {				
		   //animate the word
         while (!myWord[0].dropped() && !done.get()) {
         
               myWord[0].hungryDrop(10);
               
               try {
						sleep(myWord[0].getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
               
               while(pause.get()&&!done.get()) {};
               
         }
         if (!done.get() && myWord[0].dropped()) {
               System.out.println("done                                        done             done"+ myWord[0].getX() + "  " +myWord[0].getWord());
				   score.missedWord();
				   myWord[0].hResetWord();
            
			}
         myWord[0].hResetWord();
        // System.out.println("this is a reset");
      }
   }
}
	