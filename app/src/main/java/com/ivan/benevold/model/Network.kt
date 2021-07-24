
import com.google.gson.annotations.SerializedName
import com.ivan.benevold.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.Serializable

// Endpoint GET
// Base URL = https://api.formation-android.fr/getProduct


interface API {
    ///  POST auth/signup?type=android


    @Headers( "Content-Type: application/json" )
    @POST("auth/signup?type=android")
    fun singupAPICallAsync(@Body jsonBody: SingupRequest): Deferred<LoginResponse>

    @Headers( "Content-Type: application/json" )
    @POST("auth/signup?type=android")
    fun loginAPICallAsync(@Body jsonBody: LoginRequest): Deferred<LoginResponse>


    @Headers( "Content-Type: application/json" )
    @POST("/auth/password/email?type=android")
    fun emailAPICallAsync(@Body jsonBody: EmailRequest): Deferred<PasswordResponse>

    @Headers( "Content-Type: application/json" )
    @PUT("/auth/password/new")
    fun passwordAPICallAsync(@Body jsonBody: PasswordRequest): Deferred<PasswordResponse>

    @Headers( "Content-Type: application/json" )
    @GET("/message")
    fun messageAPICallAsync(): Deferred<MessageResponse>

    @Headers( "Content-Type: application/json" )
    @POST("android/user")
    fun userUpdateAPICallAsync(@Body jsonBody: UpdateRequest): Deferred<SimpleResponse>

    @Headers( "Content-Type: application/json" )
    @PUT("android/user")
    fun userAPICallAsync(@Body jsonBody: LoginRequest): Deferred<UserResponse>

    @Headers( "Content-Type: application/json" )
    @GET("android/categories")
    fun categoriesAPICallAsync(): Deferred<CategoriesResponse>

    @Headers( "Content-Type: application/json" )
    @POST("android/annonce")
    fun annonceAPICallAsync(@Body jsonBody: AnnonceRequest): Deferred<SimpleResponse>

}

object Network {
    val api = Retrofit.Builder()
            .baseUrl("https://benevold.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(API::class.java)
}

class PasswordRequest internal constructor(val user_id: String, val new_password: String)
data class MessageResponse(
        @SerializedName("message")
        val message:String?
) : Serializable
data class PasswordResponse(
        @SerializedName("user_id")
        val user_id:String?,
        @SerializedName("requestCode")
        val requestCode:String?
) : Serializable
data class LoginResponse(
        @SerializedName("user_id")
        val user_id:String?,
        @SerializedName("token")
        val token:String?,
        @SerializedName("requestCode")
        val requestCode:String?
) : Serializable

data class SimpleResponse(
        @SerializedName("requestCode")
        val requestCode:String?
) : Serializable
data class UserResponse(
        @SerializedName("nom")
        val nom:String?,
        @SerializedName("address")
        val address:String?,
        @SerializedName("city")
        val city:String?,
        @SerializedName("email")
        val email:String?,
        @SerializedName("number")
        val number:String?,
        @SerializedName("picLink")
        val picLink:String?
) : Serializable
data class CategoriesResponse(
        @SerializedName("categories")
        val categories:ArrayList<Categorie>?,
) : Serializable
data class Categorie(
        @SerializedName("nom_categorie")
        val nom_categorie:String?,
) : Serializable

