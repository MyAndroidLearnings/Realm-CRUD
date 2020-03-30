package com.sysaxiom.realmcrud

//region Import Statements
import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.delete
import io.realm.kotlin.where
import java.util.*


//endregion

//region Realm Class
open class ProfileRealm(

    @field:PrimaryKey
    var user_id: Int = 0,
    var full_name : String = "",
    var email : String = "",
    var date_of_birth : Date? = null,
    var profile_photo : ByteArray? = null,
    var basic_profile_done : Boolean = false,
    var efficiency : Double = 0.0,
    var efficiency_overall : Float? = null


) : RealmObject()
//endregion

//region Realm Handler
class RealmHandler{

    companion object {

        //region Read
        fun getallDataforOneObject(context:Context) : RealmResults<ProfileRealm> {

            val realm = Realm.getDefaultInstance()
            val profile = realm.where<ProfileRealm>()
            val profileValues = profile.findAll()
            return profileValues
        }

        fun getDatafromOneObjectbasedOnCondtion(context:Context,userid:Int) : ProfileRealm? {

            val realm = Realm.getDefaultInstance()
            val profile = realm.where<ProfileRealm>()
            profile.equalTo("user_id",userid)

            //lesser than
            //profile.lessThan("efficiency",90.0)

            //greater than
            //profile.greaterThan("efficiency",90.0)

            val profileValues = profile.findFirst()
            return profileValues

        }

        fun getDatabyLimit(context:Context,numberOfRows:Long) : RealmResults<ProfileRealm> {
            val realm = Realm.getDefaultInstance()
            val profile = realm.where<ProfileRealm>()
            val profileValues = profile.limit(numberOfRows).findAll()
            return profileValues
        }
        //endregion

        //region Writes
        fun insertData(context:Context, full_name:String, email:String, date_of_birth:Date, profile_photo:ByteArray, basic_profile_done:Boolean, efficiency: Double, efficiency_overall:Float) {

            val realm = Realm.getDefaultInstance()

            realm.executeTransactionAsync  { realm ->

                val maxId: Number? = realm.where(ProfileRealm::class.java).max("user_id")
                val nextId = if (maxId == null) 1 else maxId.toInt() + 1

                val profile = realm.createObject(ProfileRealm::class.java,nextId)

                profile.full_name = full_name
                profile.email = email

                profile.date_of_birth = date_of_birth
                profile.profile_photo = profile_photo

                profile.basic_profile_done = basic_profile_done

                profile.efficiency = efficiency
                profile.efficiency_overall = efficiency_overall
                realm.insert(profile)
            }

            //insertData(context,profile)

        }
        //endregion

        //region Updates
        fun updateData(context:Context,user_id:Int,efficiency:Double) : ProfileRealm? {

            val realm = Realm.getDefaultInstance()

            val profile = realm.where<ProfileRealm>().equalTo("user_id",user_id)
            val specificProfile = profile.findFirst()

            if (specificProfile != null) {
                realm.executeTransaction { _ ->
                    specificProfile.efficiency = efficiency
                }
            }

            return specificProfile

        }
        //endregion

        //region Deletes
        fun deleteAll(context: Context) {

            val realm = Realm.getDefaultInstance()

            realm.executeTransaction {realm ->
                realm.deleteAll()
            }
        }
        fun deleteSpecificObject(context:Context){

            val realm = Realm.getDefaultInstance()

            realm.executeTransaction {realm ->
                realm.delete<ProfileRealm>()
            }
        }
        fun deleteSpecificObjectonCondition(context:Context,user_id: Int){

            val realm = Realm.getDefaultInstance()

            realm.executeTransaction {realm ->
                val profile = realm.where<ProfileRealm>().equalTo("user_id",user_id).findAll()
                profile.deleteAllFromRealm()
            }
        }
        fun deleteSpecifiObjectbasedonLimit(context:Context,numberOfRows:Long) {

            val realm = Realm.getDefaultInstance()
            realm.executeTransaction {realm ->
                val profile = realm.where<ProfileRealm>().limit(numberOfRows).findAll()
                profile.deleteAllFromRealm()
            }

        }
        //endregion

    }


}
//endregion

//region Extending Application for Realm Config
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
//endregion