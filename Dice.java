
public class Dice {
	
	private static final int NUM_DICE = 2;
	private int count = 0;
	
	private int[] dice = new int [NUM_DICE];
	
	
	public void roll () {
		for (int i=0; i<NUM_DICE; i++) {
			dice[i] = 1 + (int)(Math.random() * 6);   
		}
		return;
	}
	
	public void mockRoll(){
		dice[0] = 1;
		dice[1] = 1;
	}
	public void diffRoll(){
		dice[0]=4;
		dice[1]=3;
	}

	public int[] getDice () {
		return dice;
	}
	
	public int getDice1(){
		return dice[0];
	}
	
	public int getDice2(){
		return dice[1];
	}
	
	public int getTotal () {
		return (dice[0] + dice[1]);
	}
	
	public boolean isDouble () {
		if(dice[0] == dice[1]){
			count++;
		}
		return dice[0] == dice[1];
	}
	
	public void resetCount(){
		count = 0;
	}
	
	public int noOfDoubles(){
		return count;
	}
	
	public String toString () {
		return dice[0] + " " + dice[1];
	}
	
}
