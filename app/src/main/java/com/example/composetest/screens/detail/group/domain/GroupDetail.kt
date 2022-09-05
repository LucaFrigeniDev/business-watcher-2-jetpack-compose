package com.example.composetest.screens.detail.group.domain

import com.example.composetest.screens.lists.company_list.domain.CompanyItem
import com.example.composetest.screens.lists.group_list.data.Group

data class GroupDetail(
    val group: Group,
    val rank: String,
    val turnover: String,
    val numberOfCompanies: String,
    val businessSectorStats: List<GroupBusinessSectorStats>,
    val companies: List<CompanyItem>
)
