package jp.ra9.ui.controllers

import android.app.Fragment

/**
 * [Fragment]を保持していることを表すインターフェイス.
 * @param <T> 対象のフラグメントの具象クラスを指定する
 */
interface FragmentHolder<T> where T : Fragment {

    var fragment: T
}