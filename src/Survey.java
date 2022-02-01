import java.util.Vector;

public class Survey {
    private String name;
    private Vector<SurveyEntity> entities;

    public Survey(String name, Vector<SurveyEntity> entities) {
        this.name = name;
        this.entities = entities;
    }

    public Survey() {
        this.name = "새 설문";
        this.entities = new Vector<>();
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
        name = jSurvey.getName();
        entities.removeAllElements();
        for (JSurveyEntity jEntity : jSurvey.getJSurveyEntities()) {
            entities.add(new SurveyEntity(jEntity.getDescription(), jEntity.getPrescriptions()));
        }
    }
}
