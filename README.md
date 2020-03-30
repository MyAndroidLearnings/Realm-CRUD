# Realm CRUD Kotlin

### Introduction:

This project is created in the intention to understand the CRUD operation of realm and make it as
readily usable component to integrate it with any projects


----------------------------------------------------------------------------------------------------

### Installation:

Step 1: Add the class path dependency to build.gradle

Inside dependencies:

``` 
classpath "io.realm:realm-gradle-plugin:6.0.1"
```



Step 2: Apply the realm-android plugin to the top of the application level build.gradle file.

    apply plugin: 'kotlin-kapt'
    apply plugin: 'realm-android'


----------------------------------------------------------------------------------------------------

### Configuration:

To setup the initial context for realm which we can use throughout the application we are extending
the application

```
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}
```


Include the extended application inside <application> tag in Manifest file

```
    android:name=".MyApplication">
```



----------------------------------------------------------------------------------------------------

### Handler Part:

#### Realm Class - 
Given an example realm class to understand the different data types that Realm is
offering for Android Kotlin. User id is given as primary key and it is auto incremented for this
example class


#### Handler Part -

A static or companion object is implemented to invoke without object instantiation.

##### (i) **Read** -

*getallDataforOneObject* - to get all data for single realm object.

*getDatafromOneObjectbasedOnCondtion* - to get data for realm object based on condition.

*getDatabyLimit* - to get data based on limit (number of rows)


##### (ii) **Writes**

*insertData* - a function to insert one record at a time

##### (iii) **Updates**

*updateData* - to update a record's efficiency (or any one or many fields) based on User Id

##### (iv) **Deletes**

*deleteAll* - to delete all realm objects

*deleteSpecificObject* - to delete all record of specific object

*deleteSpecificObjectonCondition* - to delete specific record based on condition

*deleteSpecifiObjectbasedonLimit* - to delete number of records (limit) of an object

----------------------------------------------------------------------------------------------------

### Usage / Example:

All function invoking part is given as regions where you can try/implement by invoking each function
and seeing it in Console.

----------------------------------------------------------------------------------------------------

###OBSERVATIONS
1. User Id Should be auto increment - done
2. Lets try to achieve Single File Concept (Like Class in Single File) - done
3. Possibility to Read Array like this
    This should be dynamic like
        This is too complicated, we can do in future - getRecord('sorttype','records');
        This is okay i feel, getRecord(100); -> It will bring old 100 (records) - done
4. Possibility to Delete array of data like (Delete 1 to 60 Records) or Delete [1,2,3,9,10] - done