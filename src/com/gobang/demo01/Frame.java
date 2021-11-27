package com.gobang.demo01;

import javax.swing.*;
import java.awt.*;

/**
 * @author fsyj on 2021/11/20
 */
public class Frame extends JPanel implements Config {
    public Graphics g;


    public Frame() {
        init();
    }

    /**
     * 用于存放棋盘的落子情况
     * 0 代表此处没有棋子
     * 1 代表黑棋
     * 2 代表白棋
     */
    private byte[][] board = new byte[ROW][COLUMN];


    public void init() {
        //初始化一个界面,设置基本信息
        JFrame jf = new JFrame();
        jf.setTitle("AI五子棋");
        jf.setSize(Config.WIDTH, Config.HEIGHT);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 设置顶级容器JFrame为框架布局
        jf.setLayout(new BorderLayout());

        Dimension board = new Dimension(550, 0);
        Dimension buttonDimension = new Dimension(150, 0);
        // 按钮大小
        Dimension buttonSize = new Dimension(140, 40);

        // 设置棋盘
        setPreferredSize(board);
        setBackground(Color.LIGHT_GRAY);
        jf.add(this, BorderLayout.CENTER);

        // 按钮区
        JPanel buttonArea = new JPanel();
        buttonArea.setSize(buttonDimension);
        buttonArea.setBackground(Color.white);
        buttonArea.setLayout(new GridLayout(BUTTONS.length, 1));
        jf.add(buttonArea, BorderLayout.EAST);

        JButton[] jButtons = new JButton[BUTTONS.length];
        for (int i = 0; i < BUTTONS.length; i++) {
            jButtons[i] = new JButton(BUTTONS[i]);
            jButtons[i].setPreferredSize(buttonSize);
            buttonArea.add(jButtons[i]);
        }

        //按钮监控类
        ButtonListener butListen = new ButtonListener(this);
        //对每一个按钮都添加状态事件的监听处理机制
        for (int i = 0; i < BUTTONS.length; i++) {
            jButtons[i].addActionListener(butListen);
        }

        jf.setResizable(false);

        jf.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 绘制棋盘
        g.setColor(Color.black);
        for (int i = 0; i < ROW; i++) {
            g.drawLine(START_X, START_Y + GRID_SIZE * i, START_X + GRID_SIZE * (COLUMN - 1), START_Y + GRID_SIZE * i);
        }
        for (int j = 0; j < COLUMN; j++) {
            g.drawLine(START_X + GRID_SIZE * j, START_Y, START_X + GRID_SIZE * j, START_Y + GRID_SIZE * (ROW - 1));
        }


        //绘制棋子
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (board[i][j] == 1) {
                    int countx = GRID_SIZE * i + 20;
                    int county = GRID_SIZE * j + 20;
                    g.setColor(Color.black);
                    g.fillOval(countx - GRID_SIZE / 2, county - GRID_SIZE / 2, GRID_SIZE, GRID_SIZE);
                } else if (board[i][j] == 2) {
                    int countx = GRID_SIZE * i + 20;
                    int county = GRID_SIZE * j + 20;
                    g.setColor(Color.white);
                    g.fillOval(countx - GRID_SIZE / 2, county - GRID_SIZE / 2, GRID_SIZE, GRID_SIZE);
                }
            }
        }
    }


    public void putChess(int x, int y, Player turn) {
        if (isAvailable(x, y)) {
            board[x][y] = (byte) (turn == Player.BLACK ? 1 : 2);
        }
    }

    public boolean isAvailable(int x, int y) {
        return board[x][y] == 0;
    }
}
