import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.MenuBarUI;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.EventListener;
import java.util.concurrent.Flow;

class SurveyView {

    private JFrame jFrame;
    private Font font;
    private SurveyController controller;

    class JSurveyPanel extends JPanel implements EventListener {
        final SurveyEntity surveyEntity;
        public JSurveyPanel(SurveyEntity surveyEntity) {
            super();
            this.surveyEntity = surveyEntity;

            JLabel label = new JLabel(surveyEntity.getName());
            label.setFont(font);


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
        JList<JButton> surveyList = new JList<>();
        JScrollPane scroll = new JScrollPane(surveyList);
        scroll.setVerticalScrollBar(new JScrollBar());
        scroll.setBorder(null);



        leftPanel.setBackground(new Color(208, 255, 243));
        surveyList.setBackground(Color.white);

        surveyList.setSize(new Dimension(200, 0));
        surveyList.setPreferredSize(new Dimension(200, 0));
        surveyList.setMaximumSize(new Dimension(200, 0));
        surveyList.setMinimumSize(new Dimension(200, 0));
        centerPanel.add(leftPanel, new GridBagConstraints(0, 0, 1, 1, 4.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0,0,0,0), 0, 0));
        centerPanel.add(scroll, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
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
