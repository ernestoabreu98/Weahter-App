package com.example.weatherapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.domain.entities.WeatherReport
import com.example.domain.useCases.GetWeatherReportUseCase
import com.example.domain.utils.Result
import com.example.weatherapp.presentation.utils.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel

    @Mock
    lateinit var getWeatherReportUseCase: GetWeatherReportUseCase

    @Mock
    lateinit var weatherSuccessResult: Result.Success<List<WeatherReport>>

    @Mock
    lateinit var weatherInvalidResult: Result.Failure
    private var weatherReport: List<WeatherReport> =
        listOf(WeatherReport(123, 00.0, 00.0, 00.0, 0, "", "", ""))

    @Mock
    lateinit var exception: Exception

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.openMocks(this)
        viewModel = WeatherViewModel(getWeatherReportUseCase)
    }

    @After
    fun after() {
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun getWeatherReportSuccess() {
        runBlocking {
            val liveDataUnderTest = viewModel.oneCallResponse.testObserver()
            whenever(getWeatherReportUseCase.invoke()).thenReturn(weatherSuccessResult)
            whenever(weatherSuccessResult.data).thenReturn(weatherReport)

            viewModel.getWeatherReport().join()
            liveDataUnderTest.observedValues.run {
                assertEquals(Status.LOADING, first()?.peekContent()?.responseType)
                assertNotNull(last()?.peekContent())
                last()?.peekContent()?.run {
                    assertEquals(Status.SUCCESSFUL, responseType)
                    assertEquals(weatherReport, data)
                }
            }
        }

    }

    @Test
    fun getWeatherReportFailure() {
        runBlocking {
            val liveDataUnderTest = viewModel.oneCallResponse.testObserver()
            whenever(getWeatherReportUseCase.invoke()).thenReturn(weatherInvalidResult)
            whenever(weatherSuccessResult.data).thenReturn(emptyList())

            viewModel.getWeatherReport().join()
            liveDataUnderTest.observedValues.run {
                assertEquals(Status.LOADING, first()?.peekContent()?.responseType)
                assertEquals(
                    last()?.peekContent()?.data?.isEmpty(),
                    last()?.peekContent()?.data?.isEmpty()
                )
                last()?.peekContent()?.run {
                    assertEquals(Status.ERROR, responseType)
                    assertEquals(null, data)
                }
            }
        }

    }
}