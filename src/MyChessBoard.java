public class MyChessBoard {
//    棋盘长宽
    public final int N = 15;
//    state
    public final int EMPTY = 0;
    public final int BLACK = 1;
    public final int WHITE = 2;
    public int[][] board = new int[N+1][N+1];
//    默认构造函数
    MyChessBoard(){}
    private static final MyChessBoard chess = new MyChessBoard();

//    返回一个新的类单例
    static MyChessBoard getInstance(){
        return chess;
    }

//    判断是否有子
    public boolean isEmpty(int x,int y){
        return board[x][y] == EMPTY;
    }

//    落子
    public void makeMove(int x,int y,int color){
        board[x][y]=color;
    }

//    判断游戏是否结束 0未结束 1白棋赢 2黑棋赢
    public int isGameOver(int x,int y,int color){
//        这两个数组表示方向，因为坐标要同时操作，+时代表{上,右，右上，右下},-时则是另外4个方向
        int dx[] = {1,0,1,1};
        int dy[] = {0,1,1,-1};
        for(int i=0;i<4;i++){
            int sum = 1;
            int cx = x + dx[i];
            int cy = y + dy[i];
            while(cx>0&&cx<=N
                && cy>0 && cy<=N
                && board[cx][cy] == color
            ){
                cx+=dx[i];
                cy+=dy[i];
                ++sum;
            }
            cx=x-dx[i];
            cy=y-dy[i];
            while(cx>0&&cx<=N
                    && cy>0 && cy<=N
                    && board[cx][cy] == color
            ){
                cx-=dx[i];
                cy-=dy[i];
                ++sum;
            }
            if(sum>=5){
                return color;
            }
        }
        return 0;
    }


}
