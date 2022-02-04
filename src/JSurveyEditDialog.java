import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class JSurveyEditDialog extends JDialog implements ActionListener, KeyListener {

    // Controller in MVC
    private final SurveyController controller;
    // Parent View in MVC
    private final SurveyView parentView;

    private JList<JSurveyEntity> list;
    private JTextField inputField;
    private JButton addBtn;
    private JButton delBtn;
    private JButton editPrescriptionsBtn;
    private JButton confirmBtn;
    private JButton cancelBtn;

    private DefaultListModel<JSurveyEntity> model;
    private JScrollPane jScrollPane;

    private JSurvey jSurvey;


    // child dialog
    private final JSurveyEditEntityDialog jSurveyEditEntityDialog;

    public JSurveyEditDialog(SurveyController controller, SurveyView parentView) {
        this.controller = controller;
        this.parentView = parentView;
        this.jSurveyEditEntityDialog = new JSurveyEditEntityDialog(controller, this);

        this.setTitle("설문 편집");
        this.setModal(true);
        this.setLocationByPlatform(true);
        this.setPreferredSize(new Dimension(800, 600));
        this.setSize(new Dimension(800, 600));
        this.setResizable(false);


        model = new DefaultListModel<>();
        list = new JList<>(model);
        inputField = new JTextField(22);
        addBtn = new JButton("추가");
        delBtn = new JButton("삭제");
        editPrescriptionsBtn = new JButton("구성요소 편집");
        confirmBtn = new JButton("확인");
        cancelBtn = new JButton("취소");

        inputField.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

        confirmBtn.addActionListener(this);
        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        delBtn.addActionListener(this);
        editPrescriptionsBtn.addActionListener(this);


        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel jLabel = new JLabel("질문 : ");
        jLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        topPanel.add(jLabel);
        topPanel.add(inputField);
        topPanel.add(addBtn);
        topPanel.add(delBtn);
        topPanel.add(editPrescriptionsBtn);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        jScrollPane = new JScrollPane(list);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottomPanel.add(confirmBtn);
        bottomPanel.add(cancelBtn);

        this.addKeyListener(this);
        this.setFocusable(true);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    public JSurvey getJSurvey() {
        return jSurvey;
    }

    public void setJSurvey(JSurvey jSurvey) {
        this.jSurvey = jSurvey;
        model.removeAllElements();
        inputField.setText("");

        for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities()) {
            model.addElement(jSurveyEntity);
        }

        refresh();
    }

    public JList<JSurveyEntity> getList() {
        return list;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JButton getAddBtn() {
        return addBtn;
    }

    public JButton getDelBtn() {
        return delBtn;
    }

    public JButton getEditPrescriptionsBtn() {
        return editPrescriptionsBtn;
    }

    public JButton getConfirmBtn() {
        return confirmBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }



    public JSurveyEntity getSelectedJSurveyEntity() {
        int selected = list.getSelectedIndex();
        return selected == -1 ? null : model.get(selected);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmBtn) {
            submit();
        } else if (e.getSource() == cancelBtn) {
            cancel();
        } else if (e.getSource() == addBtn) {
            appendJSurveyEntity();
        } else if (e.getSource() == delBtn) {
            deleteJSurveyEntity();
        } else if (e.getSource() == editPrescriptionsBtn) {
            int selected = list.getSelectedIndex();
            if (selected != -1) {
                jSurveyEditEntityDialog.updateList(model.getElementAt(selected));
                jSurveyEditEntityDialog.setVisible(true);
                // update list
                setJSurvey(jSurvey);
            }
        }
    }

    private void submit() {
        controller.editSurvey(jSurvey);
        for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities()) {
            jSurveyEntity.savePrescriptions();
            jSurveyEntity.resetRadio();
        }
        this.dispose();
    }

    private void cancel() {
        for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities()) {
            jSurveyEntity.reloadPrescriptions();
        }
        this.dispose();
    }

    private void appendJSurveyEntity() {
        String description = inputField.getText().trim();
        if (description.equals("")) {
            inputField.setText("");
            inputField.requestFocus();
            return;
        }
        JSurveyEntity jSurveyEntity = new JSurveyEntity(description, new Vector<>(), jSurvey, parentView.getFont());
        jSurvey.getJSurveyEntities().add(jSurveyEntity);
        model.addElement(jSurveyEntity);
        inputField.setText("");
        inputField.requestFocus();
        refresh();
    }

    private void deleteJSurveyEntity() {
        int selected = list.getSelectedIndex();
        if (selected != -1) {
            JSurveyEntity jSurveyEntity = model.getElementAt(selected);
            model.remove(selected);
            jSurvey.getJSurveyEntities().removeElement(jSurveyEntity);
        }
        refresh();
    }


    private void refresh() {
        this.revalidate();
        this.repaint();
    }


    // KeyListener 먹지 않는다... 다른거 시도해야할듯
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                appendJSurveyEntity();
                break;
            case KeyEvent.VK_ESCAPE:
                cancel();
                break;
            case KeyEvent.VK_DELETE:
                deleteJSurveyEntity();
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

