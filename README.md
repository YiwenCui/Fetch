# Setup

1. checkout this repo to local machine
2. open the project with android studio
3. build and run the app on emulator

# Requirement
* Android SDK: 33
* Android Studio version: 2024.1.1

# Note
The original requirement is sorted by name field, which is a string. In this case there could be a corner case during string sorting, where "Item 280" is displayed before "Item 29".

Therefore I also tried modify the logic to sort by item id field:

``` kotlin
val items = response.body()!!
   .filter { it.name != null && it.name.isNotBlank() }
   .sortedWith(compareBy({ it.listId }, { it.id }))
```

Please see demos for both versions.

# Demo

## App demo


### Sort by name
https://github.com/YiwenCui/Fetch/assets/26207491/bd68bee2-2dbe-4dde-afb5-f2755119b70b

### Sort by ID
https://github.com/YiwenCui/Fetch/assets/26207491/41da7ab0-c4b7-4800-aad4-eac6b590bb8e




## Full screen demo Youtube (with IDE and code)

* [sort by name](https://youtu.be/xbfvkMeWqYM)

* [sort by item id](https://www.youtube.com/watch?v=9huKTGzlXm4)

> Note: This is also available in demo folder in this repo



# Reference
* [Exercise Requirement](https://fetch-hiring.s3.amazonaws.com/mobile.html)


   
