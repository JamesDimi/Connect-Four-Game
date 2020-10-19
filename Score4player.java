/*
 * Äçìçôñçò Äçìçôñáêïðïõëïò-3130053
 * Ëåùíéäáò Ðáððáò-3130166
 * Áñãõñçò Ñïõóôåìçò-3130180
 */

import java.util.ArrayList;

public class Score4player {
	
	private final int MAXDEPTH=2;
	Board aigameboard;
	public Score4player(Board gameboard){
		aigameboard=new Board(gameboard);
	}
	
	public Board minimax(Board gameboard){

		gameboard.makeChildren(gameboard.currentplayer);
		ArrayList<Board> children=new ArrayList<Board>();
		children = gameboard.getChildren();
		
		double value = max(gameboard, 0);

		for(Board child: children){
			if(child.getValue() == value){
				return child;
			}
		}
		// gameboard.setGameboard(3, "O");
		
		return gameboard;
	}
	private double min(Board gameboard,int depth){
		if(gameboard.isTerminal()||depth>=MAXDEPTH){
			return gameboard.evaluate();
		}else{
			gameboard.makeChildren("X");
			ArrayList<Board> children=new ArrayList<Board>();
			children=gameboard.getChildren();
			
			for(Board child:children){
				child.setValue(max(child,depth+1));
			}
			double min=children.get(0).getValue();
			
			for(Board child:children){
				if(min>child.getValue()){
					min=child.getValue();
				}
			}
			return min;
		}
	}
	private double max(Board gameboard,int depth){
		if(gameboard.isTerminal()||depth>=MAXDEPTH){
			return gameboard.evaluate();
		}else{
			gameboard.makeChildren("O");
			ArrayList<Board> children=new ArrayList<Board>();
			children=gameboard.getChildren();
			
			for(Board child:children){
				child.setValue(min(child,depth+1));
			}
			double max=children.get(0).getValue();
			for(Board child:children){
				if(max<child.getValue()){
					max=child.getValue();
				}
			}
			return max;
		}
		
	}
}
	

