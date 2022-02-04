import org.json.simple.*;

public class SurveyPrescription {

    private String name;
    private String type;

    public SurveyPrescription(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public SurveyPrescription(JSONObject prescriptionJSON) {
        this.name = (String) prescriptionJSON.get("name");
        this.type = (String) prescriptionJSON.get("type");
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return name + "(" + type + ")";
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("type", type);
        return jsonObject;
    }
}
