package com.gobang.demo01;

/**
 * @author fsyj on 2021/11/20
 */
public interface Config {

    /**
     * 棋盘的起始点的像素位置
     */
    int START_X = 20;
    int START_Y = 20;

    /**
     * 格子大小
     */
    int GRID_SIZE = 40;

    /**
     * 棋子大小
     */
    int PIECE_SIZE = 30;

    /**
     * 棋盘行列
     */
    int ROW = 15;
    int COLUMN = 15;

    /**
     * 棋盘大小
     */
    int WIDTH = 800;
    int HEIGHT = 650;


    String[] BUTTONS = {"开始游戏","重开","认输"};

    enum Player{
        /**
         * 下棋双方
         */
        BLACK(1),
        WHITE(2);

        /**
         * 标识信息
         */
        private int flag;

        public int getFlag() {
            return flag;
        }

        Player(int i) {
            flag = i;
        }
    }

    /**
     * 棋盘偏移量
     */
    int OFFSET = 10;

}
