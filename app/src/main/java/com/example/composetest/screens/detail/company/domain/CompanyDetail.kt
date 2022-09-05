package com.example.composetest.screens.detail.company.domain

import com.example.composetest.screens.lists.company_list.data.Company

data class CompanyDetail(
    val company: Company,
    val groupName: String,
    val businessSectorName: String
)
