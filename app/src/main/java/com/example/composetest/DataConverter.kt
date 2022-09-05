package com.example.composetest

import androidx.room.TypeConverter
import com.example.composetest.screens.detail.company.domain.Customer
import com.example.composetest.screens.lists.company_list.data.Company
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun fromCompanyList(companyList: List<Company?>?): String? {
        if (companyList == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<Company?>?>() {}.type
        return gson.toJson(companyList, type)
    }

    @TypeConverter
    fun toCompanyList(companyString: String?): List<Company?>? {
        if (companyString == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<Company?>?>() {}.type
        return gson.fromJson<List<Company?>>(companyString, type)
    }

    @TypeConverter
    fun fromCustomerList(customerList: List<Customer?>?): String? {
        if (customerList == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<Customer?>?>() {}.type
        return gson.toJson(customerList, type)
    }

    @TypeConverter
    fun toCustomerList(customerString: String?): List<Customer?>? {
        if (customerString == null) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<Customer?>?>() {}.type
        return gson.fromJson<List<Customer?>>(customerString, type)
    }
}