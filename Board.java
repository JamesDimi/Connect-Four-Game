/*
 * Δημητρης Δημητρακοπουλος-3130053
 * Λεωνιδας Παππας-3130166
 * Αργυρης Ρουστεμης-3130180
 */

import java.util.Scanner;
import java.util.ArrayList;

public class Board {

	public static String currentplayer=" ";
	public static int numberOfturns=0;
	public static String winner=" ";
	public static int 	rowBound=6;
	public static int  	colBound=7;
	private double value=0;
	private String gameboard[][]=new String[rowBound][colBound];//6 grammes kai 7 sthles
	private ArrayList<Board> children=new ArrayList<Board>();
	public Board(){
		for (int i=0;i<rowBound;i++){
			for(int j=0;j<colBound;j++){
				gameboard[i][j]=" ";
			}
		}
	}
	public Board(Board Nboard){
		for (int i=0;i<rowBound;i++){
			for(int j=0;j<colBound;j++){
				gameboard[i][j]=Nboard.getGameboard()[i][j];
			}
		}
	}
	public String[][] getGameboard() {
		return gameboard;
	}
	public void setGameboard(int pos,String player) {

		if(pos<0||pos>colBound-1){
			System.out.println("Eligible move : Out of bounds ");
			System.out.println("Please select another column from 0 to "+rowBound );
			
			Scanner in = new Scanner(System.in);
			int num = in.nextInt();
			
			setGameboard(num,player);
			return;
		}
		if(!this.gameboard[0][pos].equals(" ")){
			System.out.println("Eligible move : Column maxed out");
			System.out.println("Please select another column from 0 to "+rowBound );
			
			Scanner in = new Scanner(System.in);
			int num = in.nextInt();
			
			setGameboard(num,player);
		}
		for(int i=rowBound-1;i>=0;i--){
			
			if(this.gameboard[i][pos].equals(" ")){
				this.gameboard[i][pos]=player;
				
				break;
			}
		}
	}
	public void draw(){
		for(int i=0;i<rowBound;i++){
			if (i==0){
				System.out.println("---------------");
			}
			for(int j=0;j<colBound;j++){
				if (j==0){
					System.out.print("|");
				}
				System.out.print(gameboard[i][j]+"|");
				
			}
			System.out.println("");
			if(i==rowBound-1){
				System.out.println("---------------");
			}
		}
	}
	public ArrayList<Board> getChildren(){
		return children;
	}

	public void makeChildren(String player){
		//ArrayList<Board> list=new ArrayList<Board>();
		for(int j=0;j<colBound;j++){
			if(!gameboard[0][j].equals(" ")){
				continue;
			}else{
				Board newBoard=new Board(this);
				newBoard.setGameboard(j, player);
				children.add(newBoard);
			}
		}
	}
	public boolean isTerminal(){
		
		if(numberOfturns!=rowBound*colBound+1){
			
			//return false;//TODO
			//DIAGWNIES APO ARISTERA PROS DE3IA
			for(int i=0;i<=rowBound-4;i++){
				for(int j=0;j<=colBound-4;j++){
					if(!gameboard[i][j].equals(" ")){
						if((gameboard[i][j]==gameboard[i+1][j+1])&&(gameboard[i][j]==gameboard[i+2][j+2])&&(gameboard[i][j]==gameboard[i+3][j+3])){
							return true;
						}
					}else{
						continue;
					}
				}
			}
			//DIAGWNIES APO DE3IA PROS ARISTERA
			for(int i=0;i<=rowBound-4;i++){
				for(int j=colBound-1;j>=3;j--){
					if(!gameboard[i][j].equals(" ")){
						if((gameboard[i][j]==gameboard[i+1][j-1])&&(gameboard[i][j]==gameboard[i+2][j-2])&&(gameboard[i][j]==gameboard[i+3][j-3])){
							winner=currentplayer;
						
							return true;
						}
					}else{
						continue;
					}
				}
			}
			//KA8ETA
			for(int i=0;i<=rowBound-4;i++){
				for(int j=0;j<colBound;j++){
					if(!gameboard[i][j].equals(" ")){
						if((gameboard[i][j]==gameboard[i+1][j])&&(gameboard[i][j]==gameboard[i+2][j])&&(gameboard[i][j]==gameboard[i+3][j])){
							winner=currentplayer;
							
							
							return true;
						}
					}else{
						continue;
					}
				}
			}
			//ORIZONTIA
			for(int i=0;i<rowBound;i++){
				for(int j=0;j<=colBound-4;j++){
					if(!gameboard[i][j].equals(" ")){
						if((gameboard[i][j]==gameboard[i][j+1])&&(gameboard[i][j]==gameboard[i][j+2])&&(gameboard[i][j]==gameboard[i][j+3])){
							winner=currentplayer;
							
							return true;
						}
					}else{
						continue;
					}
				}
			}
		}else{
			winner = "none";
			return true;
		}
		return false;
	}

