package ar.com.jlmiola.melitestapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import ar.com.jlmiola.melitestapp.ui.SearchFragment;


public class MainActivity extends AppCompatActivity {
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            SearchFragment searchFragment = new SearchFragment();
            fragmentManager.beginTransaction()
                    .replace( R.id.activity_main_fragment, searchFragment)
                    .commitAllowingStateLoss();
        }
    }

}
