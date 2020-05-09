package edu.towson.cosc435.sabol.tinderforfood.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import edu.towson.cosc435.sabol.tinderforfood.R
import edu.towson.cosc435.sabol.tinderforfood.RecipesAdapter
import edu.towson.cosc435.sabol.tinderforfood.interfaces.IRecipeController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class RecipeListFragment : Fragment() {

    private lateinit var recipeController: IRecipeController

    override fun onAttach(context: Context) {
        super.onAttach(context)

        when(context) {
            is IRecipeController -> recipeController = context
            else -> throw Exception("IRecipeController expected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecipesAdapter(recipeController)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(context)

        add_recipe_btn?.setOnClickListener { recipeController.launchNewRecipeScreen() }
    }

}
