package com.example.fastdo.stagnantapdaters

class StagnantShowModel {

    lateinit var id: String
    lateinit var stagnantName: String
    lateinit var stagnantType: String
    var stagnantAmount: Int = 0
    var unitPrice: Double = 0.0
    lateinit var stagnantExpiryDate: String
    var discount: Int = 0
    var unitType: String = ""
    lateinit var priceType: String
    lateinit var contactInfo: String


    constructor(
        id: String,
        stagnantName: String,
        stagnantType: String,
        stagnantAmount: Int,
        unitType: String,
        unitPrice: Double,
        priceType: String,
        stagnantExpiryDate: String,
        discount: Int,
        contactInfo: String

    ) {
        this.id = id
        this.stagnantName = stagnantName
        this.stagnantType = stagnantType
        this.stagnantAmount = stagnantAmount
        this.unitPrice = unitPrice
        this.stagnantExpiryDate = stagnantExpiryDate
        this.discount = discount
        this.unitType = unitType
        this.priceType = priceType
        this.contactInfo = contactInfo

    }

    constructor() {}

}