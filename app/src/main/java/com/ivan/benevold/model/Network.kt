
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
    @POST("auth/signin?type=android")
    fun loginAPICallAsync(@Body jsonBody: LoginRequest): Deferred<LoginResponse>

    @Headers( "Content-Type: application/json" )
    @POST("/auth/password/email?type=android")
    fun emailAPICallAsync(@Body jsonBody: EmailRequest): Deferred<PasswordResponse>

    @Headers( "Content-Type: application/json" )
    @PUT("/auth/password/new?type=android")
    fun passwordAPICallAsync(@Body jsonBody: PasswordRequest): Deferred<PasswordResponse>

    @Headers( "Content-Type: application/json" )
    @GET("/message")
    fun messageAPICallAsync(@Header("access-token") token: String): Deferred<MessageResponse>

    @Headers( "Content-Type: application/json" )
    @PUT("/user?type=android")
    fun userUpdateAPICallAsync(@Body jsonBody: UpdateRequest, @Header("access-token") token: String): Deferred<SimpleResponse>

    @Headers( "Content-Type: application/json" )
    @POST("/user?type=android")
    fun userAPICallAsync(@Body jsonBody: ProfileRequest,@Header("access-token") token: String): Deferred<UserResponse>

    @Headers( "Content-Type: application/json" )
    @GET("/categories")
    fun categoriesAPICallAsync(@Header("access-token") token: String): Deferred<CategoriesResponse>

    @Headers( "Content-Type: application/json" )
    @PUT("/annonce?type=android")
    fun annonceAPICallAsync(@Body jsonBody: AnnonceRequest, @Header("access-token") token: String) : Deferred<SimpleResponse>

    @Headers( "Content-Type: application/json" )
    @POST("/annonces")
    fun annonceSAPICallAsync(@Body jsonBody: AnnoncesRequest, @Header("access-token") token: String) : Deferred<AnnoncesResponse>

    @Headers( "Content-Type: application/json" )
    @POST("/annonce/status")
    fun annonceUpdateAPICallAsync(@Body jsonBody: AnnonceUpdateRequest, @Header("access-token") token: String): Deferred<SimpleResponse>

    @Headers( "Content-Type: application/json" )
    @POST("/annonce")
    fun annonceAPICallAsync(@Body jsonBody: AnnonceUpdateRequest, @Header("access-token") token: String) : Deferred<AnnonceResponse>
}

object Network {
    val api = Retrofit.Builder()
            .baseUrl("https://benevold.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(API::class.java)
}

data class MessageResponse(
        @SerializedName("response")
        val response:String?
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
        @SerializedName("response")
        val response:UserResponseBody?,
)
data class UserResponseBody(
        @SerializedName("fullName")
        val nom:String?,
        @SerializedName("adress")
        val adress:String?,
        @SerializedName("city")
        val city:String?,
        @SerializedName("email")
        val email:String?,
        @SerializedName("phoneNumber")
        val phoneNumber:String?,
        @SerializedName("picLink")
        val picLink:String?
) : Serializable


data class CategoriesResponse(
        @SerializedName("response")
        val response:ArrayList<Categorie>?,
) : Serializable

data class Categorie(
        @SerializedName("title")
        val title:String?,
) : Serializable


data class AnnoncesResponse(
        @SerializedName("response")
        val response:ArrayList<Annonces>?
) : Serializable
data class AnnonceResponse(
        @SerializedName("response")
        val response:Annonce?
) : Serializable
data class Annonce(
        @SerializedName("_id")
        val _id:String?,
        @SerializedName("user_id")
        val user_id:String?,
        @SerializedName("title")
        val title:String?,
        @SerializedName("category")
        val category:String?,
        @SerializedName("status")
        val status:String?,
        @SerializedName("description")
        val description:String?,
        @SerializedName("phone")
        val phone:String?,
        @SerializedName("email")
        val email:String?,
        @SerializedName("contact")
        val contact:String?,
        @SerializedName("address")
        val address:String?,
        @SerializedName("date")
        val date:String?,
        @SerializedName("time")
        val time:String?
) : Serializable

data class Annonces(
        @SerializedName("_id")
        val _id:String?,
        @SerializedName("title")
        val title:String?,
        @SerializedName("category")
        val category:String?,
        @SerializedName("status")
        val status:String?,
        @SerializedName("description")
        val description:String?,
        @SerializedName("phone")
        val phone:String?,
        @SerializedName("email")
        val email:String?,
        @SerializedName("contact")
        val contact:String?,
        @SerializedName("address")
        val address:String?,
        @SerializedName("date")
        val date:String?,
        @SerializedName("time")
        val time:String?
) : Serializable

