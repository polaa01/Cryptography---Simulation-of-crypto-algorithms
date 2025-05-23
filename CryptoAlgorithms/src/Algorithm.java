import java.util.*;
public class Algorithm {
 
	public Algorithm()
	{
		super();
	}
	
	public static String railFenceAlg()
	{
		/*
		String text="";
		int depth=0;
		Scanner scan = new Scanner(System.in);
		System.out.println("Unesite tekst: ");
		 text = scan.nextLine();
		System.out.println("Unesite broj kolosijeka: ");
		 depth = scan.nextInt();
		
		   int r=depth;
		  int length=text.length();
		  int c=length/depth;
		  char mat[][]=new char[r][c];
		  int k=0;
		  
		  String cipher = "";
		  
		  for(int i=0;i< c;i++)
		  {
		   for(int j=0;j< r;j++)
		   {
		    if(k!=length)
		     mat[j][i]=text.charAt(k++);
		    else
		     mat[j][i]='X';
		   }
		  }
		  for(int i=0;i< r;i++)
		  {
		   for(int j=0;j< c;j++)
		   {
		    cipher+=mat[i][j];
		   }
		  }
		  
		  return cipher;
	}
	*/
		String message="";
		int rails=0;
		Scanner scan = new Scanner(System.in);
		System.out.println("Unesite tekst: ");
		 message = scan.nextLine();
		System.out.println("Unesite broj kolosijeka: ");
		 rails = scan.nextInt();
		char[][] matrix = createMatrix(message, rails);
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    encryptedMessage.append(matrix[i][j]);
                }
            }
        }

        return encryptedMessage.toString();		
}
	private static char[][] createMatrix(String message, int rails) {
        char[][] matrix = new char[rails][message.length()];
        int row = 0, col = 0;
        boolean directionDown = false;

        for (int i = 0; i < message.length(); i++) {
            if (row == 0 || row == rails - 1) {
                directionDown = !directionDown;
            }

            matrix[row][col++] = message.charAt(i);

            row += directionDown ? 1 : -1;
        }

        return matrix;
    }
	
}
