import java.util.Locale;
import java.util.Vector;

public class Main {

    static void test(SurveyController sc) {
        Vector<String> d = new Vector<>();
        d.add("질문1");
        d.add("질문2");
        Vector<String> p1 = new Vector<>();
        p1.add("처방1");
        p1.add("처방2");
        Vector<String> p2 = new Vector<>();
        p2.add("처방1");
        p2.add("처방3");
        SurveyEntity entity1 = new SurveyEntity("질문1", p1);
        SurveyEntity entity2 = new SurveyEntity("질문2", p2);
        Vector<SurveyEntity> entities = new Vector<>();
        entities.add(entity1);
        entities.add(entity2);

        sc.appendSurvey(new Survey("설문1", entities));

    }

    public static void main(String[] args) {

        SurveyController sc = new SurveyController();

        for (int i = 0; i < 4; i++) {
            Main.test(sc);

        }

        sc.start();

    }
}
