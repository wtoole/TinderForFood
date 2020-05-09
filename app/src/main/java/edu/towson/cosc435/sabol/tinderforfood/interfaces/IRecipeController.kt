package edu.towson.cosc435.sabol.tinderforfood.interfaces

import edu.towson.cosc435.sabol.tinderforfood.models.Recipe


interface IRecipeController {
    fun deleteRecipe(idx: Int)
    fun toggleAwesome(idx: Int)
    fun launchNewRecipeScreen()
    val recipes: IRecipeRepository
    fun addNewRecipe(recipe: Recipe)
   // fun editRecipe(idx: Int)
    fun getRecipeForEdit(): Recipe?
    fun handleEditedRecipe(recipe: Recipe)
    fun clearEditingRecipe()
}