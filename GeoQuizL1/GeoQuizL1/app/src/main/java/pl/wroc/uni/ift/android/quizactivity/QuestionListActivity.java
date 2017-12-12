package pl.wroc.uni.ift.android.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v4.app.Fragment;

public class QuestionListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment()
    {
        return new QuestionListFragment();
    }
}
