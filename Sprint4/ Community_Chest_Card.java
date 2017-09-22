import java.util.ArrayList;
import java.util.Random;

public class Community_Chest_Card   {
	
	
	 ArrayList<Integer> numbers = new ArrayList<Integer>(); 
	 private static final int CMD_GO = 0;
	 private static final int CMD_GO_KENT = 1;
	 private static final int CMD_JAIL = 2;
	 private static final int CMD_PAY_HOSPITAL = 3;
	 private static final int CMD_PAY_DOCTOR = 4;
	 private static final int CMD_PAY_INSURENCE = 5;
	 private static final int CMD_BANKERROR = 6;
	 private static final int CMD_ANNUITY = 7;
	 private static final int CMD_INHERIT = 8;
	 private static final int CMD_STOCK= 9;
	 private static final int CMD_RECEIVE_INTEREST = 10;
	 private static final int CMD_INTEREST = 11;
	 private static final int CMD_BEAUTYCONTEST = 12;
	 private static final int CMD_BIRTHDAY = 13;
	 private static final int CMD_JAILFREE=14;
	 private static final int CMD_FINE = 15;
	 static int numb;
		
	 private final static String[] CARDS = 
			{		"Advance to Go",
					"Go back to Old Kent Road",
					"Go to jail. Move directly to jail. Do not pass Go. Do not collect £200."
					,"Pay hospital £100."
					,"Doctor's fee. Pay £50."
					,"Pay your insurance premium £50."
					,"Bank error in your favour. Collect £200."
					,"Annuity matures. Collect £100."
					,"You inherit £100."
					,"From sale of stock you get £50."
					,"Receive interest on 7% preference shares: £25."
					,"Income tax refund. Collect £20."
					,"You have won second prize in a beauty contest. Collect £10."
					,"It is your birthday. Collect £10 from each player."
					,"Get out of jail free. This card may be kept until needed or sold."
					,"Pay a £10 fine or take a Chance"
			};
		 
		
		 
		 Community_Chest_Card() {
			super();
			
		    
			return;
		}
		public void setCardNummber(int a,int[]z)
		{
			
			Community_Chest_Card.numb=z[a];
			
		}
		public int getCardNumber()
		{
			return numb;
			
		}
		public static String getCardMessage()
		{
			return CARDS[numb];
			
		}

	 public  Object[] ShuffleDeck(){
	  
		 Random randomGenerator = new Random();
		 while (numbers.size() < 16) {

		     int random = randomGenerator .nextInt(16);
		     if (!numbers.contains(random)) {
		         numbers.add(random);
		         
		     }
		 }
		 Object[] temp=numbers.toArray();
		 
		  
	
	        return temp;

		 
	}
	 public int  GettingCards(Object[] temp)
	 {
		 
		
		 
		 int cardnumber=(int) temp[0];
		 for(int j=0;j<temp.length-1;j++)
		 {
			 temp[j]=temp[j+1];
			 if(j==temp.length-1)
			 {
				 temp[j]=cardnumber;
			 }
			 
		 }
		return cardnumber;
		 
		 
	 }
//
//		    public static void main(String[] args) {
//	    	Card s = new Card();
//	    	int i=0;
//	    	s.CardCond(	i,s.ShuffleDeck());
//		 
//	    	
//	    	
//
//}
	 
	 //left
//	 Get out of jail free. This card may be kept until needed or sold.	 
		    public void CardCond(int i,Object[] temp)
		    {
		
		    	  
		    	Community_Chest_Card s=new Community_Chest_Card();
		    int[] list=new int[16];
		    	
		    	for(int k=0;k<temp.length;k++)
		    	{
		    	 list[k]=s.GettingCards(temp);
		    	 System.out.println(list[k]);
		    	}
		    	
		    	
		    	s.setCardNummber(i,list);
		    	
		    	System.out.println(s.getCardNumber());
		    	
		    	
		    	switch (s.getCardNumber())
		    	{
		    	case CMD_GO:
		    	System.out.println( CARDS[s.getCardNumber()]);
		    	   		     Monopoly.GoToGo();
		  
		    		break;
		    	case CMD_GO_KENT:
		      	
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		
		    		Monopoly.GoToOLD();
		    		break;
		    	case CMD_JAIL:
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.GoToJail();
		    		break;
		    	
		    	case CMD_PAY_HOSPITAL :
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.PayHospital();
		    		break;
		    	
		    	case CMD_PAY_DOCTOR :
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.PayDoctorFee();
		    		break;
		    	
		    	case CMD_PAY_INSURENCE:
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.PayDoctorinsurence();
		    		break;
		    	
		    	case CMD_BANKERROR:
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.BankError();
		    		break;
		    	
		    	case  CMD_ANNUITY :
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.Amunity();
		    		break;
		    	
		    	case  CMD_INHERIT :
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.Inheritence();
		    		break;
		    	
		    	case CMD_STOCK:
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.Stock();
		    		break;
		    	
		    	case CMD_RECEIVE_INTEREST :
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.Interest();
		    		break;
		    	
		    	case CMD_INTEREST:
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.RefundInterest();
		    		break;
		    	
		    	case CMD_BEAUTYCONTEST :
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.BeautyPresent();
		    		break;
		    	
		    	case CMD_BIRTHDAY :
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.BDPresent();
		    		break;
		    	
		    	case CMD_JAILFREE:
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.GetOJail();
		    		break;
		    	
		    	case CMD_FINE:
		    		System.out.println( CARDS[s.getCardNumber()]);
		    		Monopoly.PayOrChance();
		    	break;


		    	}
		
		
	}

}


