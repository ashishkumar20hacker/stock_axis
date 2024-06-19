package com.example.stockaxistask.DataSource

data class ApiModel(
    val data: List<Data>
)

data class Data(
    val AlertMsg: String,
    val ComboOffer: String,
    val CouponCode: String,
    val PAmount: String,
    val PCode: String,
    val PDescription: String,
    val PDuration: String,
    val PKGName: String,
    val PTotaAmount: String,
    val SrNo: Int
)