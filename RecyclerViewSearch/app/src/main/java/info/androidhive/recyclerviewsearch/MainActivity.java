package info.androidhive.recyclerviewsearch;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import info.androidhive.recyclerviewsearch.model.Chapters;
import info.androidhive.recyclerviewsearch.model.Course;
import info.androidhive.recyclerviewsearch.model.Courses;
import info.androidhive.recyclerviewsearch.model.Subjects;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private ContactsAdapter mAdapter;
    private SearchView searchView;
    private OkHttpClient client = getUnsafeOkHttpClient();
    private Courses courses;
    private HashMap<String, ArrayList<String>> courseMap = new HashMap<String, ArrayList<String>>();

    // url to fetch contacts json
    private static final String URL = "https://api.androidhive.info/json/contacts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new ContactsAdapter(this, contactList, this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        fetchContacts();
    }




    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private class FetchDataAsync extends AsyncTask<String, Void, Courses> {



        @Override
        protected Courses doInBackground(String... params) {

            MediaType textPlainMT = MediaType.parse("application/json; charset=utf-8");

            final Request request = new Request.Builder().url("https://s3.amazonaws.com/squareroot/Eamcet.json")
                    .addHeader("Accept", "application/json")
                    .get().build()
                    ;

            okhttp3.Response response = null;
            try {
                response = client.newCall(request).execute();

                Gson gson = new Gson();
                courses = gson.fromJson(response.body().string(), Courses.class);

                System.out.println("-->"+courses);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No Internet Connection.. Please check", Toast.LENGTH_LONG).show();
            }
            return courses;
        }

    }


    /**
     * fetches json by making http calls
     */
    private void fetchContacts() {

        Log.i("Before -->", "1");

        try {
            Object result = new FetchDataAsync().execute("").get(5, TimeUnit.SECONDS);

            ArrayList<String> mainMenu = new ArrayList<String>();

            for(Course course : courses.getCourses()){
                Contact item = new Contact(course.getCourseType());
                contactList.add(item);
                mainMenu.add(item.getName());

                ArrayList<String> subjects = new ArrayList<String>();

                for(Subjects subject : course.getSubjects()){

                    ArrayList<String> chapters = new ArrayList<String>();

                    for(Chapters chapter : subject.getChapters()){
                        chapters.add(chapter.getName());
                    }

                    courseMap.put(subject.getName(), chapters);
                    subjects.add(subject.getName());

                }
                courseMap.put(course.getCourseType(), subjects);
            }


            courseMap.put("MainMenu", mainMenu);

            Log.i("Courses -->", courseMap.toString());

            mAdapter.notifyDataSetChanged();



        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Something went wrong... Please restart the application...", Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Something went wrong... Please restart the application...", Toast.LENGTH_LONG).show();
        } catch (TimeoutException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No Internet Connection.. Please check", Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No Internet Connection.. Please check", Toast.LENGTH_LONG).show();
        }


        Log.i("After -->", "1");

        /*JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<Contact> items = new Gson().fromJson(response.toString(), new TypeToken<List<Contact>>() {
                        }.getType());

                        // adding contacts to contacts list
                        contactList.clear();
                        contactList.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);

        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }

        /*if(courseMap.get("selectedChapter") != null){
            courseMap.remove("selectedChapter");
        }else
        */

        if(courseMap.get("selectedSubject") != null){

            contactList.clear();

            for (String item : courseMap.get(courseMap.get("selectedCourse").get(0))) {
                Contact newItem = new Contact(item);
                contactList.add(newItem);
            }

            courseMap.remove("selectedSubject");

            //contactList.addAll(courseMap.get(contact.getName()));
            //mAdapter.notifyDataSetChanged();

        }else if(courseMap.get("selectedCourse") != null){

            contactList.clear();


            for (String item : courseMap.get("MainMenu")) {
                Contact newItem = new Contact(item);
                contactList.add(newItem);
            }

            courseMap.remove("selectedCourse");

            //contactList.addAll(courseMap.get(contact.getName()));


        }

        else{
            super.onBackPressed();
        }

        mAdapter.notifyDataSetChanged();

      //
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(Contact contact) {


        if(courseMap.containsKey(contact.getName())) {

            contactList.clear();

            if(courseMap.get("selectedCourse") == null){
                ArrayList<String> courseList = new ArrayList<String>();
                courseList.add(contact.getName());
                courseMap.put("selectedCourse", courseList);
            }else if(courseMap.get("selectedSubject") == null){
                ArrayList<String> courseList = new ArrayList<String>();
                courseList.add(contact.getName());
                courseMap.put("selectedSubject", courseList);
            }
            /*else if(courseMap.get("selectedChapter") == null){
                ArrayList<String> courseList = new ArrayList<String>();
                courseList.add(contact.getName());
                courseMap.put("selectedChapter", courseList);
            }*/

            for (String item : courseMap.get(contact.getName())) {
                Contact newItem = new Contact(item);
                contactList.add(newItem);
            }

            //contactList.addAll(courseMap.get(contact.getName()));
            mAdapter.notifyDataSetChanged();
        }else{

            contactList.clear();

            Contact newItem = new Contact("Take Mock Exam and Compare results with others", "Need to Solve 20 questions in 20 minutes");
            Contact newItem2 = new Contact("Practice (Over 2000+) Questions ", "With No Time Limit");
            Contact newItem3 = new Contact("Prepare for the exam", "Revise chapters and re-collect important points");

            contactList.add(0,newItem);
            contactList.add(1,newItem2);
            contactList.add(2,newItem3);
            mAdapter.notifyDataSetChanged();
        }

        Toast.makeText(getApplicationContext(), "Selected: " + contact.getName() + ", " + contact.getPhone(), Toast.LENGTH_SHORT).show();
    }
}
