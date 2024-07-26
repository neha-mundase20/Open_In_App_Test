package com.example.openinapptest

import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {
    @GET("api/v1/dashboardNew")
    suspend fun getDashboardData(): ApiResponse
}