package com.example.composetest

import com.example.composetest.screens.business_sector_list.data.BusinessSectorRepository
import com.example.composetest.screens.business_sector_list.ui.BusinessSectorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class BusinessSectorViewModelTest {

    private val repository = mock<BusinessSectorRepository>()
    private lateinit var viewModel: BusinessSectorViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = BusinessSectorViewModel(repository)
        viewModel.setName("businessSector Test")
    }

    @Test
    fun insert_test() = runTest {
        Assert.assertEquals(0, viewModel.businessSectorListSize.value)
        Assert.assertEquals("businessSector Test", viewModel.name.value)

        //Assert.assertEquals(0, viewModel.businessSector().id)
        //Assert.assertEquals("businessSector Test", viewModel.businessSector().name)
        //Assert.assertEquals("#F9C80E", viewModel.businessSector().color)
//
        launch {
            viewModel.createBusinessSector()
        }
        advanceUntilIdle()

        Assert.assertEquals(1, viewModel.businessSectorListSize.value)
//

//

//
//
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}