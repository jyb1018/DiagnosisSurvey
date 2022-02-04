import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class JSurveyEditEntityDialog extends JDialog implements ActionListener, KeyListener {

    //Controller in MVC
    private final SurveyController controller;

    // parent dialog
    private JSurveyEditDialog parentDialog;

    private JList<SurveyPrescription> list;
    private JTextField nameInputField;
    private JTextField typeInputField;
    private JButton addBtn;
    private JButton delBtn;
    private JButton confirmBtn;
    private JButton cancelBtn;

    private DefaultListModel<SurveyPrescription> model;
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

        this.addKeyListener(this);

        model = new DefaultListModel<>();
        list = new JList<>(model);
        nameInputField = new JTextField(13);
        typeInputField = new JTextField(13);
        addBtn = new JButton("추가");
        delBtn = new JButton("삭제");
        confirmBtn = new JButton("확인");
        cancelBtn = new JButton("취소");

        nameInputField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        typeInputField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));

        confirmBtn.addActionListener(this);
        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        delBtn.addActionListener(this);

        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel informTextPanel = new JPanel(new GridLayout(2, 1, 0, 23));
        JPanel textFieldPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        informTextPanel.add(new JLabel("이름 "));
        informTextPanel.add(new JLabel("종류 "));
        textFieldPanel.add(nameInputField);
        textFieldPanel.add(typeInputField);

        topPanel.add(informTextPanel);
        topPanel.add(textFieldPanel);
        topPanel.add(addBtn);
        topPanel.add(delBtn);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jScrollPane = new JScrollPane(list);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.add(confirmBtn);
        bottomPanel.add(cancelBtn);


        this.add(topPanel, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            add();
        } else if (e.getSource() == delBtn) {
            delete();
        } else if (e.getSource() == confirmBtn) {
            confirm();
        } else if (e.getSource() == cancelBtn) {
            this.dispose();
        }
    }

    private void add() {
        String name = nameInputField.getText().trim();
        String type = typeInputField.getText().trim();
        if (!name.equals("") && !type.equals("")) {
            model.addElement(new SurveyPrescription(name, type));
            typeInputField.setText("");
            nameInputField.setText("");
            nameInputField.requestFocus();
        }
    }

    private void delete() {
        int selected = list.getSelectedIndex();
        model.removeElementAt(selected);
    }

    private void confirm() {
        Vector<SurveyPrescription> prescriptions = new Vector<>();
        for (int i = 0; i < model.size(); i++) {
            prescriptions.add(model.get(i));
        }
        parentDialog.getSelectedJSurveyEntity().setPrescriptions(prescriptions);
        this.dispose();
    }

    public void updateList(JSurveyEntity jSurveyEntity) {
        model.removeAllElements();
        for (SurveyPrescription prescription : jSurveyEntity.getPrescriptions()) {
            model.addElement(prescription);
        }
        refresh();
    }

    public void refresh() {
        revalidate();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                delete();
                break;
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            case KeyEvent.VK_ENTER:
                add();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}