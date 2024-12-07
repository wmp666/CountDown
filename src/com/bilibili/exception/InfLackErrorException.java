package com.bilibili.exception;

import javax.swing.*;

public class InfLackErrorException extends RuntimeException {
  public InfLackErrorException(String message) {

    System.out.println(message);
    JOptionPane.showMessageDialog(null,
            message,
            "information.set文件中,信息缺失或错误",
            JOptionPane.ERROR_MESSAGE);

  }
}
