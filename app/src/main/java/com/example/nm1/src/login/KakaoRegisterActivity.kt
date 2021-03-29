package com.example.nm1.src.login

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
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityKakaoRegisterBinding
import com.example.nm1.src.login.model.KakaoLoginResponse
import com.example.nm1.src.login.model.KakaoRegisterResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class KakaoRegisterActivity : BaseActivity<ActivityKakaoRegisterBinding>(ActivityKakaoRegisterBinding::inflate),
    LoginActivityView {
    private var kakaoImg: String? = null
    private var email: String? = null
    private lateinit var uriPhoto: Uri
    private var uploadFile: MultipartBody.Part? = null
    private var map = HashMap<String, RequestBody>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        binding.toolbarSetprofile.toolbarTitle.text = "프로필 설정"

        kakaoImg = intent.getStringExtra("kakaoImg")
        email = intent.getStringExtra("email")

        Log.d("kakaoImg", kakaoImg!!)
        Log.d("kakaoImg", email!!)
        Log.d("kakaoImg", intent.getStringExtra("access_token")!!)

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
            var name = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.kakaoregiEdtNickname.text.toString())
            var kakaoImg = RequestBody.create("text/plain".toMediaTypeOrNull(),
                intent.getStringExtra("kakaoImg")!!
            )
            var email = RequestBody.create("text/plain".toMediaTypeOrNull(),
                intent.getStringExtra("email")!!
            )
            var access_token = RequestBody.create("text/plain".toMediaTypeOrNull(),
                intent.getStringExtra("access_token")!!)
            map["nickname"] = name
            map["email"] = email
            map["access_token"] = access_token

            if (uploadFile==null){
                map["kakaoImg"] = kakaoImg
            }

            LoginService(this).tryPostKakaoRegister(map = map, profileImg = uploadFile)
            showLoadingDialog(this)
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
                    uploadFile = MultipartBody.Part.createFormData("img", file.name, requestFile)

                    binding.kakaoregiProfile.setImageURI(uriPhoto)
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

    override fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse) {
        dismissLoadingDialog()
        this.finish()

        ApplicationClass.sSharedPreferences.edit().putBoolean("iskakaoregisterd", true) //카카오 회원가입 성공
        ApplicationClass.sSharedPreferences.edit().apply()
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoLoginFailure(message: String) {
        TODO("Not yet implemented")
    }
}