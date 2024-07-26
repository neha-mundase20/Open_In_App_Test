package com.example.openinapptest

import com.google.gson.annotations.SerializedName



data class ApiResponse(
    val status: Boolean,
    @SerializedName("statusCode") val statusCode: Int,
    val message: String,
    @SerializedName("extra_income") val extraIncome: Double,
    @SerializedName("total_links") val totalLinks: Int,
    @SerializedName("total_clicks") val totalClicks: Int,
    @SerializedName("today_clicks") val todayClicks: Int,
    @SerializedName("top_source") val topSource: String,
    @SerializedName("top_location") val topLocation: String,
    val data: Data
)


data class Data(
    @SerializedName("recent_links") val recentLinks: List<Link>,
    @SerializedName("top_links") val topLinks: List<Link>,
    @SerializedName("favourite_links") val favouriteLinks: List<Link>,
    @SerializedName("overall_url_chart") val overallUrlChart: Map<String, Int>
)


data class Link(
    @SerializedName("url_id") val urlId: Int,
    @SerializedName("web_link") val webLink: String,
    @SerializedName("smart_link") val smartLink: String,
    val title: String,
    @SerializedName("total_clicks") val totalClicks: Int,
    @SerializedName("original_image") val originalImage: String,
    val thumbnail: String?,
    @SerializedName("times_ago") val timesAgo: String,
    @SerializedName("created_at") val createdAt: String,
    val app: String,
)

