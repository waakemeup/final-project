package com.gobang.demo01;

import sun.nio.cs.ext.MacArabic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 现对GoBangframe下棋界面的监听接口处理
 *
 * @author fsyj
 */
public class BoardListener implements Config, MouseListener {

    private boolean isGameOver = false;
    private Frame frame;

    /**
     * 下棋的方
     */
    public Player playerTurn = Player.BLACK;

    public BoardListener(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isGameOver) {
            frame.showGameOver(playerTurn);
            return;
        }


        Graphics g = frame.getGraphics();

        // 获取点击的坐标
        int x = e.getX() - START_X;
        int y = e.getY() - START_Y;
        // 获取点位坐标
        int siteX = Math.round((x - OFFSET) * 1.0f / GRID_SIZE);
        int siteY = Math.round((y - OFFSET) * 1.0f / GRID_SIZE);

        if (frame.isAvailable(siteX, siteY)) {
            frame.putChess(siteX, siteY, playerTurn);
            boolean over = isOver(frame, playerTurn, siteX, siteY);
            int xPoint = siteX * GRID_SIZE + START_X - 15;
            int yPoint = siteY * GRID_SIZE + START_Y - 15;
            // 绘制棋子
            if (playerTurn == Player.BLACK) {
                // 设置棋子颜色
                g.setColor(Color.BLACK);
                g.fillOval(xPoint, yPoint, PIECE_SIZE, PIECE_SIZE);
                playerTurn = Player.WHITE;
            } else {
                g.setColor(Color.WHITE);
                g.fillOval(xPoint, yPoint, PIECE_SIZE, PIECE_SIZE);
                playerTurn = Player.BLACK;
            }
            // 判断输赢
            if (over) {
                isGameOver = true;
                frame.showGameOver(playerTurn);
            }
        }
    }

    private boolean isOver(Frame frame, Player playerTurn, int siteX, int siteY) {
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
        // 捺
        if (isOverInDirection(Math.max(siteX - 4, 0), Math.max(siteY - 4, 0), Math.min(siteX + 4, COLUMN),
                Math.min(siteY + 4, ROW), 1, 1, flag)) {
            return true;
        }
        // 撇
        if (isOverInDirection(Math.min(siteX + 4, COLUMN), Math.max(siteY - 4, 0),
                Math.max(siteX - 4, 0), Math.min(siteY + 4, ROW), -1, 1, flag)) {
            return true;
        }
        return false;
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