package edu.towson.cosc435.sabol.tinderforfood

import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeRepository
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe
import java.util.*

class RecipeRepository : IRecipeRepository {
    private var recipes: MutableList<Recipe> = mutableListOf()

    init {
        val seed = (1..10).map { idx -> Recipe(UUID.randomUUID(), "Recipe${idx}", "Artist${idx}", idx, idx % 2 == 0) }
        recipes.addAll(seed)
    }

    override fun addRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }

    override fun getCount(): Int {
        return recipes.size
    }

    override fun getRecipe(idx: Int): Recipe {
        return recipes.get(idx)
    }

    override fun getAll(): List<Recipe> {
        return recipes
    }

    override fun remove(recipe: Recipe) {
        recipes.remove(recipe)
    }

    override fun replace(idx: Int, recipe: Recipe) {
        if(idx >= recipes.size) throw Exception("Outside of bounds")
        recipes[idx] = recipe
    }
}