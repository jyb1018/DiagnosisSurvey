import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

class SurveyController {
    private SurveyView view;
    private HashMap<JSurvey, Survey> surveyMap;
    private HashMap<JSurvey, HashMap<JSurveyEntity, SurveyEntity>> surveyEntityMap;

    SurveyController() {
        surveyMap = new HashMap<>();
        surveyEntityMap = new HashMap<>();
        view = new SurveyView(this);


    }

    void start() {
        view.showMain();
    }



    // View 연동
    void updateEntitiesYn(JSurvey jSurvey) {

        HashMap<JSurveyEntity, SurveyEntity> internalMap = surveyEntityMap.get(jSurvey);
        for (Map.Entry<JSurveyEntity, SurveyEntity> entry : internalMap.entrySet()) {
            JSurveyEntity key_jSurveyEntity = entry.getKey();
            SurveyEntity value_surveyEntity = entry.getValue();
            value_surveyEntity.setYn(key_jSurveyEntity.getYn());
        }
    }

    void editSurvey(JSurvey jSurvey) {
        surveyMap.get(jSurvey).applyJSurvey(jSurvey);
        view.refresh();
    }

    private JSurvey makeJSurvey(Survey survey) {
        JSurvey ret = new JSurvey();
        ret.setSurveyName(survey.getName());
        for (SurveyEntity entity : survey.getEntities()) {
            ret.getJSurveyEntities().add(makeJSurveyEntity(entity, ret));
        }
        return ret;
    }

    private JSurveyEntity makeJSurveyEntity(SurveyEntity entity, JSurvey jSurvey) {
        Vector<SurveyPrescription> prescriptions = new Vector<>(entity.getPrescriptions());
        JSurveyEntity jSurveyEntity = new JSurveyEntity(entity.getDescription(), prescriptions, jSurvey, view.getFont());
        setLink(jSurvey, jSurveyEntity, entity);
        return jSurveyEntity;
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

    void setLink(JSurvey jSurvey, JSurveyEntity jSurveyEntity, SurveyEntity surveyEntity) {
        surveyEntityMap.putIfAbsent(jSurvey, new HashMap<>());
        HashMap<JSurveyEntity, SurveyEntity> internalMap = surveyEntityMap.get(jSurvey);
        internalMap.put(jSurveyEntity, surveyEntity);

    }

    void removeLinks(JSurvey jSurvey) {
        if (surveyEntityMap.get(jSurvey) != null)
            for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities())
                surveyEntityMap.get(jSurvey).remove(jSurveyEntity);
    }


    HashMap<String, String> calcSurveyResult(JSurvey jSurvey) {
        Survey survey = surveyMap.get(jSurvey);
        // <타입, <이름, 수>>
        HashMap<String, HashMap<String, Integer>> prescriptionMap = new HashMap<>();

        for (SurveyEntity surveyEntity : survey.getEntities()) {
            if (surveyEntity.getYn()) {
                for (SurveyPrescription prescription : surveyEntity.getPrescriptions()) {
                    HashMap<String, Integer> internalMap = prescriptionMap.computeIfAbsent(
                            prescription.getType(), k -> new HashMap<>());
                    internalMap.merge(prescription.getName(), 1, Integer::sum);
                }
            }
        }


        // 타입, 이름
        HashMap<String, String> retMap = new HashMap<>();
        for (Map.Entry<String, HashMap<String, Integer>> entry : prescriptionMap.entrySet()) {
            String type = entry.getKey();
            HashMap<String, Integer> internalMap = entry.getValue();
            String maxPrescriptionName = "";
            for (String name : internalMap.keySet()) {
                if (maxPrescriptionName.equals("") || internalMap.get(name) > internalMap.get(maxPrescriptionName))
                    maxPrescriptionName = name;
            }
            retMap.put(type, maxPrescriptionName);
        }

        return retMap;
    }

    // File 연동
    void fileRead(File file) throws Exception {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);

        StringBuilder dataStr = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) {
            dataStr.append(str);
        }
        JSONParser jsonParser = new JSONParser();
        JSONArray surveyJSONs = (JSONArray) jsonParser.parse(dataStr.toString());

        for (Object surveyJSON : surveyJSONs)
            appendSurvey(new Survey((JSONObject) surveyJSON, this));



    }

    void fileWrite(File file) throws Exception {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        JSONArray surveyJSONs = new JSONArray();
        for (Survey survey : surveyMap.values())
            surveyJSONs.add(survey.toJSON());

        bw.write(surveyJSONs.toString());
        bw.flush();
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
