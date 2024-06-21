package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.EndpointShouldNotBeCalledException
import kotlinx.coroutines.delay

class FakeSuccessApi(val delayMillis: Long) : MockApi {
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        delay(delayMillis)
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }

}