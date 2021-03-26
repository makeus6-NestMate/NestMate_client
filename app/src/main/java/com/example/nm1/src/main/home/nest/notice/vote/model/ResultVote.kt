package com.example.nm1.src.main.home.nest.notice.vote.model

import com.google.gson.annotations.SerializedName

data class ResultVote(@SerializedName("choiceId") val choiceId: Int,
                      @SerializedName("isFinished") val isFinished: String,
                      @SerializedName("voteTitle") val voteTitle: String,
                      @SerializedName("choice") val choice: ArrayList<Choice>,
                      @SerializedName("unVoteMemberCnt") val unVoteMemberCnt: Int,
                      @SerializedName("isOwner") val isOwner: Boolean
)
