package game;

import java.awt.Point;
import java.util.StringTokenizer;

/**
 * @author a50605
 * @version 1.0
 */
public class PatternReader {
	String patstr;
	
	StringTokenizer tokenizer;
	Point movexy = new Point();
	int frequency = 0;	//回数
	
	PatternReader(String str){
		patstr = str;
		tokenizer = new StringTokenizer(patstr,",");
	}
	
	Point next(){
		if (frequency==0){
			if (tokenizer.hasMoreTokens()==false){
				tokenizer = new StringTokenizer(patstr,",");
			}
			movexy.x = Integer.parseInt(tokenizer.nextToken());	
			movexy.y = Integer.parseInt(tokenizer.nextToken());	
			frequency = Integer.parseInt(tokenizer.nextToken());				
		} else {
			frequency = frequency-1;
		}
		return movexy;
	}
}