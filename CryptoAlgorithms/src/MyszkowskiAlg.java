import java.util.*;
public class MyszkowskiAlg {
  public MyszkowskiAlg()
  {
	  super();
  }
  
  public static String encrypt(String text, String key)
  {
	  StringBuilder sb = new StringBuilder();
	  int keyLen = key.length();
	  int keyorder[]=new int[keyLen];
	  
	  for(int i=0; i<keyLen; i++)
	  {
		  keyorder[i]=i;
	  }
	  
	  for(int i=0; i<keyLen-1; i++)
	  {
		  for(int j=i+1; j<keyLen; j++)
		  {
			  if(key.charAt(keyorder[i]) > key.charAt(keyorder[j]))
			  {
				  int pom=keyorder[i];
				  keyorder[i]=keyorder[j];
				  keyorder[j]=pom;
			  }
		  }
	  }
	  
	  int rows=text.length()/keyLen+1;
	  char[][]matrix=new char[rows][keyLen];
	  int index=0;
	  
	  
	  for(int col: keyorder)
	  {
		  for(int r=0; r<rows; r++)
		  {
			  if(index<text.length())
			  {
				  matrix[r][col]=text.charAt(index++);
			  }
			  else
			  {
				  matrix[r][col]='X';
			  }
		  }
	  }
	  
	  
	  for(int r=0; r<rows; r++)
	  {
		  for(int col: keyorder)
		  {
			  sb.append(matrix[r][col]);
		  }
		 
	   }
	  
	  
	  
	  return sb.toString();
  }
  
  public String toString()
  {
	  return "Myszkowski";
  }
  
}
