import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Vector;

class SurveyView implements ActionListener {

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JFrame jFrame;
    private Font font;
    private SurveyController controller;

    //중첩 클래스에서 ActionListener 접근용
    private final SurveyView surveyView = this;

    private Survey tempSurvey = null;

    HashMap<Integer, JSurvey> jSurveyMap;

    class JSurvey extends JPanel {

        private final Survey survey;
        Vector<JSurveyEntity> jSurveyEntities;
        public JSurvey(Survey survey) {
            super();
            this.survey = survey;
            jSurveyEntities = new Vector<>();
            jSurveyMap = new HashMap<>();


            if(survey != null)
                for (int i = 0; i < survey.getEntities().size(); i++) {
                    jSurveyEntities.add(new JSurveyEntity(survey.getEntities().get(i).getDescription()));
                }

            this.setSize(new Dimension(100,100));
            this.setPreferredSize(new Dimension(100,100));
            this.setMinimumSize(new Dimension(100,100));
            this.setMaximumSize(new Dimension(100,100));
            this.setBackground(new Color(178, 176, 66));


            JLabel label = new JLabel(survey != null ? survey.getName() : "새 설문");
            label.setFont(font);
            this.add(label);

        }

        public Survey getSurvey() {
            return survey;
        }
    }

    class JSurveyEntity extends JPanel {

        String description;
        Boolean yn;

        public JSurveyEntity(String description) {
            this.description = description;
            yn = null;

            this.setLayout(new GridLayout(2, 1));


            JLabel descriptionLabel = new JLabel(description);
            descriptionLabel.setFont(font);
            this.add(descriptionLabel);

            ButtonGroup ynRadio = new ButtonGroup();
            JRadioButton yRadio = new JRadioButton("예");
            JRadioButton nRadio = new JRadioButton("아니오");
            ynRadio.add(yRadio);
            ynRadio.add(nRadio);

            JPanel radioPanel = new JPanel();
            radioPanel.add(yRadio);
            radioPanel.add(nRadio);

            this.add(radioPanel);

        }
    }

    SurveyView(SurveyController surveyController) {
        controller = surveyController;


        font = new Font("맑은 고딕", Font.PLAIN, 13);

        jFrame = new JFrame();
        jFrame.setSize(new Dimension(800, 800));
        jFrame.setPreferredSize(new Dimension(800, 800));
        jFrame.setTitle("설문조사 프로그램");
        jFrame.setMinimumSize(new Dimension(800, 400));
        Container container = jFrame.getContentPane();
        container.setLayout(new BorderLayout());



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
        JScrollPane scroll1 = new JScrollPane();
        scroll1.setViewportView(rightPanel);
        scroll1.setVerticalScrollBar(new JScrollBar());
        scroll1.getVerticalScrollBar().setUnitIncrement(16);
        scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //TODO testcode starts
        Main.test(controller);
        for (int i = 0; i < 10; i++) {
            appendSurvey(controller.surveys.get(0));

        }

        //TODO testcode ends


        JScrollPane scroll2 = new JScrollPane();
        scroll2.setViewportView(leftPanel);
        scroll2.getVerticalScrollBar().setUnitIncrement(16);




        leftPanel.setBackground(new Color(208, 255, 243));
        rightPanel.setBackground(Color.white);

        scroll1.setPreferredSize(new Dimension(150, 0));
        scroll1.setMaximumSize(new Dimension(150, 0));
        scroll1.setMinimumSize(new Dimension(150, 0));

        centerPanel.add(scroll2, new GridBagConstraints(0, 0, 1, 1, 4.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0,0,0,0), 0, 0));
        centerPanel.add(scroll1, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0,0,0,0), 0, 0));


        GridLayout surveyLayout = new GridLayout();
        surveyLayout.setVgap(10);


    }

    void appendSurvey(Survey survey) {
        JSurvey jSurvey = new JSurvey(survey);
        jSurveyMap.put(survey.hashCode(), jSurvey);
        rightPanel.add(jSurvey);
        Dimension dimension = rightPanel.getPreferredSize();
        dimension.height += 94;
        rightPanel.setPreferredSize(dimension);
        if(tempSurvey == null) {
            tempSurvey = survey;
            showSurveyEntities(survey);
        }
    }

    void removeSurvey(Survey survey) {
        JSurvey jSurvey = jSurveyMap.get(survey.hashCode());
        rightPanel.remove(jSurvey);
        leftPanel.removeAll();
        //현재 표시중 설문일 경우
        if(survey == tempSurvey && !jSurveyMap.isEmpty()) {
            showSurveyEntities((new Vector<>(jSurveyMap.values())).get(0).getSurvey());
        }
    }


    void showSurveyEntities(Survey survey) {

        jSurveyMap.get(tempSurvey.hashCode()).setBackground(new Color(178, 176, 66));

        JSurvey jSurvey = jSurveyMap.get(survey.hashCode());
        jSurvey.setBackground(new Color(77, 79, 189));
        for (JSurveyEntity surveyEntity : jSurvey.jSurveyEntities) {
            leftPanel.add(surveyEntity);
        }

    }

    void showMain() {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setLocationByPlatform(true);
        jFrame.setVisible(true);
    }

    void setFont_Dialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);

        dialog.setVisible(true);

    }

    void about_Dialog() {
        JDialog dialog = new JDialog();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JMenuItem) {
            //메뉴일경우
            switch (e.getActionCommand()) {
                case "설문 프리셋 불러오기":
                    break;
                case "설문 프리셋 저장":
                    break;
                case "새 설문":
                    appendSurvey(new Survey());
                    break;
                case "모든 설문 삭제":
                    for (JSurvey jSurvey : jSurveyMap.values())
                        removeSurvey(jSurvey.getSurvey());
                    break;
                case "폰트":
                    setFont_Dialog();
                    break;
                case "도움말":
                    break;
                case "정보":
                    break;

            }
        }
    }


}
