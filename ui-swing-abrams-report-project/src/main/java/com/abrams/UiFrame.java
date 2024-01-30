package com.abrams;

import com.abrams.finder.service.ExcelWriter;
import com.abrams.finder.service.ExcelWriterFromDB;
import com.abrams.finder.service.FinderService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UiFrame extends JFrame {
    private JPanel panel = new JPanel(new GridLayout(4, 1));

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

        JButton _reportGroup = getGroupingButton();
        JButton _reportEach = getEachButton();

        panel.add(_nameClient);
        panel.add(_currentDir);
        panel.add(_reportGroup);
        panel.setBackground(Color.lightGray);
        Container container = getContentPane();
        container.add(panel);
    }

    private JButton getGroupingButton() {
        JButton _reportB = new JButton("Отчет");
        _reportB.setVisible(true);

        _reportB.addActionListener(e -> {
            _getCurrentDirText = _currentDir.getText();
            _getNameClientText = _nameClient.getText();

            try {
                FinderService finderService = new FinderService(_getNameClientText, _getCurrentDirText);
//                new ExcelWriter(finderService).writeExcel();
                new ExcelWriterFromDB(finderService).writeExcel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return _reportB;
    }

    private JButton getEachButton() {
        JButton _reportB = new JButton("Подробный");
        _reportB.setVisible(true);

        _reportB.addActionListener(e -> {
            _getCurrentDirText = _currentDir.getText();
            _getNameClientText = _nameClient.getText();

            try {
                FinderService finderService = new FinderService(_getNameClientText, _getCurrentDirText);
//                new ExcelWriter(finderService).writeExcel();
                new ExcelWriterFromDB(finderService).writeExcel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return _reportB;
    }


    public static void main(String[] args) {
        UiFrame uiFrame = new UiFrame();
        uiFrame.setVisible(true);


    }
}
