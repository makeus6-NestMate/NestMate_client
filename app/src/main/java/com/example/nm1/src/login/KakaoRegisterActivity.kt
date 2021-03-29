package com.example.nm1.src.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityKakaoRegisterBinding
import com.example.nm1.src.login.model.KakaoLoginResponse
import com.example.nm1.src.login.model.KakaoRegisterResponse
import com.example.nm1.src.login.model.PostKakaoRegisterRequest
import com.example.nm1.util.onMyTextChanged
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class KakaoRegisterActivity : BaseActivity<ActivityKakaoRegisterBinding>(ActivityKakaoRegisterBinding::inflate),
    LoginActivityView {
    private var profileImg: String? = null
    private var nickname: String? = null
    var storage: FirebaseStorage? = null
    private var firebaseuri :Uri? = null
    private val Gallery = 1
    private lateinit var uriPhoto: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileImg = intent.getStringExtra("profileImg")
        storage = FirebaseStorage.getInstance()

        Glide.with(this)
            .load(intent.getStringExtra("profileImg"))
            .error(R.drawable.chicken_img)
            .into(binding.kakaoregiProfile)

        binding.toolbarSetprofile.toolbarTitle.text = "프로필 설정"

        binding.kakaoregiEdtNickname.onMyTextChanged {
            if(binding.kakaoregiEdtNickname.text.length==2){
                binding.kakaoregiBtnConfirm.isEnabled = true
                binding.kakaoregiBtnConfirm.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            }else{
                binding.kakaoregiBtnConfirm.isEnabled = false
                binding.kakaoregiBtnConfirm.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
            }
        }

        binding.kakaoregiProfile.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)

            startActivityForResult(Intent.createChooser(intent, "Load Picture"), Gallery)
        }

        binding.kakaoregiBtnConfirm.setOnClickListener {
            val postKakaoRegisterRequest = PostKakaoRegisterRequest(binding.kakaoregiEdtNickname.text.toString(), profileImg,
                intent.getStringExtra("email")!!,
                intent.getStringExtra("access_token")!!
            )
            showLoadingDialog(this)
            LoginService(this).tryPostKakaoRegister(postKakaoRegisterRequest)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Gallery) {
            if (resultCode == RESULT_OK) {
                uriPhoto = data?.data!!

                Glide.with(this)
                    .load(uriPhoto)
                    .error(R.drawable.chicken_img)
                    .into(binding.kakaoregiProfile)
            }
        }
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