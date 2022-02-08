import assets.SurveyImageIcons;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import opensource.say.swing.JFontChooser;


class SurveyView implements ActionListener, MouseListener {

    private SurveyController controller;

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JFrame jFrame;
    private Font font;

    private JPopupMenu jPopupMenu;
    private JFileChooser jFileChooser_open;
    private JFileChooser jFileChooser_save;
    private JButton surveyResultBtn;

    private JFontChooser jFontChooser;

    private JSurveyEditDialog surveyEditDialog;
    private JSurveyResultDialog surveyResultDialog;

    //정보
    private JTextArea aboutArea;

    private JSurvey tempJSurvey = null;
    private Dimension prevFrameSize;

    private HashSet<JSurvey> jSurveySet;


    SurveyView(SurveyController surveyController) {

        // os style changer
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        localize();



        controller = surveyController;
        jSurveySet = new HashSet<>();


        font = new Font("맑은 고딕", Font.PLAIN, 13);


        jFrame = new JFrame();
        jFrame.setSize(new Dimension(800, 800));
        jFrame.setPreferredSize(new Dimension(800, 800));
        jFrame.setTitle("설문조사 프로그램");
        jFrame.setMinimumSize(new Dimension(800, 400));
        jFrame.setIconImage(SurveyImageIcons.surveyIcon.getImage());
        Container container = jFrame.getContentPane();
        container.setLayout(new BorderLayout());


        // 사전 인스턴스화 필요한 컴포넌트
        surveyEditDialog = new JSurveyEditDialog(controller, this);
        surveyResultDialog = new JSurveyResultDialog();


        // 상단 메뉴 바
        JMenuBar jMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("파일");
        JMenuItem menu_openSurvey = new JMenuItem("설문 프리셋 불러오기", KeyEvent.VK_O);
        JMenuItem menu_saveSurvey = new JMenuItem("설문 프리셋 저장", KeyEvent.VK_S);
        menu_openSurvey.addActionListener(this);
        menu_saveSurvey.addActionListener(this);
        fileMenu.add(menu_openSurvey);
        fileMenu.add(menu_saveSurvey);


        JMenu editMenu = new JMenu("편집");
        JMenuItem menu_newSurvey = new JMenuItem("새 설문", KeyEvent.VK_N);
        JMenuItem menu_deleteAllSurvey = new JMenuItem("모든 설문 삭제");
        menu_newSurvey.addActionListener(this);
        menu_deleteAllSurvey.addActionListener(this);
        editMenu.add(menu_newSurvey);
        editMenu.addSeparator();
        editMenu.add(menu_deleteAllSurvey);


        JMenu viewMenu = new JMenu("보기");
        JMenuItem menu_setFont = new JMenuItem("폰트", KeyEvent.VK_F);
        menu_setFont.addActionListener(this);
        viewMenu.add(menu_setFont);

        JMenu helpMenu = new JMenu("도움말");
        JMenuItem menu_help = new JMenuItem("도움말", KeyEvent.VK_F1);
        JMenuItem menu_about = new JMenuItem("정보", KeyEvent.VK_I);
        menu_help.addActionListener(this);
        menu_about.addActionListener(this);
        helpMenu.add(menu_help);
        helpMenu.add(menu_about);


        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(helpMenu);

        jPopupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("편집", KeyEvent.VK_E);
        JMenuItem deleteItem = new JMenuItem("삭제", KeyEvent.VK_D);
        JMenuItem renameItem = new JMenuItem("이름 바꾸기", KeyEvent.VK_M);

        editItem.addActionListener(this);
        deleteItem.addActionListener(this);
        renameItem.addActionListener(this);


        jPopupMenu.add(editItem);
        jPopupMenu.addSeparator();
        jPopupMenu.add(deleteItem);
        jPopupMenu.add(renameItem);


        jFileChooser_open = new JFileChooser();
        jFileChooser_open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser_open.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser_open.setFileFilter(
                new FileNameExtensionFilter("설문 프리셋 파일(.sur)", "sur"));

        // 왜 JDK는 한국어 기본번역을 없애버렸을까
        // 이거작동안함 ㅠㅠ
        jFileChooser_open.setLocale(Locale.KOREA);

        jFileChooser_save = null;
        jFileChooser_save = new JFileChooser();
        jFileChooser_save.setDialogType(JFileChooser.SAVE_DIALOG);
        jFileChooser_save.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser_save.setFileFilter(
                new FileNameExtensionFilter("설문 프리셋 파일(.sur)", "sur"));



        surveyResultBtn = new JButton("결과 확인");
        surveyResultBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JSurveyEntity jSurveyEntity : tempJSurvey.getJSurveyEntities()) {
                    Boolean yn = jSurveyEntity.getYn();
                    if (yn == null) {
                        JOptionPane.showMessageDialog(jFrame, "체크하지 않은 답변이 있습니다.");
                        return;
                    }
                }
                controller.updateEntitiesYn(tempJSurvey);
                surveyResultDialog.setResult(controller.calcSurveyResult(tempJSurvey));
                surveyResultDialog.setVisible(true);
            }
        });


        JPanel centerPanel = new JPanel();


        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);

        jFrame.setJMenuBar(jMenuBar);
        jFrame.add(centerPanel, BorderLayout.CENTER);


        centerPanel.setLayout(new GridBagLayout());


        leftPanel = new JPanel();
        rightPanel = new JPanel();
        JScrollPane scroll_right = new JScrollPane();
        scroll_right.setViewportView(rightPanel);
        scroll_right.setVerticalScrollBar(new JScrollBar());
        scroll_right.getVerticalScrollBar().setUnitIncrement(16);
        scroll_right.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll_right.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollPane scroll_left = new JScrollPane();
        scroll_left.setViewportView(leftPanel);
        scroll_left.getVerticalScrollBar().setUnitIncrement(16);
        scroll_left.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll_left.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        Dimension right_dim = rightPanel.getPreferredSize();
        right_dim.height = 0;
        rightPanel.setPreferredSize(right_dim);

        Dimension left_dim = leftPanel.getPreferredSize();
        left_dim.height = 0;
        leftPanel.setPreferredSize(left_dim);

        FlowLayout surveyLayout = new FlowLayout();
        surveyLayout.setVgap(10);
        rightPanel.setLayout(surveyLayout);
        right_dim = rightPanel.getPreferredSize();
        right_dim.height += 10;
        rightPanel.setPreferredSize(right_dim);

        FlowLayout surveyLayout2 = new FlowLayout();
        surveyLayout2.setVgap(10);
        leftPanel.setLayout(surveyLayout2);
        left_dim = leftPanel.getPreferredSize();
        left_dim.height += 10;
        leftPanel.setPreferredSize(left_dim);

        leftPanel.setBackground(new Color(208, 255, 243));
        rightPanel.setBackground(Color.white);

        scroll_right.setPreferredSize(new Dimension(150, 0));
        scroll_right.setMaximumSize(new Dimension(150, 0));
        scroll_right.setMinimumSize(new Dimension(150, 0));

        centerPanel.add(scroll_left, new GridBagConstraints(0, 0, 1, 1, 4.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        centerPanel.add(scroll_right, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0, 0, 0, 0), 0, 0));

        prevFrameSize = jFrame.getSize();

        jFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int widthWeight = jFrame.getSize().width - prevFrameSize.getSize().width;
                if (Math.abs(widthWeight) > 600) {
                    prevFrameSize = jFrame.getSize();
                    showSurveyEntities(tempJSurvey);
                }
                else if ((Math.abs(widthWeight) > 30 || jFrame.getSize().width == jFrame.getMinimumSize().width)
                        && tempJSurvey != null) {
                    prevFrameSize = jFrame.getSize();
                    resizeJSurveyEntities(tempJSurvey);
                }
                super.componentResized(e);
            }


        });


        // 폰트 선택
        jFontChooser = new JFontChooser();
        jFontChooser.setSelectedFont(font);

        // 정보 다이얼로그
        aboutArea = new JTextArea();
        aboutArea.setEditable(false);
        aboutArea.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        aboutArea.setLineWrap(true);
        aboutArea.append("License\n\n\n\n" +
                "" +
                "JDiagnosisSurvey (This Software)\n\n" +
                "The MIT License(MIT)\n\n" +
                "Copyright (c) 2022 jyb1018@github.com\n\n" +
                "All Rights Reserved.\n\n" +
                "Permission is hereby granted, free of charge, to any person\n" +
                "obtaining a copy of this software and associated documentation\n" +
                "files (the \"Software\"), to deal in the Software without\n" +
                "restriction, including without limitation the rights to use,\n" +
                "copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                "copies of the Software, and to permit persons to whom the\n" +
                "Software is furnished to do so, subject to the following\n" +
                "conditions:\n\n" +
                "" +
                "The above copyright notice and this permission notice shall be\n" +
                "included in all copies or substantial portions of the Software.\n\n" +
                "" +
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND,\n" +
                "EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES\n" +
                "OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND\n" +
                "NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT\n" +
                "HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,\n" +
                "WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING\n" +
                "FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR\n" +
                "OTHER DEALINGS IN THE SOFTWARE.\n\n\n\n\n" +
                "" +
                "" +
                "JFontChooser\n\n" +

                "Copyright 2004-2008 Masahiko SAWAI All Rights Reserved.\n\n" +
                "Permission is hereby granted, free of charge, to any person obtaining" +
                "a copy of this software and associated documentation files (the \"Software\")," +
                "to deal in the Software without restriction, including without limitation" +
                "the rights to use, copy, modify, merge, publish, distribute, sublicense," +
                "and/or sell copies of the Software, and to permit persons to whom" +
                "the Software is furnished to do so, subject to the following conditions:\n\n" +
                "The above copyright notice and this permission notice shall be included" +
                "in all copies or substantial portions of the Software.\n\n" +
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS" +
                "OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY," +
                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL" +
                "THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER" +
                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM," +
                "OUT OF OR IN CONNECTION WITH THE SOFTWARE" +
                "OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.\n\n\n\n" +
                "" +
                "Launch4j 3.14 (http://launch4j.sourceforge.net/)\n" +
                "Cross-platform Java application wrapper for creating Windows native executables.\n\n" +
                "" +
                "Copyright (C) 2004, 2019 Grzegorz Kowal\n\n" +
                "" +
                "Launch4j comes with ABSOLUTELY NO WARRANTY.\n" +
                "This is free software, licensed under the BSD License\n" +
                "This product includes software developed by the Apache Software Foundation\n" +
                "(http://www.apache.org/).\n\n"

        );


    }


    void appendJSurvey(JSurvey jSurvey) {
        jSurvey.addMouseListener(this);
        jSurveySet.add(jSurvey);
        rightPanel.add(jSurvey);
        Dimension dimension = rightPanel.getPreferredSize();
        dimension.height += 110;
        rightPanel.setPreferredSize(dimension);
        showSurveyEntities(jSurvey);

        refresh();

        JScrollBar scrollBar1 = ((JScrollPane) leftPanel.getParent().getParent()).getVerticalScrollBar();
        scrollBar1.setValue(scrollBar1.getMaximum());
        JScrollBar scrollBar2 = ((JScrollPane) rightPanel.getParent().getParent()).getVerticalScrollBar();
        scrollBar2.setValue(scrollBar2.getMaximum());


    }

    void removeJSurvey(JSurvey jSurvey) {
        rightPanel.remove(jSurvey);

        //현재 표시중 설문일 경우
        if (jSurvey == tempJSurvey) {
            leftPanel.removeAll();
            if (rightPanel.getComponents().length == 0)
                tempJSurvey = null;
            else
                tempJSurvey = (JSurvey) rightPanel.getComponents()[0];
            showSurveyEntities(tempJSurvey);
        }
        Dimension dimension = rightPanel.getPreferredSize();
        dimension.height -= 110;
        rightPanel.setPreferredSize(dimension);
        refresh();
    }


    private void showSurveyEntities(JSurvey jSurvey) {

        Dimension dimension = leftPanel.getPreferredSize();
        dimension.height = 0;
        leftPanel.removeAll();
        leftPanel.setPreferredSize(dimension);
        if (jSurvey == null)
            return;
        if (tempJSurvey != null)
            tempJSurvey.setBackground(new Color(178, 176, 66));
        tempJSurvey = jSurvey;
        jSurvey.setBackground(new Color(77, 79, 189));

        for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities()) {
            leftPanel.add(jSurveyEntity);
        }

        resizeJSurveyEntities(jSurvey);

        if (jSurvey.getJSurveyEntities().size() != 0) {
            leftPanel.add(surveyResultBtn);
        }


        refresh();
    }

    void showMain() {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setLocationByPlatform(true);


        // 한국어
        jFrame.setVisible(true);
    }

    void setFont_Dialog() {
        Font prevFont = jFontChooser.getSelectedFont();
        if (jFontChooser.showDialog(jFrame) == JFontChooser.CANCEL_OPTION){
            jFontChooser.setSelectedFont(prevFont);
            return;
        }
        font = jFontChooser.getSelectedFont();
        for (JSurvey jSurvey : jSurveySet) {
            jSurvey.setFontByChooser(font);
        }
        showSurveyEntities(tempJSurvey);
        refresh();
    }

    void help_Dialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("도움말");
        dialog.setIconImage(SurveyImageIcons.helpIcon.getImage());
        dialog.setSize(new Dimension(800, 600));
        dialog.setLocationByPlatform(true);
        JScrollPane scrollPane = new JScrollPane();
        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setEditable(false);
        jTextArea.setText("" +
                "<도움말>\n\n" +
                "" +
                "1. 소개\n\n" +
                "" +
                " JDiagnosisSurvey는 설문을 통해 적합한 처방을 도출하도록 하는 프로그램입니다.\n" +
                "창의 오른쪽 구역에서 지금 행할 설문을 선택할 수 있습니다. " +
                "설문을 선택하면, 해당 설문을 나타내는 요소의 배경색이 파란색으로 바뀌며, " +
                "창의 왼쪽 구역에 해당 설문의 질문들과 각각의 질문들에 답변으로 체크할 수 있는 라디오 버튼이 표시됩니다. " +
                "현재 선택한 설문의 모든 질문들에 답변을 체크한 후 질문 밑에 있는 결과 확인 버튼을 누르면, " +
                "답변한 것을 바탕으로 해당 설문이 설정한 최적의 처방이 새로운 창을 통해 결과로 나오게 됩니다. " +
                "처방을 도출해 줄 설문들은 상단 메뉴 탭과 오른쪽 구역 설문 항목의 우클릭을 통해 새로 만들거나, " +
                "자세한 설정을 할 수 있습니다." +
                "\n\n" +
                "2. 상단 메뉴\n\n" +
                "" +
                " 상단 메뉴에는 파일, 편집, 보기, 도움말의 4가지 큰 범주로 분류되어 있습니다. \n\n" +
                "\'파일\'을 누르면, \'설문 프리셋 불러오기\'와, \'설문 프리셋 저장하기\' 기능을 사용할 수 있습니다. \n" +
                "\'설문 프리셋 불러오기\'는, 기존에 저장한 설문 프리셋을 불러올 수 있습니다. " +
                "이 경우, 현재 존재하는 설문들을 없애지 않고 데이터를 추가하므로, \'모든 설문 삭제\'한 후, " +
                "불러오기를 하는 것이 좋을 수 있습니다.\n" +
                "\'설문 프리셋 저장하기\'는, 현재 존재하는 전체 설문 데이터를 파일 한 개에 저장합니다.\n\n" +
                "" +
                "\'편집\'을 누르면, \'새 설문\'과, \'모든 설문 삭제\' 기능을 사용할 수 있습니다. \n" +
                "\'새 설문\'은, 비어있는 새로운 설문 항목을 추가합니다.\n" +
                "\'모든 설문 삭제\'는, 현재 존재하는 모든 설문을 삭제합니다. \n\n" +
                "" +
                "\'보기\'를 누르면, \'폰트\'기능을 사용할 수 있습니다.\n" +
                "\'폰트\'는, 현재 화면에 보여지는 질문들의 폰트를 설정할 수 있습니다.\n\n" +
                "" +
                "\'도움말\'을 누르면, \'도움말\'과 \'정보\'를 볼 수 있습니다.\n" +
                "\'도움말\'은 현재 창을 보여줍니다.\n" +
                "\'정보\'는 라이센스 정보를 보여줍니다.\n\n" +
                "" +
                "3. 설문 설정 및 삭제\n\n" +
                "" +
                " 우측 영역의 설문 항목을 우클릭하면, \'편집\', \'삭제\', \'이름 바꾸기\' " +
                "총 세 가지 팝업 메뉴를 선택할 수 있습니다. \n" +
                "\'편집\'은 우클릭한 설문의 자세한 편집을 돕는 창을 보여줍니다.\n" +
                "\'삭제\'는 우클릭한 설문을 삭제합니다.\n" +
                "\'이름 바꾸기\'는 우클릭한 설문의 이름을 바꿉니다. \n\n" +
                "" +
                "4. 설문 편집\n\n" +
                "" +
                " 설문 편집 창에서는 질문을 추가하거나 삭제하고, 추가된 질문의 구성 요소를 편집할 수 있습니다. " +
                "상단의 입력 영역에 질문 문구를 입력한 후, \'추가\' 버튼을 누르면 현재 편집중인 설문에 해당 질문이 추가됩니다. " +
                "현재 편집중인 설문의 질문들은 편집 창 중간의 넓은 공간에서 확인할 수 있습니다. " +
                "특정 질문을 클릭한 후, \'삭제\' 버튼을 누르면 현재 편집중인 설문의 해당 질문이 삭제됩니다. " +
                "특정 질문을 클릭한 후, \'구성요소 편집\' 버튼을 누르면 해당 질문의 처방 관련 구성 요소를 설정할 수 있습니다." +
                "구성 요소는 편집 창에서 질문 옆의 [대괄호]를 통해 확인할 수 있습니다. " +
                "\'확인\' 버튼을 통해 현재 편집 상황을 내부 데이터로 저장하며, \'취소\' 버튼을 누를 시 현재 상황은 저장되지 않습니다. " +
                "\n\n" +
                "" +
                "5. 구성요소 편집\n\n" +
                "" +
                " 구성요소 편집 창에서는 현재 편집하려는 질문의 처방을 관리할 수 있습니다. " +
                "\'이름\'에 현재 처방하려는 약물/시술/무언가 의 이름을, " +
                "\'종류\'에 현재 처방하려는 약물/시술/무언가의 종류를 입력한 후 \'추가\' 버튼을 눌러 구성 요소를 추가할 수 있습니다. " +
                "추가된 구성 요소는 하단의 공간에서 \'이름\'(\'종류\')의 형태로 보여집니다. " +
                "구성 요소를 선택하고 \'삭제\' 버튼을 누르면 해당 구성 요소가 삭제됩니다. " +
                "\'확인\' 버튼을 통해 현재 구성 요소 편집 상황을 설문 편집 창의 데이터로 저장하며, " +
                "\'취소\' 버튼을 누를 시 현재 상황은 저장되지 않습니다. " +
                "\n\n" +
                "" +
                "6. 결과 확인\n\n" +
                "" +
                " 결과 확인 창에서는 현재 설문에서 질문의 답변에 따라 적합한 처방(구성요소)을 보여줍니다." +
                "현재 설문에서 \'예\'라고 답변한 질문들에 대해, " +
                "모든 \'구성 요소 종류\'들 각각에 대하여 가장 많이 존재하는 \'이름\'의 처방(구성 요소)을 결과로 보여줍니다. " +
                "동률일 경우, 동률인 처방(구성 요소)중 어떤 처방(구성 요소)이 결과로 나올지는 알 수 없습니다.\n\n" +
                "" +
                "7. 알려진 버그\n\n" +
                "" +
                " 너무 빠른 속도로 프로그램 창의 크기를 변경할 경우, 질문 크기가 창 범위를 벗어나 질문이 정상적으로 출력되지 " +
                "않을 수 있습니다. " +
                "이 경우, 우측 영역의 설문 항목을 다시한번 클릭해 주면 복구됩니다.\n\n" +
                "" +
                "추가적인 버그가 발생할 경우, 해당 상황을 jyb1018@gmail.com으로 전달해 주시면 재밌게 보겠습니다.\n\n" +
                "" +
                "8. 기타\n\n" +
                "" +
                " 이 프로그램은 MVC 모델을 기반으로 구현하려고 \'했던\' 프로그램입니다. 스파게티까진 아니지만, 프로그램보단 " +
                "파스타에 가까울 것입니다.\n" +
                "이 프로그램은 아마도 수업용 보충 자료 프로그램입니다. 이 프로그램을 2022년 1학기에 사용하는 경우, " +
                "제작자도 높은 확률로 그 수업을 듣고 있습니다. 이 프로그램을 사용하는 과제를 한다면 제작자에게 질문 메일을 " +
                "보내는 것은 지양해 주시기 바랍니다. 저도 잘 모릅니다. "



        );

        jTextArea.setCaretPosition(0);


        scrollPane.setViewportView(jTextArea);
        scrollPane.setPreferredSize(new Dimension(750, 500));
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    void about_Dialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("정보");
        dialog.setIconImage(SurveyImageIcons.informIcon.getImage());
        dialog.setSize(new Dimension(800, 600));
        dialog.setLocationByPlatform(true);
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setViewportView(aboutArea);
        scrollPane.setPreferredSize(new Dimension(750, 500));
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }


    void editSurvey_Dialog(JSurvey jSurvey) {
        surveyEditDialog.setJSurvey(jSurvey);
        surveyEditDialog.setVisible(true);
        showSurveyEntities(jSurvey);
        refresh();

    }


    void loadPreset() {


        if (jFileChooser_open.showOpenDialog(jFrame) != JFileChooser.APPROVE_OPTION)
            return;

        File file = jFileChooser_open.getSelectedFile();
        try {
            controller.fileRead(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void savePreset() {


        if (jFileChooser_save.showSaveDialog(jFrame) != JFileChooser.APPROVE_OPTION)
            return;

        File file = jFileChooser_save.getSelectedFile();
        File parentDir = file.getParentFile();
        String fileName = file.getName();
        if (fileName.length() < 5 || !fileName.substring(fileName.length() - 4).equals(".sur"))
            file = new File(parentDir.getPath() + File.separator + fileName + ".sur");
        try {
            controller.fileWrite(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(jFrame, "성공적으로 저장했습니다.");
    }


    public Font getFont() {
        return font;
    }


    private int count = 0;

    private void resizeJSurveyEntities(JSurvey jSurvey) {
//        System.out.println("resize called : " + ++count);

        Dimension dimension = leftPanel.getPreferredSize();
        dimension.height = 0;
        int width = jFrame.getSize().width;
        if (jSurvey != null) {
            for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities()) {
                Dimension size = jSurveyEntity.getPreferredSize();
                size.width = width - 220;
                jSurveyEntity.setPreferredSize(size);
                int lines = jSurveyEntity.countLines();
                Dimension dim = jSurveyEntity.getPreferredSize();
                dim.height = lines * jSurveyEntity.getDescriptionLabel()
                        .getFontMetrics(jSurveyEntity.getDescriptionLabel().getFont()).getHeight() + 53;
                jSurveyEntity.setPreferredSize(dim);
                dimension.height += jSurveyEntity.getPreferredSize().getHeight() + 10;
            }
            dimension.height += 50;
            leftPanel.setPreferredSize(dimension);

            refresh();



        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {

            JPopupMenu parent;
            JSurvey targetJSurvey;
            //메뉴일경우
            switch (e.getActionCommand()) {
                // 상단 메뉴바
                case "설문 프리셋 불러오기":
                    loadPreset();
                    break;
                case "설문 프리셋 저장":
                    savePreset();
                    break;
                case "새 설문":
                    controller.appendSurvey(new Survey(controller));
                    break;
                case "모든 설문 삭제":
                    HashSet<JSurvey> handling_concurrentModificationExceptionSet = new HashSet<>(jSurveySet);
                    for (JSurvey jSurvey : handling_concurrentModificationExceptionSet) {
                        controller.removeSurvey(jSurvey);
                        jSurveySet.remove(jSurvey);
                    }
                    break;
                case "폰트":
                    setFont_Dialog();
                    break;
                case "도움말":
                    help_Dialog();
                    break;
                case "정보":
                    about_Dialog();
                    break;

                // 우클릭 팝업
                case "이름 바꾸기":
                    parent = (JPopupMenu) ((JMenuItem) e.getSource()).getParent();
                    targetJSurvey = ((JSurvey) parent.getInvoker());
                    String name = JOptionPane.showInputDialog("설문의 이름을 새로 입력하세요.",
                            targetJSurvey.getSurveyNameLabel().getText());
                    if (name != null)
                        controller.renameSurvey(targetJSurvey, name);
                    break;
                case "편집":
                    parent = (JPopupMenu) ((JMenuItem) e.getSource()).getParent();
                    targetJSurvey = ((JSurvey) parent.getInvoker());
                    editSurvey_Dialog(targetJSurvey);
                    break;
                case "삭제":
                    parent = (JPopupMenu) ((JMenuItem) e.getSource()).getParent();
                    targetJSurvey = ((JSurvey) parent.getInvoker());
                    controller.removeSurvey(targetJSurvey);
                    jSurveySet.remove(targetJSurvey);
                    break;
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            showSurveyEntities((JSurvey) e.getComponent());
        else if (SwingUtilities.isRightMouseButton(e))
            jPopupMenu.show(e.getComponent(), e.getX(), e.getY());

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    void refresh() {
        jFrame.revalidate();
        jFrame.repaint();
    }


    private void localize() {
        UIManager.put("FileChooser.acceptAllFileFilterText", "모든 파일(*.*)");
        UIManager.put("FileChooser.lookInLabelText", "위치");
        UIManager.put("FileChooser.cancelButtonText", "취소");
        UIManager.put("FileChooser.cancelButtonToolTipText", "취소");
        UIManager.put("FileChooser.openButtonText", "열기");
        UIManager.put("FileChooser.saveButtonText", "저장");
        UIManager.put("FileChooser.chooseButtonText", "choose");
        UIManager.put("FileChooser.createButtonText", "create");
        UIManager.put("FileChooser.openButtonToolTipText", "파일 열기");
        UIManager.put("FileChooser.filesOfTypeLabelText", "파일 형식");
        UIManager.put("FileChooser.fileNameLabelText", "파일 이름");
        UIManager.put("FileChooser.listViewButtonToolTipText", "목록");
        UIManager.put("FileChooser.listViewButtonAccessibleName", "목록");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "자세히");
        UIManager.put("FileChooser.detailsViewButtonAccessibleName", "자세히");
        UIManager.put("FileChooser.upFolderToolTipText", "상위 폴더");
        UIManager.put("FileChooser.upFolderAccessibleName", "상위 폴더");
        UIManager.put("FileChooser.homeFolderToolTipText", "바탕 화면");
        UIManager.put("FileChooser.homeFolderAccessibleName", "바탕 화면");
        UIManager.put("FileChooser.fileNameHeaderText", "이름");
        UIManager.put("FileChooser.fileSizeHeaderText", "크기");
        UIManager.put("FileChooser.fileTypeHeaderText", "유형");
        UIManager.put("FileChooser.fileDateHeaderText", "데이터");
        UIManager.put("FileChooser.fileAttrHeaderText", "속성");
        UIManager.put("FileChooser.openDialogTitleText", "파일 불러오기");
        UIManager.put("FileChooser.saveDialogTitleText", "파일 저장");

        UIManager.put("FileChooser.readOnly", true);

        UIManager.put("OptionPane.okButtonText", "확인");


    }

}
