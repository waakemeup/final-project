import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class MyFrame  {
    private MyChessBoard chess = MyChessBoard.getInstance();
    private int userColor = chess.WHITE;
    private int computerColor = chess.BLACK;

    Frame frame = new Frame("棋盘");
    private MyChess toDraw = new MyChess();


    public void init(){
        Panel panel = new Panel();
//        机器先下中间
        chess.makeMove(chess.N/2,chess.N/2,chess.BLACK);

        toDraw.setPreferredSize(new Dimension(720,720));

        toDraw.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int px = (x-60)/40 + 1;
                int py = (y-60)/40 + 1;
                System.out.println(px+"---"+py);
                if(chess.isEmpty(px,py)){
                    chess.makeMove(px,py,userColor);
                    int judge = chess.isGameOver(px,py,userColor);
                    if(judge!=0){
                        System.out.println("玩家胜利");
                        return;
                    }
//                    从父类Canvas继承，顾名思义，重绘
                    toDraw.repaint();

//                    这里应该有机器下棋的代码，暂时空缺



                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        frame.add(toDraw);
        frame.setSize(900,720);
        frame.setVisible(true);
    }
}

class MyChess extends Canvas {
    private MyChessBoard chess = MyChessBoard.getInstance();
    private final int N = chess.N + 2;
    private final int square = 40;
    private final int sx = square;
    private final int sy = square;

    private final int length = (N-1)*square;

//    定义颜色
    private final Color WHITE = new Color(255,255,255);
    private final Color BLACK = new Color(0,0,0);
    private final Color BGC = new Color(213, 194, 6, 243);

    public void drawStep(int color,int x,int y,Graphics g){
        if(color==chess.BLACK){
            g.setColor(BLACK);
            g.fillArc(sx+x*square-19,sy+y*square-19,38,38,0,360);
        }else if(color==chess.WHITE){
            g.setColor(WHITE);
            g.fillArc(sx+x*square-19,sy+y*square-19,38,38,0,360);
        }
    }

    public void paint(Graphics g){
        g.setColor(BLACK);
        g.fillRect(sx-8,sy-8,sx+(N-2)*square+15,sy+(N-2)+15);
        g.setColor(BGC);
        g.fillRect(sx-4,sy-4,sx+(N-2)*square+7,sy+(N-2)*square+7);

        g.setColor(BLACK);

        g.fillArc(sx+8*square-6, sy+8*square-6, 12, 12, 0, 360);
        g.fillArc(sx+4*square-6, sy+4*square-6, 12, 12, 0, 360);
        g.fillArc(sx+4*square-6, sy+12*square-6, 12, 12, 0, 360);
        g.fillArc(sx+12*square-6, sy+4*square-6, 12, 12, 0, 360);
        g.fillArc(sx+12*square-6, sy+12*square-6, 12, 12, 0, 360);

        for(int i = 0; i < N; i++) {

            g.drawLine(sx+i*square, sy, sx+i*square, sy+length);
            g.drawLine(sx, sy+i*square, sx+length, sy+i*square);
        }

        for(int i=1; i<=chess.N; i++) {
            for(int j=1; j<=chess.N; j++) {
                drawStep(chess.board[i][j], i, j, g);
            }
        }
    }
}
