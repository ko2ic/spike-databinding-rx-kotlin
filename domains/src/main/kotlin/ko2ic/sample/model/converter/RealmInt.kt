package jp.ra9.models.converter

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
class RealmInt(var value: Int? = null) : RealmModel