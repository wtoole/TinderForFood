package edu.towson.cosc435.sabol.tinderforfood.interfaces

import edu.towson.cosc435.sabol.tinderforfood.models.Recipe


interface IRecipeRepository {
    fun getCount(): Int
    fun getRecipe(idx: Int): Recipe
    fun getAll(): List<Recipe>
    fun remove(recipe: Recipe)
    fun replace(idx: Int, recipe: Recipe)
    fun addRecipe(recipe: Recipe)
}