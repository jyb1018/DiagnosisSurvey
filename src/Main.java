import javax.swing.*;
import java.util.Vector;

public class Main {

    static void makeExample(SurveyController sc) {



        Vector<SurveyPrescription> pre1 = new Vector<>();
        pre1.add(new SurveyPrescription("처방1", "종류1"));
        pre1.add(new SurveyPrescription("처방2", "종류1"));
        Vector<SurveyPrescription> pre2 = new Vector<>();
        pre2.add(new SurveyPrescription("처방1", "종류1"));
        pre2.add(new SurveyPrescription("처방3", "종류2"));
        SurveyEntity entity1 = new SurveyEntity("질문1", pre1);
        SurveyEntity entity2 = new SurveyEntity("질문2", pre2);
        Vector<SurveyEntity> entities = new Vector<>();
        entities.add(entity1);
        entities.add(entity2);

        sc.appendSurvey(new Survey("예시 설문", entities, sc));


//        교수님 제공 예시 (문진표.sur)
//
//        String[] descriptions = {
//                "어린시절부터 잡티 or 주근깨가 있었다.",
//                "나이가 들면서 눈밑, 광대부위에 색소가 올라오거나 진해진다.",
//                "피부는 좋은데 색소침착(얼룩덜룩) 신경 쓰인다.",
//                "평소 건조함을 느끼거나 각질이 자주 일어난다.",
//                "T존 부위 or U존 부위가 항상 번들거린다.",
//                "피부가 번들거리고 트러블이 올라온다.",
//                "화장이 잘 뜨고 쉽게 지워진다.",
//                "피부 모공 or 코 주변부위에 피지가 끼이는것 같다.",
//                "피부톤이 칙칙해 보인다.",
//                "골프, 등산, 캠핑 등 야외활동을 많이 한다.",
//                "얼굴 or 코옆(실핏줄)이 붉은 편이다.",
//                "아토피 or 지루성 피부염이 있다.",
//                "얼굴에 잔주름이 많다.",
//                "햇빛 알레르기가 있다.",
//                "여드름이 생긴 뒤 붉은 자국이 남았다 or 트러블도 같이있다.",
//                "피부 처짐이나 탄력이 고민이다.",
//                "모공이 넓은 편이다."
//        };
//
//        Vector<Vector<SurveyPrescription>> prescriptionVectors = new Vector<>();
//        for (int i = 0; i < 17; i++)
//            prescriptionVectors.add(new Vector<>());
//
//        prescriptionVectors.get(0).add(new SurveyPrescription("AAA", "레이저"));
//        prescriptionVectors.get(0).add(new SurveyPrescription("BBB", "레이저"));
//        prescriptionVectors.get(0).add(new SurveyPrescription("CCC", "레이저"));
//        prescriptionVectors.get(0).add(new SurveyPrescription("가가가", "피부관리"));
//
//        prescriptionVectors.get(1).add(new SurveyPrescription("AAA", "레이저"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("BBB", "레이저"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("CCC", "레이저"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("DDD", "레이저"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("EEE", "레이저"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("FFF", "레이저"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("GGG", "레이저"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("가가가", "피부관리"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("나나나", "피부관리"));
//        prescriptionVectors.get(1).add(new SurveyPrescription("다다다", "피부관리"));
//
//        prescriptionVectors.get(2).add(new SurveyPrescription("HHH", "레이저"));
//        prescriptionVectors.get(2).add(new SurveyPrescription("III", "레이저"));
//        prescriptionVectors.get(2).add(new SurveyPrescription("JJJ", "레이저"));
//        prescriptionVectors.get(2).add(new SurveyPrescription("가가가", "피부관리"));
//        prescriptionVectors.get(2).add(new SurveyPrescription("나나나", "피부관리"));
//        prescriptionVectors.get(2).add(new SurveyPrescription("라라라", "피부관리"));
//
//        prescriptionVectors.get(3).add(new SurveyPrescription("KKK", "레이저"));
//        prescriptionVectors.get(3).add(new SurveyPrescription("001", "주사시술"));
//        prescriptionVectors.get(3).add(new SurveyPrescription("002", "주사시술"));
//        prescriptionVectors.get(3).add(new SurveyPrescription("라라라", "피부관리"));
//        prescriptionVectors.get(3).add(new SurveyPrescription("나나나", "피부관리"));
//        prescriptionVectors.get(3).add(new SurveyPrescription("마마마", "피부관리"));
//        prescriptionVectors.get(3).add(new SurveyPrescription("바바바", "피부관리"));
//
//        prescriptionVectors.get(4).add(new SurveyPrescription("002", "주사시술"));
//        prescriptionVectors.get(4).add(new SurveyPrescription("마마마", "피부관리"));
//        prescriptionVectors.get(4).add(new SurveyPrescription("사사사", "피부관리"));
//        prescriptionVectors.get(4).add(new SurveyPrescription("아아아", "피부관리"));
//
//
//
//        prescriptionVectors.get(5).add(new SurveyPrescription("LLL", "레이저"));
//        prescriptionVectors.get(5).add(new SurveyPrescription("마마마", "피부관리"));
//        prescriptionVectors.get(5).add(new SurveyPrescription("사사사", "피부관리"));
//        prescriptionVectors.get(5).add(new SurveyPrescription("아아아", "피부관리"));
//        prescriptionVectors.get(5).add(new SurveyPrescription("자자자", "피부관리"));
//
//
//        prescriptionVectors.get(6).add(new SurveyPrescription("마마마", "피부관리"));
//        prescriptionVectors.get(6).add(new SurveyPrescription("라라라", "피부관리"));
//        prescriptionVectors.get(6).add(new SurveyPrescription("나나나", "피부관리"));
//
//        prescriptionVectors.get(7).add(new SurveyPrescription("MMM", "레이저"));
//        prescriptionVectors.get(7).add(new SurveyPrescription("NNN", "레이저"));
//        prescriptionVectors.get(7).add(new SurveyPrescription("사사사", "피부관리"));
//        prescriptionVectors.get(7).add(new SurveyPrescription("아아아", "피부관리"));
//        prescriptionVectors.get(7).add(new SurveyPrescription("마마마", "피부관리"));
//
//        prescriptionVectors.get(8).add(new SurveyPrescription("EEE", "레이저"));
//        prescriptionVectors.get(8).add(new SurveyPrescription("FFF", "레이저"));
//        prescriptionVectors.get(8).add(new SurveyPrescription("KKK", "레이저"));
//        prescriptionVectors.get(8).add(new SurveyPrescription("002", "주사시술"));
//        prescriptionVectors.get(8).add(new SurveyPrescription("003", "주사시술"));
//        prescriptionVectors.get(8).add(new SurveyPrescription("나나나", "피부관리"));
//        prescriptionVectors.get(8).add(new SurveyPrescription("가가가", "피부관리"));
//        prescriptionVectors.get(8).add(new SurveyPrescription("바바바", "피부관리"));
//
//        prescriptionVectors.get(9).add(new SurveyPrescription("EEE", "레이저"));
//        prescriptionVectors.get(9).add(new SurveyPrescription("FFF", "레이저"));
//        prescriptionVectors.get(9).add(new SurveyPrescription("바바바", "피부관리"));
//        prescriptionVectors.get(9).add(new SurveyPrescription("라라라", "피부관리"));
//        prescriptionVectors.get(9).add(new SurveyPrescription("가가가", "피부관리"));
//
//        prescriptionVectors.get(10).add(new SurveyPrescription("OOO", "레이저"));
//        prescriptionVectors.get(10).add(new SurveyPrescription("PPP", "레이저"));
//        prescriptionVectors.get(10).add(new SurveyPrescription("차차차", "피부관리"));
//
//        prescriptionVectors.get(11).add(new SurveyPrescription("001", "주사시술"));
//        prescriptionVectors.get(11).add(new SurveyPrescription("002", "주사시술"));
//        prescriptionVectors.get(11).add(new SurveyPrescription("003", "주사시술"));
//        prescriptionVectors.get(11).add(new SurveyPrescription("라라라", "피부관리"));
//        prescriptionVectors.get(11).add(new SurveyPrescription("차차차", "피부관리"));
//
//
//        prescriptionVectors.get(12).add(new SurveyPrescription("QQQ", "레이저"));
//        prescriptionVectors.get(12).add(new SurveyPrescription("RRR", "레이저"));
//        prescriptionVectors.get(12).add(new SurveyPrescription("SSS", "레이저"));
//        prescriptionVectors.get(12).add(new SurveyPrescription("001", "주사시술"));
//        prescriptionVectors.get(12).add(new SurveyPrescription("002", "주사시술"));
//        prescriptionVectors.get(12).add(new SurveyPrescription("003", "주사시술"));
//        prescriptionVectors.get(12).add(new SurveyPrescription("004", "주사시술"));
//        prescriptionVectors.get(12).add(new SurveyPrescription("나나나", "피부관리"));
//        prescriptionVectors.get(12).add(new SurveyPrescription("라라라", "피부관리"));
//
//
//        prescriptionVectors.get(13).add(new SurveyPrescription("001", "레이저"));
//        prescriptionVectors.get(13).add(new SurveyPrescription("004", "레이저"));
//        prescriptionVectors.get(13).add(new SurveyPrescription("라라라", "피부관리"));
//        prescriptionVectors.get(13).add(new SurveyPrescription("카카카", "피부관리"));
//
//        prescriptionVectors.get(14).add(new SurveyPrescription("TTT", "레이저"));
//        prescriptionVectors.get(14).add(new SurveyPrescription("UUU", "레이저"));
//        prescriptionVectors.get(14).add(new SurveyPrescription("001", "주사시술"));
//        prescriptionVectors.get(14).add(new SurveyPrescription("002", "주사시술"));
//        prescriptionVectors.get(14).add(new SurveyPrescription("003", "주사시술"));
//        prescriptionVectors.get(14).add(new SurveyPrescription("005", "주사시술"));
//        prescriptionVectors.get(14).add(new SurveyPrescription("아아아", "피부관리"));
//        prescriptionVectors.get(14).add(new SurveyPrescription("카카카", "피부관리"));
//        prescriptionVectors.get(14).add(new SurveyPrescription("자자자", "피부관리"));
//
//        prescriptionVectors.get(15).add(new SurveyPrescription("VVV", "레이저"));
//        prescriptionVectors.get(15).add(new SurveyPrescription("QQQ", "레이저"));
//        prescriptionVectors.get(15).add(new SurveyPrescription("WWW", "레이저"));
//        prescriptionVectors.get(15).add(new SurveyPrescription("XXX", "레이저"));
//        prescriptionVectors.get(15).add(new SurveyPrescription("002", "주사시술"));
//        prescriptionVectors.get(15).add(new SurveyPrescription("003", "주사시술"));
//        prescriptionVectors.get(15).add(new SurveyPrescription("006", "주사시술"));
//        prescriptionVectors.get(15).add(new SurveyPrescription("라라라", "피부관리"));
//
//        prescriptionVectors.get(16).add(new SurveyPrescription("MMM", "레이저"));
//        prescriptionVectors.get(16).add(new SurveyPrescription("NNN", "레이저"));
//        prescriptionVectors.get(16).add(new SurveyPrescription("RRR", "레이저"));
//        prescriptionVectors.get(16).add(new SurveyPrescription("KKK", "레이저"));
//        prescriptionVectors.get(16).add(new SurveyPrescription("002", "주사시술"));
//        prescriptionVectors.get(16).add(new SurveyPrescription("003", "주사시술"));
//        prescriptionVectors.get(16).add(new SurveyPrescription("004", "주사시술"));
//        prescriptionVectors.get(16).add(new SurveyPrescription("카카카", "피부관리"));
//
//        Vector<SurveyEntity> exampleSurveyEntities = new Vector<>();
//        for (int i = 0; i < descriptions.length; i++) {
//            exampleSurveyEntities.add(new SurveyEntity(descriptions[i], prescriptionVectors.get(i)));
//        }
//
//        sc.appendSurvey(new Survey("문진표", exampleSurveyEntities, sc));
//
//

    }

    public static void main(String[] args) {

        SurveyController sc = new SurveyController();
        Main.makeExample(sc);


        sc.start();

    }
}



