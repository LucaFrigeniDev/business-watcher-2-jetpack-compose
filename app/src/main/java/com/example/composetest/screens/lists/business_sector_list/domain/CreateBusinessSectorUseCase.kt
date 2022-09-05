package com.example.composetest.screens.lists.business_sector_list.domain

import com.example.composetest.screens.lists.business_sector_list.data.BusinessSector
import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorRepository
import javax.inject.Inject

class CreateBusinessSectorUseCase @Inject constructor(
    private val repository: BusinessSectorRepository
) {

    suspend fun execute(name: String, size: Int) {
        val businessSector = when (size) {
            0 -> BusinessSector(0, name, "#F9C80E")
            1 -> BusinessSector(0, name, "#F86624")
            2 -> BusinessSector(0, name, "#EA3546")
            3 -> BusinessSector(0, name, "#662E9B")
            else -> BusinessSector(0, name, "#43BCCD")
        }

        repository.insert(businessSector)
    }
}