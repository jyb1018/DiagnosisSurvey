import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class JSurveyEditEntityDialog extends JDialog implements ActionListener {

    //Controller in MVC
    private final SurveyController controller;

    // parent dialog
    private JSurveyEditDialog parentDialog;

    private JList<String> list;
    private JTextField inputField;
    private JButton addBtn;
    private JButton delBtn;
    private JButton confirmBtn;
    private JButton cancelBtn;

    private DefaultListModel<String> model;
    private JScrollPane jScrollPane;

    private JSurveyEntity jSurveyEntity;




    public JSurveyEditEntityDialog(SurveyController controller, JSurveyEditDialog parentDialog) {
        this.controller = controller;
        this.parentDialog = parentDialog;

        this.setTitle("설문 구성요소 편집");
        this.setModal(true);
        this.setLocationByPlatform(true);
        this.setPreferredSize(new Dimension(400, 300));
        this.setSize(new Dimension(400, 300));
        this.setResizable(false);

        model = new DefaultListModel<>();
        list = new JList<>(model);
        inputField = new JTextField(13);
        addBtn = new JButton("추가");
        delBtn = new JButton("삭제");
        confirmBtn = new JButton("확인");
        cancelBtn = new JButton("취소");

        inputField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));

        inputField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));

        confirmBtn.addActionListener(this);
        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        delBtn.addActionListener(this);

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(inputField);
        topPanel.add(addBtn);
        topPanel.add(delBtn);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jScrollPane = new JScrollPane(list);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,10, 10));
        bottomPanel.add(confirmBtn);
        bottomPanel.add(cancelBtn);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            String text = inputField.getText().trim();
            if(!text.equals(""))
                model.addElement(text);
            inputField.setText("");
            inputField.requestFocus();
        } else if(e.getSource() == delBtn) {
            int selected = list.getSelectedIndex();
            model.removeElementAt(selected);
        } else if(e.getSource() == confirmBtn) {
            Vector<String> prescriptions = new Vector<>();
            for (int i = 0; i < model.size(); i++) {
                prescriptions.add(model.get(i));
            }
            parentDialog.getSelectedJSurveyEntity().setPrescriptions(prescriptions);
            this.dispose();
        } else if(e.getSource() == cancelBtn) {
            this.dispose();
        }
    }

    public void updateList(JSurveyEntity jSurveyEntity) {
        model.removeAllElements();
        for (String prescription :jSurveyEntity.getPrescriptions()) {
            model.addElement(prescription);
        }
        refresh();
    }

    public void refresh() {
        revalidate();
        repaint();
    }
}