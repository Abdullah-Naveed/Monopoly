//j is the index of the person that starts
			int index_Of_Player_That_Starts=j ;
			
			//Takes turns based on the player that starts
			switch(index_Of_Player_That_Starts){
			
			//Turns are taken as normal if person 0 starts
			case 0:
				for(i=j;i<player.size();i++){
					
					if(gameover) break;
					turn(player.get(i), i);
				}
				break;
			//If person 1 and above starts, turns are taken in ascending order then the person 0 takes their turn
			case 1:
				for(i=j;i<player.size();i++){
					if(gameover) break;
					turn(player.get(i), i);
					
				}
				
				for(int k=0;k<i;k++){
					if(gameover) break;
					turn(player.get(k), k);
				}
				break;
			
			case 2:
				for(i=j;i<player.size();i++){
					if(gameover) break;
					turn(player.get(i), i);
				}
				for(int k=0;k<i;k++){
					if(gameover) break;
					turn(player.get(k), k);
				}
				break;
			case 3:
				for(i=j;i<player.size();i++){
					if(gameover) break;
					turn(player.get(i), i);
				}
				for(int k=0;k<i;k++){
					if(gameover) break;
					turn(player.get(k), k);
				}
				break;
			
			case 4:
				for(i=j;i<player.size();i++){
					if(gameover) break;
					turn(player.get(i), i);
				}
				for(int k=0;k<i;k++){
					if(gameover) break;
					turn(player.get(k), k);
				}
				break;
			
			case 5:
				for(i=j;i<player.size();i++){
					if(gameover) break;
					turn(player.get(i), i);
				}
				for(int k=0;k<i;k++){
					if(gameover) break;
					turn(player.get(k), k);
				}
				break;