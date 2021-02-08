package com.example.fastdo.models

class Stagnants {
    lateinit var id: String
    lateinit var stagnantName: String
    lateinit var stagnantType: String
    var stagnantAmount: Int = 0
    var unitPrice: Double = 0.0
    lateinit var priceType: String
    lateinit var stagnantExpiryDate: String
    var discount: Int = 0
    lateinit var expressYourProduct: String

    constructor(
        id: String,
        stagnantName: String,
        stagnantType: String,
        stagnantAmount: Int,
        unitPrice: Double,
        priceType: String,
        stagnantExpiryDate: String,
        discount: Int,
        expressYourProduct: String
    ) {
        this.id = id
        this.stagnantName = stagnantName
        this.stagnantType = stagnantType
        this.stagnantAmount = stagnantAmount
        this.unitPrice = unitPrice
        this.priceType = priceType
        this.stagnantExpiryDate = stagnantExpiryDate
        this.discount = discount
        this.expressYourProduct = expressYourProduct

    }

    constructor() {}
}


/*
: Parcelable {
    constructor(parcel: Parcel) : this(
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readInt(),
    parcel.readDouble(),
    parcel.readString()!!,
    parcel.readString()!!,
    parcel.readInt(),
    parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeString(stagnantName)
        writeString(stagnantType)
        writeInt(stagnantAmount)
        writeDouble(unitPrice)
        writeString(priceType)
        writeString(stagnantExpiryDate)
        writeInt(discount)
        writeString(expressYourProduct)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stagnants> {
        override fun createFromParcel(parcel: Parcel): Stagnants {
            return Stagnants(parcel)
        }

        override fun newArray(size: Int): Array<Stagnants?> {
            return arrayOfNulls(size)
        }
    }
}*/
/*
class Stagnants(
    stagnantName: String,
    stagnantType: String,
    stagnantAmount: Int,
    unitPrice: Double,
    priceType: String,
    stagnantExpiryDate: String,
    discount: Int,
    expressYourProduct: String
) {
    var stagnantName: String? = null
    var stagnantType: String? = null
    var stagnantAmount: Int? = null
    var unitPrice: Double? = null
    var priceType: String? = null
    var stagnantExpiryDate: String? = null
    var discount: Int? = null
    var expressYourProduct: String? = null
}*/