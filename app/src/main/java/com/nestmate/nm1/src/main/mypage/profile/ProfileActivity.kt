package com.nestmate.nm1.src.main.mypage.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nestmate.nm1.R
import com.nestmate.nm1.config.BaseActivity
import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.databinding.ActivityProfileBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate), ProfileView {
    private var profileImg: String? = null
    private var nickname: String? = null
    private lateinit var uriPhoto: Uri
    private var uploadFile: MultipartBody.Part? = null
    private var map = HashMap<String, RequestBody>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.profileToolbar.toolbarTitle.text = "프로필 수정"

        profileImg = intent.getStringExtra("profileImg")
        nickname = intent.getStringExtra("nickname")

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        binding.profileNickname.setText(nickname)
        Glide.with(this).load(profileImg).error(R.drawable.chicken_img).into(binding.profileImg)

        binding.profileImg.setOnClickListener {
            var photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, 0)
        }

        binding.profileConfirmBtn.setOnClickListener {
            var name = RequestBody.create("text/plain".toMediaTypeOrNull(), binding.profileNickname.text.toString())
            map["nickname"] = name

            ProfileService(this).tryPutProfile(map = map, img = uploadFile)
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

                    binding.profileImg.setImageURI(uriPhoto)
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

    override fun onPutProfileSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                finish()
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPutProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}