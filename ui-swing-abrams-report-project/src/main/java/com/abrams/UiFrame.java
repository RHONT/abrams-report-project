package com.abrams;

import com.abrams.finder.ExcelWriter;
import com.abrams.finder.Finder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UiFrame extends JFrame {
    private JPanel panel = new JPanel(new GridLayout(3, 1));

    private JTextField _nameClient = new JTextField();
    private JTextField _currentDir = new JTextField();
    private String _getNameClientText;
    private String _getCurrentDirText;


    public UiFrame() throws HeadlessException {
        setTitle("Абрамс");
        setSize(300, 300); // Задаем размеры окна приложения
        setLocationRelativeTo(null); // Окно приложения центрируется относительно экрана
        setResizable(false); // запрещаем возможность растягивать окно
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton _reportB = new JButton("Отчет");
        _reportB.setVisible(true);

        _currentDir.setText("C:\\labaratory\\dirTest");
        _nameClient.setText("Яркие");

        _reportB.addActionListener(e -> {
            _getCurrentDirText = _currentDir.getText();
            _getNameClientText = _nameClient.getText();

            try {
                Finder finder = new Finder(_getNameClientText, _getCurrentDirText);
                ExcelWriter.writeExcel(finder);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            System.out.println(_getCurrentDirText);
            System.out.println(_getNameClientText);

        });


        panel.add(_nameClient);
        panel.add(_currentDir);
        panel.add(_reportB);

        _getNameClientText = _currentDir.getText();

//        panel.setDoubleBuffered(true);
        panel.setBackground(Color.lightGray);
        Container container = getContentPane();
        container.add(panel);
    }


    public static void main(String[] args) {
        UiFrame uiFrame = new UiFrame();
        uiFrame.setVisible(true);


    }
}
