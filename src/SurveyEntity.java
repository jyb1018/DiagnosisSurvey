import java.util.Vector;

public class SurveyEntity {
    // 공개되는 정보
    private String description;
    private Boolean yn;

    // 공개되지 않는 정보?
    private Vector<String> prescriptions;

    public SurveyEntity(String description, Vector<String> prescriptions) {
        this.description = description;
        this.yn = null;
        this.prescriptions = prescriptions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isYn() {
        return yn;
    }

    public void setYn(boolean yn) {
        this.yn = yn;
    }

    public Vector<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Vector<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(description).append("$");
        for (String s : prescriptions) {
            str.append(s).append("^");
        }
        return str.toString();
    }
}
