package DroneZone;

import java.util.*;

public class DroneZone {
	public static void main(String args[])
	{
		System.out.println("enter number of rows");
		Scanner sc=new Scanner(System.in);
		int row=sc.nextInt();//taking grid row size
		System.out.println("enter number of rows");
		int col=sc.nextInt();//taking grid coloumn size
		int[][] drones=new int[4][2];
		int[] t=new int[2];
		System.out.println("You have to enter 4 drones positions");
		//taking drone inputs and storing it in a 4*2 matrix
		for(int i=0;i<4;i++)
		{
				System.out.println("enter drown "+(i+1)+" row:(starts from 0)");
				int r=sc.nextInt();
				if(r>=row)
				{
					System.out.println("cannot create out of bounds");
					return;
				}
				else
					drones[i][0]=r;
				System.out.println("enter drown "+(i+1)+" coloumn:(starts from 0)");
				int c=sc.nextInt();
				if(c>=col)
				{
					System.out.println("cannot create out of bounds");
					return;
				}
				else
					drones[i][1]=c;
		}
		//taking target from user
		System.out.println("enter target row:(starts from 0)");
		t[0]=sc.nextInt();
		System.out.println("enter target coloumn:(starts from 0)");
		t[1]=sc.nextInt();
		for(int i=0;i<4;i++)
		{
			System.out.print("Drone "+(i+1)+" path to target : "+printpath(drones[i],t));
			System.out.print("("+t[0]+","+t[1]+")");
			System.out.println();
		}
		
	}
	public static String printpath(int[] d,int[] t)
	{
		int x=d[0];//x of drone
		int y=d[1];//y of drone
		int tx=t[0];//x of target
		int ty=t[1];//y of target
		String res="";
		//there are 4 conditions where drone can be present with respect to the target
		//1 . where drone is above the target or  the column position is less than target coloumn
		if(x<tx || y<ty)
		{
			while(x<tx)
			{
				res+="("+x+","+y+")->";
				x+=1;
			}
			while(y<ty)
			{
				res+="("+x+","+y+")->";
				y+=1;
			}
		}
		//2 . where drone is below the target or the column position is greater than target coloumn
		else if(x>tx || y>ty )
		{
			while(x>tx)
			{
				res+="("+x+","+y+")->";
				x-=1;
			}
			while(y>ty)
			{
				res+="("+x+","+y+")->";	
				y-=1;
			}
		}
		//3 . where drone is below target or the column position is less than target coloumn
		else if(x>tx || y<ty)
		{
			while(x>tx)
			{
				res+="("+x+","+y+")->";
				x-=1;
			}
			while(y<ty)
			{
				res+="("+x+","+y+")->";
				y+=1;
			}
		}
		//4 .where drone is above the target or the column position is greater than target coloumn
		else if(x<tx || y>ty)
		{
			while(x<tx)
			{
				res+="("+x+","+y+")->";
				x+=1;
			}
			while(y>ty)
			{
				res+="("+x+","+y+")->";	
				y-=1;
			}
		}
		return res;
	}
}
