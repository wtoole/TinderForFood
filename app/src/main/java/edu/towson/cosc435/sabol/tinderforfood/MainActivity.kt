package edu.towson.cosc435.sabol.tinderforfood

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import edu.towson.cosc435.sabol.tinderforfood.R
import edu.towson.cosc435.sabol.tinderforfood.database.RecipeDatabaseRepository
// import edu.towson.cosc435.sabol.tinderforfood.RecipeDatabaseRepository
import edu.towson.cosc435.sabol.tinderforfood.fragments.AddRecipeFragment
import edu.towson.cosc435.sabol.tinderforfood.fragments.RecipeListFragment
import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeController
import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeRepository
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recipe_list.*

class MainActivity : AppCompatActivity(), IRecipeController {

    override fun launchNewRecipeScreen() {
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_recipeListFragment_to_addRecipeFragment)
        }

    }

    override fun addNewRecipe(recipe: Recipe) {
        recipes.addRecipe(recipe)
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            findNavController(R.id.nav_host_fragment)
                .popBackStack()
        } else {
            // update the recyclerview
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun deleteRecipe(idx: Int) {
        val current = recipes.getRecipe(idx)
        recipes.remove(current)
    }

    override fun toggleAwesome(idx: Int) {
        val recipe = recipes.getRecipe(idx)
        val newRecipe = recipe.copy(isAwesome = !recipe.isAwesome)
        recipes.replace(idx, newRecipe)
    }

    /*
    override fun editRecipe(idx: Int) {
        val recipe = recipes.getRecipe(idx)
        editingRecipe = recipe
        editingRecipeIdx = idx

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            findNavController(R.id.nav_host_fragment)
                .navigate(R.id.action_recipeListFragment_to_addRecipeFragment)
        } else {
            // landscape
            (addRecipeFragment as AddRecipeFragment).populateRecipe()
        }
    }
*/
    override fun getRecipeForEdit(): Recipe? {
        return editingRecipe
    }

    override fun clearEditingRecipe() {
        editingRecipe = null
        editingRecipeIdx = -1
    }

    override fun handleEditedRecipe(recipe: Recipe) {
        recipes.replace(editingRecipeIdx, recipe)

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            findNavController(R.id.nav_host_fragment)
                .popBackStack()
        } else {
            recyclerView.adapter?.notifyItemChanged(editingRecipeIdx)
        }

        clearEditingRecipe()
    }

    override lateinit var recipes: IRecipeRepository
    private var editingRecipe: Recipe? = null
    private var editingRecipeIdx: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO - 5. replace with your Database repository
        recipes = RecipeDatabaseRepository(this)
    }
}
