package pl.wroc.uni.ift.android.quizactivity;

import java.util.ArrayList;
import java.util.List;

////QUESTIONBANK JEST SINGLETONEM - JEGO KONSTRUKTOR JEST ZDEFINIOWANY JAKO PRYWATNY,
/// ЇEBY NIC NIE MOGЈO GO WYWOЈAЖ - WYKONUJE SIК GO POPRZEZ WYWOЈANIE JEGO FUNKJI PUBLICZNEJ
/// getInstance()
class QuestionBank{

    private static QuestionBank instance;
    private static List<Question> mQuestionList = new ArrayList<Question>();

    private QuestionBank() {
        mQuestionList.add(new Question(R.string.question_stolica_polski, true));
        mQuestionList.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        mQuestionList.add(new Question(R.string.question_sniezka, true));
        mQuestionList.add(new Question(R.string.question_wisla, true));
        mQuestionList.add(new Question(R.string.question_polska, false));
    }
    //FUNKCJIA SPRAWDZA CZY ISTNIEJE JUЇ UTWORZONA INSTANCJA OBIEKTU - JEЊLI NIE TO GO TWORZY,
    // PO CZYM JEST TA INSTANCJA ZWRACANA - POZWALA TO NA ODWOЈYWANIE SIК ZAWSZE DO TEGO SAMEGO,
    // POJEDYСCZEGO OBIEKTU
    public static QuestionBank getInstance() {
        if(instance == null)
        {
            instance = new QuestionBank();
        }
        return instance;
    }
    //FUNKCJA ZWRACA POJEDYСCZY OBIEKT Z LISTY, KTУREGO POZYCJA WSKAZANA JEST PRZEZ POBRANY INDEX
    public Question getQuestion(int index) {
        return mQuestionList.get(index);
    }
    //zwraca liste
    public List<Question> getQuestions() {
        return mQuestionList;
    }

    //FUNKCJA ZWRACA ROZMIAR LISTY
    public int size() {
        return mQuestionList.size();
    }
    //FUNKCJA DODAJE DO LISTY BAZOWE PYTANIA
    public static void setQuestions() {
        mQuestionList.add(new Question(R.string.question_stolica_polski, true));
        mQuestionList.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        mQuestionList.add(new Question(R.string.question_sniezka, true));
        mQuestionList.add(new Question(R.string.question_wisla, true));
        mQuestionList.add(new Question(R.string.question_polska, false));
    }

}