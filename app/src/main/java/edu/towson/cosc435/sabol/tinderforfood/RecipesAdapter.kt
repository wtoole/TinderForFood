package edu.towson.cosc435.sabol.tinderforfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeController
import edu.towson.cosc435.sabol.tinderforfood.models.Recipe
import kotlinx.android.synthetic.main.recipe_view.view.*

class RecipesAdapter(private val controller: IRecipeController) : RecyclerView.Adapter<RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent, false)
        val viewHolder = RecipeViewHolder(view)

        view.deleteButton.setOnClickListener {
            val position = viewHolder.adapterPosition
            controller.deleteRecipe(position)
            this.notifyItemRemoved(position)
        }
        view.isAwesomeCb.setOnClickListener {
            val position = viewHolder.adapterPosition
            controller.toggleAwesome(position)
            this.notifyItemChanged(position)
        }
        /*
        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            controller.editRecipe(position)
        }*/

        return viewHolder
    }

    override fun getItemCount(): Int {
        return controller.recipes.getCount()
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = controller.recipes.getRecipe(position)
        holder.bindRecipe(recipe)
    }
}

class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindRecipe(recipe: Recipe) {
        itemView.recipeName.text = recipe.name
        itemView.recipeArtist.text = recipe.artist
        itemView.recipeTrackNum.text = recipe.trackNum.toString()
        itemView.isAwesomeCb.isChecked = recipe.isAwesome
    }
}