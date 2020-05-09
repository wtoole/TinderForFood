package edu.towson.cosc435.sabol.tinderforfood.database

import androidx.room.*
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe
import java.util.*

@Dao
interface RecipeDao {
    @Insert
    fun addRecipe(recipe: Recipe)

    @Update
    fun updateRecipe(recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Query("select id, name, artist, track_num, is_awesome from Recipe")
    fun getAllRecipes(): List<Recipe>
}

class UUIDConverter{
    @TypeConverter
    fun fromString(uuid: String): UUID {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun toString(uuid: UUID): String {
        return uuid.toString()
    }
}

@Database(entities = arrayOf(Recipe::class), version = 1, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}