import java.util.*;
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}

class Game {
    Board board;
    Player p1;
    Player p2;
    Player currentPlayer;
    public Game() {
        board = new Board();
        p1 = new Player(-1);
        p2 = new Player(1);
        currentPlayer = p1;
    }
    public void reset(){
        board = new Board();
        currentPlayer = p1;
    }
    public int getScore(Player player){
        return player.getValue() * board.getWinner() * board.emptyCells();
    }
    public void play() {
        int moves = 0;
        while(board.getWinner()==0 && moves++ < 9) {
            board.print();
            System.out.print(currentPlayer.getSymbol() + "'s turn: ");
            int[] move = currentPlayer.getMove();
            while(!board.isValid(move)){
                System.out.println("Invalid move.");
                System.out.print(currentPlayer.getSymbol() + "'s turn: ");
                move = currentPlayer.getMove();
            }
            if(board.isValid(move)){
                board.move(move,currentPlayer.getValue());
            }
            switchPlayer();
        }
        board.print();
        if(board.getWinner()==0){
            System.out.println("It's a tie!");
        }
        else if(board.getWinner()==1){
            System.out.println("Player O wins!");
        }
        else{
            System.out.println("Player X wins!");
        }
    }
    public void switchPlayer() {
        if(currentPlayer == p1) {
            currentPlayer = p2;
        }
        else{
            currentPlayer = p1;
        }
    }
}

class Player {
    private final int value;
    private final Bot bot;
    private final Scanner sc;
    public Player(int symbol) {
        value = symbol;
        bot = new Bot("data"+getSymbol()+".csv");
        sc = new Scanner(System.in);
    }
    public int getValue() {
        return value;
    }
    public char getSymbol() {
        return value == 1 ? 'O' : 'X';
    }
    public int[] getMove() {
        int[] move = new int[2];
        move[0] = sc.nextInt();
        move[1] = sc.nextInt();
        return move;
    }
}

class Board{
    private final int[][] grid = new int[3][3];
    public void move(int[] move, int val){
        move(move[0],move[1],val);
    }
    public void move(int row, int col, int val){
        grid[row][col] = val;
    }
    public int getWinner(){
        int[][] groups = new int[][]{
                {0,1,2},
                {3,4,5},
                {6,7,8},
                {0,3,6},
                {1,4,7},
                {2,5,8},
                {0,4,8},
                {2,4,6}
        };
        for(int[] group : groups){
            if(grid[group[0]/3][group[0]%3] == grid[group[1]/3][group[1]%3] && grid[group[1]/3][group[1]%3] == grid[group[2]/3][group[2]%3]){
                return grid[group[0]/3][group[0]%3];
            }
        }
        return 0;
    }
    public boolean isValid(int[] move){
        return isValid(move[0],move[1]);
    }
    public boolean isValid(int row, int col){
        if(row<0 || row>=grid.length || col<0 || col>=grid[0].length) return false;
        return grid[row][col] == 0;
    }
    public int emptyCells(){
        int count = 0;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(grid[i][j] == 0) count++;
            }
        }
        return count;
    }
    public void print(){
        for(int[] row : grid){
            System.out.println();
            for(int col : row){
                char symbol = '-';
                if(col==-1) symbol = 'X';
                if(col==1) symbol = 'O';
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }
}