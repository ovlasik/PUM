//package pl.wroc.uni.ift.android.quizactivity;

//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


//public class QuestionAdapter extends RecyclerView.Adapter {

    /// ArrayList<Question> questions = new ArrayList<>();

    //private RecyclerView recyclerView;

    //public class ViewHolder extends RecyclerView.ViewHolder {

      //  public TextView mTextView;
      //  public TextView mTextView2;

        //pobieramy id textView
       // public ViewHolder(View v) {
           // super(v);
           // mTextView = (TextView) v.findViewById(R.id.question_text);
           // mTextView2 = (TextView) v.findViewById(R.id.question_title);
       // }
   // }
    //konstruktor przypisuje liste pytan i recycler view
   // public QuestionAdapter(ArrayList<Question> questions, RecyclerView recyclerView) {
   //     this.questions = questions;
    //    this.recyclerView = recyclerView;
   // }
    //ustalamy jak ma wygladac widok pojedunczego pytania
    //@Override
   // public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {

    //    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question, viewGroup, false);
     //   return new ViewHolder(view);

   // }

    //ustawiamy tresc pytania w widoku recycler view
   // @Override
    //public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {

     //   ((ViewHolder) viewHolder).mTextView.setText(questions.get(i).getTextResId());
       // ((ViewHolder) viewHolder).mTextView2.setText("Pytanie # " + String.valueOf(i));
//
   // }
    //zwracamy ile ma byc tych obiektow
   // @Override
   // public int getItemCount() {
     //   return questions.size();
   // }
//}