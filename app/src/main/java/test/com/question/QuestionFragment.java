package test.com.question;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {


    String content;

    public void setContent(String content){
        this.content = content;
    }

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        WebView webView = (WebView)getView().findViewById(R.id.webView);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.getSettings().setJavaScriptEnabled(true);


        webView.loadDataWithBaseURL("file:///android_asset/",
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML Basic 1.1//EN\"\n" +
                        "    \"http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd\">" +
                        "<html>\n" +
                        "<head>" +
                        //"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                        "<link rel=\"stylesheet\" href=\"file:///android_asset/jqmath-0.4.3.css\">\n" +
                        "</head>" +
                        "<body>" +
                        "<b>Question</b> : "+content+
                        "</body>\n" +
                        "<script src=\"file:///android_asset/script.js\"></script>\n" +
                        "<script src=\"file:///android_asset/jquery-1.4.3.min.js\"></script>\n" +
                        "<script src=\"file:///android_asset/jqmath-etc-0.4.6.min.js\" charset=\"utf-8\"></script>\n" +
                        "<script src=\"file:///android_asset/script.js\"></script>\n" +

                        "</html>", "text/html", "UTF-8", null);


    }
}
