package com.abrams;

import com.abrams.finder.creatorsreports.ExcelEachCustomer;
import com.abrams.finder.creatorsreports.ExcelGroupCustomer;
import com.abrams.finder.search.FinderDirectoriesByName;
import com.abrams.finder.search.FinderFiles;
import com.abrams.finder.write.WriterToDB;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UiFrame extends JFrame {
    private final JTextField _nameClient = new JTextField();
    private final JTextField _currentDir = new JTextField();
    private String _getNameClientText;
    private String _getCurrentDirText;


    public UiFrame() throws HeadlessException {
        setTitle("Абрамс");
        setSize(300, 300); // Задаем размеры окна приложения
        setLocationRelativeTo(null); // Окно приложения центрируется относительно экрана
        setResizable(false); // запрещаем возможность растягивать окно
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBackground(Color.lightGray);

        Container container = getContentPane();

        JButton _reportGroup = getGroupingButton();
        JButton _reportEach = getEachButton();

        panel.add(_nameClient);
        panel.add(_currentDir);
        panel.add(_reportGroup);
        panel.add(_reportEach);

        container.add(panel);
    }

    private JButton getGroupingButton() {
        JButton _button = new JButton("Сгруппированный");
        _button.setVisible(true);

        _button.addActionListener(e -> {
            refreshTextField();
            try {
                writeCustomersToDB();
                new ExcelGroupCustomer(_getNameClientText,_getCurrentDirText).save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return _button;
    }

    private JButton getEachButton() {
        JButton _button = new JButton("Подробный");
        _button.setVisible(true);

        _button.addActionListener(e -> {
            refreshTextField();
            try {
                writeCustomersToDB();
                new ExcelEachCustomer(_getNameClientText,_getCurrentDirText).save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return _button;
    }

    private void writeCustomersToDB() throws IOException {
        new WriterToDB(
                new FinderFiles(
                        new FinderDirectoriesByName(_getNameClientText, _getCurrentDirText)));
    }

    private void refreshTextField() {
        _getCurrentDirText = _currentDir.getText();
        _getNameClientText = _nameClient.getText();
    }


    public static void main(String[] args) {
        UiFrame uiFrame = new UiFrame();
        uiFrame.setVisible(true);
    }
}
