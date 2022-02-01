import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Vector;

public class JSurveyEntity extends JPanel {

    private final JTextArea descriptionLabel;
    private JSurvey jSurvey;
    private String description;
    private Vector<String> prescriptions;
    private Boolean yn;

    public JSurveyEntity(String description, Vector<String> prescriptions, JSurvey jSurvey) {
        super();
        this.prescriptions = prescriptions;
        this.description = description;
        this.jSurvey = jSurvey;
        yn = null;

        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);


        this.setPreferredSize(new Dimension(620, 120));


        descriptionLabel = new JTextArea(description);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setMaximumSize(new Dimension(600, 600));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        descriptionLabel.setEditable(false);
        descriptionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        this.add(descriptionLabel);

        ButtonGroup ynRadio = new ButtonGroup();
        JRadioButton yRadio = new JRadioButton("예");
        yRadio.setBackground(getBackground());
        JRadioButton nRadio = new JRadioButton("아니오");
        nRadio.setBackground(getBackground());
        ynRadio.add(yRadio);
        ynRadio.add(nRadio);

        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(getBackground());
        radioPanel.add(yRadio);
        radioPanel.add(nRadio);

        this.add(radioPanel);

    }



    public JSurvey getjSurvey() {
        return jSurvey;
    }

    public void setjSurvey(JSurvey jSurvey) {
        this.jSurvey = jSurvey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vector<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Vector<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Boolean getYn() {
        return yn;
    }

    public void setYn(Boolean yn) {
        this.yn = yn;
    }

    private Vector<String> cutString(String str, int length) {
        Vector<String> v = new Vector<>();
        int prev_idx = -1;
        for (int i = 1; i < str.length(); i++) {
            if(i % length == 0) {
                v.add(str.substring(prev_idx+1, i+1));
                prev_idx = i;
            }
        }
        if(prev_idx != str.length()-1)
            v.add(str.substring(prev_idx+1));
        return v;
    }


    public void setFontByChooser(Font font) {
        descriptionLabel.setFont(font);
    }

    // JList에 나타나는 문구
    public String toString() {
        StringBuilder prescription_str = new StringBuilder();

        for (String prescription: prescriptions) {
            prescription_str.append(prescription).append(", ");
        }
        if(prescription_str.length() != 0)
            prescription_str.delete(prescription_str.length() - 2, prescription_str.length());
        return "질문 : " + description + " [" + prescription_str + "]";

    }
}