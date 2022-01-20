import javax.swing.*;
import java.awt.*;

class SurveyView {

    private JFrame jFrame;

    SurveyView() {
        jFrame = new JFrame();
        jFrame.setSize(new Dimension(800, 600));
        jFrame.setLocationByPlatform(true);




    }

    void showMain() {
        jFrame.setVisible(true);
    }
}
