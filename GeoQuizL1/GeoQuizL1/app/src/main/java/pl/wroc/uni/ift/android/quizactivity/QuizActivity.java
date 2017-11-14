package pl.wroc.uni.ift.android.quizactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_QUESTIONS = "questions";
    private static final int CHEAT_REQEST_CODE = 0;
    private static final String KEY_SCORE = "score";
    private static final String KEY_NUMBERANSWER = "numberAnswer";
    private static final String KEY_CHECKQUESTIONS = "checkquestions";
    private static final String TOKEN = "CurrentTokens";

    private String androidOS = Build.VERSION.RELEASE;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;

    private TextView mQuestionTextView;
    private TextView mAnsweredTextView;
    private TextView mTokenTextView;
    private TextView mApplikLevelTextView;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    private int mCurrentIndex = 0;
    //
    private int mScore = 0;
    private int mNumberAnswer = 0;

    private int mCheatTokens = 3;
    private int mAnsweredQuestions = 0;



    private boolean[] CheckQuestion = new boolean[mQuestionsBank.length];
    //
    private boolean[] mIsCheater = {false, false, false, false};



    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");

        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);

        //check for saved data
        //Sprawdzinie czy istnieje juz zapisana instancja
        if(savedInstanceState != null)
        {
            //jesli instnieje t owykonuje sie ponizszy kod
            //przywracajacy zmiene do stanow zapisanych w instancji
            mCheatTokens = savedInstanceState.getInt(TOKEN);
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            Log.i(TAG, String.format("onCreate(): Restoring saved index: %d", mCurrentIndex));
            // here in addition we are restoring our Question array;
            // getParcelableArray returns object of type Parcelable[]
            // since our Question is implementing this interface (Parcelable)
            // we are allowed to cast the Parcelable[] to desired type which
            // is the Question[] here.
            mScore = savedInstanceState.getInt(KEY_SCORE);
            mNumberAnswer = savedInstanceState.getInt(KEY_NUMBERANSWER);
            CheckQuestion = savedInstanceState.getBooleanArray(KEY_CHECKQUESTIONS);
            mQuestionsBank = (Question []) savedInstanceState.getParcelableArray(KEY_QUESTIONS);
            if(mQuestionsBank == null)
            {
                Log.e(TAG,"Question bank array was not correctly returned from Bundle");
            }
            else
            {
                Log.i(TAG, "Question bank array was correctly returned from Bundle");
            }
        }


        mCheatButton = (Button) findViewById(R.id.button_cheat);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //sprawdza czy odpowiedz jest prawda lub falsz
                boolean currentAnswer = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                //przekazuje ta wartosc do intent
                Intent intent = CheatActivity.newIntent(QuizActivity.this, currentAnswer,mIsCheater[mCurrentIndex]);

                startActivityForResult(intent, CHEAT_REQEST_CODE);
            }
        });

        Log.i(TAG, String.format("Masz tyle tokenow: %d", mCheatTokens));

        if(mCheatTokens <=  0)
        {
            mTokenTextView = (TextView) findViewById(R.id.cheat_used_all_tokens);
            mTokenTextView.setText(R.string.used_all_tokens);
            mCheatButton.setVisibility(View.GONE);
        }


        mApplikLevelTextView = (TextView) findViewById(R.id.aplik_level_text_view);
        mApplikLevelTextView.setText("Android version: " + androidOS);

        mAnsweredTextView = (TextView) findViewById(R.id.answered_text_view);

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
    // Wykonuje sie po powrocie z innej instancji i otrzymaniu kodu zwrotnego
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK){
            return;
        }
        if(requestCode == CHEAT_REQEST_CODE){
            if(data != null)
            {
                boolean answerWasShown = CheatActivity.wasAnswerShown(data);
                if(answerWasShown){
                    if(!mIsCheater[mCurrentIndex]){
                        Toast.makeText(this,
                                R.string.message_for_cheaters,
                                Toast.LENGTH_LONG).show();
                        mIsCheater[mCurrentIndex] = true;
                        mCheatTokens = mCheatTokens - 1;
                        if(mCheatTokens <= 0)
                        {
                            mTokenTextView = (TextView) findViewById(R.id.cheat_used_all_tokens);
                            mTokenTextView.setText(R.string.used_all_tokens);
                            mCheatButton.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }

    //Zapisanie instancji po obroceniu ekranu
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, String.format("onSaveInstanceState: current index %d ", mCurrentIndex) );
        Log.i(TAG, String.format("onSaveInstanceState: current tokens %d ", mCheatTokens) );
        //we still have to store current index to correctly reconstruct state of our app
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(TOKEN, mCheatTokens);
        // because Question is implementing Parcelable interface
        // we are able to store array in Bundle
        savedInstanceState.putBooleanArray("checkquestions",CheckQuestion);
        savedInstanceState.putParcelableArray(KEY_QUESTIONS, mQuestionsBank);
        savedInstanceState.putInt(KEY_SCORE, mScore);
        savedInstanceState.putInt(KEY_NUMBERANSWER, mNumberAnswer);
        savedInstanceState.putBooleanArray("isCheater", mIsCheater);
    }

    //SPRECYZOWANIE CO SIĘ DZIEJE PRZY PRZYWRACANIU INSTANCJI
   // protected void onRestoreInstanceState(Bundle savedInstanceState) {
     //   super.onRestoreInstanceState(savedInstanceState);
       // mIsCheater = savedInstanceState.getBooleanArray("isCheater");
    //}


    //wyswietlanie ile poprawnych odpowiedzi dano
    private void checkOdpowiedzi()
    {
       if(mNumberAnswer == mQuestionsBank.length)  {
            String odpowiedzi_string = getString(R.string.odpowiedzi_toast, mScore);
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
            mScore += 1;
           // CheckQuestion[mCurrentIndex] = true;
            //
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {
            toastMessageId = R.string.incorrect_toast;
            //
           // CheckQuestion[mCurrentIndex] = true;
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
            //
        }
        CheckQuestion[mCurrentIndex] = true;
        mNumberAnswer +=1;
        checkOdpowiedzi();
        Toast toast = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        toast.show();
        toast.setGravity(Gravity.TOP,0,200);
    }
}
