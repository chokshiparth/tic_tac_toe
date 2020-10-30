import java.util.Scanner;
//----------------------------------------------------------------------
//board of tictoctoe game
class Box
{
	private int position;
	public char value;
	public static int connectedNodes[][];
	void setValue(int name)
	{
		this.value='-';
		this.position=name;
		//adjacency matrix for tictoctoe board
		connectedNodes=new int [9][];
		connectedNodes[0]=new int[] {2,4,5};//1
		connectedNodes[1]=new int[] {1,3,5};//2
		connectedNodes[2]=new int[] {2,6,5};//3
		connectedNodes[3]=new int[] {1,7,5};//4
		connectedNodes[4]=new int[] {1,2,3,4};//5
		connectedNodes[5]=new int[] {3,9,5};//6
		connectedNodes[6]=new int[] {4,8,5};//7
		connectedNodes[7]=new int[] {7,9,5};//8
		connectedNodes[8]=new int[] {6,8,5};//9
	}
	//setSign
	void setEle(char element)
	{
		this.value=element;
	}
	//getSign function 
	char getSign()
	{
		return value;
	}
}
//----------------------------------------------------------------------
//board class
abstract class Board
{
	static Box tictocBoard[]=new Box[9];
	Board()
	{
		for(int i=0;i<9;i++)
		{
			tictocBoard[i]=new Box();
			tictocBoard[i].setValue(i+1);
		}
	}
	//set user sign
	public int setSign(char sign,int pos)
	{
		if(tictocBoard[pos].value=='-')
		{
			tictocBoard[pos].setEle(sign);
			return 0;
		}
		else
		{
			System.out.println("Alreay Exist");
			return 1;
		}
	}
	//display board
	public void displayBoard()
	{
		for(int i=0;i<9;i++)
		{
			System.out.print(tictocBoard[i].getSign()+" ");
			if(i==2 || i==5 || i== 8)
				System.out.println();
		}
	}
	//calculate score
	public int calcScore(char sign,int pos)
	{
		int score=-1,flag;
		//if position is even
		if(pos%2==0)
		{
			int compare1=tictocBoard[pos-1].connectedNodes[pos-1][0];//left or right node
			int compare2=tictocBoard[pos-1].connectedNodes[pos-1][1];//left or right node
			if(tictocBoard[compare1-1].value==sign && tictocBoard[compare2-1].value==sign)//compare left and right node
				score=3;
			else//compare horizontal vertical nodes
			{
				int compare3=tictocBoard[pos-1].connectedNodes[pos-1][2];//middle node
				if(compare3 > pos)
				{
					int compare4=(compare3+(compare3-pos));
					if(tictocBoard[compare3-1].value==sign && tictocBoard[compare4-1].value==sign)
						score=3;
				}
				else
				{
					int compare4=(compare3-(pos-compare3));
					if(tictocBoard[compare3-1].value==sign && tictocBoard[compare4-1].value==sign)
						score=3;
				}
			}
		}
		//if position is odd
		else if(pos%2!=0 && pos!=5)
		{
			for(int i=0;i<3;i++)
			{
				int compare1=tictocBoard[pos-1].connectedNodes[pos-1][i];
				if(compare1 > pos)
				{
					if(tictocBoard[compare1-1].value==sign)
					{
						int compare2=(compare1+(compare1-pos));
						if(tictocBoard[compare1-1].value==sign && tictocBoard[compare2-1].value==sign)
						{
							score=3;
							break;
						}
					}
				}
				else
				{
					if(tictocBoard[compare1-1].value==sign)
					{
						int compare2=(compare1-(pos-compare1));
						if(tictocBoard[compare1-1].value==sign && tictocBoard[compare2-1].value==sign)
						{
							score=3;
							break;
						}
					}
				}
			}
		}
		//if position is 5
		else if(pos==5)
		{
			for(int i=0;i<4;i++)
			{
				int compare1=tictocBoard[pos-1].connectedNodes[pos-1][i];
				if(tictocBoard[compare1-1].value==sign)
				{
					int compare2=(pos+(pos-compare1));
					if(tictocBoard[compare1-1].value==sign && tictocBoard[compare2-1].value==sign)
					{
						score=3;
						break;
					}
				}
			}
		}
		return score;
	}
}
//----------------------------------------------------------------------
//user class
class User extends Board
{
	private int score;
	private char sign;
	User(){}
	User(char sign)
	{
		score=0;
		this.sign=sign;
	}
	//set score
	public int setScore(int pos)
	{
		this.score=calcScore(this.sign,pos);
		return score;
	}
	//return sign
	public char getSign(){
		return this.sign;
	}
}
//----------------------------------------------------------------------
class game
{
	public static void main(String args[])
	{
		Scanner s1 = new Scanner(System.in);
		User u1= new User('X');
		User u2= new User('O');
		u1.displayBoard();
		System.out.print("USER 1 -> ");
		u1.setSign(u1.getSign(),s1.nextInt()-1);
		u1.displayBoard();
		for(int i=1;i<9;i++)
		{
			if(i%2==0)
			{
				System.out.print("USER 1 -> ");
				int pos=s1.nextInt();
				int flag=u1.setSign(u1.getSign(),pos-1);
				if(flag==0)
				{
					u1.displayBoard();
					if(i>3){
						int s=u1.setScore(pos);
						if(s==3)
						{
							System.out.println("********** USER 1 WON **********");
							break;
						}
					}
				}
				else
					i--;
			}
			else
			{
				System.out.print("USER 2 -> ");
				int pos=s1.nextInt();
				int flag=u2.setSign(u2.getSign(),pos-1);
				if(flag==0)
				{
					u2.displayBoard();
					if(i>3){
						int s=u2.setScore(pos);
						if(s==3)
						{
							System.out.println("********** USER 2 WON **********");
							break;
						}
					}
				}
				else
					i--;
			}
		}
	}
}
