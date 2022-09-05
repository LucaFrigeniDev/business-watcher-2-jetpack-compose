package com.example.composetest.screens.creation.company.domain

import android.net.Uri
import android.util.Log
import com.example.composetest.screens.detail.company.domain.CompanyAddress
import com.example.composetest.screens.lists.company_list.data.Company
import com.example.composetest.screens.lists.company_list.data.CompanyRepository
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.inject.Inject

class CreateCompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {

    suspend fun execute(
        name: String,
        businessSector: Long,
        group: String,
        companyAddress: CompanyAddress,
        turnover: Int,
        description: String,
        independentLogoUri: Uri?,
        groupLogo: String
    ) {
        Log.e("execute: ", companyAddress.latitude.toString())

        val groupId: Long? =
            if (group == "Independent") null
            else group.toLong()

        val uuid: String = UUID.randomUUID().toString()

        val logo =
            if (groupId == null) {
                FirebaseStorage
                    .getInstance()
                    .getReference(uuid)
                    .putFile(independentLogoUri!!)

                uuid

            } else
                groupLogo

        companyRepository.insert(
            Company(
                0,
                name,
                businessSector,
                groupId,
                companyAddress.city,
                companyAddress.postalCode,
                companyAddress.street,
                companyAddress.latitude,
                companyAddress.longitude,
                turnover,
                listOf(),
                description,
                logo
            )
        )
    }
}