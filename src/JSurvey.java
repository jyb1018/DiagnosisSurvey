import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class JSurvey extends JPanel {

    private Vector<JSurveyEntity> jSurveyEntities;
    private JLabel surveyNameLabel;

    public JSurvey(Font font) {
        this(new Vector<>(), null, font);
    }

    public JSurvey(Vector<JSurveyEntity> jSurveyEntities, String name, Font font) {
        super();
        this.jSurveyEntities = jSurveyEntities;

        this.setSize(new Dimension(100, 100));
        this.setPreferredSize(new Dimension(100, 100));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(100, 100));
        this.setBackground(new Color(178, 176, 66));


        surveyNameLabel = new JLabel(name != null ? name : "새 설문");
        surveyNameLabel.setFont(font);
        surveyNameLabel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
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