import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.EventListener;

class SurveyView {

    private JFrame jFrame;
    private Font font;
    private SurveyController controller;

    class JSurvey extends JPanel {
        final Survey survey;
        JList<JSurveyEntity> surveyEntityJList;
        public JSurvey(Survey survey) {
            super();
            this.survey = survey;
            surveyEntityJList = new JList<>();

            for (int i = 0; i < survey.getEntities().size(); i++) {
                surveyEntityJList.add(new JSurveyEntity(survey.getEntities().get(i).getDescription()));
            }

            this.setSize(new Dimension(10,10));

            JLabel label = new JLabel(survey.getName());

            label.setFont(font);

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
        JMenuItem newSurvey = new JMenuItem("새 설문", KeyEvent.VK_N);
        JMenuItem openSurvey = new JMenuItem("설문 프리셋 불러오기", KeyEvent.VK_O);
        JMenuItem saveSurvey = new JMenuItem("설문 프리셋 저장", KeyEvent.VK_S);
        fileMenu.add(newSurvey);
        fileMenu.add(openSurvey);
        fileMenu.add(saveSurvey);

        JMenu viewMenu = new JMenu("보기");
        JMenuItem setFont = new JMenuItem("폰트", KeyEvent.VK_F);
        viewMenu.add(setFont);

        JMenu helpMenu = new JMenu("도움말");
        JMenuItem help = new JMenuItem("도움말", KeyEvent.VK_F1);
        JMenuItem about = new JMenuItem("정보", KeyEvent.VK_I);
        helpMenu.add(help);
        helpMenu.add(about);


        jMenuBar.add(fileMenu);
        jMenuBar.add(viewMenu);
        jMenuBar.add(helpMenu);

        JPanel centerPanel = new JPanel();




        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);

        jFrame.setJMenuBar(jMenuBar);
        jFrame.add(centerPanel, BorderLayout.CENTER);



        centerPanel.setLayout(new GridBagLayout());




        JPanel leftPanel = new JPanel();
        //TODO JButton 고치기
        JList<JSurvey> surveyList = new JList<>();
        JScrollPane scroll1 = new JScrollPane(surveyList);
        scroll1.setVerticalScrollBar(new JScrollBar());
        scroll1.setBorder(null);

        //TODO testcode
        Main.test(controller);
        JSurvey jSurvey = new JSurvey(controller.surveys.get(0));
        surveyList.getModel().getElementAt()

        JScrollPane scroll2 = new JScrollPane();



        leftPanel.setBackground(new Color(208, 255, 243));
        surveyList.setBackground(Color.white);

        surveyList.setSize(new Dimension(150, 0));
        surveyList.setPreferredSize(new Dimension(150, 0));
        surveyList.setMaximumSize(new Dimension(150, 0));
        surveyList.setMinimumSize(new Dimension(150, 0));
        centerPanel.add(leftPanel, new GridBagConstraints(0, 0, 1, 1, 4.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0,0,0,0), 0, 0));
        centerPanel.add(scroll1, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                new Insets(0,0,0,0), 0, 0));


        GridLayout surveyLayout = new GridLayout();
        surveyLayout.setVgap(10);


    }

    void showMain() {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setLocationByPlatform(true);
        jFrame.setVisible(true);
    }
}
