package com.example.fastdo.models

class Users {
    lateinit var id: String
    lateinit var pharmacyName: String
    lateinit var pharmacyManagerName: String
    lateinit var pharmacyOwnerName: String
    lateinit var city: String
    lateinit var center: String
    lateinit var licenceImage: String
    lateinit var commercialFileImage: String
    var phoneNumber: Long = 0L
    var telephoneNumber: Long = 0L
    lateinit var exactAddress: String
    lateinit var email: String
    lateinit var fcmToken: String

    constructor(
        id: String,
        pharmacyName: String,
        pharmacyManagerName: String,
        pharmacyOwnerName: String,
        city: String,
        center: String,
        licenceImage: String,
        commercialFileImage: String,
        phoneNumber: Long,
        telephoneNumber: Long,
        exactAddress: String,
        email: String,
        fcmToken: String
    ) {
        this.id = id
        this.pharmacyName = pharmacyName
        this.pharmacyManagerName = pharmacyManagerName
        this.pharmacyOwnerName = pharmacyOwnerName
        this.city = city
        this.center = center
        this.licenceImage = licenceImage
        this.commercialFileImage = commercialFileImage
        this.phoneNumber = phoneNumber
        this.telephoneNumber = telephoneNumber
        this.exactAddress = exactAddress
        this.email = email
        this.fcmToken = fcmToken
    }
    constructor(){}

}
/*
data class Users(
    val id: String = " ",
    val pharmacyName: String = " ",
    val pharmacyManagerName: String = " ",
    val pharmacyOwnerName: String = " ",
    val city: String = " ",
    val center: String = " ",
    val licenceImage: String = " ",
    val commercialFileImage: String = " ",
    val phoneNumber: Long = 0,
    val telephoneNumber: Long = 0,
    val exactAddress: String = " ",
    val email: String = " ",
    val fcmToken: String = " "

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel){
        writeString(id)
        writeString(pharmacyName)
        writeString(pharmacyManagerName)
        writeString(pharmacyOwnerName)
        writeString(city)
        writeString(center)
        writeString(licenceImage)
        writeString(commercialFileImage)
        writeLong(phoneNumber)
        writeLong(telephoneNumber)
        writeString(exactAddress)
        writeString(email)
        writeString(fcmToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }

}*/