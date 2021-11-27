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
        Graphics g = frame.getGraphics();

        // 获取点击的坐标
        int x = e.getX() - START_X;
        int y = e.getY() - START_Y;
        // 获取点位坐标
        int siteX = Math.round((x - OFFSET) * 1.0f / GRID_SIZE);
        int siteY = Math.round((y - OFFSET) * 1.0f / GRID_SIZE);

        if (frame.isAvailable(siteX, siteY)) {
            frame.putChess(x, y, playerTurn);
            if (playerTurn == Player.BLACK) {
                // 设置棋子颜色
                g.setColor(Color.BLACK);
                g.fillOval(siteX * GRID_SIZE + START_X - 15, siteY * GRID_SIZE + START_Y - 15, PIECE_SIZE, PIECE_SIZE);
                playerTurn = Player.WHITE;
            } else {
                g.setColor(Color.WHITE);
                g.fillOval(siteX * GRID_SIZE + START_X - 15, siteY * GRID_SIZE + START_Y - 15, PIECE_SIZE, PIECE_SIZE);
                playerTurn = Player.BLACK;
            }
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
}