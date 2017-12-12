package pl.wroc.uni.ift.android.quizactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


import static android.app.Activity.RESULT_OK;


public class QuizFragment extends Fragment {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_QUESTIONS = "questions";
    private static final String KEY_SCORE = "score";
    private static final String KEY_NUMBERANSWER = "numberAnswer";
    private static final String KEY_LockedQuestions = "Lockedquestions";
    private static final String TOKEN = "CurrentTokens";
    private static final String KEY_MISCHEATER = "mIsCheater";
    private static final String ARG_QUESTION_ID = "question_id";
    private static final int CHEAT_REQEST_CODE = 0;

    private String androidOS = Build.VERSION.RELEASE;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;
    private Button mQuestionsListButton;

    private TextView mQuestionTextView;
    private TextView mAnsweredTextView;
    private TextView mTokenTextView;
    private TextView mApplikLevelTextView;

    private QuestionBank mQuestionsBank = QuestionBank.getInstance();

    private int mCurrentIndex = 0;
    private int mQuestionId;
    private int mScore = 0;
    private int mNumberAnswer = 0;
    private int mCheatTokens = 3;
    private int mAnsweredQuestions = 0;

    private boolean[] mLockedQuestions;
    private boolean[] mIsCheater;


    public static QuizFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION_ID, id);
        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check for saved data
        //Sprawdzinie czy istnieje juz zapisana instancja
        if (savedInstanceState != null) {
            //jesli instnieje t owykonuje sie ponizszy kod
            //przywracajacy zmiene do stanow zapisanych w instancji
            // here in addition we are restoring our Question array;
            // getParcelableArray returns object of type Parcelable[]
            // since our Question is implementing this interface (Parcelable)
            // we are allowed to cast the Parcelable[] to desired type which
            // is the Question[] here.
            mCheatTokens = savedInstanceState.getInt(TOKEN);
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mScore = savedInstanceState.getInt(KEY_SCORE);
            mNumberAnswer = savedInstanceState.getInt(KEY_NUMBERANSWER);
            mLockedQuestions = savedInstanceState.getBooleanArray(KEY_LockedQuestions);
            mIsCheater = savedInstanceState.getBooleanArray(KEY_MISCHEATER);
        } else {
            mIsCheater = new boolean[mQuestionsBank.size()];
            mLockedQuestions = new boolean[mQuestionsBank.size()];
            mIsCheater = initArray(mIsCheater, false);
            mLockedQuestions = initArray(mLockedQuestions, false);
        }
        mQuestionId = (int) getArguments().getSerializable(ARG_QUESTION_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment, container, false);

        mCheatButton = (Button) v.findViewById(R.id.button_cheat);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sprawdza czy odpowiedz jest prawda lub falsz
                boolean currentAnswer = mQuestionsBank.getQuestion(mCurrentIndex).isAnswerTrue();
                //przekazuje ta wartosc do intent
                Intent intent = CheatActivity.newIntent(getActivity(), currentAnswer, mIsCheater[mCurrentIndex]);
                startActivityForResult(intent, CHEAT_REQEST_CODE);
            }
        });

        //if(mCheatTokens <=  0)
        //{
        //  mTokenTextView = (TextView) findViewById(R.id.cheat_used_all_tokens);
        //mTokenTextView.setText(R.string.used_all_tokens);
        //mCheatButton.setVisibility(View.GONE);
        //}
        mApplikLevelTextView = (TextView) v.findViewById(R.id.aplik_level_text_view);
        mApplikLevelTextView.setText("Android version: " + androidOS);

        mAnsweredTextView = (TextView) v.findViewById(R.id.answered_text_view);

        mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.size();
                updateQuestion();
            }
        });

        mTrueButton = (Button) v.findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                    }
                }
        );

        mFalseButton = (Button) v.findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.size();
                updateQuestion();
            }
        });

        mPreviousButton = (ImageButton) v.findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1);
                if (mCurrentIndex < 0)
                    mCurrentIndex = mQuestionsBank.size() - 1;
                updateQuestion();
            }
        });

        updateQuestion();

        mQuestionsListButton = (Button) v.findViewById(R.id.questions_button);
        mQuestionsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuestionListActivity.class);
                startActivity(intent);
            }
        });

        updateQuestion();
        return v;
    }
    // Wykonuje sie po powrocie z innej instancji i otrzymaniu kodu zwrotnego
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CHEAT_REQEST_CODE) {
            if (data != null)
            {
                boolean answerWasShown = CheatActivity.wasAnswerShown(data);
                if (answerWasShown) {

                    if(!mIsCheater[mCurrentIndex]) {
                        Toast.makeText(getActivity(), R.string.message_for_cheaters, Toast.LENGTH_LONG).show();
                        mIsCheater[mCurrentIndex] = true;
                        mCheatTokens = mCheatTokens - 1;
                        if(mCheatTokens <= 0)
                        {
                            //mTokensTextView = (TextView) findViewById(R.id.cheat_used_all_tokens);
                            //mTokensTextView.setText(R.string.used_all_tokens);
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
        //we still have to store current index to correctly reconstruct state of our app
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(TOKEN, mCheatTokens);
        savedInstanceState.putBooleanArray(KEY_LockedQuestions,mLockedQuestions);
        savedInstanceState.putInt(KEY_SCORE, mScore);
        savedInstanceState.putInt(KEY_NUMBERANSWER, mNumberAnswer);
        savedInstanceState.putBooleanArray("isCheater", mIsCheater);
    }
    //wyswietlanie ile poprawnych odpowiedzi dano
    private void checkOdpowiedzi()
    {
        if(mNumberAnswer == mQuestionsBank.size())  {
            String odpowiedzi_string = getString(R.string.odpowiedzi_toast, mScore);
            Toast toast = Toast.makeText(getActivity(), (odpowiedzi_string), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }

    }

    // wylaczanie mozliwosci ponownego podania odpowiedzi na pytanie
    private void updateQuestion()
    {
        int question = mQuestionId;//mQuestionsBank.getQuestion(mCurrentIndex).getTextResId();
        mQuestionTextView.setText(question);
        if (!(mLockedQuestions[mCurrentIndex] == true)) {
            mTrueButton.setVisibility(View.VISIBLE);
            mFalseButton.setVisibility(View.VISIBLE);
        }
        else {
            mTrueButton.setVisibility(View.GONE);
            mFalseButton.setVisibility(View.GONE);
        }
    }

    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionsBank.getQuestion(mCurrentIndex).isAnswerTrue();


        mLockedQuestions[mCurrentIndex] = true;
        mTrueButton.setVisibility(View.GONE);
        mFalseButton.setVisibility(View.GONE);
        int toastMessageId = 0;

        if (userPressedTrue == answerIsTrue) {
            toastMessageId = R.string.correct_toast;
            mScore += 1;
        } else {
            toastMessageId = R.string.incorrect_toast;
        }
        mNumberAnswer +=1;
        checkOdpowiedzi();
        Toast toast = Toast.makeText(getActivity(), toastMessageId, Toast.LENGTH_SHORT);
        toast.show();
        toast.setGravity(Gravity.TOP,0,200);
    }

    // ?????
    private boolean[] initArray(boolean[] arrayToInit, boolean initValue) {
        for(int i = 0; i < arrayToInit.length; i++) {
            arrayToInit[i] = initValue;
        }
        return arrayToInit;
    }
}
