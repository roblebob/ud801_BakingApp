# Baking App

The data involved is splitted into 3 destinct entities:

- Recipe
- RecipeIngredient
- recipeStep

In short:

for every __Recipe__, there exist multiple __RecipeIngredient__ _s_ and __RecipeStep__ _s_, each.
Therefore those associated all include the ID of the __Recipe__ they correspond to.  


widget vector image resource used:
[https://pixabay.com/vectors/cake-dessert-food-chocolate-sweet-5306411/](https://pixabay.com/vectors/cake-dessert-food-chocolate-sweet-5306411/)


known issues:
+ sometimes when the app widget is initialized, it does not show any data. Simply initializing it again solves the problem. 

