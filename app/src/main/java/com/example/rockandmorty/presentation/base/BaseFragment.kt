package com.example.rockandmorty.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<T : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {

    private var _binding: T? = null

    protected val binding: T get() = _binding!!

    private var _activity: BaseActivity<*>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, saved: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        _activity = (requireActivity() as BaseActivity<*>)
        return _binding?.root
    }

    fun showDialog(message: String) = _activity?.showDialog(message)

    fun showSnackBar(message: String) =
        Snackbar.make(_binding!!.root, message, Snackbar.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _activity = null
        _binding = null
    }
}