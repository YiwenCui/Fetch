1. Filter out any items where "name" is blank or null.
2. Sort the results first by "listId" then by "name" when displaying.

This is listed by name (string), so "Item 280" is displayed before "Item 29". If we want to sort by ItemId, then try :

`val items = response.body()!!
   .filter { it.name != null && it.name.isNotBlank() }
   .sortedWith(compareBy({ it.listId }, { it.id }))`
   
3. Display all the items grouped by "listId" by clicking dropdown menu, select the desired "listId" to filter out


   

