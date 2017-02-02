package ru.geekbrains.java2.dz.dz4.gubenkoDM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nestor on 04.12.2016.
 */
public class ClientChatWindow extends JFrame implements ConstI {
    public ClientChatWindow() throws HeadlessException{
        //установка заголовка
        setTitle("GeekBrains Chat");
        //закрытие ПО
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //усатновка размеров окна
        setBounds(X_W_COORD,Y_W_COORD,W_WIDTH,W_HEIGHT);
        //добавим 2 панели
        //реализация менее удачного варианта масштабирования с ArrayList
        //setLayout(new GridLayout(1,2));
        //реализация более удачного варианта масштабирования без ArrayList
        setLayout(new BorderLayout());
        //создадим список панелей
        ArrayList<JPanel> panelList=new ArrayList<>();

        JPanel textPannel=new JPanel();
        textPannel.setBackground(new Color(100,100,200));
        //textPannel.setLayout(new BoxLayout(textPannel,BoxLayout.Y_AXIS));
        textPannel.setLayout(new BorderLayout());

        JLabel jlTA=new JLabel("Окно переписки пользователей");
        jlTA.setFont(chatFont);
        //установка горизонтального выравнивания
        jlTA.setHorizontalAlignment(JLabel.CENTER);
        textPannel.add(jlTA,BorderLayout.NORTH);

        //окно переписки в чате
        JPanel panelTextArea=new JPanel();
        //panelTextArea.setBackground(new Color(100,150,0));
        panelTextArea.setLayout(new BorderLayout());
        JTextArea jtaTA=new JTextArea();
        jtaTA.setLineWrap(true);
        jtaTA.setEditable(false);
        JScrollPane jspTA=new JScrollPane(jtaTA);
        panelTextArea.add(jspTA);
        //добавление первой подпанели
        textPannel.add(panelTextArea,BorderLayout.CENTER);

        JPanel panelTextField=new JPanel();
        //panelTextField.setBackground(new Color(0,100,0));
        panelTextField.setLayout(new BorderLayout());
        JTextField jtfTF=new JTextField();

        //работа с файлом
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(new FileWriter("chatHistory.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Произошла ошибка ввода/вывода");
        }
        PrintWriter finalPw = pw;

        //добавление обработчика для отправки сообщения по Enter
        jtfTF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtaTA.append("["+new Date().toString()+"] "+jtfTF.getText().concat("\n"));
                finalPw.println("["+new Date().toString()+"] "+jtfTF.getText());
                jtfTF.setText(null);
            }
        });

        panelTextField.add(jtfTF,BorderLayout.CENTER);
        JButton jBTF=new JButton("Отправить");


        //добавим обработчик кнопки
        jBTF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtaTA.append("["+new Date().toString()+"] "+jtfTF.getText().concat("\n"));
                finalPw.println("["+new Date().toString()+"] "+jtfTF.getText());
                jtfTF.setText(null);
            }
        });
        panelTextField.add(jBTF,BorderLayout.EAST);

        //добавление второй подпанели
        textPannel.add(panelTextField,BorderLayout.PAGE_END);

        //добавление левой панели в список панелей
        panelList.add(textPannel);

        JPanel userPanel=new JPanel();
        userPanel.setBackground(new Color(200,100,100));
        userPanel.setLayout(new BorderLayout());

        JLabel jlUP=new JLabel("Список пользователей в чате");
        jlUP.setFont(chatFont);
        //установка горизонтального выравнивания
        jlUP.setHorizontalAlignment(JLabel.CENTER);
        userPanel.add(jlUP,BorderLayout.PAGE_START);

        JTextArea jtaUP=new JTextArea();
        jtaUP.setLineWrap(true);
        jtaUP.setEditable(false);
        JScrollPane jspUP=new JScrollPane(jtaUP);
        userPanel.add(jspUP,BorderLayout.CENTER);
        //ручное добавление пользователей
        jtaUP.append("Иван"+"\n");
        jtaUP.append("Петр");
        panelList.add(userPanel);

        //добавлфяем элементы на форму
        //for (JPanel jp:panelList) {
            //add(jp);
        //}
        add(textPannel,BorderLayout.CENTER);
        add(userPanel,BorderLayout.EAST);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                finalPw.close();
                super.windowClosing(e);
            }
        });

    }
}
