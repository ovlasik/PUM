package pl.wroc.uni.ift.android.quizactivity;

/**
 * Created by jpola on 26.07.17.
 */

import android.os.Parcel;
import android.os.Parcelable;


//Parcelable daje mozliwosc przeslania calej klasy do Bundle
public class Question implements Parcelable {

    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue)    {

        mTextResId=textResId;
        mAnswerTrue = answerTrue;
    }

    // Wymagane metody z interfejsu Parcelable
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flag){
        //wpisujemy mTextResId
        destination.writeInt(mTextResId);
        // nie ma domyślnej opcji writeBoolean
        // użyjemy int 0 dla false, a 1 dla prawdziwego zamiast
        int value = mAnswerTrue ? 1 : 0;
        destination.writeInt(value);
    }
    // prywatny konstruktor Question do utworzenia z obiektu Parcel
    // pamiętaj, aby zamówienie było ważne, patrz metoda writeToParcel
    // bazując na metodzie writeToParcel wiemy, że musimy czytać
    // dwie kolejne liczby całkowite z paczki. Pierwszy to identyfikator zasobu dla pytania
    // drugi jest odpowiedzią na pytanie (true lub false) zapisaną tutaj jako int.
    private Question(Parcel in)
    {
        mTextResId = in.readInt();
        int value = in.readInt();
        if (value == 1) {
            mAnswerTrue = true;
        } else  { mAnswerTrue = false; }
    }

    // Interfejs parcelowy wymaga zaimplementowania pola statycznego CREATOR, które jest używane
    // wewnętrznie. Tutaj wdrażamy to w locie
    public static final Parcelable.Creator<Question> CREATOR =
            new Parcelable.Creator<Question>()
            {
                public Question createFromParcel(Parcel in)
                {
                    // this is the reason why we created private constructir in line 44
                    Question question = new Question(in);
                    return question;
                }

                // jest to druga metoda wymagana przez klasę <Pytanie> Creatora
                // właśnie zwracamy tablicę pytań [] o rozmiarze = rozmiar;
                public Question[] newArray(int size) { return new Question[size]; }

            };

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
