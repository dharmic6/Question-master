package test.com.question;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }


    public String[] options;

    int questionIndex;

    public void setOptions(String[] options, int questionIndex) {
        this.options = options;
        this.questionIndex = questionIndex;
    }


    @Override
    public void onStart() {
        super.onStart();
        ListView listView = (ListView) getView().findViewById(R.id.lView);
        final OptionsWebAdapter webAdapter =  new OptionsWebAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,options, (FragmentActivity) getActivity(), questionIndex);
        listView.setAdapter(webAdapter);
    }
}
