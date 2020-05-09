package edu.towson.cosc435.sabol.tinderforfood.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.towson.cosc435.sabol.tinderforfood.R
import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeController
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe
import kotlinx.android.synthetic.main.fragment_add_recipe.*
import java.lang.Exception
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddRecipeFragment : Fragment() {

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

}
