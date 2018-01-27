package jp.ra9.models.converter

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
class RealmString(var value: String = "") : RealmModel