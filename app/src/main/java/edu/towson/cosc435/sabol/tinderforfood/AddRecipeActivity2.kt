package edu.towson.cosc435.sabol.tinderforfood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe
import kotlinx.android.synthetic.main.activity_add_recipe2.*
import java.util.*

class AddRecipeActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe2)

        addRecipeBtn.setOnClickListener { handleAddRecipeClick() }
    }

    private fun handleAddRecipeClick() {
        val intent = Intent()

        val trackNum = try {
            recipeTrackEt.editableText.toString().toInt()
        } catch (e: Exception) {
            0
        }

        val recipe = Recipe(
            id = UUID.randomUUID(),
            name = recipeNameEt.editableText.toString(),
            artist = recipeArtistEt.editableText.toString(),
            isAwesome = recipeIsAwesomeCb.isChecked,
            trackNum = trackNum
        )

        val json: String = Gson().toJson(recipe)

        intent.putExtra(RECIPE_KEY, json)

        setResult(Activity.RESULT_OK, intent)

        finish()
    }

    private fun handleBackBtnClick(){

    }

    companion object {
        val RECIPE_KEY = "RECIPE_EXTRA"
    }
}
