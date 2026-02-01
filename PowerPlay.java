import java.util.Scanner;
import java.util.Random;

//Board + Game logic.
class TicTacToe 
{
    //make board static so Player classes can access it via class name.
    static char[][] board;

    public TicTacToe() 
    {
        board = new char[3][3];   //Object creation for board.
        initBoard();              //empty method for immediate initialization
    }

    //Fill Spaces in Board.
    static void initBoard() 
    {
        for(int i=0; i<board.length; i++)
        {
            for(int j=0; j<board[i].length; j++)
            {
                board[i][j] = ' ';
            }
        }
    }

    //Print empty Game Board.
    static void dispBoard()
    {
        System.out.println("-------------");
        for(int i=0; i<board.length; i++)
        {
            System.out.print("| ");
            for(int j=0; j<board[i].length; j++)
            {
                System.out.print(board[i][j] + " | "); 
            }
            System.out.println();
            System.out.println("-------------");
        }
    }


    //Placing X & O at (row, col) if within bounds.
    static void placeMark(int row, int col, char mark)
    {
        if((row >=0 && row <board.length && col >=0 && col <board[row].length) && (board[row][col] == ' '))
        {
            board[row][col] = mark;  
        }
        else
        {
            System.out.println("Invalid Position...");

        }
    }


    //column-wise win checking.
   static boolean checkColumnWin()
    {
        for(int j=0; j<board[0].length; j++) 
        {
            if(board[0][j] != ' ' && 
                board[0][j] == board[1][j] && 
                board[1][j] == board[2][j])
            {
                return true;
            }
        }
        return false;
    }

    
    //row-wise win checking.
    static boolean checkRowWin()
    {
        for(int i=0; i<board.length; i++) 
        {
            if(board[i][0] != ' ' && 
                board[i][0] == board[i][1] && 
                board[i][1] == board[i][2])
            {
                return true;
            }
        }
        return false;
    }

    //diagonal-wise win checking
    static boolean checkDiagonalWin()
    {
        if((board[0][0] != ' ' && 
            board[0][0] == board[1][1] && 
            board[1][1] == board[2][2] ) || 
            (board[0][2] != ' ' && 
            board[0][2] == board[1][1] && 
            board[1][1] == board[2][0]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //Draw checking (no empty spaces)
    static boolean checkDraw()
    {
        for(int i=0; i<board.length; i++)
        {
            for(int j=0; j<board[i].length; j++)
            {
                if(board[i][j] == ' ')
                {            
                    return false;      //at least one empty cell left
                }
            }
        }
        return true;     //board full and no winner
    }

}


//common parent for all players
abstract class Players
{
    String name;
    char mark;

    abstract void makeMove();

    // true if (row, col) inside board and empty
     boolean isValidMove(int row, int col)
    {
        if(row >=0 && row <TicTacToe.board.length && col >=0 && col <TicTacToe.board[row].length)
        {
            if(TicTacToe.board[row][col] == ' ')
            {
                return true;
            }
        }
        return false;
    }

}


// Human player implementation
class HumanPlayer extends Players
{
    static Scanner scan = new Scanner(System.in);

    public HumanPlayer(String name, char mark)
    {
        this.name = name;
        this.mark = mark;
    }

    @Override
    void makeMove()
    {
        int row;
        int col;

        // keep asking until user enters a valid empty cell
        do
        {
            System.out.println("Enter the row and col");
        row = scan.nextInt();
        col = scan.nextInt();
        } while(!isValidMove(row, col));

        TicTacToe.placeMark(row, col, mark);

    }

}


// simple AI: random valid move
class AIPlayer extends Players
{
    public AIPlayer(String name, char mark)
    {
        this.name = name;
        this.mark = mark;
    }

    @Override
    void makeMove()
    {
        Random r = new Random();
        int row;
        int col;

        // generate random cells until a valid one is found
        do
        {
             row = r.nextInt(TicTacToe.board.length);  // 0-2
             col = r.nextInt(TicTacToe.board[row].length);  // 0-2
             
        } while(!isValidMove(row, col));

        TicTacToe.placeMark(row, col, mark);
    }
}


// main driver class
public class PowerPlay {

    public static void main(String[] args) {

        // create game and show empty board
       TicTacToe t = new TicTacToe();
        
        // choose mode:
        // Human vs Human
       HumanPlayer p1 = new HumanPlayer("Bob", 'X');
        HumanPlayer p2 = new HumanPlayer("Priya", 'O');

        // Human vs AI
       // HumanPlayer p1 = new HumanPlayer("Bob", 'X');
      //  AIPlayer p2 = new AIPlayer("TAP", 'O');

        Players cp;
        cp = p1;   // X always starts

        while (true) {
            System.out.println(cp.name + " turn");
        cp.makeMove();

        TicTacToe.dispBoard();

        // checking for Win.
        if(TicTacToe.checkColumnWin() || 
        TicTacToe.checkRowWin() || 
        TicTacToe.checkDiagonalWin() )
        {
            System.out.println(cp.name + " has won!");
            break;
        }
        else if(TicTacToe.checkDraw())    // checking for draw
        {
            System.out.println("Game is a draw");
            break;
        }
        else                        // switch player
        {
            if(cp == p1)
            {
                cp = p2;
            }
            else
            {
                cp = p1;
            }
        }
        }
        
    }
    
}

