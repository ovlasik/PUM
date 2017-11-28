package pl.wroc.uni.ift.android.quizactivity;

import java.util.ArrayList;
import java.util.List;


public class QuestionBank {

    static QuestionBank instance = null;

    private ArrayList<Question> mQuestionBank;

    //lista pytan
    protected QuestionBank() {
        mQuestionBank = new ArrayList<>();
        mQuestionBank.add(new Question(R.string.question_stolica_polski, true));
        mQuestionBank.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        mQuestionBank.add(new Question(R.string.question_sniezka, true));
        mQuestionBank.add(new Question(R.string.question_wisla, true));
    }

    //FUNKCJIA SPRAWDZA CZY ISTNIEJE JUЇ UTWORZONA INSTANCJA OBIEKTU - JEЊLI NIE TO GO TWORZY,
    // PO CZYM JEST TA INSTANCJA ZWRACANA - POZWALA TO NA ODWOЈYWANIE SIК ZAWSZE DO TEGO SAMEGO,
    // POJEDYСCZEGO OBIEKTU
    public static QuestionBank getInstance() {
        if(instance == null) {
            instance = new QuestionBank();
        }

        return instance;
    }

    //zwracamy index pytania
    public Question getQuestion(int index) {
        return mQuestionBank.get(index);
    }
    // lista wszystkich pytań
    public ArrayList<Question> getQuestions() { return mQuestionBank; }
    // ilość wszystkich pytań
    public int size() { return mQuestionBank.size(); }

}