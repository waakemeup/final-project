package com.gobang.demo01;//设置按钮监听方法ButttonLitener类

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 实现对JPanel的监听接口处理
 * @author fsyj
 */
public class ButtonListener implements Config, ActionListener {
    public Frame frame;

    public ButtonListener(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("开始游戏".equals(e.getActionCommand())) {
            BoardListener bl=new BoardListener(frame);
            bl.newGame();
            frame.addMouseListener(bl);
        }
    }
}