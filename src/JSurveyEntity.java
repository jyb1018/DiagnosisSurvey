import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Vector;

public class JSurveyEntity extends JPanel {

    private final JTextArea descriptionLabel;
    private final JRadioButton yRadio;
    private final JRadioButton nRadio;
    private JSurvey jSurvey;
    private String description;
    private Vector<SurveyPrescription> prescriptions;
    private Vector<SurveyPrescription> originPrescriptions;

    private Boolean yn;

    public JSurveyEntity(String description, Vector<SurveyPrescription> prescriptions, JSurvey jSurvey, Font font) {
        super();
        this.originPrescriptions = prescriptions;
        this.prescriptions = prescriptions;
        this.description = description;
        this.jSurvey = jSurvey;
        yn = null;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);


        this.setPreferredSize(new Dimension(620, 0));

        descriptionLabel = new JTextArea(description);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setMaximumSize(new Dimension(600, 600));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        descriptionLabel.setEditable(false);
        descriptionLabel.setFont(font);
        this.add(descriptionLabel, BorderLayout.CENTER);

        ButtonGroup ynRadio = new ButtonGroup();
        yRadio = new JRadioButton("예");
        yRadio.setBackground(getBackground());
        nRadio = new JRadioButton("아니오");
        nRadio.setBackground(getBackground());
        ynRadio.add(yRadio);
        ynRadio.add(nRadio);

        JPanel radioPanel = new JPanel();
        radioPanel.setBackground(getBackground());
        radioPanel.add(yRadio);
        radioPanel.add(nRadio);

        yRadio.addActionListener(e -> yn = true);
        nRadio.addActionListener(e -> yn = false);

        this.add(radioPanel, BorderLayout.SOUTH);
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

    public Vector<SurveyPrescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Vector<SurveyPrescription> prescriptions) {
        originPrescriptions = this.prescriptions;
        this.prescriptions = prescriptions;
    }

    public void reloadPrescriptions() {
        prescriptions = originPrescriptions;
    }

    public void savePrescriptions() {
        originPrescriptions = prescriptions;
    }

    public Boolean getYn() {
        return yn;
    }

    public void setYn(Boolean yn) {
        this.yn = yn;
    }

    public JTextArea getDescriptionLabel() {
        return descriptionLabel;
    }

    private Vector<String> cutString(String str, int length) {
        Vector<String> v = new Vector<>();
        int prev_idx = -1;
        for (int i = 1; i < str.length(); i++) {
            if (i % length == 0) {
                v.add(str.substring(prev_idx + 1, i + 1));
                prev_idx = i;
            }
        }
        if (prev_idx != str.length() - 1)
            v.add(str.substring(prev_idx + 1));
        return v;
    }

    public int countLines() {
        AttributedString text = new AttributedString(descriptionLabel.getText());
        text.addAttribute(TextAttribute.FONT, descriptionLabel.getFont());
        FontRenderContext frc = descriptionLabel.getFontMetrics(descriptionLabel.getFont())
                .getFontRenderContext();
        descriptionLabel.getDocument().getDefaultRootElement();
        AttributedCharacterIterator charIt = text.getIterator();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);

        float formatWidth = (float) (this.getPreferredSize().width - 20);
        lineMeasurer.setPosition(charIt.getBeginIndex());

        int noLines = 0;
        while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
            lineMeasurer.nextLayout(formatWidth);
            noLines++;
        }

        return noLines;


    }

    public void resetRadio() {
        yRadio.setSelected(false);
        nRadio.setSelected(false);
    }



    public void setFontByChooser(Font font) {
        descriptionLabel.setFont(font);
    }

    // JList에 나타나는 문구
    public String toString() {
        StringBuilder prescription_str = new StringBuilder();

        for (SurveyPrescription prescriptions : prescriptions) {
            prescription_str.append(prescriptions.toString()).append(", ");
        }
        if (prescription_str.length() != 0)
            prescription_str.delete(prescription_str.length() - 2, prescription_str.length());
        return "질문 : " + description + " [" + prescription_str + "]";

    }




}