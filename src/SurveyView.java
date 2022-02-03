import assets.SurveyImageIcons;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import opensource.say.swing.JFontChooser;


class SurveyView implements ActionListener, MouseListener {

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JFrame jFrame;
    private Font font;
    public static SurveyController controller = null;

    private JPopupMenu jPopupMenu = null;
    private JFileChooser jFileChooser_open = null;
    private JFileChooser jFileChooser_save = null;

    private JFontChooser jFontChooser;

    private JSurveyEditDialog surveyEditDialog;

    //정보
    private JTextArea aboutArea;

    //중첩 클래스에서 ActionListener 접근용
    private final SurveyView surveyView = this;

    private JSurvey tempJSurvey = null;

    HashMap<Integer, JSurvey> jSurveyMap;
    private JDialog editSurveyDialog;



    SurveyView(SurveyController surveyController) {

        // window style
        if (System.getProperty("os.name").toLowerCase().contains("win"))
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }


        controller = surveyController;
        jSurveyMap = new HashMap<>();



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


        // 폰트 선택
        jFontChooser = new JFontChooser();
        jFontChooser.setFont(font);

        // 정보 다이얼로그
        aboutArea = new JTextArea();
        aboutArea.setEditable(false);
        aboutArea.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        aboutArea.setLineWrap(true);
        aboutArea.append("License\n\n\n\n" +

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
                        "안녕"
        );



    }


    void appendJSurvey(JSurvey jSurvey) {
        jSurvey.addMouseListener(this);
        jSurveyMap.put(jSurvey.hashCode(), jSurvey);
        rightPanel.add(jSurvey);
        Dimension dimension = rightPanel.getPreferredSize();
        dimension.height += 110;
        rightPanel.setPreferredSize(dimension);
        showSurveyEntities(jSurvey);

        tempJSurvey = jSurvey;
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




    void showSurveyEntities(JSurvey jSurvey) {

        Dimension dimension = leftPanel.getPreferredSize();
        leftPanel.removeAll();
        if(jSurvey == null)
            return;
        if (tempJSurvey != null)
            jSurveyMap.get(tempJSurvey.hashCode()).setBackground(new Color(178, 176, 66));
        tempJSurvey = jSurvey;
        jSurvey.setBackground(new Color(77, 79, 189));
        for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities()) {
            int lines = jSurveyEntity.getDescriptionLabel().getLineCount();
            Dimension dim = jSurveyEntity.getDescriptionLabel().getPreferredSize();
            dim.height = lines * font.getSize();
            jSurveyEntity.getDescriptionLabel().setPreferredSize(dim);
            dimension.height += jSurveyEntity.getPreferredSize().getHeight();
            leftPanel.add(jSurveyEntity);
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
        jFontChooser.showDialog(jFrame);
        font = jFontChooser.getSelectedFont();
        for (JSurvey jSurvey : jSurveyMap.values()) {
            jSurvey.setFontByChooser(font);
        }
        refresh();
    }

    void help_Dialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("도움말");
        dialog.setSize(new Dimension(800, 600));
        dialog.setLocationByPlatform(true);
        JScrollPane scrollPane = new JScrollPane();

        ImageIcon img = new ImageIcon();

        scrollPane.setViewportView(new JLabel(img));
        scrollPane.setPreferredSize(new Dimension(750, 500));
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    void about_Dialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("정보");
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

    void editSurveyEntity_Dialog() {

    }


    void loadPreset() {
        // if null, init
        if (jFileChooser_open == null) {

            jFileChooser_open = new JFileChooser();
            jFileChooser_open.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser_open.setFileFilter(new FileNameExtensionFilter("설문 프리셋 파일(.sur)", "sur"));

            // 왜 JDK는 한국어 기본번역을 없애버렸을까
            // 이거작동안함
            jFileChooser_open.setLocale(Locale.KOREA);

        }

        if (jFileChooser_open.showOpenDialog(jFrame) != JFileChooser.APPROVE_OPTION)
            return;

        File file = jFileChooser_open.getSelectedFile();
        try {
//            controller.fileRead(file);
            System.out.println("파일 읽기 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void savePreset() {
        //if null, init
        if (jFileChooser_open == null) {
            jFileChooser_save = new JFileChooser();
            jFileChooser_save.setDialogType(JFileChooser.SAVE_DIALOG);
            jFileChooser_save.setFileFilter(new FileNameExtensionFilter("설문 프리셋 파일(.sur)", "sur"));
        }

        if (jFileChooser_save.showSaveDialog(jFrame) != JFileChooser.APPROVE_OPTION)
            return;

        File file = jFileChooser_save.getSelectedFile();
        try {
//            controller.fileWrite(file);
            System.out.println("파일 쓰기 완료");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(jFrame, "성공적으로 저장했습니다.");
    }

    public int getJFrameState() {
        return jFrame.getState();
    }

    public void setJFrameState(int state) {
        jFrame.setState(state);
    }

    public Font getFont() {
        return font;
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
                    controller.appendSurvey(new Survey());
                    break;
                case "모든 설문 삭제":
                    // ConcurrentModificationException's Solution
                    Vector<JSurvey> values = new Vector<>(jSurveyMap.values());
                    for (JSurvey jSurvey : values) {
                        controller.removeSurvey(jSurvey);
                        jSurveyMap.remove(jSurvey.hashCode());
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
                    // TODO 이거 가능하면 깔끔하게 JDialog 새로 만들어서 보내줄것
                    String name = JOptionPane.showInputDialog("설문의 이름을 새로 입력하세요.",
                            targetJSurvey.getSurveyNameLabel().getText());
                    if(name != null)
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
                    jSurveyMap.remove(targetJSurvey.hashCode());
                    break;
            }
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        // if null, init
        if (jPopupMenu == null) {
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
        }


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


}
