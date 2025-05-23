import java.util.*;
public class RailFenceAlg {
	
	public RailFenceAlg()
	{
		super();
	}
	
	public static String encrypt(String str, Integer rails)
	{
		StringBuilder sb = new StringBuilder();
		char [][]fence = new char[rails][str.length()];
		int r=0; int c=0; //red i kolona
		boolean flag=false;
	
		for(int i=0; i<rails; i++)
		{
			for(int j=0; j<str.length();j++)
			{
				fence[i][j]=' ';
			}
		}
	
		
		for (int i = 0; i < str.length(); i++) {
			 fence[r][c++]=str.charAt(i);
            if (r == 0 || r == rails - 1) {
                flag = !flag;
            }
            
           
            
            if(flag)
            {
            	r++;
            }
            else
            {
            	r--;
            }
            
		} 
            
            for(int i1=0; i1<rails; i1++)
            {
            	for(int j=0;j<str.length();j++)
            	{
            		if(fence[i1][j]!= ' ')
            		{
            			sb.append(fence[i1][j]);
            		}
            	}
            }
            
        String res=sb.toString();    
		return res;
		
	
	}
	
	 public String toString()
	  {
		  return "Rail fence";
	  }
	

}
	

