package pl.wroc.uni.ift.android.quizactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;

    private TextView mQuestionTextView;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    private int mCurrentIndex = 0;
    //
    private int mCorrectIndex = 0;

    private boolean[] CheckQuestion = new boolean[mQuestionsBank.length];
    //



    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCurrentIndex = (mCurrentIndex +1 )% mQuestionsBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                    }
                }
        );

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCurrentIndex = (mCurrentIndex - 1);
                if(mCurrentIndex < 0 )
                    mCurrentIndex = mQuestionsBank.length - 1;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    //wyswietlanie ile poprawnych odpowiedzi dano
    private void checkOdpowiedzi()
    {
       if(mCorrectIndex == mQuestionsBank.length)  {
            String odpowiedzi_string = getString(R.string.odpowiedzi_toast, mCorrectIndex);
            Toast.makeText(this, odpowiedzi_string, Toast.LENGTH_LONG).show();
        }

    }




    // wylaczanie mozliwosci ponownego podania odpowiedzi na pytanie
    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        //
        if (CheckQuestion[mCurrentIndex] == true) {
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
        else
        {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
        //

    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int toastMessageId = 0;

        if (userPressedTrue == answerIsTrue) {
            toastMessageId = R.string.correct_toast;
            //
            CheckQuestion[mCorrectIndex] = true;
            mCorrectIndex += 1;
            //
            checkOdpowiedzi();
        } else {
            toastMessageId = R.string.incorrect_toast;
            //
            CheckQuestion[mCorrectIndex] = false;
            //
        }


        Toast toast = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        toast.show();
        toast.setGravity(Gravity.TOP,0,200);

    }
}
