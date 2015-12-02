package ucf.eel4915l.mini_mixermobile;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class DrinkManager extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        token = bundle.getString("authtoken");

        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;

                intent = new Intent(view.getContext(), CreateDrinkActivity.class);
                intent.putExtra("authtoken", token);
                view.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drink_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Our fragment containing the pump management.
     */
    public static class PumpFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static final String BASE_URL = "http://192.168.8.1:8000";
        ArrayList<Drink> drinks = new ArrayList<>();
        ArrayList<Drink> loadedDrinks = new ArrayList<>();
        private ArrayAdapter<Drink> pump_a_spinner_adapter;
        private ArrayAdapter<Drink> pump_b_spinner_adapter;
        private ArrayAdapter<Drink> pump_c_spinner_adapter;
        private ArrayAdapter<Drink> pump_d_spinner_adapter;
        private ArrayAdapter<Drink> pump_e_spinner_adapter;
        private ArrayAdapter<Drink> pump_f_spinner_adapter;
        private Spinner pump_a_spinner;
        private Spinner pump_b_spinner;
        private Spinner pump_c_spinner;
        private Spinner pump_d_spinner;
        private Spinner pump_e_spinner;
        private Spinner pump_f_spinner;
        private TextView pump_a_loaded;
        private TextView pump_b_loaded;
        private TextView pump_c_loaded;
        private TextView pump_d_loaded;
        private TextView pump_e_loaded;
        private TextView pump_f_loaded;

        private final int mSpinnerCount = 6;
        private int mSpinnersStarted = 0;

        public PumpFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PumpFragment newInstance(int sectionNumber, String token) {
            PumpFragment fragment = new PumpFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("authtoken", token);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pump_manager, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            final String token = getArguments().getString("authtoken");



            pump_a_spinner = (Spinner) rootView.findViewById(R.id.pump_a_spinner);
            pump_b_spinner = (Spinner) rootView.findViewById(R.id.pump_b_spinner);
            pump_c_spinner = (Spinner) rootView.findViewById(R.id.pump_c_spinner);
            pump_d_spinner = (Spinner) rootView.findViewById(R.id.pump_d_spinner);
            pump_e_spinner = (Spinner) rootView.findViewById(R.id.pump_e_spinner);
            pump_f_spinner = (Spinner) rootView.findViewById(R.id.pump_f_spinner);

            pump_a_loaded = (TextView) rootView.findViewById(R.id.pump_a_loaded);
            pump_b_loaded = (TextView) rootView.findViewById(R.id.pump_b_loaded);
            pump_c_loaded = (TextView) rootView.findViewById(R.id.pump_c_loaded);
            pump_d_loaded = (TextView) rootView.findViewById(R.id.pump_d_loaded);
            pump_e_loaded = (TextView) rootView.findViewById(R.id.pump_e_loaded);
            pump_f_loaded = (TextView) rootView.findViewById(R.id.pump_f_loaded);



            // Create an ArrayAdapter using the string array and a default spinner layout
            pump_a_spinner_adapter = new ArrayAdapter<Drink>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, drinks);
            // Specify the layout to use when the list of choices appears
            pump_a_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            pump_a_spinner.setAdapter(pump_a_spinner_adapter);


            // Create an ArrayAdapter using the string array and a default spinner layout
            pump_b_spinner_adapter = new ArrayAdapter<Drink>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, drinks);
            // Specify the layout to use when the list of choices appears
            pump_b_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            pump_b_spinner.setAdapter(pump_b_spinner_adapter);


            // Create an ArrayAdapter using the string array and a default spinner layout
            pump_c_spinner_adapter = new ArrayAdapter<Drink>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, drinks);
            // Specify the layout to use when the list of choices appears
            pump_c_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            pump_c_spinner.setAdapter(pump_c_spinner_adapter);


            // Create an ArrayAdapter using the string array and a default spinner layout
            pump_d_spinner_adapter = new ArrayAdapter<Drink>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, drinks);
            // Specify the layout to use when the list of choices appears
            pump_d_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            pump_d_spinner.setAdapter(pump_d_spinner_adapter);


            // Create an ArrayAdapter using the string array and a default spinner layout
            pump_e_spinner_adapter = new ArrayAdapter<Drink>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, drinks);
            // Specify the layout to use when the list of choices appears
            pump_e_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            pump_e_spinner.setAdapter(pump_e_spinner_adapter);


            // Create an ArrayAdapter using the string array and a default spinner layout
            pump_f_spinner_adapter = new ArrayAdapter<Drink>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, drinks);
            // Specify the layout to use when the list of choices appears
            pump_f_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            pump_f_spinner.setAdapter(pump_f_spinner_adapter);


            pump_a_spinner.setSelection(Adapter.NO_SELECTION, false);
            pump_b_spinner.setSelection(Adapter.NO_SELECTION, false);
            pump_c_spinner.setSelection(Adapter.NO_SELECTION, false);
            pump_d_spinner.setSelection(Adapter.NO_SELECTION, false);
            pump_e_spinner.setSelection(Adapter.NO_SELECTION, false);
            pump_f_spinner.setSelection(Adapter.NO_SELECTION, false);

            pump_a_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    if(mSpinnersStarted < mSpinnerCount) {

                        mSpinnersStarted++;
                    }

                    else {


                        Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                        Drink drink = pump_a_spinner_adapter.getItem(pos);
                        drink.setInPump("A");
                        setLoadedDrink(token, drink);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

            pump_b_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if(mSpinnersStarted < mSpinnerCount) {

                        mSpinnersStarted++;
                    }

                    else {
                        Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                        Drink drink = pump_b_spinner_adapter.getItem(pos);
                        drink.setInPump("B");
                        setLoadedDrink(token, drink);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

            pump_c_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if(mSpinnersStarted < mSpinnerCount) {

                        mSpinnersStarted++;
                    }

                    else {
                        Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                        Drink drink = pump_c_spinner_adapter.getItem(pos);
                        drink.setInPump("C");
                        setLoadedDrink(token, drink);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

            pump_d_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if(mSpinnersStarted < mSpinnerCount) {

                        mSpinnersStarted++;
                    }

                    else {
                        Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                        Drink drink = pump_d_spinner_adapter.getItem(pos);
                        drink.setInPump("D");
                        setLoadedDrink(token, drink);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

            pump_e_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if(mSpinnersStarted < mSpinnerCount) {

                        mSpinnersStarted++;
                    }

                    else {
                        Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                        Drink drink = pump_e_spinner_adapter.getItem(pos);
                        drink.setInPump("E");
                        setLoadedDrink(token, drink);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

            pump_f_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if(mSpinnersStarted < mSpinnerCount) {

                        mSpinnersStarted++;
                    }

                    else {
                        Toast.makeText(parent.getContext(), "Selected: \n" + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                        Drink drink = pump_f_spinner_adapter.getItem(pos);
                        drink.setInPump("F");
                        setLoadedDrink(token, drink);


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });


            fetchLoadedDrinks(token);
            fetchDrinks(token);

            return rootView;
        }

        public void setLoadedDrink(final String token, Drink drink){

            for(Drink loadedDrink: loadedDrinks) {
                if (loadedDrink.getInPump().equals(drink.getInPump())) {

                    loadedDrink.setInPump("");
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    MiniMixerServiceInterface apiService =
                            retrofit.create(MiniMixerServiceInterface.class);
                    Call<Drink> call = apiService.updateDrink(token, loadedDrink, loadedDrink.getId());

                    call.enqueue(new Callback<Drink>() {
                        @Override
                        public void onResponse(Response<Drink> response, Retrofit retrofit) {

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            // Log error here since request failed
                            Log.d("DrinkManager", "Failed update!");
                        }
                    });

                }
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MiniMixerServiceInterface apiService =
                    retrofit.create(MiniMixerServiceInterface.class);
            Call<Drink> call = apiService.updateDrink(token, drink, drink.getId());

            call.enqueue(new Callback<Drink>() {
                @Override
                public void onResponse(Response<Drink> response, Retrofit retrofit) {
                    fetchLoadedDrinks(token);

                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    Log.d("DrinkManager", "Failed update!");
                }
            });

        }

        public void fetchLoadedDrinks(String token) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MiniMixerServiceInterface apiService =
                    retrofit.create(MiniMixerServiceInterface.class);
            Call<ArrayList<Drink>> call = apiService.loadedDrinks(token);

            call.enqueue(new Callback<ArrayList<Drink>>() {
                @Override
                public void onResponse(Response<ArrayList<Drink>> response, Retrofit retrofit) {

                    loadedDrinks = response.body();
                    pump_a_loaded.setText("None");
                    pump_b_loaded.setText("None");
                    pump_c_loaded.setText("None");
                    pump_d_loaded.setText("None");
                    pump_e_loaded.setText("None");
                    pump_f_loaded.setText("None");


                    for(Drink drink: loadedDrinks) {
                        if(drink.getInPump().equals("A")) {
                            pump_a_loaded.setText(drink.getName());
                        }
                        else if(drink.getInPump().equals("B")) {
                            pump_b_loaded.setText(drink.getName());
                        }
                        else if(drink.getInPump().equals("C")) {
                            pump_c_loaded.setText(drink.getName());
                        }
                        else if(drink.getInPump().equals("D")) {
                            pump_d_loaded.setText(drink.getName());
                        }
                        else if(drink.getInPump().equals("E")) {
                            pump_e_loaded.setText(drink.getName());
                        }
                        else if(drink.getInPump().equals("F")) {
                            pump_f_loaded.setText(drink.getName());
                        }


                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    Log.d("OrderManager", "Failed retrieving!");
                }
            });


        }

        public void fetchDrinks(String token) {

            // Remember to CLEAR OUT old items before appending in the new ones
            drinks.clear();



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MiniMixerServiceInterface apiService =
                    retrofit.create(MiniMixerServiceInterface.class);
            Call<ArrayList<Drink>> call = apiService.drinkList(token);

            call.enqueue(new Callback<ArrayList<Drink>>() {
                @Override
                public void onResponse(Response<ArrayList<Drink>> response, Retrofit retrofit) {
                    drinks.clear();
                    drinks.addAll(response.body());


                    pump_a_spinner_adapter.notifyDataSetChanged();
                    pump_b_spinner_adapter.notifyDataSetChanged();
                    pump_c_spinner_adapter.notifyDataSetChanged();
                    pump_d_spinner_adapter.notifyDataSetChanged();
                    pump_e_spinner_adapter.notifyDataSetChanged();
                    pump_f_spinner_adapter.notifyDataSetChanged();

                    pump_a_spinner.setSelection(Adapter.NO_SELECTION, false);
                    pump_b_spinner.setSelection(Adapter.NO_SELECTION, false);
                    pump_c_spinner.setSelection(Adapter.NO_SELECTION, false);
                    pump_d_spinner.setSelection(Adapter.NO_SELECTION, false);
                    pump_e_spinner.setSelection(Adapter.NO_SELECTION, false);
                    pump_f_spinner.setSelection(Adapter.NO_SELECTION, false);


                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    Log.d("OrderManager", "Failed retrieving!");
                }
            });


        }
    }

    /**
     * Our fragment containing the drink management.
     */
    public static class DrinkFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private DrinksAdapter adapter;
        public static final String BASE_URL = "http://192.168.8.1:8000";
        ArrayList<Drink> drinks = new ArrayList<>();
        private SwipeRefreshLayout swipeContainer;


        public DrinkFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static DrinkFragment newInstance(int sectionNumber, String token) {
            DrinkFragment fragment = new DrinkFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("authtoken", token);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_drink_manager, container, false);

            final String token = getArguments().getString("authtoken");

            // Lookup the recyclerview in activity layout
            RecyclerView rvDrinks = (RecyclerView) rootView.findViewById(R.id.rvDrinks);
            // Create adapter passing in the sample user data
            adapter = new DrinksAdapter(drinks, token, getActivity());
            fetchDrinks(token);
            // Attach the adapter to the recyclerview to populate items
            rvDrinks.setAdapter(adapter);
            FragmentActivity c = getActivity();
            // Set layout manager to position the items
            rvDrinks.setLayoutManager(new LinearLayoutManager(c));
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
            // Setup refresh listener which triggers new data loading
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.
                    fetchDrinks(token);
                }
            });


            return rootView;
        }

        public void fetchDrinks(String token) {

            // Remember to CLEAR OUT old items before appending in the new ones
            adapter.clear();



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MiniMixerServiceInterface apiService =
                    retrofit.create(MiniMixerServiceInterface.class);
            Call<ArrayList<Drink>> call = apiService.drinkList(token);

            call.enqueue(new Callback<ArrayList<Drink>>() {
                @Override
                public void onResponse(Response<ArrayList<Drink>> response, Retrofit retrofit) {
                    adapter.clear();
                    adapter.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(Throwable t) {
                    // Log error here since request failed
                    Log.d("OrderManager", "Failed retrieving!");
                }
            });


        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PumpFragment (defined as a static inner class below).
            if(position == 0) {
                return DrinkFragment.newInstance(position + 1, token);
            }
            else {
                return PumpFragment.newInstance(position + 1, token);
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Drinks";
                case 1:
                    return "Pumps";
            }
            return null;
        }
    }
}
