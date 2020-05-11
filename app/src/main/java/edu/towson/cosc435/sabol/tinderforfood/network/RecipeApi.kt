package edu.towson.cosc435.sabol.tinderforfood.network

import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeController
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.util.*
import kotlinx.coroutines.async

class RecipeApi(val controller: IRecipeController) {


    private val BASE_URL: String = "https://my-json-server.typicode.com/wtoole/recipe/recipes"
    private val client: OkHttpClient = OkHttpClient()
/*

    suspend fun fetchRecipes(): Deferred<List<Recipe>> {
        return controller.async(Dispatchers.IO) {
            val request = Request.Builder()
                .url(BASE_URL)
                .get()
                .build()
            val result: String? = client.newCall(request).execute().body?.string()
            val recipes: List<Recipe> = parseJson(result)
            recipes
        }
    }
*/

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
}

