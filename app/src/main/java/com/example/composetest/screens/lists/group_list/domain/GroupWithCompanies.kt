package com.example.composetest.screens.lists.group_list.domain

import com.example.composetest.screens.lists.company_list.data.Company
import com.example.composetest.screens.lists.group_list.data.Group

data class GroupWithCompanies(
    val group: Group,
    val companies: MutableList<Company>
)

