package ucf.eel4915l.mini_mixermobile;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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

import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RecipeManager extends AppCompatActivity {

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
        setContentView(R.layout.activity_recipe_manager);

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

                intent = new Intent(RecipeManager.this, CreateRecipeActivity.class);
                intent.putExtra("authtoken", token);
                RecipeManager.this.startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_manager, menu);
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
    public static class MyRecipesFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private RecipesAdapter adapter;
        public static final String BASE_URL = "http://192.168.8.1:8000";
        ArrayList<Recipe> recipes = new ArrayList<>();
        private SwipeRefreshLayout swipeContainer;

        public MyRecipesFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static MyRecipesFragment newInstance(int sectionNumber, String token) {
            MyRecipesFragment fragment = new MyRecipesFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("authtoken", token);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recipe_manager, container, false);

            final String token = getArguments().getString("authtoken");

            // Lookup the recyclerview in activity layout
            RecyclerView rvRecipes = (RecyclerView) rootView.findViewById(R.id.rvRecipes);
            // Create adapter passing in the sample user data
            adapter = new RecipesAdapter(recipes, token, getActivity());
            fetchRecipes(token);
            // Attach the adapter to the recyclerview to populate items
            rvRecipes.setAdapter(adapter);
            FragmentActivity c = getActivity();
            // Set layout manager to position the items
            rvRecipes.setLayoutManager(new LinearLayoutManager(c));
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
                    fetchRecipes(token);
                }
            });

            return rootView;
        }

        public void fetchRecipes(String token) {

            // Remember to CLEAR OUT old items before appending in the new ones
            adapter.clear();



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MiniMixerServiceInterface apiService =
                    retrofit.create(MiniMixerServiceInterface.class);
            Call<ArrayList<Recipe>> call = apiService.myrecipeList(token);

            call.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Response<ArrayList<Recipe>> response, Retrofit retrofit) {
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
     * Our fragment containing the drink management.
     */
    public static class AllRecipesFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private RecipesAdapter adapter;
        public static final String BASE_URL = "http://192.168.8.1:8000";
        ArrayList<Recipe> recipes = new ArrayList<>();
        private SwipeRefreshLayout swipeContainer;

        public AllRecipesFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static AllRecipesFragment newInstance(int sectionNumber, String token) {
            AllRecipesFragment fragment = new AllRecipesFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("authtoken", token);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recipe_manager, container, false);

            final String token = getArguments().getString("authtoken");

            // Lookup the recyclerview in activity layout
            RecyclerView rvRecipes = (RecyclerView) rootView.findViewById(R.id.rvRecipes);
            // Create adapter passing in the sample user data
            adapter = new RecipesAdapter(recipes, token, getActivity());
            fetchRecipes(token);
            // Attach the adapter to the recyclerview to populate items
            rvRecipes.setAdapter(adapter);
            FragmentActivity c = getActivity();
            // Set layout manager to position the items
            rvRecipes.setLayoutManager(new LinearLayoutManager(c));
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
                    fetchRecipes(token);
                }
            });

            return rootView;
        }

        public void fetchRecipes(String token) {

            // Remember to CLEAR OUT old items before appending in the new ones
            adapter.clear();



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MiniMixerServiceInterface apiService =
                    retrofit.create(MiniMixerServiceInterface.class);
            Call<ArrayList<Recipe>> call = apiService.recipeList(token);

            call.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Response<ArrayList<Recipe>> response, Retrofit retrofit) {
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
                return MyRecipesFragment.newInstance(position + 1, token);
            }
            else {
                return AllRecipesFragment.newInstance(position + 1, token);
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
                    return "My Recipes";
                case 1:
                    return "All Recipes";
            }
            return null;
        }
    }

}
