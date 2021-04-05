package com.nestmate.nm1.src.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseActivity
import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.databinding.ActivityKakaoRegisterBinding
import com.nestmate.nm1.src.login.model.KakaoLoginResponse
import com.nestmate.nm1.src.login.model.PostKakaoLoginRequest
import com.nestmate.nm1.src.main.MainActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class KakaoRegisterActivity : BaseActivity<ActivityKakaoRegisterBinding>(ActivityKakaoRegisterBinding::inflate),
    LoginActivityView {
    private var kakaoImg: String? = null
    private var email: String? = null
    private lateinit var uriPhoto: Uri
    private var uploadFile: MultipartBody.Part? = null
    val editor = ApplicationClass.sSharedPreferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        binding.toolbarSetprofile.toolbarTitle.text = "프로필 설정"

        kakaoImg = intent.getStringExtra("kakaoImg")
        email = intent.getStringExtra("email")

        Glide.with(this).load(kakaoImg).error(R.drawable.chicken_img).into(binding.kakaoregiProfile)

        binding.kakaoregiEdtNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.kakaoregiEdtNickname.text.length==2){
                    binding.kakaoregiBtnConfirm.isEnabled = true //버튼 활성화
                    binding.kakaoregiBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
                } else {
                    binding.kakaoregiBtnConfirm.isEnabled = false
                    binding.kakaoregiBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_grey_bg)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.kakaoregiProfile.setOnClickListener {
            var photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, 0)
        }

        binding.kakaoregiBtnConfirm.setOnClickListener {
            showLoadingDialog(this)
            var nickname = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.kakaoregiEdtNickname.text.toString())
            var email = RequestBody.create("text/plain".toMediaTypeOrNull(),
                email!!
            )
            var access_token = RequestBody.create("text/plain".toMediaTypeOrNull(), intent.getStringExtra("access_token")!!)
            if (uploadFile!=null){ //갤러리로 고른 사진이 있다면
                LoginService(this).tryPostKakaoRegister(
                    nickname = nickname, profileImg = uploadFile, email = email,
                    access_token = access_token, kakaoImg = null
                )
            } else if (uploadFile==null && kakaoImg!=null){ //갤러리x, 카톡프사 o-> 카톡 프사 넘기기
                var kakaoProfile = RequestBody.create("text/plain".toMediaTypeOrNull(), kakaoImg!!)
                LoginService(this).tryPostKakaoRegister(
                    nickname = nickname, profileImg = null, email = email,
                    access_token = access_token, kakaoImg = kakaoProfile
                )
            } else if (uploadFile==null && kakaoImg==null){ //갤러리에서 고르기 x, 카톡프사 x
                LoginService(this).tryPostKakaoRegister(
                    nickname = nickname, profileImg = null, email = email,
                    access_token = access_token, kakaoImg = null
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                uriPhoto = data?.data!!

                if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED){
                    Log.d("확인", "사진 받아옴")


                    var file = File(getRealPathFromURI(uriPhoto))
                    var requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    uploadFile = MultipartBody.Part.createFormData("profileImg", file.name, requestFile)

                    Glide.with(this).load(uriPhoto).error(R.drawable.chicken_img).into(binding.kakaoregiProfile)
                }
            }
        }
    }

    fun getRealPathFromURI(uri: Uri): String{
        var path = ""
        var cursor = this.contentResolver.query(uri, null, null, null, null)
        if(cursor == null){
            path = uri.path!!
        }else{
            cursor.moveToFirst()
            var idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            path = cursor.getString(idx)
            cursor.close()
        }
        return path
    }

    override fun onPostKakaoRegisterSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        showLoadingDialog(this)
        val postKakaoLoginRequest = PostKakaoLoginRequest(intent.getStringExtra("email")!!, intent.getStringExtra("access_token")!!)
        LoginService(this).tryPostKakaoLogin(postKakaoLoginRequest = postKakaoLoginRequest)
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        dismissLoadingDialog()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.result.token)
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.finish()
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}