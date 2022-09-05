package com.example.composetest.screens.creation.company.ui

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetest.screens.creation.company.domain.CheckBeforeCompanyCreationUseCase
import com.example.composetest.screens.creation.company.domain.CreateCompanyUseCase
import com.example.composetest.screens.lists.business_sector_list.data.BusinessSectorRepository
import com.example.composetest.screens.lists.group_list.data.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyCreationViewModel
@Inject constructor(
    private val groupRepository: GroupRepository,
    private val sectorRepository: BusinessSectorRepository,
    private val createCompanyUseCase: CreateCompanyUseCase,
    private val checkBeforeCompanyCreationUseCase: CheckBeforeCompanyCreationUseCase
) : ViewModel() {

    val error: StateFlow<String> = checkBeforeCompanyCreationUseCase.error

    private val _sectorsNames = MutableStateFlow(listOf<String>())
    val sectorsNames: StateFlow<List<String>> = _sectorsNames

    private val _groupsNames = MutableStateFlow(listOf<String>())
    val groupsNames: StateFlow<List<String>> = _groupsNames

    init {
        viewModelScope.launch {

            sectorRepository.businessSectorList.collect {
                val names: MutableList<String> = mutableListOf()

                it.forEach { businessSector ->
                    names.add(businessSector.name)
                }

                _sectorsNames.value = names.sorted()
            }
        }

        viewModelScope.launch {

            groupRepository.groupList.collect {
                val names: MutableList<String> = mutableListOf()

                it.forEach { group ->
                    names.add(group.name)
                }

                names.sorted()
                names.add(0, "Independent")

                _groupsNames.value = names
            }
        }
    }

    private val name = MutableStateFlow("")
    private val businessSector = MutableStateFlow(0L)
    private val street = MutableStateFlow("")
    private val city = MutableStateFlow("")
    private val postalCode = MutableStateFlow("")
    private val turnover = MutableStateFlow(0)
    private val description = MutableStateFlow("")

    private val _group = MutableStateFlow("Independent")
    val group: StateFlow<String> = _group

    private val _groupLogo = MutableStateFlow("")
    val groupLogo: StateFlow<String> = _groupLogo

    private val _independentLogoUri = MutableStateFlow<Uri?>(null)
    val independentLogoUri: StateFlow<Uri?> = _independentLogoUri

    fun setName(companyName: String) {
        name.value = companyName
    }

    fun setPostalCode(companyPC: String) {
        postalCode.value = companyPC
    }

    fun setCity(companyCity: String) {
        city.value = companyCity
    }

    fun setAddress(companyAddress: String) {
        street.value = companyAddress
    }

    fun setTurnover(companyTurnover: Int) {
        turnover.value = companyTurnover
    }

    fun setDescription(companyDescription: String) {
        description.value = companyDescription
    }

    fun setIndependentLogo(uri: Uri?) {
        _independentLogoUri.value = uri
    }

    fun setGroup(companyGroup: String) = viewModelScope.launch {
        if (companyGroup == "Independent") {
            _group.value = "Independent"
            _groupLogo.value = ""
        } else {
            groupRepository.getGroupId(companyGroup).collect { id ->
                _group.value = id.toString()

                groupRepository.getGroupLogo(id).collect { logo ->
                    _groupLogo.value = logo
                    _independentLogoUri.value = null
                }
            }
        }
    }

    fun setSector(companyBusinessSector: String) = viewModelScope.launch {
        sectorRepository.getBusinessSectorId(companyBusinessSector).collect {
            businessSector.value = it
        }
    }

    fun checkAndCreate(context: Context) = viewModelScope.launch {

        checkBeforeCompanyCreationUseCase.execute(
            context,
            name.value,
            businessSector.value,
            street.value,
            city.value,
            postalCode.value,
            turnover.value,
            description.value,
            groupLogo.value,
            independentLogoUri.value
        )

        if (checkBeforeCompanyCreationUseCase.error.value == "company registered")
            createCompanyUseCase.execute(
                name.value,
                businessSector.value,
                group.value,
                checkBeforeCompanyCreationUseCase.companyAddress.value,
                turnover.value,
                description.value,
                independentLogoUri.value,
                groupLogo.value
            )

    }
}