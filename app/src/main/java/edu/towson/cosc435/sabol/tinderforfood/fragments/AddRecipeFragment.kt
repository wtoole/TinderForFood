package edu.towson.cosc435.sabol.tinderforfood.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import edu.towson.cosc435.sabol.tinderforfood.R
import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeController
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe
import kotlinx.android.synthetic.main.fragment_add_recipe.*
import kotlinx.android.synthetic.main.recipe_view.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddRecipeFragment : Fragment() {

    private val BASE_URL: String = "https://my-json-server.typicode.com/wtoole/recipe/recipes"

    var recipeList: List<Recipe> = emptyList()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }

    private lateinit var recipeController: IRecipeController

    override fun onAttach(context: Context) {
        super.onAttach(context)

        when(context) {
            is IRecipeController -> recipeController = context
            else -> throw Exception("IRecipeController expected")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addRecipeBtn.setOnClickListener { handleAddRecipeClick() }
        backBtn.setOnClickListener { handleBackBtnClick() }
        skipBtn.setOnClickListener { handleSkipBtnClick() }
        foodCategory()
        fetchJson()
        makeInvisible()
        recipeIsAwesomeCb.visibility = View.INVISIBLE
        addRecipeBtn.visibility = View.INVISIBLE
        food_cat.visibility = View.INVISIBLE
        skipBtn.text = "START"

        val recipe = recipeController.getRecipeForEdit()
        populateRecipeForm(recipe)
    }

    private fun populateRecipeForm(recipe: Recipe?) {
        if(recipe != null) {
            clearForm()
            recipeNameEt.editableText.insert(0, recipe.name)
            recipeArtistEt.editableText.insert(0, recipe.artist)
            recipeTrackEt.editableText.insert(0, recipe.trackNum.toString())
            recipeIsAwesomeCb.isChecked = recipe.isAwesome
            //addRecipeTitle?.text = "Edit Recipe"
            addRecipeBtn.text = "Edit Recipe"
        } else {
            //addRecipeTitle?.text = resources.getString(R.string.add_recipe_btn_txt)
            addRecipeBtn.text = resources.getString(R.string.add_recipe_btn_txt)
        }
    }

    private fun clearForm() {
        recipeNameEt.editableText.clear()
        recipeArtistEt.editableText.clear()
        recipeTrackEt.editableText.clear()
        recipeIsAwesomeCb.isChecked = false
    }

    private fun handleBackBtnClick() {
        val trackNum = try {
            recipeTrackEt.editableText.toString().toInt()
        } catch (e: Exception) {
            0
        }

        val recipe = Recipe(
            id = getRecipeId(),
            name = recipeNameEt.editableText.toString(),
            artist = recipeArtistEt.editableText.toString(),
            isAwesome = recipeIsAwesomeCb.isChecked,
            trackNum = trackNum
        )

        if(recipeController.getRecipeForEdit() == null) {
            recipeController.addNewRecipe(recipe)
        } else {
            recipeController.handleEditedRecipe(recipe)
            addRecipeBtn.text = resources.getString(R.string.add_recipe_btn_txt)
        }

        recipeController.deleteRecipe(recipeController.recipes.getCount() - 1)

        clearForm()
    }

    private fun handleSkipBtnClick() {
        foodCategory()
        addRecipeBtn.visibility = View.VISIBLE
        food_cat.visibility = View.VISIBLE
        skipBtn.text = "SKIP"
        getRandomRecipeByFoodCategory(food_cat.text.toString())
    }


    private fun handleAddRecipeClick() {
        val trackNum = try {
            recipeTrackEt.editableText.toString().toInt()
        } catch (e: Exception) {
            0
        }

        val recipe = Recipe(
            id = getRecipeId(),
            name = recipeNameEt.editableText.toString(),
            artist = recipeArtistEt.editableText.toString(),
            isAwesome = recipeIsAwesomeCb.isChecked,
            trackNum = trackNum
        )

        if(recipeController.getRecipeForEdit() == null) {
            recipeController.addNewRecipe(recipe)
        } else {
            recipeController.handleEditedRecipe(recipe)
            addRecipeBtn.text = resources.getString(R.string.add_recipe_btn_txt)
        }

        clearForm()
    }

    private fun foodCategory(){
        Log.i("foot cat", "FOR THE LOG")
        val random = Random().nextInt(10) + 1
        if(random == 1 )
            food_cat.text = "Pizza"
        if(random == 2 )
            food_cat.text = "Burger"
        if(random == 3 )
            food_cat.text = "Pasta"
        if(random == 4 )
            food_cat.text = "Salad"
        if(random == 5 )
            food_cat.text = "Sandwhich"
        if(random == 6 )
            food_cat.text = "Chicken"
        if(random == 7 )
            food_cat.text = "Seafood"
        if(random == 8 )
            food_cat.text = "vegan"
        if(random == 9 )
            food_cat.text = "Dessert"
        if(random == 10 )
            food_cat.text = "Soup"
    }

    fun populateRecipe() {
        val recipe = recipeController.getRecipeForEdit()
        populateRecipeForm(recipe)
    }

    private fun getRecipeId(): UUID {
        val id: UUID
        val editRecipe = recipeController.getRecipeForEdit()
        if(editRecipe == null){
            id = UUID.randomUUID()
        }else{
            id = editRecipe.id
        }
        return id
    }

    private fun parseJson(json: String?): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        if(json == null) return recipes
        val jsonArr = JSONArray(json)
        var i = 0
        while(i < jsonArr.length()) {
            val jsonObj = jsonArr.getJSONObject(i)
            val recipe = Recipe(
                id = UUID.fromString(jsonObj.getString("id")),
                artist = jsonObj.getString("artist"),
                name = jsonObj.getString("name"),
                trackNum = jsonObj.getInt("track_num"),
                isAwesome = jsonObj.getBoolean("is_awesome")
            )
            recipes.add(recipe)
            i++
        }

        return recipes
    }

    private fun fetchJson() {

        val request = Request.Builder().url(BASE_URL).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed")
            }

            override fun onResponse(call: Call, response: Response) {
                recipeList = parseJson(response.body?.string())
            }
        })
    }

    private fun getRandomRecipeByFoodCategory(food: String){
        var random = Random().nextInt(30)
        while(!recipeList.get(random).name.equals(food)) {
            random = Random().nextInt(30)
            println(random)
            recipeNameEt.setText(recipeList.get(random).name)
            recipeArtistEt.setText(recipeList.get(random).artist)
        }

    }

    private fun makeInvisible() {
        nameLabel.visibility = View.INVISIBLE
        recipeNameEt.visibility = View.INVISIBLE
        artistLabel.visibility = View.INVISIBLE
        recipeArtistEt.visibility = View.INVISIBLE
        trackLabel.visibility = View.INVISIBLE
        recipeTrackEt.visibility = View.INVISIBLE
    }



}
