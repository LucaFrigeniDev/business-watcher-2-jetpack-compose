package com.example.composetest.screens.lists.company_list.domain

import com.example.composetest.screens.lists.business_sector_list.data.BusinessSector
import com.example.composetest.screens.lists.company_list.data.Company
import com.example.composetest.screens.lists.group_list.data.Group

data class CompanyItem(
    val company: Company,
    val businessSector: BusinessSector,
    var group: Group?
)