package pl.wroc.uni.ift.android.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

public class CheatActivity extends AppCompatActivity {

    private final static String EXTRA_KEY_ANSWER = "Answer";
    private final static String KEY_QUESTION = "QUESTION";
    private final static String EXTRA_KEY_SHOWN = "wasShown";
    private final static String EXTRA_KEY_CHEATER = "alreadyCheated";
    TextView mTextViewAnswer;
    Button mButtonShow;

    boolean mAnswer;
    boolean wasClicked;

    //Okesla co ma byc zapisane w momencie zmiany instancji - obrocenia ekranu
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("wasClicked", wasClicked);
        super.onSaveInstanceState(savedInstanceState);
    }
    // okresla co ma byc przywrocone w momencie zmainy instancji - obrocenia ekranu
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //zmiena ktora ustawia czy kliknalem
        wasClicked = savedInstanceState.getBoolean("wasClicked");
        if(savedInstanceState.getBoolean("wasClicked")) {
            if (mAnswer) {
                mTextViewAnswer.setText("Prawda");
            } else {
                mTextViewAnswer.setText("Fałsz");
            }
        }
        setAnswerShown(savedInstanceState.getBoolean("wasClicked"));
    }
    //pokazuje czy odpowiedz byla dana dla danego pytania przy kliknieciu na show
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            setAnswerShown(savedInstanceState.getBoolean("wasShown"));
        } else {
            setAnswerShown(false);
        }


        setContentView(R.layout.activity_cheat);
        mAnswer = getIntent().getBooleanExtra(EXTRA_KEY_ANSWER,false);

        mTextViewAnswer = (TextView) findViewById(R.id.text_view_answer);
        mButtonShow = (Button) findViewById(R.id.button_show_answer);
        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswer) {
                    mTextViewAnswer.setText("Prawda");
                } else {
                    mTextViewAnswer.setText("Fałsz");
                }
                wasClicked = true;
                setAnswerShown(true);
            }
        });
        //Wyciaganie z intentu wartosci pod kodem EXTRA_KEY_CHEATER
        // i wykonanie(lub nie )kodu od zaleznosci od jej instancji

        if(getIntent().getBooleanExtra(EXTRA_KEY_CHEATER, false)) {
            if (mAnswer) {
                mTextViewAnswer.setText("Prawda");
            } else {
                mTextViewAnswer.setText("Fałsz");
            }
        }
    }

   //zwraca czy odpowiedz zostala podana dla danego pytania
    public static boolean wasAnswerShown(Intent data)
    {
        return data.getBooleanExtra(EXTRA_KEY_SHOWN, false);
    }
    ////FUNKCJA PRZYJMUJE DANE PRZEKAZANE JEJ PRZY WYWOŁANIU W QUIZACTIVITY I DODAJE JE DO INTENCJI,
    /// KTÓRA JEST POTEM WYKORZYSTYWANA W ONCREATE
    public static Intent newIntent(Context context, boolean answerIsTrue, boolean alreadyCheated)
    {

        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_KEY_ANSWER, answerIsTrue);
        intent.putExtra(EXTRA_KEY_CHEATER, alreadyCheated);
        return intent;

    }
  //
    private void setAnswerShown (boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_KEY_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }






}
