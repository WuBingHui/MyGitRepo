package com.anthony.wu.my.git.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.adapter.UserAdapter
import com.anthony.wu.my.git.base.BaseDialogFragment
import com.anthony.wu.my.git.dto.Status
import com.anthony.wu.my.git.extension.addTo
import com.anthony.wu.my.git.viewmodel.GitViewModel
import com.anthony.wu.my.git.widget.CustomLoadingDialog
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_dialog_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchDialogFragment : BaseDialogFragment() {

    private var userAdapter: UserAdapter? = null

    private val viewModel by viewModel<GitViewModel>()


    private var customLoadingDialog: CustomLoadingDialog? = null

    private val userCallBack = BehaviorSubject.create<String>()

    fun getUserCallBack(): Observable<String> = userCallBack

    companion object {

        fun newInstance(): SearchDialogFragment {

            return SearchDialogFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.let { dialog ->

            val dm = DisplayMetrics()
            //取得視窗屬性
            activity?.windowManager?.defaultDisplay?.getMetrics(dm)

//            視窗的寬度
//            val screenWidth = dm.widthPixels

            //視窗高度
            val screenHeight = dm.heightPixels

            //去掉標題欄
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.window?.let { window ->

                //去掉默認padding
                window.decorView.setPadding(0, 0, 0, 0)
                val layoutParams = window.attributes
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height = (screenHeight * 0.64).toInt()
                //設置dialog在底部
                layoutParams.gravity = Gravity.BOTTOM
                //设置dialog的动画
                layoutParams.windowAnimations = R.style.BottomDialogAnimation
                window.attributes = layoutParams
                window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            }
        }

//        isCancelable = false

        return inflater.inflate(R.layout.fragment_dialog_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initViewModel()

    }

    private fun  initView(){

        context?.let {context ->

            customLoadingDialog = CustomLoadingDialog.newInstance()

            userAdapter = UserAdapter(context)
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            usersRecyclerView.layoutManager = linearLayoutManager
            usersRecyclerView.adapter = userAdapter

            searchImg.setOnClickListener {

                if(searchUserEditText.text.toString().isNotEmpty()){

                    customLoadingDialog?.show(childFragmentManager, customLoadingDialog!!.tag)

                    viewModel.getUserList(searchUserEditText.text.toString())

                }else{

                    Toast.makeText(context,getString(R.string.not_empty), Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun initViewModel(){

        viewModel.onUserList.observe(viewLifecycleOwner, Observer {
                dto ->

            when (dto.status) {
                Status.SUCCESS -> {

                    dto.data?.let {

                        if(it.items.isNotEmpty()){
                            usersRecyclerView.visibility = View.VISIBLE
                            noUserDataLabel.visibility = View.GONE
                            userAdapter?.update(it.items)
                        }else{
                            usersRecyclerView.visibility = View.GONE
                            noUserDataLabel.visibility = View.VISIBLE
                        }

                    }

                }
                Status.ERROR -> {
                    usersRecyclerView.visibility = View.GONE
                    noUserDataLabel.visibility = View.VISIBLE
                    Toast.makeText(context, dto.message, Toast.LENGTH_SHORT).show()
                }
            }

            customLoadingDialog?.dismissAllowingStateLoss()

        })

        userAdapter?.getUserCallBack()?.subscribe { user ->

            userCallBack.onNext(user)

            dismissAllowingStateLoss()

        }?.addTo(compositeDisposable)

    }
}
