import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

class SurveyController {
    private SurveyView view;
    public HashMap<JSurvey, Survey> surveyMap;

    SurveyController() {
        surveyMap = new HashMap<>();
        view = new SurveyView(this);


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

    void editSurvey(JSurvey jSurvey) {
        surveyMap.get(jSurvey).applyJSurvey(jSurvey);
        view.refresh();
    }

    JSurvey makeJSurvey(Survey survey) {
        JSurvey ret = new JSurvey();
        ret.setSurveyName(survey.getName());
        for (SurveyEntity entity :survey.getEntities()) {
            ret.getJSurveyEntities().add(makeJSurveyEntity(entity, ret));
        }
        return ret;
    }

    JSurveyEntity makeJSurveyEntity(SurveyEntity entity, JSurvey jSurvey) {
        Vector<String> prescriptions = new Vector<>(entity.getPrescriptions());
        return new JSurveyEntity(entity.getDescription(), prescriptions, jSurvey);
    }

    void appendSurvey(Survey survey) {
        JSurvey jSurvey = makeJSurvey(survey);
        surveyMap.put(jSurvey, survey);
        view.appendJSurvey(jSurvey);
    }

    void removeSurvey(JSurvey jSurvey) {
        surveyMap.remove(jSurvey);
        view.removeJSurvey(jSurvey);
    }

    void renameSurvey(JSurvey jSurvey, String name) {
        surveyMap.get(jSurvey).setName(name);
        jSurvey.setSurveyName(name);
        view.refresh();
    }

    // TODO 다시짤것
    // File 연동
    void fileRead(File file) throws Exception {
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
//                entities.add(new SurveyEntity(description, prescriptions));
        }

    }

    void fileWrite(File file) throws Exception {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        StringBuilder dataStr = new StringBuilder();
        for (Survey survey : surveyMap.values()) {
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