	public int whosQuad(String str){//0 tou O, 1 tou X, 2 kanenos
			if(str.contains("X")&&str.contains("O")){
				return 2;
			}else if (str.contains("X")){
				return 1;
			}else{
				return 0;
			}
	}
	private int numOfXO(String str, char player){ // returns the number of X or O the a string contains
		int counter = 0;
		for(int i = 0; i < str.length(); i++){
			if( str.charAt(i) == player){
				counter++;
			}
		}
		return counter;
	}
	
	public double evaluate(){

		for(int i = rowBound-1; i>=0; i--){
			if(i < 3){ 
				for(int j=0; j <= colBound -4; j++){//koitaei deksia(gia yellow)// *****koita orizontia *****
						if(gameboard[i][j].equals(" ")&&gameboard[i][j+1].equals(" ") && gameboard[i][j+2].equals(" ") && gameboard[i][j+3].equals(" ")){ // mia tetrada apo 4 kena pame sto epomeno keli..
							//System.out.println("kanw continue gia i="+i+" kai j=" +j );
							continue;
						}
						String str=gameboard[i][j]+gameboard[i][j+1]+gameboard[i][j+2]+gameboard[i][j+3];
						int sun8hkh=whosQuad(str);
						
						if(sun8hkh==0){
							int Onum=numOfXO(str,'O');
							
							if(Onum==1){
								value+=1;
							}else if(Onum==2){
								value+=5;
							}else if(Onum==3){
								value+=10;
							}else if(Onum==4){
								value+=100000;
							}
							//System.out.println("pi8anh tetrada O apo j="+ j+" ews " +(j+3) );// POSSA O EXEI 
						}else if(sun8hkh==1){
							int Onum=numOfXO(str,'X');
							if(Onum==1){
								value-=1;
							}else if(Onum==2){
								value-=5;
							}else if(Onum==3){
								value-=10;
							}else if(Onum==4){
								value-=100000;
							}
							//System.out.println("pi8anh tetrada X apo j="+ j+" ews " +(j+3) );
						}
				}
			}
		else{ // bottom-left des deksia/panw/plagia(pros panw deksia)
				for(int j=0; j <= colBound -4; j++){
					
					
						// *****koita orizontia gia red *****
						if(gameboard[i][j].equals(" ")&&gameboard[i][j+1].equals(" ") && gameboard[i][j+2].equals(" ") && gameboard[i][j+3].equals(" ")){ // mia tetrada apo 4 kena pame sto epomeno keli..
							continue;
						}
						String str=gameboard[i][j]+gameboard[i][j+1]+gameboard[i][j+2]+gameboard[i][j+3];
						int sun8hkh=whosQuad(str);
						if(sun8hkh==0){
							int Onum=numOfXO(str,'O');
							
							if(Onum==1){
								value+=1;
							}else if(Onum==2){
								value+=5;
							}else if(Onum==3){
								value+=10;
							}else if(Onum==4){
								value+=100000;
							}
							//System.out.println("pi8anh tetrada O apo j="+ j+" ews " +(j+3) );// POSSA O EXEI 
						}else if(sun8hkh==1){
							int Onum=numOfXO(str,'X');
							if(Onum==1){
								value-=1;
							}else if(Onum==2){
								value-=5;
							}else if(Onum==3){
								value-=10;
							}else if(Onum==4){
								value-=100000;
							}
							//System.out.println("pi8anh tetrada X apo j="+ j+" ews " +(j+3) );
						}
						
						
						// *****koita katheta gia red*****
						if(gameboard[i][j].equals(" ")&&gameboard[i-1][j].equals(" ") && gameboard[i-2][j].equals(" ") && gameboard[i-3][j].equals(" ")){ // mia tetrada apo 4 kena pame sto epomeno keli..
							continue;
						}
						str=gameboard[i][j]+gameboard[i-1][j]+gameboard[i-2][j]+gameboard[i-3][j];
						sun8hkh=whosQuad(str);
						if(sun8hkh==0){
							int Onum=numOfXO(str,'O');
							if(Onum==1){
								value+=1;
							}else if(Onum==2){
								value+=5;
							}else if(Onum==3){
								value+=10;
							}else if(Onum==4){
								value+=100000;
							}
							//System.out.println("pi8anh tetrada O apo j="+ j+" ews " +(j+3) );// POSSA O EXEI 
						}else if(sun8hkh==1){
							int Onum=numOfXO(str,'X');
							if(Onum==1){
								value-=1;
							}else if(Onum==2){
								value-=5;
							}else if(Onum==3){
								value-=10;
							}else if(Onum==4){
								value-=100000;
							}
							//System.out.println("pi8anh tetrada X apo j="+ j+" ews " +(j+3) );
						}
						
						// *****koita plagia deksia gia red*****
						if(gameboard[i][j].equals(" ")&&gameboard[i-1][j+1].equals(" ") && gameboard[i-2][j+2].equals(" ") && gameboard[i-3][j+3].equals(" ")){ // mia tetrada apo 4 kena pame sto epomeno keli..
							continue;
						}
						str=gameboard[i][j]+gameboard[i-1][j+1]+gameboard[i-2][j+2]+gameboard[i-3][j+3];
						sun8hkh=whosQuad(str);
						if(sun8hkh==0){
							int Onum=numOfXO(str,'O');
							if(Onum==1){
								value+=1;
							}else if(Onum==2){
								value+=5;
							}else if(Onum==3){
								value+=10;
							}else if(Onum==4){
								value+=100000;
							}
							//System.out.println("pi8anh tetrada O apo j="+ j+" ews " +(j+3) );// POSSA O EXEI 
						}else if(sun8hkh==1){
							int Onum=numOfXO(str,'X');
							if(Onum==1){
								value-=1;
							}else if(Onum==2){
								value-=5;
							}else if(Onum==3){
								value-=10;
							}else if(Onum==4){
								value-=100000;
							}
							//System.out.println("pi8anh tetrada X apo j="+ j+" ews " +(j+3) );
						}
					
				}	
			}
		}
		
		// Right Side evaluation(prasino)
		for(int i = rowBound-1; i>=3; i--){
			for(int j=colBound-1; j >= 3 ; j--){	
				// *****koita plagia*****
				if(gameboard[i][j].equals(" ")&&gameboard[i-1][j-1].equals(" ") && gameboard[i-2][j-2].equals(" ") && gameboard[i-3][j-3].equals(" ")){ // mia tetrada apo 4 kena pame sto epomeno keli..
					continue;
				}
				String str=gameboard[i][j]+gameboard[i-1][j-1]+gameboard[i-2][j-2]+gameboard[i-3][j-3];
				int sun8hkh=whosQuad(str);
				if(sun8hkh==0){
					int Onum=numOfXO(str,'O');
					if(Onum==1){
						value+=1;
					}else if(Onum==2){
						value+=5;
					}else if(Onum==3){
						value+=10;
					}else if(Onum==4){
						value+=100000;
					}
					//System.out.println("pi8anh tetrada O apo j="+ j+" ews " +(j+3) );// POSSA O EXEI 
				}else if(sun8hkh==1){
					int Onum=numOfXO(str,'X');
					if(Onum==1){
						value-=1;
					}else if(Onum==2){
						value-=5;
					}else if(Onum==3){
						value-=10;
					}else if(Onum==4){
						value-=100000;
					}
					//System.out.println("pi8anh tetrada X apo j="+ j+" ews " +(j+3) );
				}// pithani tetrada me toul ena "O"
			}	
		}
		for(int i = rowBound-1; i>=3; i--){
			for(int j=colBound-1; j >= colBound-3; j--){
				// *****koita katheta*****
				if(gameboard[i][j].equals(" ")&&gameboard[i-1][j].equals(" ") && gameboard[i-2][j].equals(" ") && gameboard[i-3][j].equals(" ")){ // mia tetrada apo 4 kena pame sto epomeno keli..
					continue;
				}
				String str=gameboard[i][j]+gameboard[i-1][j]+gameboard[i-2][j]+gameboard[i-3][j];
				int sun8hkh=whosQuad(str);
				if(sun8hkh==0){
					int Onum=numOfXO(str,'O');
					if(Onum==1){
						value+=1;
					}else if(Onum==2){
						value+=5;
					}else if(Onum==3){
						value+=10;
					}else if(Onum==4){
						value+=100000;
					}
					//System.out.println("pi8anh tetrada O apo j="+ j+" ews " +(j+3) );// POSSA O EXEI 
				}else if(sun8hkh==1){
					int Onum=numOfXO(str,'X');
					if(Onum==1){
						value-=1;
					}else if(Onum==2){
						value-=5;
					}else if(Onum==3){
						value-=10;
					}else if(Onum==4){
						value-=100000;
					}
					//System.out.println("pi8anh tetrada X apo j="+ j+" ews " +(j+3) );
				}// pithani tetrada me toul ena "O"
			}
		}
		return value;
	}
		
	
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
}
