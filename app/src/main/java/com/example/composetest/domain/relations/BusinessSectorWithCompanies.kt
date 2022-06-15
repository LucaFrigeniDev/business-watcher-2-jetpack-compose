package com.example.composetest.domain.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.composetest.domain.entities.BusinessSector
import com.example.composetest.domain.entities.Company

data class BusinessSectorWithCompanies(
    @Embedded val businessSector: BusinessSector,
    @Relation(parentColumn = "id", entityColumn = "businessSectorId")
    val companies: MutableList<Company>
)
