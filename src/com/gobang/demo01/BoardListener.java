package com.gobang.demo01;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 现对GoBangframe下棋界面的监听接口处理
 *
 * @author fsyj
 */
public class BoardListener implements Config, MouseListener {

    private Brain brain;

    private boolean isGameOver = false;
    private Frame frame;

    /**
     * 下棋的方
     */
    public Player playerTurn = Player.BLACK;

    public BoardListener(Frame frame) {
        this.frame = frame;
        brain = new Brain(frame);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isGameOver) {
            frame.showGameOver(playerTurn);
            return;
        }


        // 获取点击的坐标
        int x = e.getX() - START_X;
        int y = e.getY() - START_Y;
        // 获取点位坐标
        int siteX = Math.round((x - OFFSET) * 1.0f / GRID_SIZE);
        int siteY = Math.round((y - OFFSET) * 1.0f / GRID_SIZE);

        if (frame.isAvailable(siteX, siteY)) {
            // 玩家落子
            if (putChess(siteX, siteY)) {
                return;
            }

            // AI下棋
            // 获取棋盘剩余位置的权值
            brain.getWeight();
            // 取得权值最大的位置
            int[] location = brain.getLocation();
            putChess(location[1],location[0]);
        }
    }

    /**
     * 绘制棋子的方法
     *
     * @param g
     * @param xPoint
     * @param yPoint
     */
    private void drawChess(Graphics g, int xPoint, int yPoint) {
        g.setColor(playerTurn == Player.BLACK ? Color.BLACK : Color.WHITE);
        g.fillOval(xPoint, yPoint, PIECE_SIZE, PIECE_SIZE);
    }

    /**
     * 判断是否结束
     *
     * @param siteX 最后一步的X
     * @param siteY 最后一步的Y
     * @return
     */
    private boolean isOver(int siteX, int siteY) {
        // 横竖撇捺方向的判断
        int flag = playerTurn.getFlag();
        // 横
        if (isOverInDirection(Math.max(siteX - 4, 0), siteY, Math.min(siteX + 4, COLUMN), siteY, 1, 0, flag)) {
            return true;
        }
        // 竖
        if (isOverInDirection(siteX, Math.max(siteY - 4, 0), siteX, Math.min(siteY + 4, ROW), 0, 1, flag)) {
            return true;
        }
        // 捺，这里不可以直接减，否则会有BUG
        int startOffset = getTargetSite(siteX, siteY, -1, -1);
        int endOffset = getTargetSite(siteX, siteY, 1, 1);
        if (isOverInDirection(siteX - startOffset, siteY - startOffset, siteX + endOffset,
                siteY + endOffset, 1, 1, flag)) {
            return true;
        }
        // 撇，注意事项同上
        startOffset = getTargetSite(siteX, siteY, 1, -1);
        endOffset = getTargetSite(siteX, siteY, -1, 1);
        return isOverInDirection(siteX + startOffset, siteY - startOffset,
                siteX - endOffset, siteY + endOffset, -1, 1, flag);
    }

    /**
     * 获取指定方向上的偏移量
     *
     * @param siteX
     * @param siteY
     * @param xOffset
     * @param yOffset
     * @return 这个方向上的偏移量
     */
    private static int getTargetSite(int siteX, int siteY, int xOffset, int yOffset) {
        int offset = 0;
        for (int i = 0; i < 4; i++) {
            if (!Frame.isEffective(siteX, siteY)) {
                break;
            }
            siteX += xOffset;
            siteY += yOffset;
            offset++;
        }
        return offset;
    }


    /**
     * 判断给定方向上是否游戏结束 -- 待测试
     *
     * @param startX     起点坐标
     * @param startY
     * @param endX       终点坐标
     * @param endY
     * @param xOffset    x的偏移量
     * @param yOffset    y的偏移量
     * @param playerFlag 玩家标志
     * @return true:游戏结束
     */
    private boolean isOverInDirection(int startX, int startY, int endX, int endY, int xOffset, int yOffset, int playerFlag) {
        int curX = startX;
        int curY = startY;
        int count = 0;

        boolean isOk = false;
        while (!isOk) {
            int curChess = frame.getChess(curX, curY);
            if (curChess == -1) {
                return false;
            }
            if (count >= 5) {
                return true;
            }

            // 当到达终点
            if (curX == endX && curY == endY) {
                isOk = true;
            }


            if (frame.getChess(curX, curY) == playerFlag) {
                count++;
            } else {
                // 重置计数器
                count = 0;
            }
            // 坐标偏移
            curX += xOffset;
            curY += yOffset;
        }
        return count >= 5;
    }


    public void newGame() {
        isGameOver = false;
    }

    /**
     * 落子的方法，在方法中并不会交换选手，所以需要在调用方法后手动交换
     * @param siteX
     * @param siteY
     * @return
     */
    private boolean putChess(int siteX, int siteY) {
        Graphics g = frame.getGraphics();
        frame.putChess(siteX, siteY, playerTurn);
        // 获取棋盘位置在窗口中的相对坐标
        int xPoint = siteX * GRID_SIZE + START_X - 15;
        int yPoint = siteY * GRID_SIZE + START_Y - 15;
        drawChess(g, xPoint, yPoint);
        boolean over = isOver(siteX, siteY);
        // 设置游戏结束标识
        if (over) {
            isGameOver = true;
            frame.showGameOver(playerTurn);
            return true;
        }
        playerTurn = Player.change(playerTurn);
        return false;
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


}