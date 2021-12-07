package com.gobang.demo01;

import java.util.Arrays;

/**
 * @author fsyj on 2021/11/27
 */
public class Brain {
    private Frame board;
    private static final String initStr = "0";
    private static StringBuilder content;
    private int[][] weightArray;

    public Brain(Frame board) {
        this.board = board;
        weightArray = new int[Config.ROW][Config.COLUMN];
    }

    private void initWeight() {
        for (int[] ints : weightArray) {
            Arrays.fill(ints, 0);
        }
    }

    public void getWeight() {
        // 初始化权值数组
        initWeight();

        for (int i = 0; i < Config.ROW; i++) {
            for (int j = 0; j < Config.COLUMN; j++) {
                if (board.isAvailable(j, i)) {
                    // 向左延申
                    content = new StringBuilder(initStr);
                    int jMin = Math.max(0, j - 4);
                    for (int positionJ = j - 1; positionJ >= jMin; positionJ--) {
                        content.append(board.getChess(j, i));
                    }
                    // 将左边的权值加入到当前权值数组
                    Integer leftVal = ifPut(content, i, j);


                    // 向右延申
                    content = new StringBuilder(initStr);
                    int jMax = Math.min(Config.COLUMN, j + 4);
                    for (int positionJ = j + 1; positionJ <= jMax; positionJ++) {
                        content.append(board.getChess(j, i));
                    }
                    Integer rightVal = ifPut(content, i, j);

                    // 联合行判断
                    weightArray[i][j] += unionWeight(leftVal, rightVal);

                    // 上
                    content = new StringBuilder(initStr);
                    int iMin = Math.max(0, i - 4);
                    for (int positionI = i - 1; positionI >= iMin; positionI--) {
                        content.append(board.getChess(j, i));
                    }
                    Integer upVal = ifPut(content, i, j);

                    // 下
                    content = new StringBuilder(initStr);
                    int iMax = Math.min(Config.ROW, i + 4);
                    for (int positionI = i + 1; positionI <= iMin; positionI++) {
                        content.append(board.getChess(j, i));
                    }
                    Integer downVal = ifPut(content, i, j);

                    // 联合列
                    weightArray[i][j] += unionWeight(upVal, downVal);

                    // 左上
                    content = new StringBuilder(initStr);
                    for (int position = -1; position >= -4; position--) {
                        if ((i + position >= 0) && (i + position <= Config.ROW) && (j + position >= 0) && (j + position <= Config.COLUMN)) {
                            content.append(board.getChess(j + position, i + position));
                        }
                    }
                    Integer leftUpVal = ifPut(content, i, j);

                    // 右下
                    content = new StringBuilder(initStr);
                    for (int position = 1; position <= 4; position++) {
                        if (i + position <= 14 && j + position <= 14) {
                            content.append(board.getChess(j + position, i + position));
                        }
                    }
                    Integer rightDownVal = ifPut(content, i, j);
                    weightArray[i][j] += unionWeight(leftUpVal, rightDownVal);

                    // 右上
                    content = new StringBuilder(initStr);
                    for (int position = 1; position <= 4; position++) {
                        if (i - position >= 0 && j + position <= 14) {
                            content.append(board.getChess(j + position, i - position));
                        }
                    }
                    Integer rightUpVal = ifPut(content, i, j);

                    // 左下
                    content = new StringBuilder(initStr);
                    for (int position = 1; position <= 4; position++) {
                        if (i + position <= 14 && j - position >= 0) {
                            content.append(board.getChess(j - position, i + position));
                        }
                    }
                    Integer leftDownVal = ifPut(content, i, j);
                    weightArray[i][j] += unionWeight(leftDownVal, rightUpVal);
                }
            }
        }
    }


    private Integer ifPut(StringBuilder content, int i, int j) {
        Integer weightVal = ChessInfo.map.get(content.toString());
        if (weightVal != null) {
            weightArray[i][j] += weightVal;
        }
        return weightVal;
    }

    public Integer unionWeight(Integer a, Integer b) {
        //必须要先判断a,b两个数值是不是null
        if ((a == null) || (b == null)) {
            return 0;
        }
        //一一:101/202
        else if ((a >= 22) && (a <= 25) && (b >= 22) && (b <= 25)) {
            return 60;
        }
        //一二、二一:1011/2022
        else if (((a >= 22) && (a <= 25) && (b >= 76) && (b <= 80)) || ((a >= 76) && (a <= 80) && (b >= 22) && (b <= 25))) {
            return 800;
        }
        //一三、三一、二二:10111/20222
        else if (((a >= 10) && (a <= 25) && (b >= 1050) && (b <= 1100)) || ((a >= 1050) && (a <= 1100) && (b >= 10) && (b <= 25)) || ((a >= 76) && (a <= 80) && (b >= 76) && (b <= 80))) {
            return 3000;
        }
        //眠三连和眠一连。一三、三一
        else if (((a >= 22) && (a <= 25) && (b >= 140) && (b <= 150)) || ((a >= 140) && (a <= 150) && (b >= 22) && (b <= 25))) {
            return 3000;
        }
        //二三、三二:110111
        else if (((a >= 76) && (a <= 80) && (b >= 1050) && (b <= 1100)) || ((a >= 1050) && (a <= 1100) && (b >= 76) && (b <= 80))) {
            return 3000;
        } else {
            return 0;
        }
    }

    /**
     * 返回一个长度为2的数组，下标为0的代表行坐标，下标为1代表列坐标
     *
     * @return location
     */
    public int[] getLocation() {
        int[] location = new int[2];
        int weightMax = 0;
        for (int i = 0; i < Config.ROW; i++) {
            for (int j = 0; j < Config.COLUMN; j++) {
                if (weightMax < weightArray[i][j]) {
                    weightMax = weightArray[i][j];
                    location[0] = i;
                    location[1] = j;
                }
            }
        }
        return location;
    }
}