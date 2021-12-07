class Chess {
    public static final int CHESSBOARD_SIZE = 15;
    public static int FIRST = 1;//先手，-1表示机器，1表示人类
    private int[][] chessboard = new int[CHESSBOARD_SIZE][CHESSBOARD_SIZE];//与界面棋盘对应，0代表空，-1代表机器，1代表人类
    private int[][] score = new int[CHESSBOARD_SIZE][CHESSBOARD_SIZE];//每个位置得分
    /*
    横坐标x
    纵坐标y
    落子方owner
     */
    public Chess(){}

    public void init(){
        FIRST = 1;//默认人类先手
        for(int i = 0; i  < CHESSBOARD_SIZE; i++){
            for(int j = 0; j < CHESSBOARD_SIZE; j++){
                chessboard[i][j] = 0;//棋盘初始化
                score[i][j] = 0;//得分初始化
            }
        }
    }

//   落子
    public void putChess(int x,int y,int owner){
        chessboard[x][y]=owner;
    }
//    判断棋盘对应处是否可落子
    public boolean isEmpty(int x,int y){
        if(x>=0 && x<CHESSBOARD_SIZE && y>=0 && y<CHESSBOARD_SIZE && chessboard[x][y]==0)
            return true;
        else
            return false;
    }
//    owner在（x，y）落子后，判断owner是否获胜
    public boolean isWin(int x,int y,int owner){
        int count = 0;//记录是否有五连珠

        //判断纵向是否有五连珠
        for (int i = x - 1; i >= 0; i--){
            if(chessboard[i][y] == owner)
                count++;
            else
                break;
        }
        for (int i = x + 1; i < CHESSBOARD_SIZE; i++){
            if(chessboard[i][y] == owner)
                count++;
            else
                break;
        }
        if (count>4)
            return true;
        //判断横向是否有五连珠
        count = 0;
        for (int j = y - 1; j >= 0; j--){
            if(chessboard[x][j] == owner)
                count++;
            else
                break;
        }
        for (int j = y + 1; j < CHESSBOARD_SIZE; j++){
            if(chessboard[x][j] == owner)
                count++;
            else
                break;
        }
        if(count>4)
            return true;
        //判断右上左下是否有五连珠
        count=0;
        for(int i=x-1,j=y+1;i>=0 && j<CHESSBOARD_SIZE;i--,j++){
            if(chessboard[i][j] == owner)
                count++;
            else
                break;
        }
        for(int i=x+1,j=y-1;i<CHESSBOARD_SIZE && j>=0;i++,j--){
            if(chessboard[i][j] == owner)
                count++;
            else
                break;
        }
        if(count>4)
            return true;
        //判断左上右下是否有五连珠
        count=0;
        for(int i=x-1,j=y-1;i>=0 && j>=0;i--,j--){
            if(chessboard[i][j] == owner)
                count++;
            else
                break;
        }
        for(int i=x+1,j=y+1;x<CHESSBOARD_SIZE && y<CHESSBOARD_SIZE;i++,j++){
            if(chessboard[i][j] == owner)
                count++;
            else
                break;
        }
        if(count>4)
            return true;
        return false;
    }
}
