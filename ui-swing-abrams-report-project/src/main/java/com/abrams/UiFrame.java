package com.abrams;

import com.abrams.reports.SelectAllCustomerReportExcel;
import com.abrams.reports.GroupCustomerReportExcel;
import com.abrams.search.FinderOrders;
import com.abrams.outputto.WriterEntitiesToDB;

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

        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.setBackground(Color.lightGray);

        Container container = getContentPane();

        JButton _reportGroup = getGroupingButton();
        JButton _reportEach = getEachButton();
        JLabel _labelNameClient=new JLabel("Введите имя клиента",SwingConstants.CENTER);
        JLabel _labelDirectory=new JLabel("Директория для поиска",SwingConstants.CENTER);

        panel.add(_labelNameClient);
        panel.add(_nameClient);
        panel.add(_labelDirectory);
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
                findAndWriteCustomersToDB();
                createXlsGroupReport();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return _button;
    }

    private void createXlsGroupReport() throws IOException {
        new GroupCustomerReportExcel(_getNameClientText,_getCurrentDirText).createXlsFile();
    }

    private JButton getEachButton() {
        JButton _button = new JButton("Подробный");
        _button.setVisible(true);

        _button.addActionListener(e -> {
            refreshTextField();
            try {
                findAndWriteCustomersToDB();
                createXlsDetailReport();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return _button;
    }

    private void createXlsDetailReport() throws IOException {
        new SelectAllCustomerReportExcel(_getNameClientText,_getCurrentDirText).createXlsFile();
    }

    private void findAndWriteCustomersToDB() throws IOException {
        new WriterEntitiesToDB(new FinderOrders(_getNameClientText, _getCurrentDirText)
                .giveOrders());
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
