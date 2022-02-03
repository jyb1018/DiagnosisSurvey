import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class JSurvey extends JPanel {

    private Vector<JSurveyEntity> jSurveyEntities;
    private JLabel surveyNameLabel;

    public JSurvey() {
        this(new Vector<>(), null);
    }

    public JSurvey(Vector<JSurveyEntity> jSurveyEntities, String name) {
        super();
        this.jSurveyEntities = jSurveyEntities;

        this.setSize(new Dimension(100, 100));
        this.setPreferredSize(new Dimension(100, 100));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(100, 100));
        this.setBackground(new Color(178, 176, 66));
        this.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));


        surveyNameLabel = new JLabel(name != null ? name : "새 설문");
        surveyNameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        this.add(surveyNameLabel);

    }

    public JLabel getSurveyNameLabel() {
        return surveyNameLabel;
    }

    public void setSurveyName(String name) {
        surveyNameLabel.setText(name);
    }

    public Vector<JSurveyEntity> getJSurveyEntities() {
        return jSurveyEntities;
    }

    public void setJSurveyEntities(Vector<JSurveyEntity> jSurveyEntities) {
        this.jSurveyEntities = jSurveyEntities;
    }

    public void setFontByChooser(Font font) {
        for (JSurveyEntity jSurveyEntity : jSurveyEntities) {
            jSurveyEntity.setFontByChooser(font);
        }
    }

}