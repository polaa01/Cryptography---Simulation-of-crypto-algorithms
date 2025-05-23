import java.net.http.WebSocket.Builder;
import java.util.*;
public class PlayfairAlg {
	
	public static final int NUM_OF_FIELDS=5;
	private static char[][]keyTable;
	
	
	public PlayfairAlg()
	{
		super();
	}
	
	
	private static String removeDuplicates(String text)
	{
		StringBuilder sb = new StringBuilder();
		boolean[] flag=new boolean[256];
		for(char ch: text.toCharArray())
		{
			if(!flag[ch])
			{
				sb.append(ch);
				flag[ch]=true;
			}
		}
		return sb.toString();
	}
	
	private static int[] findPos(char c)
	{
		int[] pos = new int[2];
		//pos[0]=-1;
		//pos[1]=-1;
		for(int i=0; i<PlayfairAlg.NUM_OF_FIELDS; i++)
		{
			for(int j=0; j<PlayfairAlg.NUM_OF_FIELDS; j++)
			{
				if(keyTable[i][j] == c)
				{
					pos[0]=i;
					pos[1]=j;
					return pos;
				}
			}
		}
		return pos;
	}
	
	private static String encryptPair(char f, char s)
	{
		int firstPosition[] = findPos(f);
		int secondPosition[] = findPos(s);
		 if (firstPosition[0] == secondPosition[0]) {
	            
	            return "" + keyTable[firstPosition[0]][(firstPosition[1] + 1) % 5] + keyTable[secondPosition[0]][(secondPosition[1] + 1) % 5];
	        } else if (firstPosition[1] == secondPosition[1]) {
	            
	            return "" + keyTable[(firstPosition[0] + 1) % 5][firstPosition[1]] + keyTable[(secondPosition[0] + 1) % 5][secondPosition[1]];
	        } else {
	            
	            return "" + keyTable[firstPosition[0]][secondPosition[1]] + keyTable[secondPosition[0]][firstPosition[1]];
	        }
	}
	
	
	private static void genKeyTable(String key)
	{
		key=key.toUpperCase().replaceAll("[^A-Z]", "");
		keyTable = new char[NUM_OF_FIELDS][NUM_OF_FIELDS];
		
		String newKey = removeDuplicates(key);
		
		int k=0;
		for(int i=0; i<PlayfairAlg.NUM_OF_FIELDS; i++)
		{
			for(int j=0; j<PlayfairAlg.NUM_OF_FIELDS; j++)
			{
				if(k<newKey.length())
				{
					keyTable[i][j]=newKey.charAt(k);
					k++;
				}
			}
		}
		
		char firstChar='A';
		for(int i=0; i<PlayfairAlg.NUM_OF_FIELDS; i++)
		{
			for(int j=0; j<PlayfairAlg.NUM_OF_FIELDS; j++)
			{
				 if (keyTable[i][j] == 0) {
	                    while (key.indexOf(firstChar) >= 0) {
	                    	firstChar++;
	                    }
	                    keyTable[i][j]=firstChar++;
				 }
	                    
			}
		}
		
		
	}
	
	
	
	
	public static String encrypt(String text, String key)
	{
		StringBuilder sb = new StringBuilder();
		PlayfairAlg.genKeyTable(key);
		 text = text.toUpperCase().replaceAll("[^A-Z]", "");
		 
		  for (int i = 0; i < text.length(); i += 2) {
	            char first = text.charAt(i);
	            char second = (i + 1 < text.length()) ? text.charAt(i + 1) : 'X';
	            sb.append(encryptPair(first, second));
	        }
		return sb.toString();
	}
	
	
	 public String toString()
	  {
		  return "Playfair";
	  }

}
