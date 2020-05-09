package edu.towson.cosc435.sabol.tinderforfood.database

import android.content.Context
import androidx.room.Room
import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeRepository
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe


class RecipeDatabaseRepository(ctx: Context) : IRecipeRepository {

    private val recipeList: MutableList<Recipe> = mutableListOf()
    private val db: RecipeDatabase

   init {
       db = Room.databaseBuilder(
           ctx,
           RecipeDatabase::class.java,
           "recipes.db"
       ).allowMainThreadQueries().build()
       refreshRecipeList()
   }

    override fun getCount(): Int {
        return recipeList.size
    }

    override fun getRecipe(idx: Int): Recipe {
        return recipeList.get(idx)
    }

    override fun getAll(): List<Recipe> {
        return recipeList
    }

    override fun remove(recipe: Recipe) {
        db.recipeDao().deleteRecipe(recipe)
        refreshRecipeList()
    }

    override fun replace(idx: Int, recipe: Recipe) {
        db.recipeDao().updateRecipe(recipe)
        refreshRecipeList()
    }

    override fun addRecipe(recipe: Recipe) {
        db.recipeDao().addRecipe(recipe)
        refreshRecipeList()
    }

    private fun refreshRecipeList() {
        recipeList.clear()
        val recipes = db.recipeDao().getAllRecipes()
        recipeList.addAll(recipes)
    }

}