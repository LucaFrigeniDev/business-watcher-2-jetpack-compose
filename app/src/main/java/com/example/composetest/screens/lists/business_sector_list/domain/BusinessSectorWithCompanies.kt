package com.example.composetest.screens.lists.business_sector_list.domain

import com.example.composetest.screens.lists.business_sector_list.data.BusinessSector
import com.example.composetest.screens.lists.company_list.data.Company

data class BusinessSectorWithCompanies(
    var businessSector: BusinessSector,
    var companies: MutableList<Company>
)
