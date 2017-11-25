package pl.wroc.uni.ift.android.quizactivity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 281664 on 21.11.2017.
 */

class QuestionBank {
    private static QuestionBank instance;
    private static List<Question> mQuestionList = new ArrayList<Question>();

    private QuestionBank() {}

    public static QuestionBank getInstance(){
        if(instance == null)
        {
            instance = new QuestionBank();
        }
        return instance;
    }

    public Question getQuestion(int index){
        return mQuestionList.get(index);
    }

    public List<Question> getmQuestion(){
        return mQuestionList;
    }
    public int size(){
        return mQuestionList.size();
    }

    public void setQuestions(){
        mQuestionList.add(new Question(R.string.question_stolica_polski, true));
        mQuestionList.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        mQuestionList.add(new Question(R.string.question_sniezka, true));
        mQuestionList.add(new Question(R.string.question_wisla, true));
    }


}
