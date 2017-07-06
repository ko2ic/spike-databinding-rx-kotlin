package jp.ra9.ui.controllers

import ko2ic.sample.ui.viewmodel.ViewModel

/**
 * [ViewModel]を保持していることを表すインターフェイス.
 * @param <T> 対象のViewModelの具象クラスを指定する
 */
interface ViewModelHolder<T> where T : ViewModel {

    var viewModel: T
}