

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class WordMover extends Thread {
	private FallingWord myWord;
   private FallingWord[] Hword;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
   private int x;
	CountDownLatch startLatch; //so all can start at once
	
	WordMover( FallingWord word) {
		myWord = word;
	}
	
	WordMover( FallingWord word,FallingWord[] Hword,WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
      
		this(word);
      this.Hword = Hword;
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
      x = myWord.getX();
	}
	
	
	
	public void run() {

		//System.out.println("   " +myWord.getWord() + " falling speed = " + myWord.getSpeed());
		try {
			//System.out.println(myWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start
		//System.out.println(myWord.getWord() + " started" );
		while (!done.get()) {				
			//animate the word
         
         
			while (!myWord.dropped() && !done.get()) {
               
				    myWord.drop(10);
                 System.out.println("  HUNGRY             "+ Hword[0].getX() + "  " + Hword[0].getWord() );
                 
                 // this are the xcoordinates of word
                 int w_start = x ;
                 int w_end = x + myWord.getWord().length() * 10;
                 
                 //this are the xcoordinates of hungryWord
                 
                 int h_start = Hword[0].getX() - Hword[0].getWord().length()*10;
                 int h_end = Hword[0].getX()  + Hword[0].getWord().length()*10;
                 
                 
                  
                
                if(myWord.getY() >= 240  && myWord.getY() <= 270 ){
                     
                     if(h_end >= w_start && h_end <= w_end  ){
                             score.missedWord();
                             myWord.resetWord();
                             
                           
                    }
                    else if(h_start >= w_start && h_start <= w_end){
                             score.missedWord();
                             myWord.resetWord();
                             
                    }
                    
                  
                  
               }
					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};		
					while(pause.get()&&!done.get()) {};
			}
			if (!done.get() && myWord.dropped()) {
				score.missedWord();
				myWord.resetWord();
			}
			myWord.resetWord();
		}
	}
	
}
