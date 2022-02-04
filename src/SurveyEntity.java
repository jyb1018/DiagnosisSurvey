import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Vector;

public class SurveyEntity {

    private String description;
    private Boolean yn;

    private Vector<SurveyPrescription> prescriptions;

    public SurveyEntity(String description, Vector<SurveyPrescription> prescriptions) {
        this.description = description;
        yn = null;
        this.prescriptions = prescriptions;
    }

    public SurveyEntity(JSONObject entityJSON) {
        this.description = (String) entityJSON.get("description");
        Vector<SurveyPrescription> tempPrescriptions = new Vector<>();
        for (Object prescriptionJSON: (JSONArray) entityJSON.get("prescriptions"))
            tempPrescriptions.add(new SurveyPrescription((JSONObject)prescriptionJSON));

        this.prescriptions = tempPrescriptions;
        this.yn = null;
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
        this.prescriptions = prescriptions;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(description).append("$");
        for (SurveyPrescription s : prescriptions) {
            str.append(s.toString()).append("^");
        }
        return str.toString();
    }

    public Boolean getYn() {
        return yn;
    }

    public void setYn(Boolean yn) {
        this.yn = yn;
    }

    public JSONObject toJSON() {
        JSONObject surveyEntityJSON = new JSONObject();
        JSONArray prescriptionJSONs = new JSONArray();
        for (SurveyPrescription prescription : prescriptions)
            prescriptionJSONs.add(prescription.toJSON());

        surveyEntityJSON.put("description", description);
        surveyEntityJSON.put("prescriptions", prescriptionJSONs);
        return surveyEntityJSON;
    }
}
