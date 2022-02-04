import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Vector;

public class Survey {
    private String name;
    private Vector<SurveyEntity> entities;

    // link 전달용
    private SurveyController controller;

    public Survey(String name, Vector<SurveyEntity> entities, SurveyController controller) {
        this.name = name;
        this.entities = entities;
        this.controller = controller;
    }

    public Survey(SurveyController controller) {
        this.name = "새 설문";
        this.entities = new Vector<>();
        this.controller = controller;
    }

    public Survey(JSONObject surveyJSON, SurveyController controller) {
        this.name = (String) surveyJSON.get("name");
        Vector<SurveyEntity> tempEntities = new Vector<>();
        for (Object entityJSON : ((JSONArray) surveyJSON.get("entities"))) {
            tempEntities.add(new SurveyEntity((JSONObject) entityJSON));
        }

        this.entities = tempEntities;
        this.controller = controller;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<SurveyEntity> getEntities() {
        return entities;
    }

    public void setEntities(Vector<SurveyEntity> entities) {
        this.entities = entities;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (SurveyEntity entity : entities) {
            str.append(entity.toString());
            str.append("\\");
        }
        return str.toString();
    }

    public void applyJSurvey(JSurvey jSurvey) {
        controller.removeLinks(jSurvey);
        entities.removeAllElements();
        for (JSurveyEntity jSurveyEntity : jSurvey.getJSurveyEntities()) {
            SurveyEntity surveyEntity = new SurveyEntity(jSurveyEntity.getDescription(), jSurveyEntity.getPrescriptions());
            controller.setLink(jSurvey, jSurveyEntity, surveyEntity);
            entities.add(surveyEntity);
        }
    }

    public JSONObject toJSON() {
        JSONObject surveyJSON = new JSONObject();
        surveyJSON.put("name", name);
        JSONArray entityJSONs = new JSONArray();
        for (SurveyEntity surveyEntity : entities) {
            entityJSONs.add(surveyEntity.toJSON());
        }
        surveyJSON.put("entities", entityJSONs);
        return surveyJSON;
    }
}
