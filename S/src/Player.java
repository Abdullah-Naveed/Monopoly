
public class Player {
	
	private int position;
	public String name;
	Player () {
		position = 0;
		//this.name = name;
		return;
	}
	
	public void move (int squares) {
		position = position + squares;
		if (position < 0) {
			position = position + Monopoly.NUM_SQUARES;
		} else if (position >= Monopoly.NUM_SQUARES) {
			position = position % Monopoly.NUM_SQUARES;
		}
		return;
	}
	
	public int getPosition () {
		return position;
	}
	public String Getname()
	{
		return name;
		
	}
	public void Setname(String user)
	{
		this.name =user;
		
		
	}
	
}
