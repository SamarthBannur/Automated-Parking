package com.app.sample.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sample.recipe.data.Tools;
import com.app.sample.recipe.data.Utils;
import com.app.sample.recipe.model.Recipe;

public class ActivityRecipeDetails extends AppCompatActivity {

    public static final String EXTRA_OBJCT = "com.app.sample.recipe.OBJ";

    // give preparation animation activity transition
    public static void navigate(AppCompatActivity activity, View transitionImage, Recipe obj) {
        Intent intent = new Intent(activity, ActivityRecipeDetails.class);
        intent.putExtra(EXTRA_OBJCT, obj);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_OBJCT);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    private Recipe recipe;
    private FloatingActionButton fab,book;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        parent_view = findViewById(android.R.id.content);

        // animation transition
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_OBJCT);

        recipe = (Recipe) getIntent().getSerializableExtra(EXTRA_OBJCT);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        book = (FloatingActionButton) findViewById(R.id.book);


        fabToggle();

        setupToolbar(recipe.getName());

        ((ImageView) findViewById(R.id.image)).setImageResource(recipe.getPhoto());

        LinearLayout ingredients = (LinearLayout) findViewById(R.id.ingredients);
        TextView instructions = (TextView) findViewById(R.id.instructions);

        String[] title_ingredients = getResources().getStringArray(R.array.ingredients);
        addIngredientsList(ingredients, title_ingredients);
        addIngredientsList(ingredients, title_ingredients);

//        instructions.setText(Html.fromHtml(getString(R.string.instruction)));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isIdExist(getApplicationContext(), recipe.getId() + "")) {
                    Utils.delFavoriteId(getApplicationContext(), recipe.getId() + "");
                    Snackbar.make(parent_view, recipe.getName() + " remove from favorites", Snackbar.LENGTH_SHORT).show();
                } else {
                    Utils.addFavoriteId(getApplicationContext(), recipe.getId() + "");
                    Snackbar.make(parent_view, recipe.getName() + " added to favorites", Snackbar.LENGTH_SHORT).show();
                }
                fabToggle();
            }
        });




//        Button btnBook = (Button) findViewById(R.id.book);
           book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: navigating to register screen");
                Intent i = new Intent(ActivityRecipeDetails.this,book.class);
                startActivity(i);


            }
        });




    }

    private void setupToolbar(String name) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        ((TextView) findViewById(R.id.toolbar_title)).setText(name);
    }

    private void fabToggle() {
        if (Utils.isIdExist(this, recipe.getId() + "")) {
            fab.setImageResource(R.drawable.ic_nav_favorites);
        } else{
            fab.setImageResource(R.drawable.ic_nav_favorites_outline);
        }
    }

    private void addIngredientsList(LinearLayout linearLayout, String[] title){
        for (int i = 0; i < title.length; i++) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setTextColor(getResources().getColor(R.color.material_grey_600));
            checkBox.setText(title[i]);
            ll.addView(checkBox);
            linearLayout.addView(ll);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }else{
            Snackbar.make(parent_view, item.getTitle()+" clicked", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_recipe_details, menu);
        return true;
    }
}


// Add another text field called slots in the activity_recipe_details.xml file
// Override onStart, get reference to this field (Ctrl + O, select onStart)
// GET request to the server, populate the text field with the response