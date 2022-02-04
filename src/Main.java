import java.util.Locale;
import java.util.Vector;

public class Main {

    static void test(SurveyController sc) {
        Vector<String> d = new Vector<>();
        d.add("질문1");
        d.add("질문2");
        Vector<SurveyPrescription> p1 = new Vector<>();
        p1.add(new SurveyPrescription("처방1", "레이저"));
        p1.add(new SurveyPrescription("처방2", "레이저"));
        Vector<SurveyPrescription> p2 = new Vector<>();
        p2.add(new SurveyPrescription("처방1", "레이저"));
        p2.add(new SurveyPrescription("처방3", "주사시술"));
        SurveyEntity entity1 = new SurveyEntity("질문1", p1);
        SurveyEntity entity2 = new SurveyEntity("질문2", p2);
        Vector<SurveyEntity> entities = new Vector<>();
        entities.add(entity1);
        entities.add(entity2);

        sc.appendSurvey(new Survey("설문1", entities, sc));

    }

    public static void main(String[] args) {

        SurveyController sc = new SurveyController();

        for (int i = 0; i < 4; i++) {
            Main.test(sc);

        }

        sc.start();

    }
}



// TODO 는 아니고 추가로 하면 좋은 것
// tooltip text 설정하기
// 도움말 다이얼로그 만들기
// Github README 만들기
// 정보란 라이센스 작성하기