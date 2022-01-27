import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

class SurveyController {
    private SurveyView view;
    public Vector<Survey> surveys;
    private File file = new File("./data.dat");

    SurveyController() {
        surveys = new Vector<>();
        view = new SurveyView(this);


        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void start() {
        view.showMain();
    }

    void menu_newSurvey() {

    }

    void menu_openSurvey() {

    }

    void menu_saveSurvey() {

    }

    void menu_setFont() {

    }

    void menu_info() {

    }

    void survey_submit() {

    }

    void survey_return() {

    }

    void survey_executeAnotherSurvey(SurveyEntity survey) {

    }



    // View 연동
    void entityUpdate() {

    }

    // File 연동
    void fileRead() throws Exception {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);

        StringBuilder dataStr = new StringBuilder();
        String str;
        while((str = reader.readLine()) != null) {
            dataStr.append(str);
        }
        String[] splits1 = dataStr.toString().split("\\$");
        for (String line : splits1) {
            String[] splits2 = line.split("\\^");
            String description = splits2[0];
            Vector<String> prescriptions = new Vector<>(Arrays.asList(splits2).subList(1, splits2.length));
//            entities.add(new SurveyEntity(description, prescriptions));
        }

    }

    void fileWrite() throws Exception {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        StringBuilder dataStr = new StringBuilder();
        for (Survey survey : surveys) {
            dataStr.append(survey.toString());
        }
        bw.write(dataStr.toString());

    }

    // DB 연동
//    void insertDB() {
//
//    }
//
//    void deleteDB() {
//
//    }
//
//    void selectDB() {
//
//    }


}
